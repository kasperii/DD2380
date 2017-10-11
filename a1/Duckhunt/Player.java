
class Player {
  Matriximo A = new Matriximo("5 5 0.4 0.2 0.2 0.2 0.2 0.2 0.4 0.2 0.2 0.2 0.2 0.2 0.4 0.2 0.2 0.2 0.2 0.2 0.4 0.2 0.2 0.2 0.2 0.2 0.4");
  Matriximo B = new Matriximo("5 9 0.4 0.2 0.2 0.2 0.2 0.4 0.2 0.2 0.2 0.2 0.2 0.2 0.2 0.4 0.2 0.2 0.2 0.2 0.2 0.2 0.2 0.4 0.2 0.2 0.2 0.2 0.2 0.2 0.2 0.4 0.2 0.2 0.2 0.2 0.2 0.2 0.2 0.4 0.2 0.2 0.2 0.2 0.2 0.2 0.2");
  Matriximo PI = new Matriximo("1 5 0.2 0.2 0.2 0.2 0.2");
  int[][] observations = new int[20][100];
  int loop = 0;
    public Player() {
    }

    /**
     * Shoot!
     *
     * This is the function where you start your work.
     *
     * You will receive a variable pState, which contains information about all
     * birds, both dead and alive. Each bird contains all past moves.
     *
     * The state also contains the scores for all players and the number of
     * time steps elapsed since the last time this function was called.
     *
     * @param pState the GameState object with observations etc
     * @param pDue time before which we must have returned
     * @return the prediction of a bird we want to shoot at, or cDontShoot to pass
     */
    public Action shoot(GameState pState, Deadline pDue) {
        /*
         * Here you should write your clever algorithms to get the best action.
         * This skeleton never shoots.
         */
        // This line chooses not to shoot.

        //return new Action(0-20ish, 0-9);
        for(int ey=0;ey<pState.getNumBirds();ey++){
          observations[ey][loop] = pState.getBird(ey).getObservation(loop);
        }
        System.err.print(pState.getBird(0).getObservation(loop) + " ");
        loop+=1;
        return cDontShoot;
        //return new Action(0, 4);
        // This line would predict that bird 0 will move right and shoot at it.
        // return Action(0, MOVE_RIGHT);
    }

    /**
     * Guess the species!
     * This function will be called at the end of each round, to give you
     * a chance to identify the species of the birds for extra points.
     *
     * Fill the vector with guesses for the all birds.
     * Use SPECIES_UNKNOWN to avoid guessing.
     *
     * @param pState the GameState object with observations etc
     * @param pDue time before which we must have returned
     * @return a vector with guesses for all the birds
     */
    public int[] guess(GameState pState, Deadline pDue) {
        /*
         * Here you should write your clever algorithms to guess the species of
         * each bird. This skeleton makes no guesses, better safe than sorry!
         */
        //System.err.println(Constants);
        HMM birdo = new HMM(A,B,PI);
        birdo.learn(observations[0],1000);
        HMM birdo2 = new HMM(A,B,PI);
        birdo2.learn(observations[1],1000);
        System.err.println("birdo.A.toString()");

        System.err.println(birdo.A.toString());
        System.err.println("birdo2.A.toString()");
        System.err.println(birdo2.A.toString());

        System.err.println("guess");
        int[] lGuess = new int[pState.getNumBirds()];
        for (int i = 0; i < pState.getNumBirds(); ++i)
            lGuess[i] = Constants.SPECIES_BLACK_STORK;
        return lGuess;
    }

    /**
     * If you hit the bird you were trying to shoot, you will be notified
     * through this function.
     *
     * @param pState the GameState object with observations etc
     * @param pBird the bird you hit
     * @param pDue time before which we must have returned
     */
    public void hit(GameState pState, int pBird, Deadline pDue) {
        System.err.println("HIT BIRD!!!");
    }

    /**
     * If you made any guesses, you will find out the true species of those
     * birds through this function.
     *
     * @param pState the GameState object with observations etc
     * @param pSpecies the vector with species
     * @param pDue time before which we must have returned
     */
    public void reveal(GameState pState, int[] pSpecies, Deadline pDue) {
      System.err.println("pSpecies 0= " + pSpecies[0]);
      System.err.println("pSpecies 1= " + pSpecies[1]);
/**
      System.err.println("numb of birds = " + pState.getNumBirds());
      System.err.println("pSpecies = " + pSpecies[0]);
      System.err.println("pSpecies = " + pSpecies[1]);
      System.err.println("pSpecies = " + pSpecies[2]);
      System.err.println("pSpecies = " + pSpecies[3]);
      System.err.println("pSpecies = " + pSpecies[pState.getNumBirds() -1]);
*/
    }

    public static final Action cDontShoot = new Action(-1, -1);
}
