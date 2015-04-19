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
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.EditText;
import android.widget.Toast;

public final class TimedMastermind extends Mastermind {
    private Chronometer mTimer;
    private boolean mResume = false, mStopped = false;
    private int mTime;
    private long mElapsedTime, mMinutes, mSeconds;
    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;
    private AlertDialog mPauseAlert;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createPauseAlert();

        mTimer = (Chronometer) findViewById(R.id.timer);
        mTimer.setVisibility(View.VISIBLE);
        mTimer.setBase(SystemClock.elapsedRealtime());
        mTimer.setOnChronometerTickListener(new OnChronometerTickListener() {
            public void onChronometerTick(final Chronometer chrono) {
                if (!mResume) {
                    mMinutes = ((SystemClock.elapsedRealtime() - mTimer.getBase()) / 1000) / 60;
                    mSeconds = ((SystemClock.elapsedRealtime() - mTimer.getBase()) / 1000) % 60;
                    mElapsedTime = SystemClock.elapsedRealtime();
                } else {
                    mMinutes = ((mElapsedTime - mTimer.getBase()) / 1000) / 60;
                    mSeconds = ((mElapsedTime - mTimer.getBase()) / 1000) % 60;
                    mElapsedTime = mElapsedTime + 1000;
                }
                final String secs = (mSeconds < 10) ? ("0" + ((Integer) (int) mSeconds).toString())
                        : ((Integer) (int) mSeconds).toString();
                final String mins = (mMinutes < 10) ? ("0" + ((Integer) (int) mMinutes).toString())
                        : ((Integer) (int) mMinutes).toString();
                final String currentTime = mins + ":" + secs;
                chrono.setText(currentTime);
                chrono.setPadding(10, 10, 10, 10);
            } // onChronometerTick(Chronometer)
        });

        mTimer.start();
        mSensorListener = new ShakeEventListener();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {
            @Override
            public void onShake() {
                pauseGame();
            } // onShake()
        });
    } // onCreate(Bundle)

    private void createPauseAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Game Paused")
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(final DialogInterface dialog) {
                        resume(dialog);
                    } // onCancel(DialogInterface)
                }).setNeutralButton("Resume", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        resume(dialog);
                    } // onClick(DialogInterface, int)
                });

        mPauseAlert = builder.create();
        final WindowManager.LayoutParams lp = mPauseAlert.getWindow().getAttributes();
        lp.dimAmount = 1.0f;
        mPauseAlert.getWindow().setAttributes(lp);
        mPauseAlert.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        mPauseAlert.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    } // createPauseAlert()

    private void pauseGame() {
        mTimer.stop();
        mPauseAlert.show();
    } // pauseGame()

    private void resume(final DialogInterface dialog) {
        dialog.dismiss();
        mResume = true;
        mTimer.start();
    } // resume(DialogInterface)

    @Override
    protected void onResume() {
        super.onResume();
        if (mStopped) {
            pauseGame();
            mStopped = false;
        } // if
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    } // onResume()

    @Override
    protected void onPause() {
        mTimer.stop();
        mStopped = true;
        super.onPause();
    } // onPause()

    @Override
    protected void onStop() {
        mSensorManager.unregisterListener(mSensorListener);
        mTimer.stop();
        mStopped = false;
        super.onStop();
    } // onStop()

    @Override
    protected void onRestart() {
        super.onRestart();
        mResume = true;
        mTimer.start();
    } // onRestart()

    private final class CheckIfHighScore extends AsyncTask<Integer, Void, Boolean> {
        @Override
        protected Boolean doInBackground(final Integer... time) {
            final DataHelper dh = DataHelper.getInstance(TimedMastermind.this);
            if (dh.getCount() < 7) { return Boolean.TRUE; }
            final List<Integer> list = dh.selectAllTimes();
            final int last = list.get(list.size() - 1);
            if (time[0] < last) { return Boolean.TRUE; }
            return Boolean.FALSE;
        } // doInBackground(Integer...)

        @Override
        protected void onPostExecute(final Boolean newScore) {
            if (newScore) {
                enterName();
            } // if
        } // onPostExecute(Boolean)
    } // class CheckIfHighScore

    private final class AddLocalScore extends AsyncTask<Object, Void, Long> {
        @Override
        protected Long doInBackground(final Object... params) {
            final DataHelper dh = DataHelper.getInstance(TimedMastermind.this);
            return dh.insert(params[0].toString(), (Integer) params[1], (Integer) params[2]);
        } // doInBackground(Object...)

        @Override
        protected void onPostExecute(final Long result) {
            if (result == -1) {
                Toast.makeText(TimedMastermind.this, "There was an error submitting your score",
                        Toast.LENGTH_SHORT).show();
            } // if
        } // onPostExecute(Long)
    } // AddLocalScore

    private void enterName() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("NEW HIGHSCORE!");
        final EditText userName = new EditText(this);
        userName.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(userName).setMessage("Enter Your Name")
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int whichButton) {
                        new AddLocalScore().execute(userName.getText().toString(), mTime, mGuess);
                        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(userName.getWindowToken(), 0);
                    } // onClick(DialogInterface, int)
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int whichButton) {
                        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(userName.getWindowToken(), 0);
                    } // onClick(DialogInterface, int)
                });
        builder.create().show();
    } // enterName()

    @Override
    protected void endGame() {
        mTimer.stop();
        mTime = (int) (((mElapsedTime - mTimer.getBase()) / 1000));
        mMinutes = mTime / 60;
        mSeconds = mTime % 60;
        final String secs = (mSeconds < 10) ? ("0" + ((Integer) (int) mSeconds).toString())
                : ((Integer) (int) mSeconds).toString();
        final String mins = (mMinutes < 10) ? ("0" + ((Integer) (int) mMinutes).toString())
                : ((Integer) (int) mMinutes).toString();

        showEndAlert(mins + ":" + secs);

        new CheckIfHighScore().execute(mTime);
    } // endGame()

    private void showEndAlert(final String timeScore) {
        final Intent again = new Intent(this, TimedMastermind.class);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(
                "Congratulations! You cracked the code in " + mGuess + " attempts and in time "
                        + timeScore + "!").setCancelable(false)
                .setPositiveButton("Start Again", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(again);
                        finish();
                    }
                }).setNegativeButton("Quit to Main Menu", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        finish();
                    }
                }).create().show();
    } // showEndAlert(String)

    @Override
    protected void loseGame() {
        super.loseGame();
        mTimer.stop();
    } // loseGame()

    private static final int MENU_PAUSE = Menu.FIRST + 2;

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        menu.add(Menu.NONE, MENU_PAUSE, MENU_PAUSE, "Pause Game");
        return super.onCreateOptionsMenu(menu);
    } // onCreateOptionsMenu(Menu)

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
    } // onOptionsItemSelected(MenuItem)

} // class TimedMastermind