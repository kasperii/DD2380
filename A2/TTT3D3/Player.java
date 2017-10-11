import java.util.*;

public class Player {

    Hashtable<String, Integer> valueTable=new Hashtable<String, Integer>(64*64*64);

    /**
     * Performs a move
     *
     * @param gameState
     *            the current state of the board
     * @param deadline
     *            time before which we must have returned
     * @return the next state the board is in after our move
     */

    int ourPlayer;


    final long roundTime=6000000000L/100;

    //final long escapeMargin=100000;



    public GameState play(final GameState gameState, final Deadline deadline) {

        ourPlayer=gameState.getNextPlayer();

        valueTable.clear();

        Vector<GameState> nextStates = new Vector<GameState>();
        gameState.findPossibleMoves(nextStates);

        long roundDeadline=deadline.getCpuTime()+roundTime;;

        if (nextStates.size() == 0) {
            // Must play "pass" move if there are no other moves possible.
            return new GameState(gameState, new Move());
        }

        /**
         * Here you should write your algorithms to get the best next move, i.e.
         * the best next state. This skeleton returns a random move instead.
         */
        // System.err.println("");
        // System.err.println("Before Statevalue="+evaluate(gameState,1));
        // System.err.println("");


        nextStates=sortStates(nextStates);


        long lastSearchDuration=0;

        int searchDepth=1;
        if(nextStates.size()<50)searchDepth=2;
        if(nextStates.size()<25)searchDepth=3;
        if(nextStates.size()<10)searchDepth=4;
        if(nextStates.size()<10)searchDepth=4;
        if(nextStates.size()<5)searchDepth=5;
        int bestIdx=0;

        //while(lastSearchDuration*64<roundDeadline-deadline.getCpuTime()){

        long searchStart=deadline.getCpuTime();

        int v0=Integer.MIN_VALUE;
        int bestValue=Integer.MIN_VALUE;

        int stateValue;

        int alpha0=Integer.MIN_VALUE;
        int beta0=Integer.MAX_VALUE;

        for(int i=0;i<nextStates.size();i++){
            searchStart=deadline.getCpuTime();
            stateValue=alphaBeta(nextStates.elementAt(i),searchDepth,alpha0,beta0,ourPlayer%2+1);
            v0=Math.max(v0,stateValue);
            alpha0=Math.max(alpha0,v0);
            if(stateValue>bestValue){
                bestValue=stateValue;
                bestIdx=i;
            }

           // System.err.println("value "+stateValue+ "  value index"+i);
           lastSearchDuration=deadline.getCpuTime()-searchStart;

        }





        lastSearchDuration=deadline.getCpuTime()-searchStart;

        //System.err.println("lastSearchDuration"+lastSearchDuration);

        //System.err.println("//////////////////////////searchDepth="+searchDepth);



        //searchDepth++;



        //}

        // System.err.println("");
        // System.err.println("After Statevalue="+evaluate(nextStates.elementAt(bestIdx),2));
        // System.err.println("");


        return nextStates.elementAt(bestIdx);
    }

    int alphaBeta(GameState state,int depth,int alpha,int beta, int playerIdx){
        int v;


        if(depth==0){
                 v=evaluate(state,ourPlayer);
        }
        else{
            Vector<GameState> nextStates = new Vector<GameState>();
            state.findPossibleMoves(nextStates);
            if(nextStates.size()==0){
                v=evaluate(state,ourPlayer);
            }
            else{
                if(playerIdx==ourPlayer){
                    v=Integer.MIN_VALUE;
                    for(GameState s : nextStates){
                        v=Math.max(v,alphaBeta(s,depth-1,alpha,beta,2));
                        alpha=Math.max(alpha,v);
                        if(beta<=alpha) break;
                    }
                }
                else{
                    v=Integer.MAX_VALUE;
                    for(GameState s: nextStates){
                        v=Math.min(v,alphaBeta(s,depth-1,alpha,beta,1));
                        beta=Math.min(beta,v);
                        if(beta<=alpha) break;
                    }
                }
            }
        }
        return v;
    }

      int evaluate(GameState s, int playerIdx){


        int[][] scoreArray=new int[4][2];
        int score = 76767676; //the most negative possible move
        String cellString=getCellsAsString(s);
        if(valueTable.containsKey(cellString)){
            //System.err.println(".  w22orks");
            return valueTable.get(cellString);
        }
        else{


        //48 orthogonals, 24 diagonals, 4 main diagonals

        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                scoreArray=checkL(s,playerIdx,0,i,j,3,i,j,scoreArray);
                scoreArray=checkL(s,playerIdx,i,0,j,i,3,j,scoreArray);
                scoreArray=checkL(s,playerIdx,i,j,0,i,j,3,scoreArray);
            }
        }

        for(int i=0;i<diags.length;i++){
                //System.err.println("diag nr:"+i);
                scoreArray=checkL(s,playerIdx,diags[i][0][0],diags[i][0][1],diags[i][0][2], diags[i][1][0],diags[i][1][1],diags[i][1][2],scoreArray);
        }


        }
        for(int i = 0;i <4;i++){
          if((0<scoreArray[i][0])||(0<scoreArray[i][1])){
            score = score + (scoreArray[i][0] - scoreArray[i][1])*(int)Math.pow(100,(i+1));
          }
        }
        valueTable.put(cellString,score);
        return score;
    }


    int[][] checkL(GameState s,int currentPlayerIdx, int row1, int col1, int layer1, int row2, int col2, int layer2,int[][] scoreArray) {
     int BOARD_SIZE=4;
     int playerIdx=currentPlayerIdx;
     int dRow = (row2 - row1) / (BOARD_SIZE - 1);
     int dCol = (col2 - col1) / (BOARD_SIZE - 1);
     int dLayer = (layer2 - layer1) / (BOARD_SIZE - 1);
     int opponentIdx = (currentPlayerIdx%2)+1;

     int playerPoints = 0;
     int opponentPoints=0;

     for (int i = 0; i < BOARD_SIZE; ++i) {
        //System.err.println("point:"+ i+ "|| row :"+(row1+dRow*i)+"| col :"+(col1+dCol*i)+"| lay:"+(layer1+dLayer*i));
       if (s.at(row1 + dRow * i, col1 + dCol * i, layer1 + dLayer * i) == playerIdx) {
         playerPoints++;
       }
       else if (s.at(row1 + dRow * i, col1 + dCol * i, layer1 + dLayer * i) == opponentIdx) {
         opponentPoints++;
       }
     }

      if (opponentPoints>0){
        if(playerPoints>0)return scoreArray;
        scoreArray[opponentPoints-1][opponentIdx-1]++;
        return scoreArray;
      }
      if (playerPoints>0){
          scoreArray[playerPoints-1][playerIdx-1]++;
      }


     return scoreArray;
     }

     final int[][][] diags={
                                        //rowwise diagonals
                                        {{0,0,0},{0,3,3}},
                                        {{0,0,3},{0,3,0}},

                                        {{1,0,0},{1,3,3}},
                                        {{1,0,3},{1,3,0}},

                                        {{2,0,0},{2,3,3}},
                                        {{2,0,3},{2,3,0}},

                                        {{3,0,0},{3,3,3}},
                                        {{3,0,3},{3,3,0}},

                                        //columnwise diagonals
                                        {{0,0,0},{3,0,3}},
                                        {{0,0,3},{3,0,0}},

                                        {{0,1,0},{3,1,3}},
                                        {{0,1,3},{3,1,0}},

                                        {{0,2,0},{3,2,3}},
                                        {{0,2,3},{3,2,0}},

                                        {{0,3,0},{3,3,3}},
                                        {{0,3,3},{3,3,0}},

                                        //layerwise diagonals
                                        {{0,0,0},{3,3,0}},
                                        {{0,3,0},{3,0,0}},

                                        {{0,0,1},{3,3,1}},
                                        {{0,3,1},{3,0,1}},

                                        {{0,0,2},{3,3,2}},
                                        {{0,3,2},{3,0,2}},

                                        {{0,0,3},{3,3,3}},
                                        {{0,3,3},{3,0,3}},

                                        //main diagonals

                                        {{0,0,0},{3,3,3}},
                                        {{3,0,0},{0,3,3}},
                                        {{0,3,0},{3,0,3}},
                                        {{0,0,3},{3,3,0}}
                                    };


    String getCellsAsString(GameState s){
        String out="";
        for(int i=0;i<64;i++){
            out+=Integer.toString(s.at(i));
        }
        return out;
    }

    public class stateComparator implements Comparator<GameState>{

    //@Override
        public int compare(GameState sA, GameState sB){
            return evaluate(sB,1)- evaluate(sA,1);
        }

    //    @Override
        public boolean equals(GameState sA, GameState sB){
            return evaluate(sB,1)==evaluate(sA,1);
        }
    }

    Vector<GameState> sortStates(Vector<GameState> stateVector){
        Collections.sort(stateVector, new stateComparator());
        return stateVector;
    }




}
