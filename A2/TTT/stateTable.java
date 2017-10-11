


//if a state has already been visited, it

import java.util.Hashtable;

class stateTable{

	Hashtable<int[],Integer> xTable;
	Hashtable<int[],Integer> oTable;

	stateTable(int startSize){
		xTable=new Hashtable<int[],Integer>(startSize);
		oTable=new Hashtable<int[],Integer>(startSize);
	}

	void put(int playerIdx, int[] st, int value){
		if(playerIdx==1){
			xTable.put(state,value);
		}
		else{
			oTable.put(state,value);
		}
	}

	int get( int playerIdx,int[] st){
		if(playerIdx==1){
			return xTable.get(state);
		}
		else{
			return oTable.get(state);
		}
	}

	boolean contains(int playerIdx,int[] st){
		if(playerIdx==1){
			return xTable.containsKey(state);
		}
		else{
			return oTable.containsKey(state);
		}
	}
	int[][][] symetries(int[][] state){
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
		//mirror
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				states[0][i][3-j] = states[3][i][j];
			}
		}

		//rotate the normal and the mirrored

		for(int a=0;a<3;a++){
			for(int i=0; i<4; i++){
	        for(int j=4; j>=0; j--){
	            states[a][i][j] = states[a+1][j][i];
							states[a+4][i][j] = states[a+5][j][i];

	        }
	    }
		}

		return states;
	}


}
