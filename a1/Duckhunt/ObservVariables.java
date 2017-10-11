
public class ObservVariables {
  float[][] alpha;
  float[][] beta;
  float[][] gamma;
  float[][][] diGamma;
  float[] c;
  int T;
  int N;
  int[] O;

  ObservVariables(int[] O,int n){
    this.T = O.length;
    this.N = n;
    this.gamma = new float[T][N];
    this.diGamma = new float[T][N][N];
    this.alpha=new float[T][N];
    this.beta=new float[T][N];
    this.c = new float[T];
    this.O = O;


  }
  void setAlpha(float[][] Ain){
    alpha = Ain;
  }
  void setBeta(float[][] Bin){
    beta = Bin;
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

  int getT(){
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
}
