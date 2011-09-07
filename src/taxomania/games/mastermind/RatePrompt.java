package taxomania.games.mastermind;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/*
 *  A class to Prompt the user to rate the app after 10 launches.
 *  It displays a dialog with options to rate now, rate later or to never rate the app.
 */
public class RatePrompt {
    private static final String TAG = RatePrompt.class.getSimpleName();
    private static final String MARKET_URI = "market://details?id=";
    private static final String WEB_URI = "https://market.android.com/details?id=";
    private static final int LAUNCHES_TO_PROMPT = 6;

    private static int sLaunches = 0;
    private static boolean sDoesPrompt = true;
    private static AlertDialog sDialog = null;

    public static void appLaunched(final Context context) {
        if (sDoesPrompt) {
            sLaunches++;
            if (sLaunches == LAUNCHES_TO_PROMPT) {
                prompt(context);
            } // if
        } // if
    } // appLaunched(Context)

    private static void cancel() {
        sLaunches = 0;
        sDialog.dismiss();
    } // cancel

    private static void prompt(final Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Rate Mastermind").setView(getDialogLayout(context)).setCancelable(true)
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel(final DialogInterface dialog) {
                        cancel();
                    } // onCancel
                });
        sDialog = builder.create();
        sDialog.show();
    } // prompt(Context)

    private static View getDialogLayout(final Context context) {
        final LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.rate_prompt_dialog, null);

        ((Button) v.findViewById(R.id.rateOk)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(MARKET_URI + context.getPackageName()));
                try {
                    context.startActivity(browserIntent);
                } catch (final ActivityNotFoundException e) {
                    browserIntent.setData(Uri.parse(WEB_URI + context.getPackageName()));
                    try {
                        context.startActivity(browserIntent);
                    } catch (final ActivityNotFoundException ee) {
                        Log.e(TAG, "Could not open app page");
                    } // catch
                } // catch
                sDoesPrompt = false;
                sDialog.dismiss();
            } // onClick
        });

        ((Button) v.findViewById(R.id.rateLater)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                cancel();
            } // onClick
        });

        ((Button) v.findViewById(R.id.rateNever)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                sDoesPrompt = false;
                sDialog.dismiss();
            } // onClick
        });

        return v;
    } // getDialogLayout(Context)
} // RatePrompt
