package taxomania.games.mastermind;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

/**
 * 
 * @author Tariq Patel
 * 
 */

public final class MainMenu extends ActionBarActivity {
    private static final String TAG = MainMenu.class.getSimpleName();
    private static final String MARKET_URI = "market://details?id=taxomania.games.mastermindpro";
    private static final String WEB_URI = "https://market.android.com/details?id=taxomania.games.mastermindpro";

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        RatePrompt.appLaunched(this);
    } // onCreate(Bundle)

    public void onNewTimedGameClick(final View view) {
        startActivity(TimedMastermind.class);
    } // onNewGameClick(View)

    public void onNewGameClick(final View view) {
        startActivity(Mastermind.class);
    } // onNewGameClick(View)

    private void startActivity(final Class<? extends Activity> clazz) {
        startActivity(new Intent(this, clazz));
    } // startActivity(Class)

    public void onInstructionsClick(final View view) {
        startActivity(Instructions.class);
    } // onInstructionsClick(View)

    public void onHighScoresClick(final View view) {
        startActivity(HighScoreActivity.class);
    } // onHighScoresClick(View)

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
    } // onProClick(View)
} // class MainMenu