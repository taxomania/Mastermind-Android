package taxomania.games.mastermind;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Chronometer;
import android.widget.Toast;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.EditText;

public class TimedMastermind extends Mastermind {
    private Chronometer timer;
    private boolean resume;
    private int  time;
    private long elapsedTime, minutes, seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        resume = false;
        timer = (Chronometer) findViewById(R.id.timer);
        timer.setVisibility(View.VISIBLE);
        timer.setBase(SystemClock.elapsedRealtime());
        timer.setOnChronometerTickListener(new OnChronometerTickListener() {
            public void onChronometerTick(Chronometer chrono) {
                // TODO Auto-generated method stub
                if (!resume) {
                    minutes = ((SystemClock.elapsedRealtime() - timer
                            .getBase()) / 1000) / 60;
                    seconds = ((SystemClock.elapsedRealtime() - timer
                            .getBase()) / 1000) % 60;
                    String secs = (seconds < 10) ? ("0"+((Integer)(int)seconds).toString()) : ((Integer)(int)seconds).toString();
                    String mins = (minutes < 10) ? ("0"+((Integer)(int)minutes).toString()) : ((Integer)(int)minutes).toString();
                    String currentTime = mins + ":" + secs;
                    chrono.setText(currentTime);
                    chrono.setPadding(10, 10, 10, 10);
                    elapsedTime = SystemClock.elapsedRealtime();
                } else {
                    minutes = ((elapsedTime - timer.getBase()) / 1000) / 60;
                    seconds = ((elapsedTime - timer.getBase()) / 1000) % 60;
                    String secs = (seconds < 10) ? ("0"+((Integer)(int)seconds).toString()) : ((Integer)(int)seconds).toString();
                    String mins = (minutes < 10) ? ("0"+((Integer)(int)minutes).toString()) : ((Integer)(int)minutes).toString();
                    String currentTime = mins + ":" + secs;
                    chrono.setText(currentTime);
                    chrono.setPadding(10, 10, 10, 10);
                    elapsedTime = elapsedTime + 1000;
                }
            }
        });

        timer.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        timer.stop();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        timer.stop();
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        resume = true;
        timer.start();
    }


    private final class CheckIfHighScore extends AsyncTask<Integer,  Void, Boolean>{
        @Override
        protected Boolean doInBackground(Integer... time)
        {
            final DataHelper dh = DataHelper.getInstance(TimedMastermind.this);
            if (dh.getCount() < 7) return true;
            final List<Integer> list = dh.selectAllTimes();
            final int last = list.get(list.size()-1);
            if (time[0] < last) return true;
            return false;
        } // doInBackground

        @Override
        protected void onPostExecute(Boolean newScore)
        {
            if (newScore) enterName();
        } // onPostExecute
    } // CheckIfHighScore

    private final class AddLocalScore extends AsyncTask<Object, Void, Long> {

        @Override
        protected Long doInBackground(Object... params) {
            final DataHelper dh = DataHelper.getInstance(TimedMastermind.this);
            return dh.insert(params[0].toString(), (Integer) params[1], (Integer) params[2]);
        } // doInBackground

        @Override
        protected void onPostExecute(Long result)
        {
            if (result == -1)
            {
                Toast.makeText(TimedMastermind.this, "There was an error submitting your score",
                        Toast.LENGTH_SHORT).show();
            }
        } // onPostExecute

    } // AddLocalScore

    private void enterName()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("NEW HIGHSCORE!");
        final EditText userName = new EditText(this);
        builder.setView(userName).setMessage("Enter Your Name")
                .setPositiveButton("Done",
                        new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        new AddLocalScore().execute(userName.getText().toString(), time, guess);
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(userName.getWindowToken(), 0);
                    }
                }).setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(userName.getWindowToken(), 0);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private String timeScore;
    @Override
    protected void endGame() {
        timer.stop();
        time = (int)(((elapsedTime - timer.getBase()) / 1000));
        minutes = time / 60;
        seconds = time % 60;
        String secs = (seconds < 10) ? ("0"+((Integer)(int)seconds).toString()) : ((Integer)(int)seconds).toString();
        String mins = (minutes < 10) ? ("0"+((Integer)(int)minutes).toString()) : ((Integer)(int)minutes).toString();
        timeScore = mins + ":" + secs;
        showEndAlert();

        new CheckIfHighScore().execute(time);
    }

    private void showEndAlert() {
        final Intent again = new Intent(this, TimedMastermind.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(
                "Congratulations! You cracked the code in " + guess
                        + " attempts and in time " + timeScore + "!")
                .setCancelable(false)
                .setPositiveButton("Start Again",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(again);
                                finish();
                            }
                        })
                .setNegativeButton("Quit to Main Menu",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void loseGame() {
        super.loseGame();
        timer.stop();
    }
}
