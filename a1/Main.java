package a1;

/* Labb 2 i DD1352 Algoritmer, datastrukturer och komplexitet    */
/* Se labbanvisning under kurssidan http://www.csc.kth.se/DD1352 */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se*/

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Main {
  public static void main(String args[]) throws IOException {
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
    String input1 = stdin.readLine();
    String input2 = stdin.readLine();
    String input3 = stdin.readLine();

    Matriximo transition = new Matriximo(input1);
    Matriximo emission = new Matriximo(input2);
    Matriximo initial = new Matriximo(input3);
    Matriximo standbyMatrix = initial.multWith(transition);
    System.out.println(standbyMatrix.multWith(emission));

   }
}
