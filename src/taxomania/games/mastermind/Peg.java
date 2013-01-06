package taxomania.games.mastermind;

final class Peg {
    private final int mPegColour;
    private boolean mReady;

    Peg(final int peg) {
        mPegColour = peg;
        mReady = true;
    } // Peg(int)

    int getPeg() {
        return mPegColour;
    } // getPeg()

    boolean getReady() {
        return mReady;
    } // getReady()

    void setReady(final boolean ready) {
        mReady = ready;
    } // setReady(boolean)
} // class Peg
