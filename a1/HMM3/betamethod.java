	float[][] betaPass(int[] O){
		int T = O.length;
		float[][] beta=new float[T][N];

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
