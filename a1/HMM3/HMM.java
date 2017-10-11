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


//ALPHA


	class AlphaDelivery{
		float[][] alpha;
		float[] c;
		public AlphaDelivery(float[] Cin,float[][] Ain){
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

	float alphaPass(ObservVariables ObsV){
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
			alpha[0][i] = c[0]*alpha[0][i]
		}

		// compute αt(i)

    for(int t=1;t<T;t++){ //2.13
				c[t]=0
      	for(int i=0;i<N;i++){
					alpha[t][i] = 0;
      		for(int j=0;j<N;j++){
						alpha[t][i]+=A.getValue(j,i)*alpha[t-1][j];
      		}
      		alpha[t][i]=B.getValue(i,ObsV.O[t])*alpha[t][i];
					c[t]+=alpha[t][i]
      	}

    }
		c[t] = 1/c[t]
    for(int j=0;j<N;j++){//2.4
      	alpha[t][i] = c[t]*alpha[t][i];
    }

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
		for(int t=T-2;t>0;i--){ //check bounds
			for(int i=0;i<N;i++){
				beta[t][i]=0;
				for(int j=0;j<N;j++){
					beta[t][i]=beta[t][i]+A.getValue(i,j)*B.getValue(j,O[t+1])*beta[t+1][j]; //check indices
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

public static void updateGammaAndDiGamma (ObservVariables ObsV){
	int T = ObsV.O.length;
	float[][] alpha = ObsV.alpha;
	float[][] beta = ObsV.beta;
	float[][] gamma = ObsV.gamma;
	float[][][] diGamma = ObsV.diGamma;
	//>in alpha, beta, A, B and O
	for (t = 0, t < T-1, t++){
		denom = 0
		for (i = 0, i < N, i++){
			for (j = 0, j < N, j++){
				denom = denom + alpha[t][i]*A.getValue(i,j)*B.getValue(j,O[t+1])*beta[t+1][j]
			}
		}
		for (i = 0, i < N, i++){
			gamma[t][i] = 0;
			for (j = 0, j < N, j++){
				diGamma[t][i][j] = alpha[t][i]*A.getValue(i,j)*B.getValue(j,O[t+1])*beta[t+1][j]/denom;
				gamma[t][i] = gamma[t][i] + diGamma[t][i][j];
			}
		}
	}
	GammaDelivery returnPackage = new GammaDelivery(gamma,diGamma);
	return returnPackage;
}
}
