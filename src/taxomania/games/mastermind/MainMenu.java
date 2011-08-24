package taxomania.games.mastermind;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.view.View;
import com.google.ads.*;


/**
 * 
 * @author Tariq Patel
 *
 */

public class MainMenu extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AdView adView = new AdView(this, AdSize.BANNER, "a14e0f6dfe88c0e");
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainLayout);
        layout.addView(adView);
	    adView.loadAd(new AdRequest());
    }
    
    private void newGameDialog(){
    	final Intent classic = new Intent(this, Mastermind.class);
    	final Intent timed = new Intent(this, TimedMastermind.class);
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"Select game mode")
				.setCancelable(true)
				.setPositiveButton("Classic",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								startActivity(classic);
							}
						})
				.setNegativeButton("Timed",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								startActivity(timed);
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
    }
    
    public void onScoresClick(View view) {startActivity(new Intent(this, HighScoreActivity.class));}
    public void onNewGameClick(View view){newGameDialog();}
    public void onInstructionsClick(View view){startActivity(new Intent(this, Instructions.class));}
    public void onProClick(View view){
    	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://market.android.com/details?id=taxomania.games.mastermindpro"));
    	startActivity(browserIntent);
    }
    public void onQuitClick(View view){finish();}
}