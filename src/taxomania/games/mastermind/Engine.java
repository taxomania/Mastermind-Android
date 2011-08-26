package taxomania.games.mastermind;

/**
 *
 * @author Tariq Patel
 *
 */
public class Engine {
    protected static final int TOTAL_NO_PEGS = 6;
    private static final int COMBO_LENGTH = 4;
    private final static Pair[] sCombination = new Pair[COMBO_LENGTH];

    public Engine(){
        for (int i = 0; i < sCombination.length; i++)
            sCombination[i] = new Pair(Mastermind.sPegs[(int)((TOTAL_NO_PEGS) * Math.random())]);
    }

    public void resetStates(){
        for (int i=0;i<sCombination.length;i++)
            sCombination[i].setReady(true);
    }

    // Check the peg is the right colour and in the right position
    public boolean checkPos(int guess, int pos){
        if (sCombination[pos].getReady())
            if (guess == sCombination[pos].getPeg()){
                sCombination[pos].setReady(false);
                return true;
            }
        return false;
    } // checkPos

    // Check the peg is the right colour but in the wrong position
    public boolean checkPeg(int guess){
        for (int i=0; i< sCombination.length;i++)
            if (sCombination[i].getReady())
                if (guess == sCombination[i].getPeg()){
                    sCombination[i].setReady(false);
                    return true;
                }

        return false;
    } // checkPeg
}