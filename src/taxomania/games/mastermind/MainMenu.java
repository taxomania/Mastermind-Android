package taxomania.games.mastermind;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 *
 * @author Tariq Patel
 *
 */

public class MainMenu extends Activity {
    private static final String TAG = MainMenu.class.getSimpleName();
    private static final String MARKET_URI = "market://details?id=taxomania.games.mastermindpro";
    private static final String WEB_URI =
            "https://market.android.com/details?id=taxomania.games.mastermindpro";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        RatePrompt.appLaunched(this);
    }

    private void newGameDialog() {
        final Intent classic = new Intent(this, Mastermind.class);
        final Intent timed = new Intent(this, TimedMastermind.class);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Select game mode").setCancelable(true)
                .setPositiveButton("Classic", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(classic);
                    }
                }).setNegativeButton("Timed", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(timed);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void onScoresClick(final View view) {
        startActivity(new Intent(this, HighScoreActivity.class));
    }

    public void onNewGameClick(final View view) {
        newGameDialog();
    }

    public void onInstructionsClick(final View view) {
        startActivity(new Intent(this, Instructions.class));
    }

    public void onProClick(final View view) {
        final Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse(MARKET_URI));
        try {
            startActivity(browserIntent);
        } catch (final ActivityNotFoundException e) {
            browserIntent.setData(Uri.parse(WEB_URI));
            try {
                startActivity(browserIntent);
            } catch (final ActivityNotFoundException ee) {
                Log.e(TAG, "Could not open app page");
            } // catch
        } // catch
    }

    public void onQuitClick(final View view) {
        finish();
    }
}