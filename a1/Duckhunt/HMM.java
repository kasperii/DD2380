public class HMM {

	//HMM parameters aka Lambdas
	Matriximo A;
	Matriximo B;
	Matriximo PI;

	//Lambda dimensions
	int N;
	int M;

	public HMM(Matriximo Atmp,Matriximo Btmp,Matriximo PItmp){
		A=Atmp;
		B=Btmp;
		PI=PItmp;
		N=PI.m;
		M=B.m; //Kontrollera att detta är rätt.
	}


	void reEstimatePi (float[][] gamma){
  // re-estimate π
  	for (int i = 0; i < N; i++){
    	PI.setValue(0,i, gamma[0][i]);
  		}
	}

	void reEstimateA (float[][] gamma, float[][][] diGamma,int T){
  // re-estimate A
	  for (int i = 0; i < N; i++){
	    for (int j = 0; j < N; j++){
	      float numer = 0;
	      float denom = 0;
	      for (int t = 0; t < T-1; t++){
	        numer = numer + diGamma[t][i][j];
	        denom = denom + gamma[t][i];
	      }
	      A.setValue(i,j,numer/denom);
	    }
	  }
	}


	void reEstimateB (float[][] gamma, float[][][] diGamma,int T, int[] O){
  // re-estimate B
	  for (int i = 0; i < N; i++){
	    for (int j = 0; j < M; j++){
	      float numer = 0;
	      float denom = 0;
	      for (int t = 0; t < T; t++){
	        if(O[t] == j){
	          numer = numer + gamma[t][i];
	        }
	        denom = denom + gamma[t][i];
	      }
	      B.setValue(i,j,numer/denom);
	    }
	  }
	}




	public void learn(int[] observationSequence, int maxIterations){

		ObservVariables ObsV=new ObservVariables(observationSequence,N);

		int iteration = 0;

		double oldLogProb=-99999999;
		double logProb = -9999999;

		while(iteration<maxIterations && logProb>oldLogProb){
			AlphaDelivery alphaAndconstants = alphaPass(ObsV);
			ObsV.setAlpha(alphaAndconstants.alpha);
			ObsV.setC(alphaAndconstants.c);
			float[][] beta=betaPass(ObsV);
			ObsV.setBeta(beta);
			GammaDelivery gammaAndDiGamma=GammaAndDiGammaPass(ObsV);
			ObsV.setGamma(gammaAndDiGamma.getGamma());
			ObsV.setDiGamma(gammaAndDiGamma.getDiGamma());

			reEstimatePi(ObsV.getGamma());
			reEstimateA(ObsV.getGamma(), ObsV.getDiGamma(), ObsV.getT());
			reEstimateB(ObsV.getGamma(), ObsV.getDiGamma(), ObsV.getT(),ObsV.getO());



			oldLogProb=logProb;
			logProb = 0;
			float[] c = ObsV.getC();
				for(int i=0;i<ObsV.T;i++){
					logProb = logProb + Math.log(c[i]);
				}
				logProb = (-1)*logProb;
			iteration+=1;
		}

	}










//ALPHA

	AlphaDelivery alphaPass(ObservVariables ObsV){
		int T = ObsV.O.length;
		float[] c = ObsV.c;
		float[][] alpha = ObsV.alpha;

		// compute α0(i)

		c[0] = 0;

    for(int i=0;i<N;i++){ //2.8
      	alpha[0][i]=B.getValue(i,ObsV.O[0])*PI.getValue(0,i);
				c[0]+=alpha[0][i];
    }

			// scale the α0(i) c0 = 1/c0
		for(int i=0;i<N;i++){
			alpha[0][i] = c[0]*alpha[0][i];
		}

		// compute αt(i)

    for(int t=1;t<T;t++){ //2.13
				c[t]=0;
      	for(int i=0;i<N;i++){

					alpha[t][i] = 0;
      		for(int j=0;j<N;j++){
						alpha[t][i]+=A.getValue(j,i)*alpha[t-1][j];
      		}
      		alpha[t][i]=B.getValue(i,ObsV.O[t])*alpha[t][i];
					c[t]+=alpha[t][i];
      	}


			c[t] = 1/c[t];
	    for(int j=0;j<N;j++){//2.4
	      	alpha[t][j] = c[t]*alpha[t][j];
	    }
		}
//!!!!!!!!!
		AlphaDelivery returnPackage = new AlphaDelivery(c,alpha);
    return returnPackage;
	}


//BETA


	float[][] betaPass(ObservVariables ObsV){
		int T = ObsV.O.length;
		float[] c = ObsV.c;
		float[][] beta = ObsV.beta;


		//initialize last timestep of beta to 1 scaled by c(t-1)
		for(int i=0;i<N;i++){
			beta[T-1][i]=c[T-1];
		}

		//betaPass
		for(int t=T-2;t>0;t--){ //check bounds
			for(int i=0;i<N;i++){
				beta[t][i]=0;
				for(int j=0;j<N;j++){
					beta[t][i]=beta[t][i]+A.getValue(i,j)*B.getValue(j,ObsV.O[t+1])*beta[t+1][j]; //check indices
				}
			beta[t][i]=c[t]*beta[t][i];
			}
		}

		return beta;
	}


//GAMMA


class GammaDelivery{
	float[][] gamma;
	float[][][] diGamma;
	public GammaDelivery(float[][] gin,float[][][] dgin){
		gamma = gin;
		diGamma = dgin;
	}
	void setGamma(float[][] gin){
		gamma = gin;
	}
	void setDiGamma(float[][][] dgin){
		diGamma = dgin;
	}
	float[][] getGamma(){
		return gamma;
	}
	float[][][] getDiGamma(){
		return diGamma;
	}
}

public GammaDelivery GammaAndDiGammaPass(ObservVariables ObsV){
	int T = ObsV.O.length;
	int[] O = ObsV.O;
	float[][] alpha = ObsV.alpha;
	float[][] beta = ObsV.beta;
	float[][] gamma = ObsV.gamma;
	float[][][] diGamma = ObsV.diGamma;
	//>in alpha, beta, A, B and O
	for (int t = 0; t < T-1; t++){
		float denom = 0;
		for (int i = 0; i < N; i++){
			for (int j = 0; j < N; j++){
				denom = denom + alpha[t][i]*A.getValue(i,j)*B.getValue(j,O[t+1])*beta[t+1][j];
			}
		}
		for (int i = 0; i < N; i++){
			gamma[t][i] = 0;
			for (int j = 0; j < N; j++){


				diGamma[t][i][j] = alpha[t][i]*A.getValue(i,j)*B.getValue(j,O[t+1])*beta[t+1][j]/denom;
				gamma[t][i] = gamma[t][i] + diGamma[t][i][j];
			}
		}
	}
	GammaDelivery returnPackage = new GammaDelivery(gamma,diGamma);
	return returnPackage;
	}


public class AlphaDelivery{
	float[][] alpha;
	float[] c;
	AlphaDelivery(float[] Cin,float[][] Ain){
		c = Cin;
		alpha = Ain;
	}
	void setAlpha(float[][] Ain){
		alpha = Ain;
	}
	void setC(float[] Cin){
		c = Cin;
	}

	float[][] getAlpha(){
		return alpha;
	}
	float[] getC(){
		return c;
	}
}
}
