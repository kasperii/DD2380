import java.util.*;


public class Player {
		/**
		 * Performs a move
		 *
		 * @param gameState
		 *            the current state of the board
		 * @param deadline
		 *            time before which we must have returned
		 * @return the next state the board is in after our move
		 */
		stateTable table;


		public GameState play(final GameState gameState, final Deadline deadline) {
				
				//System.err.println("X-players evaluation:"+evaluate(gameState,1));
				//System.err.println("O-players evaluation:"+evaluate(gameState,2));

				table=new stateTable(16*16*16*16);

				Vector<GameState> nextStates = new Vector<GameState>();
				gameState.findPossibleMoves(nextStates);

				if (nextStates.size() == 0) {
						// Must play "pass" move if there are no other moves possible.
						return new GameState(gameState, new Move());
				}


				/**
				 * Here you should write your algorithms to get the best next move, i.e.
				 * the best next state. This skeleton returns a random move instead.
				 */

				int bestValue=-2147483648;
				int stateValue;
				int bestStateIdx=0;
				int alphaInit=-2147483648;
				int betaInit=2147483647;

				int searchDepth=3;


				for(int i=0;i<nextStates.size();i++){

						stateValue=alphaBetaValue(nextStates.elementAt(i),searchDepth,alphaInit,betaInit,1);
						if(stateValue>bestValue){
								bestValue=stateValue;
								bestStateIdx=i;
								}
								//System.err.println("||||||||best state is at "+i);
						}

			 // Random random = new Random();
			 // return nextStates.elementAt(random.nextInt(nextStates.size()));

				return nextStates.elementAt(bestStateIdx);
		} 


		private int alphaBetaValue(GameState currentState,int depth,int alpha,int beta,int playerIdx){

				//System.err.println("hey");
			int v;

			if(table.contains(playerIdx,currentState)){
				System.err.println("hämtar");
				v=table.get(playerIdx,currentState);
			}

			else{
				if(depth==0 ){
						v=evaluateSimple(currentState,playerIdx);      
					}

				else{
					Vector<GameState> nextStates = new Vector<GameState>();
					currentState.findPossibleMoves(nextStates);
					if(nextStates.size() == 0){
							v=evaluateSimple(currentState,playerIdx);
					}						
					else{
						if(playerIdx==1){ 
								v=-2147483648; //smallest value in java?
									for(GameState s:nextStates){
											v=Math.max(v,alphaBetaValue(s,depth-1,alpha,beta,2));
											alpha=Math.max(alpha,v);
											if(beta<=alpha) break;
									} 
						}

						else{ //player måste isf vara 2
								v=2147483647;
								for(GameState s:nextStates){
									v=Math.min(v,alphaBetaValue(s,depth-1,alpha,beta,1));
									beta=Math.min(beta,v);
									if(beta<=alpha) break;
								} 

						}
					}
				}

			  System.err.println("sparar");

				table.put(playerIdx,currentState,v);

			}

			return v;
				
		}  


		private int evaluate(GameState state, int player){

		//sum of the number of marks in each row, column, diagonals that do not
		//contain any of the opponents marks.

			int playerCellValue = 3-player;
			int OtherPlayerCellValue = player;
			//this is 1 for player with X and 2 for player with O
			int[] differentSums = new int[10];
			boolean[] nonNulledLine = new boolean[10];
			//the first 4 are for the colunms, the following for the rows and the last for the diagonals
			for (int c=0;c<4;c++){
			 for (int r=0;r<4;r++){
				 int valueOfCell = state.at(r,c);

				 //this one is 1 for X and 2 for O
				 if (valueOfCell == playerCellValue){
					 //this is if the value is the one that represents the players
					 differentSums[c] = (differentSums[c] + 1);

					 differentSums[4+r] = (differentSums[4+r] + 1);
					 if(r==c){
						 //this is for one of the diagonals
						 differentSums[8] = (differentSums[8] + 1);
					 }
					 if(3-r==c){
						 //this is for the other of the diagonals
						 differentSums[9] = (differentSums[9] + 1);
					 }
				 }else if (valueOfCell == OtherPlayerCellValue){
					 //this is the cell has a value that is not empty and also not the players
					nonNulledLine[c] = true;
					nonNulledLine[4+r] = true;
					if(r==c){
						nonNulledLine[8] = true;
					}
					if(3-r==c){
						nonNulledLine[9] = true;
					}
				 }
			 }
			 }
			 int returnSum = 0;
			 for (int i = 0;i<10;i++){
				 if (!nonNulledLine[i]){
					 returnSum+=differentSums[i];
				 }
			 }
			 return returnSum;
			} 

 //      col 0  1  2  3  
 // * row  ---------------
 // *  0  |  .  .  .  .  |  0
 // *  1  |  .  .  .  .  |  1
 // *  2  |  .  .  .  .  |  2
 // *  3  |  .  .  .  .  |  3
 // *      ---------------
 // *        0  1  2  3

		private int evaluateSimple(GameState state, int playerIdx){
				int value=0;
				for(int r=0;r<4;r++){
						for(int c=0;c<4;c++){
								if(state.at(r,c)==playerIdx)value=value+2;

						}
				}

				if(state.at(0,0)==playerIdx)value++;
				if(state.at(1,1)==playerIdx)value++;
				if(state.at(2,2)==playerIdx)value++;
				if(state.at(3,3)==playerIdx)value++;

				if(state.at(3,0)==playerIdx)value++;
				if(state.at(2,1)==playerIdx)value++;
				if(state.at(1,2)==playerIdx)value++;
				if(state.at(0,3)==playerIdx)value++;

				return value;


		}
}
