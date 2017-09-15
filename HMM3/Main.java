
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

public class Main {
  public static void main(String args[]) throws IOException {
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
    String input1 = stdin.readLine();
    String input2 = stdin.readLine();
    String input3 = stdin.readLine();
    String input4 = stdin.readLine();

    Matriximo A = new Matriximo(input1);
    Matriximo B = new Matriximo(input2);
    Matriximo PI = new Matriximo(input3);
    String[] observatList = input4.split(" ");
    int T = Integer.parseInt(observatList[0]); //number of observations
    int[] O = new int[T];
    for (int i=0;i<T;i++){
      O[i] = Integer.parseInt(observatList[i+1]);
    }
    //aPass alpha = new aPass(A,B,PI,O);
    //System.out.println(alpha.calcAlpha());
    //bPass beta = new bPass(A,B,PI,O);
    //System.out.println(beta.calcBeta());


//1. Initialize λ = (A, B, π) (2.38) - this is given



//2.Compute all αt(i),βt(i),γt(i,j),γt(i)values (2.39)
//aPass alpha = new aPass(A,B,PI,O);

//γt(i,j) = P(xt = qi,xt+1 = qj |O,λ).
//γt(i) = αt(i)βt(i) / P(O|λ)

//3. Re-estimate λ = (A, B, π) (2.40)
//4. Repeat from 2. until convergence
   }

}
