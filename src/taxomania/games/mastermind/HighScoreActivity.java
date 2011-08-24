package taxomania.games.mastermind;

import java.util.List;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
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

		addHeaders();
		printHighscores();
	}

	private void addHeaders() {
		TableRow tr = new TableRow(this);
		highScoreTable.addView(tr);

		TextView rankText = new TextView(this);
		rankText.setText("Rank");
		rankText.setTextColor(Color.WHITE);
		rankText.setGravity(Gravity.CENTER);
		tr.addView(rankText);
		

		TextView nameText = new TextView(this);
		nameText.setText("Name");
		nameText.setTextColor(Color.WHITE);
		tr.addView(nameText);

		TextView scoreText = new TextView(this);
		scoreText.setText("Time");
		scoreText.setTextColor(Color.WHITE);
		tr.addView(scoreText);
		
		TextView gText = new TextView(this);
		gText.setText("Attempts");
		gText.setTextColor(Color.WHITE);
		gText.setGravity(Gravity.CENTER);
		tr.addView(gText);
	}
	
	private void printHighscores() {
		DataHelper dh = new DataHelper(this);
		List<String> nameList = dh.selectAllNames();
		List<Integer> timeList = dh.selectAllTimes();
		List<Integer> gList = dh.selectAllGuesses();

		
		for (int i = 0; i < nameList.size(); i++) {
			int minutes = (timeList.get(i)) / 60;
			int seconds = (timeList.get(i)) % 60;
			String secs = (seconds < 10) ? ("0"+ seconds) : ((Integer)seconds).toString();
			String mins = (minutes < 10) ? ("0"+ minutes) : ((Integer)minutes).toString();
			
			
				TableRow tr = new TableRow(this);
				highScoreTable.addView(tr);

				TextView rankText = new TextView(this);
				rankText.setText("" + (i+1));
				rankText.setTextColor(Color.WHITE);
				rankText.setGravity(Gravity.CENTER);
				tr.addView(rankText);

				TextView nameText = new TextView(this);
				nameText.setText(nameList.get(i));
				nameText.setTextColor(Color.WHITE);
				tr.addView(nameText);

				TextView scoreText = new TextView(this);
				scoreText.setText(mins + ":" + secs);
				scoreText.setTextColor(Color.WHITE);
				tr.addView(scoreText);
				
				TextView gText = new TextView(this);
				gText.setText("" + gList.get(i));
				gText.setGravity(Gravity.CENTER);
				gText.setTextColor(Color.WHITE);
				tr.addView(gText);
			}
	}

} // class HighScoreActivity