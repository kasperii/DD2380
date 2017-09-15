public static void reEstimatePi (int[] PI, int[][] gamma){
  // re-estimate π
  for (i = 0, i < N, i++){
    PI[i] = gamma[0][i];
  }
}

public static void reEstimateA (int[][] gamma, int[][][] diGamma,int T){
  // re-estimate A
  for (i = 0, i < N, i++){
    for (j = 0, j < N, j++){
      int numer = 0;
      int denom = 0;
      for (t = 0, t < T-1, t++){
        numer = numer + diGamma[t][i][j];
        denom = denom + gamma[t][i]
      }
      A.setValue(i,j,numer/denom);
    }
  }
}

public static void reEstimateB (int[][] gamma, int[][][] diGamma,int T){
  // re-estimate B
  for (i = 0, i < N, i++){
    for (j = 0, j < M, j++){
      int numer = 0;
      int denom = 0;
      for (t = 0, t < T, t++){
      for t = 0 to T − 1
        if(O[t] == j){
          numer = numer + gamma[t][i];
        }
        denom = denom + gamma[t][i];
      }
      B.setValue(i,j,numer/denom);
    }
  }
}
