package a1;

public class Matriximo {
  int n;
  int m;
  float[][] data;

  Matriximo(int first, int second){
    n = first;
    m = second;
    data = new float[n][m];
  }

  void setValue(int i,int j,float value){
    data[i][j]=value;
  }

  float getValue(int i,int j){
    return data[i][j];
  }

  Matriximo(String all){
    String[] splittedString = all.split(" ");
    n = Integer.parseInt(splittedString[0]);
    m = Integer.parseInt(splittedString[1]);

    data = new float[n][m];

    for (int i=0;i<n;i++){
      for (int j=0;j<m;j++){

        data[i][j] = Float.parseFloat(splittedString[i*m+j + 2]);
      }
    }
  }

  public String toString(){
    String output = ("" + n + " " + m);
    for (int i=0;i<n;i++){
      for (int j=0;j<m;j++){
        output = output + " " + data[i][j];
      }
    }
    return output;
  }

  public Matriximo multWith(Matriximo otherMat){
    Matriximo newMat = new Matriximo(n,otherMat.m);
    if (m == otherMat.n){

      for (int i=0;i<n;i++){
        for (int j=0;j<otherMat.m;j++){
          float sum = 0;
          for (int k=0;k<m;k++){
            sum = sum + data[i][k]*otherMat.getValue(k,j);
          }
          newMat.setValue(i,j,sum);
        }
      }

      //mult
    }
    else{
      System.out.println("Nope - dim error");
    }
    return newMat;
  }
  }
