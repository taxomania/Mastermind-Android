package taxomania.games.mastermind;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Instructions extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.instructions);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		AdView adView = new AdView(this, AdSize.BANNER, "a14e0f6dfe88c0e");
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.instructionsLayout);
		layout.addView(adView);
		adView.loadAd(new AdRequest());
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		TextView v = (TextView) findViewById(R.id.textView1);
		v.setText("Aim:\nCrack the code\n\nInstructions:\nClick on a peg to select it.\n\nSelected pegs are highlighted.\n\nSelect a slot in the row pointed to by" +
				" the arrow to place the peg in it.\n\nWhen all 4 slots are filled, click on the tick to confirm your guess.\n\nThe small red peg means one of" +
				" the four pegs is the correct colour and in the correct position.\n\nThe small white peg means one of the four pegs is the correct" +
				" colour but is in the wrong position.");
		v.setTextSize(16);
	}
}
