package taxomania.games.mastermind;

public class Pair {
	private int peg;
	private boolean ready;

	public Pair(int p) {
		peg = p;
		ready = true;
	}
	
	public int getPeg(){return this.peg;}
	public void setPeg(int p){peg = p;}
	public boolean getReady(){return this.ready;}
	public void setReady(boolean b){ready = b;}
}
