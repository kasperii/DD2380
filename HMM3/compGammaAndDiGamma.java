public static void updateGammaAndDiGamma (int[][] gamma){

for (t = 0, t < T-1, t++){
  denom = 0
  for (i = 0, i < N, i++){
    for (j = 0, j < N, j++){
      denom = denom + αt(i)aijbj(Ot+1)βt+1(j)
    }
  }
  for (i = 0, i < N, i++){
    gamma[t][i] = 0;
    for (j = 0, j < N, j++){
      diGamma[t][i][j]=
      //γt(i,j) = (αt(i)aijbj(Ot+1)βt+1(j))/denom
      γt(i) = γt(i) + γt(i, j)
    }
  }
}
