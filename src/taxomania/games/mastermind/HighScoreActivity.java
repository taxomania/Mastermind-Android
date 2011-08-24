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
    private TableLayout highScoreTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score_layout);
        highScoreTable = (TableLayout) findViewById(R.id.highScoreTable);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        new PrintHighScores().execute(false);
    }

    private void addHeaders() {
        final TableRow tr = new TableRow(this);
        highScoreTable.addView(tr);

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
    }

    private void printScores(final List<Map<String, Object>> scores){
        int i = 1;
        for (Map<String, Object> score : scores) {
            final TableRow tr = new TableRow(this);
            highScoreTable.addView(tr);

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

            final int time = (Integer) score.get(DataHelper.Scores.TIME);
            final int minutes = time / 60;
            final int seconds = time % 60;
            final String secs = (seconds < 10) ? ("0" + seconds) : ((Integer) seconds)
                    .toString();
            final String mins = (minutes < 10) ? ("0" + minutes) : ((Integer) minutes)
                    .toString();

            final TextView scoreText = new TextView(this);
            scoreText.setText(mins + ":" + secs);
            scoreText.setTextColor(Color.WHITE);
            tr.addView(scoreText);

            final TextView gText = new TextView(this);
            gText.setText(score.get(DataHelper.Scores.GUESSES).toString());
            gText.setGravity(Gravity.CENTER);
            gText.setTextColor(Color.WHITE);
            tr.addView(gText);
        } // for each score
    } // printScores

    private final class PrintHighScores extends AsyncTask<Boolean, Void, List<Map<String, Object>>>{

        @Override
        protected void onPreExecute() {
            highScoreTable.removeAllViewsInLayout();
            addHeaders();
        } // onPreExecute

        @Override
        protected List<Map<String, Object>> doInBackground(Boolean... delete) {
            final DataHelper dh = DataHelper.getInstance(HighScoreActivity.this);
            if (delete[0]){
                dh.deleteAll();
            }
            return dh.selectAll();
        } // doInBackground

        @Override
        protected void onPostExecute(final List<Map<String, Object>> scores) {
            printScores(scores);
        } // onPostExecute
    } // PrintHighScores

    private static final int MENU_RESET = Menu.FIRST;

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_RESET, 0, "Reset High Scores");
        return true;
    } // onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()){
            case MENU_RESET:
                new PrintHighScores().execute(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } // switch
    } // onOptionsItemSelected

} // class HighScoreActivity
