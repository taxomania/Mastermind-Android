package taxomania.games.mastermind;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public final class PegSlot extends ImageView {
    public PegSlot(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        setBackgroundResource(R.drawable.pegslot);
    } // PegSlot(Context, AttributeSet)
} // class PegSlot
