package taxomania.games.mastermind;

public class Pair {
    private final int mPeg;
    private boolean mReady;

    public Pair(final int p) {
        mPeg = p;
        mReady = true;
    } // Pair(int)

    public int getPeg() {
        return this.mPeg;
    } // getPeg()

    public boolean getReady() {
        return this.mReady;
    } // getReady()

    public void setReady(final boolean b) {
        mReady = b;
    } // setReady(boolean)
} // Pair
