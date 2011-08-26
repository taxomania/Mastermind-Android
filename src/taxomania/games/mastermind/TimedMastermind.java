package taxomania.games.mastermind;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.EditText;
import android.widget.Toast;

public class TimedMastermind extends Mastermind {
    private Chronometer mTimer;
    private static boolean sResume = false;
    private int time;
    private long elapsedTime, minutes, seconds;
    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;
    private AlertDialog mPauseAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createPauseAlert();

        mTimer = (Chronometer) findViewById(R.id.timer);
        mTimer.setVisibility(View.VISIBLE);
        mTimer.setBase(SystemClock.elapsedRealtime());
        mTimer.setOnChronometerTickListener(new OnChronometerTickListener() {
            public void onChronometerTick(Chronometer chrono) {
                if (!sResume) {
                    minutes = ((SystemClock.elapsedRealtime() - mTimer
                            .getBase()) / 1000) / 60;
                    seconds = ((SystemClock.elapsedRealtime() - mTimer
                            .getBase()) / 1000) % 60;
                    String secs = (seconds < 10) ? ("0"+((Integer)(int)seconds).toString()) : ((Integer)(int)seconds).toString();
                    String mins = (minutes < 10) ? ("0"+((Integer)(int)minutes).toString()) : ((Integer)(int)minutes).toString();
                    String currentTime = mins + ":" + secs;
                    chrono.setText(currentTime);
                    chrono.setPadding(10, 10, 10, 10);
                    elapsedTime = SystemClock.elapsedRealtime();
                } else {
                    minutes = ((elapsedTime - mTimer.getBase()) / 1000) / 60;
                    seconds = ((elapsedTime - mTimer.getBase()) / 1000) % 60;
                    String secs = (seconds < 10) ? ("0"+((Integer)(int)seconds).toString()) : ((Integer)(int)seconds).toString();
                    String mins = (minutes < 10) ? ("0"+((Integer)(int)minutes).toString()) : ((Integer)(int)minutes).toString();
                    String currentTime = mins + ":" + secs;
                    chrono.setText(currentTime);
                    chrono.setPadding(10, 10, 10, 10);
                    elapsedTime = elapsedTime + 1000;
                }
            }
        });

        mTimer.start();
        mSensorListener = new ShakeEventListener();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener,
            mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_UI);

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

          public void onShake() {
                  pauseGame();
          }
        });
    } // onCreate

    private void createPauseAlert(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Game Paused")
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(final DialogInterface dialog){
                        resume(dialog);
                    }
                })
                .setNeutralButton("Resume",
                        new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int id) {
                                resume(dialog);
                            }
                        });

        mPauseAlert = builder.create();
        WindowManager.LayoutParams lp = mPauseAlert.getWindow().getAttributes();
        lp.dimAmount=1.0f;
        mPauseAlert.getWindow().setAttributes(lp);
        mPauseAlert.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        mPauseAlert.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    } // createPauseAlert

    private void pauseGame(){
        mTimer.stop();
        mPauseAlert.show();
    } // pauseGame

    private static boolean sStopped = false;
    private void resume(final DialogInterface dialog){
        dialog.dismiss();
        sResume = true;
        mTimer.start();
    } // resume

    @Override
    protected void onResume() {
      super.onResume();
      if (sStopped)
      {
          pauseGame();
          sStopped = false;
      }
      mSensorManager.registerListener(mSensorListener,
          mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
          SensorManager.SENSOR_DELAY_UI);
    } // onResume

    @Override
    protected void onPause() {
        mTimer.stop();
        sStopped = true;
        super.onPause();
    }

    @Override
    protected void onStop() {
        mSensorManager.unregisterListener(mSensorListener);
        mTimer.stop();
        sStopped = false;
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        sResume = true;
        mTimer.start();
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
        mTimer.stop();
        time = (int)(((elapsedTime - mTimer.getBase()) / 1000));
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
        mTimer.stop();
    } // loseGame

    private static final int MENU_PAUSE = Menu.FIRST+2;

    @Override
    public boolean onCreateOptionsMenu(final Menu menu)
    {
        menu.add(Menu.NONE, MENU_PAUSE, MENU_PAUSE, "Pause Game");
        return super.onCreateOptionsMenu(menu);
    } // onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case MENU_NEW_GAME:
                startActivity(new Intent(this, getClass()));
                finish();
                return true;
            case MENU_INSTRUCTIONS:
                startActivity(new Intent(this, Instructions.class));
                return true;
            case MENU_PAUSE:
                pauseGame();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } // switch
    } // onOptionsItemSelected

} // TimedMastermind