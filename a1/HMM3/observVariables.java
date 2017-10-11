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
  void setAlpha(float[][] Ain){
    alpha = Ain;
  }
  void setC(float[] Cin){
    c = Cin;
  }
  void setGamma(float[][] gin){
    gamma = gin;
  }
  void setDiGamma(float[][][] dgin){
    diGamma = dgin;
  }

  int[] getO(){
    return O;
  }

  int T getT(){
    return T;
  }

  float[][] getGamma(){
    return gamma;
  }
  float[][][] getDiGamma(){
    return diGamma;
  }

  float[][] getAlpha(){
    return alpha;
  }
  float[] getC(){
    return c;
  }
