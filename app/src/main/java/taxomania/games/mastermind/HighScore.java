package taxomania.games.mastermind;

/**
 * Created by tariq on 19/04/15.
 */
public class HighScore {
    int rank;
    String name;
    int time;
    int attempts;

    public String getTime() {
        final int minutes = time / 60;
        final int seconds = time % 60;
        final String secs = (seconds < 10) ? ("0" + seconds) : Integer.toString(seconds);
        final String mins = (minutes < 10) ? ("0" + minutes) : Integer.toString(minutes);
        return mins + ":" + secs;
    }
}
