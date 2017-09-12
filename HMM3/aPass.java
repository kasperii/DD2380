
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;


// Xt = xi,i ∈ {1,2,...N} : N possible hidden states Ot=ok,k∈{1,2,...K}:K possibleobservations
// πi = P (X1 = i ) : the initial probability vector π ∈ R1,N with elements πi
// ai,j =P(Xt+1=j|Xt=i):thetransitionprobabilitymatrixA∈RN,N withelementsai,j
// bi (k) = P(Ot = k|Xt = i) : the observation probability matrix B ∈ RN,K with elements bi,k = bi (k)

public class aPass {

    Matriximo A;
    Matriximo B;
    Matriximo PI;
    int[] O;
    int N;
    int T;

    aPass(Matriximo Ain, Matriximo Bin,Matriximo PIin, int[] Oin){
      A = Ain;
      B = Bin;
      PI = PIin;
      O = Oin;
      N=PI.m;
      T = O.length;
    }

    float calcAlpha(){

      float[][] alpha=new float[T][N];

      for(int i=0;i<N;i++){ //2.8
      	alpha[0][i]=B.getValue(i,O[0])*PI.getValue(0,i);
      	//System.out.println("<"+alpha[0][i]);

      }

      for(int t=1;t<T;t++){ //2.13
      	//System.out.println("t is "+t);
      	for(int i=0;i<N;i++){
      		float alphaProductSum=0;
      		for(int j=0;j<N;j++){
      			alphaProductSum+=A.getValue(j,i)*alpha[t-1][j];
      		}
      		alpha[t][i]=B.getValue(i,O[t])*alphaProductSum;
      		//System.out.println(alpha[t][i]);
      	}

      }

      float P=0;

      for(int j=0;j<N;j++){//2.4
      	P+=alpha[T-1][j];
      }

      return P;
    }
}
