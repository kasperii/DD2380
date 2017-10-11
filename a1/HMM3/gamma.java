//OLD!

public class gamma{
  //this stores the gamma and the diGamma values

  int[][] gamma;
  int[][][] diGamma;
  int T;
  int N;

  gamma(int n,int t){
    T = t;
    N = n;
    gamma = new int[T][N-1];
    diGamma = new int[T][N-2][N-1];
  }

  public static void updateGammaAndDiGamma (Matriximo A, Matriximo B, int[] O, float[][] alpha, float[][] beta){
    //>in alpha, beta, A, B and O
    for (t = 0, t < T-1, t++){
      denom = 0
      for (i = 0, i < N, i++){
        for (j = 0, j < N, j++){
          denom = denom + alpha[t][i]*A.getValue(i,j)*b.getValue(j,O[t+1])*beta[t+1][j]
        }
      }
      for (i = 0, i < N, i++){
        gamma[t][i] = 0;
        for (j = 0, j < N, j++){
          diGamma[t][i][j] = alpha[t][i]*A.getValue(i,j)*b.getValue(j,O[t+1])*beta[t+1][j]/denom;
          gamma[t][i] = gamma[t][i] + diGamma[t][i][j];
        }
      }
    }
  }
}
