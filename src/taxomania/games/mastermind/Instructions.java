package taxomania.games.mastermind;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;

public final class Instructions extends Activity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final TextView v = new TextView(this);
        setContentView(v);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        v.setText("Aim:\nCrack the code\n\nInstructions:\nClick on a peg to select it.\n\n"
                + "Selected pegs are highlighted.\n\nSelect a slot in the row pointed to by "
                + "the arrow to place the peg in it.\n\nWhen all 4 slots are filled, click on the "
                + "tick to confirm your guess.\n\nThe small red peg means one of "
                + "the four pegs is the correct colour and in the correct position.\n\nThe small "
                + "white peg means one of the four pegs is the correct colour but is in "
                + "the wrong position.");
        final Resources res = getResources();
        final DisplayMetrics dm = res.getDisplayMetrics();
        final float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, dm);
        v.setTextSize(px);
        final int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, dm);
        v.setPadding(padding, padding, padding, padding);
        v.setTextColor(Color.WHITE);
        v.setBackgroundDrawable(res.getDrawable(R.drawable.bg));
    } // onCreate(Bundle)
} // class Instructions
