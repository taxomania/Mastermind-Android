package taxomania.games.mastermind;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TextView;

public class Instructions extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructions);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final TextView v = (TextView) findViewById(R.id.textView1);
        v.setText("Aim:\nCrack the code\n\nInstructions:\nClick on a peg to select it.\n\n"
                + "Selected pegs are highlighted.\n\nSelect a slot in the row pointed to by "
                + "the arrow to place the peg in it.\n\nWhen all 4 slots are filled, click on the "
                + "tick to confirm your guess.\n\nThe small red peg means one of "
                + "the four pegs is the correct colour and in the correct position.\n\nThe small "
                + "white peg means one of the four pegs is the correct colour but is in "
                + "the wrong position.");
        final float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());
        v.setTextSize(px);
    }
}
