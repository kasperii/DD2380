public class ObservVariables {
  float[][] alpha;
  float[][] beta;
  float[][] gamma;
  float[][][] diGamma;
  float[] c;
  int T;
  int N;
  int[] O;

  ObservVariables(int n,int t){
    T = t;
    N = n;
    gamma = new float[T-1][N-1];
    diGamma = new float[T-1][N-2][N-1];
    alpha=new float[T-1][N];
    beta=new
    c=new float[T];
    O = new int[T-1];

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
