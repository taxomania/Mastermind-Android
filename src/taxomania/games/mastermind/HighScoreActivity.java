package taxomania.games.mastermind;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class HighScoreActivity extends Activity {
    private TableLayout mHighScoreTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score_layout);
        mHighScoreTable = (TableLayout) findViewById(R.id.highScoreTable);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        new PrintHighScores().execute(false);
    } // onCreate(Bundle)

    private void addHeaders() {
        final TableRow tr = new TableRow(this);
        mHighScoreTable.addView(tr);

        final TextView rankText = new TextView(this);
        rankText.setText("Rank");
        rankText.setTextColor(Color.WHITE);
        rankText.setGravity(Gravity.CENTER);
        tr.addView(rankText);

        final TextView nameText = new TextView(this);
        nameText.setText("Name");
        nameText.setTextColor(Color.WHITE);
        tr.addView(nameText);

        final TextView scoreText = new TextView(this);
        scoreText.setText("Time");
        scoreText.setTextColor(Color.WHITE);
        tr.addView(scoreText);

        final TextView gText = new TextView(this);
        gText.setText("Attempts");
        gText.setTextColor(Color.WHITE);
        gText.setGravity(Gravity.CENTER);
        tr.addView(gText);
    } // addHeaders()

    private void printScores(final List<Map<String, Object>> scores) {
        int i = 1;
        for (final Map<String, Object> score : scores) {
            final TableRow tr = new TableRow(this);
            mHighScoreTable.addView(tr);

            final TextView rankText = new TextView(this);
            rankText.setText(((Integer) i).toString());
            rankText.setTextColor(Color.WHITE);
            rankText.setGravity(Gravity.CENTER);
            tr.addView(rankText);
            i++;

            final TextView nameText = new TextView(this);
            nameText.setText(score.get(DataHelper.Scores.NAME).toString());
            nameText.setTextColor(Color.WHITE);
            tr.addView(nameText);

            final TextView scoreText = new TextView(this);
            scoreText.setText(getTimeString((Integer) score.get(DataHelper.Scores.TIME)));
            scoreText.setTextColor(Color.WHITE);
            tr.addView(scoreText);

            final TextView gText = new TextView(this);
            gText.setText(score.get(DataHelper.Scores.GUESSES).toString());
            gText.setGravity(Gravity.CENTER);
            gText.setTextColor(Color.WHITE);
            tr.addView(gText);
        } // for each score
    } // printScores(List<Map<String,Object>>)

    // This method can be made public static for reusability
    private String getTimeString(final int time) {
        final int minutes = time / 60;
        final int seconds = time % 60;
        final String secs = (seconds < 10) ? ("0" + seconds) : ((Integer) seconds).toString();
        final String mins = (minutes < 10) ? ("0" + minutes) : ((Integer) minutes).toString();
        return mins + ":" + secs;
    } // getTimeString(int)

    private final class PrintHighScores extends AsyncTask<Boolean, Void, List<Map<String, Object>>> {
        @Override
        protected void onPreExecute() {
            mHighScoreTable.removeAllViewsInLayout();
            addHeaders();
        } // onPreExecute()

        @Override
        protected List<Map<String, Object>> doInBackground(final Boolean... delete) {
            final DataHelper dh = DataHelper.getInstance(HighScoreActivity.this);
            if (delete[0]) {
                dh.deleteAll();
            } // if
            return dh.selectAll();
        } // doInBackground(Boolean)

        @Override
        protected void onPostExecute(final List<Map<String, Object>> scores) {
            printScores(scores);
        } // onPostExecute(List<Map<String,Object>>)
    } // PrintHighScores

    private static final int MENU_RESET = Menu.FIRST;

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_RESET, 0, "Reset High Scores");
        return true;
    } // onCreateOptionsMenu(Menu)

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case MENU_RESET:
                new PrintHighScores().execute(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } // switch
    } // onOptionsItemSelected(MenuItem)

} // class HighScoreActivity