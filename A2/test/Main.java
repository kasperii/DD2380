import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import java.io.BufferedWriter;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * Starts the game for one client.
 *
 * Note:
 *      Use the verbose flag for outputting game information.
 *      Use the fast flag for using 100ms move deadline instead of 1000ms.
 *      Use the init flag if you want this client to initialize the game, that
 *      is, send a starting board without moving for the other client to move
 *      first.
 */
public class Main {
    public static void main(String[] args) throws IOException {
      int[][] state = new int[4][4];
      state[0][0] = 1;
      state[1][0] = 2;

      int[][][] states = symetries(state);


      for(int a=0;a<8;a++){
  			for(int i=0; i<4; i++){
  	        for(int j=0; j<4; j++){
  	            System.out.print(states[a][i][j] + " ");
  	        }
            System.out.println();
  	    }
        System.out.println();
  		}
    }
    static int[][][] symetries(int[][] state){
  /**
  xo.. .... .... ...x x... .... .... ..ox
  .... .... .... ...o o... .... .... ....
  .... o... .... .... .... .... ...o ....
  .... x... ..ox .... .... xo.. ...x ....
  **/
  		int[][][] states = new int[8][4][4];
  		//add first
  		for(int i=0; i<4; i++){
  			for(int j=0; j<4; j++){
  				states[0][i][j] = state[i][j];
  			}
  		}
      System.out.println(states[0][0][0]);
  		//mirror
  		for(int i=0; i<4; i++){
  			for(int j=0; j<4; j++){
  				states[3][i][j] = states[0][i][3-j];
  			}
  		}

  		//rotate the normal and the mirrored

  		for(int a=0;a<3;a++){
  			for(int i=0; i<4; i++){
  	        for(int j=3; j>=0; j--){

  	            states[a+1][i][j] = states[a][j][i];
  							states[a+5][i][j] = states[a+4][j][i];

  	        }
  	    }
  		}

  		return states;
  	}


}
