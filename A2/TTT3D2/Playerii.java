import java.util.*;

public class Playerii {

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

    long playTime=55000000L;
    long roundTime;
    public GameState play(final GameState gameState, final Deadline deadline) {


        long roundStartTime=Deadline.getCpuTime();

        valueTable.clear();

        //System.err.println("contains? "+valueTable.containsKey(getCellsAsString(gameState)));

        Vector<GameState> nextStates = new Vector<GameState>();
        gameState.findPossibleMoves(nextStates);

       // System.err.println("currentStateValue:"+ evaluate(gameState, "test"));

        if (nextStates.size() == 0) {
            // Must play "pass" move if there are no other moves possible.
            return new GameState(gameState, new Move());
        }

        /**
         * Here you should write your algorithms to get the best next move, i.e.
         * the best next state. This skeleton returns a random move instead.
         */
        int v0=Integer.MIN_VALUE;
        int bestValue=Integer.MIN_VALUE;
        int bestIdx=0;
        int stateValue;
        int searchDepth=2;
        int alpha0=Integer.MIN_VALUE;
        int beta0=Integer.MAX_VALUE;


        nextStates=sortStates(nextStates);

        for(int i=0;i<nextStates.size();i++){
            stateValue=alphaBeta(nextStates.elementAt(i),searchDepth,alpha0,beta0,2);
            v0=Math.max(v0,stateValue);
            alpha0=Math.max(alpha0,v0);
            if(stateValue>bestValue){
                bestValue=stateValue;
                bestIdx=i;
                //System.err.println(bestValue);
            }
            if(Deadline.getCpuTime()-roundStartTime>8500000)break;

            //System.err.println("stateValue="+stateValue);


        }
        return nextStates.elementAt(bestIdx);

        // Random random = new Random();
        // return nextStates.elementAt(random.nextInt(nextStates.size()));
    }

    int alphaBeta(GameState state,int depth,int alpha,int beta, int playerIdx){
        int v;

        if(depth==0){
                 v=evaluate(state,playerIdx);
        }
        else{
            Vector<GameState> nextStates = new Vector<GameState>();
            state.findPossibleMoves(nextStates);
            //if(depth==2)nextStates=sortStates(nextStates);
            if(nextStates.size()==0){
                v=evaluate(state,playerIdx);
            }
            else{
                if(playerIdx==1){
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

        String cellString=getCellsAsString(s);
        if(valueTable.containsKey(cellString)){
            //System.err.println(".  w22orks");
            return valueTable.get(cellString);
        }

        int score=0;


        // long t0=System.nanoTime();

        //on each line, count one point per mark unless enemy has mark then 0.

        //48 orthogonals, 24 diagonals, 4 main diagonals

        int power=10;

        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                score+=checkL(s,playerIdx,0,i,j,3,i,j);
                score+=checkL(s,playerIdx,i,0,j,i,3,j);
                score+=checkL(s,playerIdx,i,j,0,i,j,3);
            }
        }

        for(int i=0;i<diags.length;i++){

                score+=checkL(s,playerIdx,diags[i][0][0],diags[i][0][1],diags[i][0][2], diags[i][1][0],diags[i][1][1],diags[i][1][2]);

        }

       // long t1=System.nanoTime();

       // System.err.println("time to evaluate:"+(t1-t0));


        valueTable.put(cellString,score);
        //System.err.println("putkey");

        score = score - evaluate(s,2);
        return score;

    }

//repurposed a bit of code from gameState
    int checkL(GameState s,int currentPlayerIdx, int row1, int col1, int layer1, int row2, int col2, int layer2) {
     int BOARD_SIZE=4;
     int playerIdx=1;
     int dRow = (row2 - row1) / (BOARD_SIZE - 1);
     int dCol = (col2 - col1) / (BOARD_SIZE - 1);
     int dLayer = (layer2 - layer1) / (BOARD_SIZE - 1);
     int opponentIdx = 2;
     if (currentPlayerIdx==2){
       opponentIdx=1;
     }

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
        //if(opponentPoints==3 && currentPlayerIdx==1)return -(int)Math.pow(5,12);
        return 0;
     }

    // if (playerPoints>0){
    //     if(playerPoints==3 && currentPlayerIdx==2)return (int)Math.pow(5,12);
    //  }



     return (int)Math.pow(playerPoints,12);
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
                                        {{3,0,0},{0,0,3}},
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

//bygg eval uppåt, utgå från förra eval o kolla endast raderna/diags som kan ha föändrats.



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
