package taxomania.games.mastermind;

/**
 *
 * @author Tariq Patel
 *
 */
final class Engine {
    static final int TOTAL_NO_PEGS = 6;
    private static final int COMBO_LENGTH = 4;
    private final Peg[] mCombination = new Peg[COMBO_LENGTH];

    Engine() {
        for (int i = 0; i < mCombination.length; i++)
            mCombination[i] = new Peg(Mastermind.sPegs[(int) ((TOTAL_NO_PEGS) * Math.random())]);
    } // Engine()

    Peg[] getCombination() {
        return mCombination;
    } // getCombination()

    void resetStates() {
        for (int i = 0; i < mCombination.length; i++) {
            mCombination[i].setReady(true);
        } // for
    } // resetStates()

    // Check the peg is the right colour and in the right position
    boolean checkPos(final int guess, final int pos) {
        if (mCombination[pos].getReady()) {
            if (guess == mCombination[pos].getPeg()) {
                mCombination[pos].setReady(false);
                return true;
            } // if
        } // if
        return false;
    } // checkPos(int, int)

    // Check the peg is the right colour but in the wrong position
    boolean checkPeg(final int guess) {
        for (int i = 0; i < mCombination.length; i++) {
            if (mCombination[i].getReady()) {
                if (guess == mCombination[i].getPeg()) {
                    mCombination[i].setReady(false);
                    return true;
                } // if
            } // if
        } // for
        return false;
    } // checkPeg(int)
} // class Engine