package taxomania.games.mastermind;

import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

/**
 *
 * @author Tariq Patel
 *
 */
public class Mastermind extends Activity {
    protected Engine mGame;
    // Constant declarations
    protected static final int BLUE = 0, GREEN = 1, RED = 2, WHITE = 3, YELLOW = 4, PURPLE = 5,
            TOTAL_PEG_SLOTS = 40, MAX_PEGS = 4, MAX_GUESSES = 10;
    // state[] shows which colour peg has been selected
    protected static Boolean[] sState = new Boolean[Engine.TOTAL_NO_PEGS];
    // slotPosition[] maps peg slots to view IDs
    protected static int[] sSlotPosition = new int[TOTAL_PEG_SLOTS],
            sSmallSlotPosition = new int[TOTAL_PEG_SLOTS];
    protected static int[][] pegSlots = new int[MAX_GUESSES][MAX_PEGS],
            sSmallPegSlots = new int[MAX_GUESSES][MAX_PEGS];
    protected static int sGuess;
    protected static Resources sResources;
    protected static final int[] sPegs = { R.drawable.bluepeg, R.drawable.greenpeg,
            R.drawable.redpeg, R.drawable.whitepeg, R.drawable.yellowpeg, R.drawable.purplepeg };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mastermind);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sResources = getResources();
        // Initialise the state array
        for (int i = 0; i < sState.length; i++)
            sState[i] = false;

        // Map the peg slots on the board to an array index
        inefficientMapping();
        map2(sSlotPosition, pegSlots);
        map2(sSmallSlotPosition, sSmallPegSlots);

        // New game, create a new combination
        mGame = new Engine();
        sGuess = 0;
        findViewById(R.id.confirm10).setClickable(false);
    }

    protected static final int MENU_NEW_GAME = Menu.FIRST;
    protected static final int MENU_INSTRUCTIONS = Menu.FIRST + 1;

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        menu.add(Menu.NONE, MENU_NEW_GAME, MENU_NEW_GAME, "New Game");
        menu.add(Menu.NONE, MENU_INSTRUCTIONS, MENU_INSTRUCTIONS, "Instructions");
        return true;
    }

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
            default:
                return super.onOptionsItemSelected(item);
        } // switch
    } // onOptionsItemSelected

    public void setGame(final Engine game) {
        mGame = game;
    } // setGame

    public Engine getGame() {
        return mGame;
    } // getGame

    public static void setState(final Boolean[] state) {
        Mastermind.sState = state;
    } // setState

    public static Boolean[] getState() {
        return sState;
    } // getState

    public static void setGuess(final int guess) {
        Mastermind.sGuess = guess;
    } // setGuess

    public static int getGuess() {
        return sGuess;
    } // getGuess

    // Map the slotPositions to an index for each row on the board
    protected void map2(final int[] array, final int[][] dArray) {
        int k = 0;
        for (int i = 0; i < dArray.length; i++)
            for (int j = 0; j < dArray[i].length; j++) {
                dArray[i][j] = array[k];
                k++;
            }
    } // map2

    // Map the ImageView id's to an array index for simplicity when referencing later
    // Very inefficient, needs to be fixed.
    protected void inefficientMapping() {
        sSlotPosition[3] = R.id.peg40;
        sSlotPosition[2] = R.id.peg39;
        sSlotPosition[1] = R.id.peg38;
        sSlotPosition[0] = R.id.peg37;
        sSlotPosition[7] = R.id.peg36;
        sSlotPosition[6] = R.id.peg35;
        sSlotPosition[5] = R.id.peg34;
        sSlotPosition[4] = R.id.peg33;
        sSlotPosition[11] = R.id.peg32;
        sSlotPosition[10] = R.id.peg31;
        sSlotPosition[9] = R.id.peg30;
        sSlotPosition[8] = R.id.peg29;
        sSlotPosition[15] = R.id.peg28;
        sSlotPosition[14] = R.id.peg27;
        sSlotPosition[13] = R.id.peg26;
        sSlotPosition[12] = R.id.peg25;
        sSlotPosition[19] = R.id.peg24;
        sSlotPosition[18] = R.id.peg23;
        sSlotPosition[17] = R.id.peg22;
        sSlotPosition[16] = R.id.peg21;
        sSlotPosition[23] = R.id.peg20;
        sSlotPosition[22] = R.id.peg19;
        sSlotPosition[21] = R.id.peg18;
        sSlotPosition[20] = R.id.peg17;
        sSlotPosition[27] = R.id.peg16;
        sSlotPosition[26] = R.id.peg15;
        sSlotPosition[25] = R.id.peg14;
        sSlotPosition[24] = R.id.peg13;
        sSlotPosition[31] = R.id.peg12;
        sSlotPosition[30] = R.id.peg11;
        sSlotPosition[29] = R.id.peg10;
        sSlotPosition[28] = R.id.peg09;
        sSlotPosition[35] = R.id.peg08;
        sSlotPosition[34] = R.id.peg07;
        sSlotPosition[33] = R.id.peg06;
        sSlotPosition[32] = R.id.peg05;
        sSlotPosition[39] = R.id.peg04;
        sSlotPosition[38] = R.id.peg03;
        sSlotPosition[37] = R.id.peg02;
        sSlotPosition[36] = R.id.peg01;

        sSmallSlotPosition[0] = R.id.smallPeg40;
        sSmallSlotPosition[1] = R.id.smallPeg39;
        sSmallSlotPosition[2] = R.id.smallPeg38;
        sSmallSlotPosition[3] = R.id.smallPeg37;
        sSmallSlotPosition[4] = R.id.smallPeg36;
        sSmallSlotPosition[5] = R.id.smallPeg35;
        sSmallSlotPosition[6] = R.id.smallPeg34;
        sSmallSlotPosition[7] = R.id.smallPeg33;
        sSmallSlotPosition[8] = R.id.smallPeg32;
        sSmallSlotPosition[9] = R.id.smallPeg31;
        sSmallSlotPosition[10] = R.id.smallPeg30;
        sSmallSlotPosition[11] = R.id.smallPeg29;
        sSmallSlotPosition[12] = R.id.smallPeg28;
        sSmallSlotPosition[13] = R.id.smallPeg27;
        sSmallSlotPosition[14] = R.id.smallPeg26;
        sSmallSlotPosition[15] = R.id.smallPeg25;
        sSmallSlotPosition[16] = R.id.smallPeg24;
        sSmallSlotPosition[17] = R.id.smallPeg23;
        sSmallSlotPosition[18] = R.id.smallPeg22;
        sSmallSlotPosition[19] = R.id.smallPeg21;
        sSmallSlotPosition[20] = R.id.smallPeg20;
        sSmallSlotPosition[21] = R.id.smallPeg19;
        sSmallSlotPosition[22] = R.id.smallPeg18;
        sSmallSlotPosition[23] = R.id.smallPeg17;
        sSmallSlotPosition[24] = R.id.smallPeg16;
        sSmallSlotPosition[25] = R.id.smallPeg15;
        sSmallSlotPosition[26] = R.id.smallPeg14;
        sSmallSlotPosition[27] = R.id.smallPeg13;
        sSmallSlotPosition[28] = R.id.smallPeg12;
        sSmallSlotPosition[29] = R.id.smallPeg11;
        sSmallSlotPosition[30] = R.id.smallPeg10;
        sSmallSlotPosition[31] = R.id.smallPeg9;
        sSmallSlotPosition[32] = R.id.smallPeg8;
        sSmallSlotPosition[33] = R.id.smallPeg7;
        sSmallSlotPosition[34] = R.id.smallPeg6;
        sSmallSlotPosition[35] = R.id.smallPeg5;
        sSmallSlotPosition[36] = R.id.smallPeg4;
        sSmallSlotPosition[37] = R.id.smallPeg3;
        sSmallSlotPosition[38] = R.id.smallPeg2;
        sSmallSlotPosition[39] = R.id.smallPeg1;
    } // inefficientMapping

    protected void checkState() {
        for (int i = 0; i < sState.length; i++)
            if (sState[i]) {
                switch (i) {
                    case BLUE:
                        onBlue(findViewById(R.id.bluePeg));
                        break;
                    case GREEN:
                        onGreen(findViewById(R.id.greenPeg));
                        break;
                    case RED:
                        onRed(findViewById(R.id.redPeg));
                        break;
                    case PURPLE:
                        onPurple(findViewById(R.id.purplePeg));
                        break;
                    case WHITE:
                        onWhite(findViewById(R.id.whitePeg));
                        break;
                    case YELLOW:
                        onYellow(findViewById(R.id.yellowPeg));
                        break;
                    default:
                        break;
                } // switch
                return;
            } // if
    } // checkState

    public void onBlue(final View view) {
        if (!sState[BLUE]) {
            checkState();
            ((ImageView) view).setImageResource(R.drawable.bluepicked);
            sState[BLUE] = true;
        } else {
            ((ImageView) view).setImageResource(R.drawable.bluepeg);
            sState[BLUE] = false;
        }
    } // onBlue

    public void onGreen(final View view) {
        if (!sState[GREEN]) {
            checkState();
            sState[GREEN] = true;
            ((ImageView) view).setImageResource(R.drawable.greenpicked);
        } else {
            ((ImageView) view).setImageResource(R.drawable.greenpeg);
            sState[GREEN] = false;
        }
    } // onGreen

    public void onPurple(final View view) {
        if (!sState[PURPLE]) {
            checkState();
            sState[PURPLE] = true;
            ((ImageView) view).setImageResource(R.drawable.purplepicked);
        } else {
            ((ImageView) view).setImageResource(R.drawable.purplepeg);
            sState[PURPLE] = false;
        }
    } // onPurple

    public void onRed(final View view) {
        if (!sState[RED]) {
            checkState();
            sState[RED] = true;
            ((ImageView) view).setImageResource(R.drawable.redpicked);
        } else {
            ((ImageView) view).setImageResource(R.drawable.redpeg);
            sState[RED] = false;
        }
    } // onRed

    public void onWhite(final View view) {
        if (!sState[WHITE]) {
            checkState();
            sState[WHITE] = true;
            ((ImageView) view).setImageResource(R.drawable.whitepicked);
        } else {
            sState[WHITE] = false;
            ((ImageView) view).setImageResource(R.drawable.whitepeg);
        }
    } // onWhite

    public void onYellow(final View view) {
        if (!sState[YELLOW]) {
            checkState();
            ((ImageView) view).setImageResource(R.drawable.yellowpicked);
            sState[YELLOW] = true;
        } else {
            sState[YELLOW] = false;
            ((ImageView) view).setImageResource(R.drawable.yellowpeg);
        }
    } // onYellow

    protected static boolean checkGuess(final int viewID) {
        for (int i = 0; i < pegSlots[sGuess].length; i++)
            if (pegSlots[sGuess][i] == viewID) return true;
        return false;
    } // checkGuess

    protected void checkPegs() {
        boolean check = true;
        ImageView view = null;
        switch (sGuess) {
            case 0:
                for (int i = 0; i < 4; i++)
                    if (((ImageView) findViewById(sSlotPosition[i])).getDrawable() == null) {
                        check = false;
                        break;
                    }
                view = (ImageView) findViewById(R.id.confirm10);
                break;

            case 1:
                for (int i = 4; i < 8; i++)
                    if (((ImageView) findViewById(sSlotPosition[i])).getDrawable() == null) {
                        check = false;
                        break;
                    }
                view = (ImageView) findViewById(R.id.confirm09);
                break;

            case 2:
                for (int i = 8; i < 12; i++)
                    if (((ImageView) findViewById(sSlotPosition[i])).getDrawable() == null) {
                        check = false;
                        break;
                    }
                view = (ImageView) findViewById(R.id.confirm08);
                break;

            case 3:
                for (int i = 12; i < 16; i++)
                    if (((ImageView) findViewById(sSlotPosition[i])).getDrawable() == null) {
                        check = false;
                        break;
                    }
                view = (ImageView) findViewById(R.id.confirm07);
                break;

            case 4:
                for (int i = 16; i < 20; i++)
                    if (((ImageView) findViewById(sSlotPosition[i])).getDrawable() == null) {
                        check = false;
                        break;
                    }
                view = (ImageView) findViewById(R.id.confirm06);
                break;

            case 5:
                for (int i = 20; i < 24; i++)
                    if (((ImageView) findViewById(sSlotPosition[i])).getDrawable() == null) {
                        check = false;
                        break;
                    }
                view = (ImageView) findViewById(R.id.confirm05);
                break;

            case 6:
                for (int i = 24; i < 28; i++)
                    if (((ImageView) findViewById(sSlotPosition[i])).getDrawable() == null) {
                        check = false;
                        break;
                    }
                view = (ImageView) findViewById(R.id.confirm04);
                break;

            case 7:
                for (int i = 28; i < 32; i++)
                    if (((ImageView) findViewById(sSlotPosition[i])).getDrawable() == null) {
                        check = false;
                        break;
                    }
                view = (ImageView) findViewById(R.id.confirm03);
                break;

            case 8:
                for (int i = 32; i < 36; i++)
                    if (((ImageView) findViewById(sSlotPosition[i])).getDrawable() == null) {
                        check = false;
                        break;
                    }
                view = (ImageView) findViewById(R.id.confirm02);
                break;

            case 9:
                for (int i = 36; i < 40; i++)
                    if (((ImageView) findViewById(sSlotPosition[i])).getDrawable() == null) {
                        check = false;
                        break;
                    }
                view = (ImageView) findViewById(R.id.confirm01);
                break;
            default:
                break;
        }
        if (check) {
            view.setImageDrawable(sResources.getDrawable(R.drawable.confirm));
            view.setClickable(true);
        } else {
            view.setImageDrawable(sResources.getDrawable(R.drawable.row));
            view.setClickable(false);
        }
    } // checkPegs

    public void select(final View view) {
        if (!checkGuess(view.getId())) return;
        for (int i = 0; i < sState.length; i++)
            if (sState[i]) {
                switch (i) {
                    case BLUE:
                        ((ImageView) view).setImageDrawable(sResources
                                .getDrawable(R.drawable.bluepeg));
                        view.setTag(R.drawable.bluepeg);
                        onBlue(findViewById(R.id.bluePeg));
                        break;
                    case RED:
                        ((ImageView) view).setImageDrawable(sResources
                                .getDrawable(R.drawable.redpeg));
                        view.setTag(R.drawable.redpeg);
                        onRed(findViewById(R.id.redPeg));
                        break;
                    case WHITE:
                        ((ImageView) view).setImageDrawable(sResources
                                .getDrawable(R.drawable.whitepeg));
                        view.setTag(R.drawable.whitepeg);
                        onWhite(findViewById(R.id.whitePeg));
                        break;
                    case YELLOW:
                        ((ImageView) view).setImageDrawable(sResources
                                .getDrawable(R.drawable.yellowpeg));
                        view.setTag(R.drawable.yellowpeg);
                        onYellow(findViewById(R.id.yellowPeg));
                        break;
                    case GREEN:
                        ((ImageView) view).setImageDrawable(sResources
                                .getDrawable(R.drawable.greenpeg));
                        view.setTag(R.drawable.greenpeg);
                        onGreen(findViewById(R.id.greenPeg));
                        break;
                    case PURPLE:
                        ((ImageView) view).setImageDrawable(sResources
                                .getDrawable(R.drawable.purplepeg));
                        view.setTag(R.drawable.purplepeg);
                        onPurple(findViewById(R.id.purplePeg));
                        break;
                    default:
                        break;
                }
                checkPegs();
                return;
            }
        ((ImageView) view).setImageDrawable(null);
        view.setTag(R.drawable.pegslot);
        checkPegs();
    } // select

    protected int[] parseAttempt(int[] tags) {
        final int[] attempt = new int[MAX_PEGS];
        for (int i = 0; i < MAX_PEGS; i++)
            if (tags[i] == sPegs[0])
                attempt[i] = sPegs[0];
            else if (tags[i] == sPegs[1])
                attempt[i] = sPegs[1];
            else if (tags[i] == sPegs[2])
                attempt[i] = sPegs[2];
            else if (tags[i] == sPegs[3])
                attempt[i] = sPegs[3];
            else if (tags[i] == sPegs[4])
                attempt[i] = sPegs[4];
            else if (tags[i] == sPegs[5]) attempt[i] = sPegs[5];

        return attempt;
    } // parseAttempt

    protected int[] getTags() {
        final int[] tags = new int[MAX_PEGS];
        for (int i = 0; i < MAX_PEGS; i++)
            tags[i] = (Integer) findViewById(pegSlots[sGuess][i]).getTag();
        return tags;
    } // getTags

    private static final int CORRECT_COLOUR_POSITION = 2;
    private static final int CORRECT_COLOUR_WRONG_POSITION = 1;

    protected boolean parseResponse(final int[] resp) {
        Arrays.sort(resp);
        for (int i = 0; i < MAX_PEGS; i++) {
            if (resp[i] == CORRECT_COLOUR_POSITION)
                ((ImageView) findViewById(sSmallPegSlots[sGuess][i]))
                        .setImageResource(R.drawable.smallred);
            else if (resp[i] == CORRECT_COLOUR_WRONG_POSITION)
                ((ImageView) findViewById(sSmallPegSlots[sGuess][i]))
                        .setImageResource(R.drawable.smallwhite);
        }
        // Sorted in ascending order, therefore if first element is correct peg
        // colour and position, all are correct
        if (resp[0] == CORRECT_COLOUR_POSITION)
            return true;
        else return false;
    } // parseResponse

    protected int[] makeResponse(final boolean[] pos, final boolean[] col) {
        final int[] resp = new int[MAX_PEGS];
        for (int i = 0; i < MAX_PEGS; i++)
            // If in right position, return 2
            if (pos[i])
                resp[i] = CORRECT_COLOUR_POSITION;
            else
            // If in wrong position, return 1
            if (col[i]) resp[i] = CORRECT_COLOUR_WRONG_POSITION;

        return resp;
    } // makeResponse

    public void confirm(final View view) {
        view.setClickable(false);
        // Get the tags for the current guess and
        // Parse the combination provided by the user into an array
        final int[] attempt = parseAttempt(getTags());

        // Check the attempt against the solution
        // First check if a peg is the same colour and in the right position
        final boolean[] pos = new boolean[MAX_PEGS], col = new boolean[MAX_PEGS];
        for (int i = 0; i < MAX_PEGS; i++)
            pos[i] = mGame.checkPos(attempt[i], i);

        // Then check if the peg is in the wrong position
        for (int i = 0; i < MAX_PEGS; i++)
            if (!pos[i]) col[i] = mGame.checkPeg(attempt[i]);

        // Pass the result onto the user through use of the smaller pegs
        final boolean correct = parseResponse(makeResponse(pos, col));

        sGuess++;
        // If the attempt is correct, end the game
        if (correct)
            endGame();
        else {
            // Lose
            if (sGuess == MAX_GUESSES) {
                loseGame();
            } else {
                checkPegs();
                mGame.resetStates();
            }
        } // else
    } // confirm

    protected void loseGame() {
        final Intent again = new Intent(this, this.getClass());
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You failed to crack the code!").setCancelable(false)
                .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(again);
                        finish();
                    }
                }).setNegativeButton("Quit to Main Menu", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    } // loseGame

    protected void endGame() {
        final Intent again = new Intent(this, Mastermind.class);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Congratulations! You cracked the code in " + sGuess + " attempts!")
                .setCancelable(false)
                .setPositiveButton("Start Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(again);
                        finish();
                    }
                }).setNegativeButton("Quit to Main Menu", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    } // endGame

} // class Mastermind