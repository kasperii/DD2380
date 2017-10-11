

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;



// Xt = xi,i ∈ {1,2,...N} : N possible hidden states Ot=ok,k∈{1,2,...K}:K possibleobservations
// πi = P (X1 = i ) : the initial probability vector π ∈ R1,N with elements πi
// ai,j =P(Xt+1=j|Xt=i):thetransitionprobabilitymatrixA∈RN,N withelementsai,j
// bi (k) = P(Ot = k|Xt = i) : the observation probability matrix B ∈ RN,K with elements bi,k = bi (k)

public class MainHmm {
	public static void main(String args[]) throws IOException {

			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));

			//read input
		    String input1 = stdin.readLine();
		    String input2 = stdin.readLine();
		    String input3 = stdin.readLine();
		    String input4 = stdin.readLine();

		    //convert input into appropriate types.

		    Matriximo A = new Matriximo(input1);
		    Matriximo B = new Matriximo(input2);
		    Matriximo PI = new Matriximo(input3);

		    String[] observatList = input4.split(" ");
		    int T = Integer.parseInt(observatList[0]); //number of observations
		    int[] O = new int[T];
		    for (int i=0;i<T;i++){
		      O[i] = Integer.parseInt(observatList[i+1]);
		    }

		    //create HMM objet from lambda.

		   	HMM mark=new HMM(A,B,PI);

		   	//update lambdas

		    mark.learn(O,10000);

		    //output A and B matrix
				String Output = mark.A.toString();
				String Output2 = mark.B.toString();
		  	System.out.println(Output);
		  	System.out.println(Output2);
		  }
}
