package taxomania.games.mastermind;

/**
 * 
 * @author Tariq Patel
 *
 */
public class Engine {
	protected static final int TOTAL_NO_PEGS = 6;
	private final static Pair[] combination = new Pair[4];
	
	public Engine(){
		for (int i = 0; i < combination.length; i++)
			combination[i] = new Pair(Mastermind.pegs[(int)((TOTAL_NO_PEGS) * Math.random())]);
	}

	public void resetStates(){
		for (int i=0;i<combination.length;i++)
			combination[i].setReady(true);
	}
	
	// Check the peg is the right colour and in the right position
	public boolean checkPos(int guess, int pos){
		if (combination[pos].getReady())
			if (guess == combination[pos].getPeg()){
				combination[pos].setReady(false);
				return true;
			}
		return false;
	} // checkPos
	
	// Check the peg is the right colour but in the wrong position
	public boolean checkPeg(int guess){
		for (int i=0; i< combination.length;i++)
			if (combination[i].getReady())
				if (guess == combination[i].getPeg()){
					combination[i].setReady(false);
					return true;
				}
		
		return false;
	} // checkPeg
}