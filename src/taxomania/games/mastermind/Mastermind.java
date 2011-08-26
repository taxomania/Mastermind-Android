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
import android.view.MenuInflater;
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
    protected static final int BLUE = 0, GREEN = 1, RED = 2, WHITE = 3,
            YELLOW = 4, PURPLE = 5, TOTAL_PEG_SLOTS = 40, MAX_PEGS = 4,
            MAX_GUESSES = 10;
    // state[] shows which colour peg has been selected
    protected static Boolean[] state = new Boolean[Engine.TOTAL_NO_PEGS];
    // slotPosition[] maps peg slots to view IDs
    protected static int[] slotPosition = new int[TOTAL_PEG_SLOTS],
            smallSlotPosition = new int[TOTAL_PEG_SLOTS];
    protected static int[][] pegSlots = new int[MAX_GUESSES][MAX_PEGS],
            smallPegSlots = new int[MAX_GUESSES][MAX_PEGS];
    protected static int guess;
    protected static Resources res;
    protected static final int[] pegs = { R.drawable.bluepeg,
            R.drawable.greenpeg, R.drawable.redpeg, R.drawable.whitepeg,
            R.drawable.yellowpeg, R.drawable.purplepeg };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mastermind);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        res = getResources();
        // Initialise the state array
        for (int i = 0; i < state.length; i++)
            state[i] = false;

        // Map the peg slots on the board to an array index
        inefficientMapping();
        map2(slotPosition, pegSlots);
        map2(smallSlotPosition, smallPegSlots);

        // New game, create a new combination
        mGame = new Engine();
        guess = 0;
        findViewById(R.id.confirm10).setClickable(false);
    }


    protected static final int MENU_NEW_GAME = Menu.FIRST;
    protected static final int MENU_INSTRUCTIONS = Menu.FIRST+1;

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

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void setGame(Engine game) {
        mGame = game;
    }

    public Engine getGame() {
        return mGame;
    }

    public static void setState(Boolean[] state) {
        Mastermind.state = state;
    }

    public static Boolean[] getState() {
        return state;
    }

    public static void setGuess(int guess) {
        Mastermind.guess = guess;
    }

    public static int getGuess() {
        return guess;
    }

    // Map the slotPositions to an index for each row on the board
    protected void map2(int[] array, int[][] dArray) {
        int k = 0;
        for (int i = 0; i < dArray.length; i++)
            for (int j = 0; j < dArray[i].length; j++) {
                dArray[i][j] = array[k];
                k++;
            }
    } // map2

    // Map the ImageView id's to an array index for simplicity when referencing
    // later
    // Very inefficient, needs to be fixed.
    protected void inefficientMapping() {
        slotPosition[3] = R.id.peg40;
        slotPosition[2] = R.id.peg39;
        slotPosition[1] = R.id.peg38;
        slotPosition[0] = R.id.peg37;
        slotPosition[7] = R.id.peg36;
        slotPosition[6] = R.id.peg35;
        slotPosition[5] = R.id.peg34;
        slotPosition[4] = R.id.peg33;
        slotPosition[11] = R.id.peg32;
        slotPosition[10] = R.id.peg31;
        slotPosition[9] = R.id.peg30;
        slotPosition[8] = R.id.peg29;
        slotPosition[15] = R.id.peg28;
        slotPosition[14] = R.id.peg27;
        slotPosition[13] = R.id.peg26;
        slotPosition[12] = R.id.peg25;
        slotPosition[19] = R.id.peg24;
        slotPosition[18] = R.id.peg23;
        slotPosition[17] = R.id.peg22;
        slotPosition[16] = R.id.peg21;
        slotPosition[23] = R.id.peg20;
        slotPosition[22] = R.id.peg19;
        slotPosition[21] = R.id.peg18;
        slotPosition[20] = R.id.peg17;
        slotPosition[27] = R.id.peg16;
        slotPosition[26] = R.id.peg15;
        slotPosition[25] = R.id.peg14;
        slotPosition[24] = R.id.peg13;
        slotPosition[31] = R.id.peg12;
        slotPosition[30] = R.id.peg11;
        slotPosition[29] = R.id.peg10;
        slotPosition[28] = R.id.peg09;
        slotPosition[35] = R.id.peg08;
        slotPosition[34] = R.id.peg07;
        slotPosition[33] = R.id.peg06;
        slotPosition[32] = R.id.peg05;
        slotPosition[39] = R.id.peg04;
        slotPosition[38] = R.id.peg03;
        slotPosition[37] = R.id.peg02;
        slotPosition[36] = R.id.peg01;

        smallSlotPosition[0] = R.id.smallPeg40;
        smallSlotPosition[1] = R.id.smallPeg39;
        smallSlotPosition[2] = R.id.smallPeg38;
        smallSlotPosition[3] = R.id.smallPeg37;
        smallSlotPosition[4] = R.id.smallPeg36;
        smallSlotPosition[5] = R.id.smallPeg35;
        smallSlotPosition[6] = R.id.smallPeg34;
        smallSlotPosition[7] = R.id.smallPeg33;
        smallSlotPosition[8] = R.id.smallPeg32;
        smallSlotPosition[9] = R.id.smallPeg31;
        smallSlotPosition[10] = R.id.smallPeg30;
        smallSlotPosition[11] = R.id.smallPeg29;
        smallSlotPosition[12] = R.id.smallPeg28;
        smallSlotPosition[13] = R.id.smallPeg27;
        smallSlotPosition[14] = R.id.smallPeg26;
        smallSlotPosition[15] = R.id.smallPeg25;
        smallSlotPosition[16] = R.id.smallPeg24;
        smallSlotPosition[17] = R.id.smallPeg23;
        smallSlotPosition[18] = R.id.smallPeg22;
        smallSlotPosition[19] = R.id.smallPeg21;
        smallSlotPosition[20] = R.id.smallPeg20;
        smallSlotPosition[21] = R.id.smallPeg19;
        smallSlotPosition[22] = R.id.smallPeg18;
        smallSlotPosition[23] = R.id.smallPeg17;
        smallSlotPosition[24] = R.id.smallPeg16;
        smallSlotPosition[25] = R.id.smallPeg15;
        smallSlotPosition[26] = R.id.smallPeg14;
        smallSlotPosition[27] = R.id.smallPeg13;
        smallSlotPosition[28] = R.id.smallPeg12;
        smallSlotPosition[29] = R.id.smallPeg11;
        smallSlotPosition[30] = R.id.smallPeg10;
        smallSlotPosition[31] = R.id.smallPeg9;
        smallSlotPosition[32] = R.id.smallPeg8;
        smallSlotPosition[33] = R.id.smallPeg7;
        smallSlotPosition[34] = R.id.smallPeg6;
        smallSlotPosition[35] = R.id.smallPeg5;
        smallSlotPosition[36] = R.id.smallPeg4;
        smallSlotPosition[37] = R.id.smallPeg3;
        smallSlotPosition[38] = R.id.smallPeg2;
        smallSlotPosition[39] = R.id.smallPeg1;
    } // inefficientMapping

    protected void checkState() {
        for (int i = 0; i < state.length; i++)
            if (state[i]) {
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

    public void onBlue(View view) {
        if (!state[BLUE]) {
            checkState();
            ((ImageView) view).setImageResource(R.drawable.bluepicked);
            state[BLUE] = true;
        } else {
            ((ImageView) view).setImageResource(R.drawable.bluepeg);
            state[BLUE] = false;
        }
    }

    public void onGreen(View view) {
        if (!state[GREEN]) {
            checkState();
            state[GREEN] = true;
            ((ImageView) view).setImageResource(R.drawable.greenpicked);
        } else {
            ((ImageView) view).setImageResource(R.drawable.greenpeg);
            state[GREEN] = false;
        }
    }

    public void onPurple(View view) {
        if (!state[PURPLE]) {
            checkState();
            state[PURPLE] = true;
            ((ImageView) view).setImageResource(R.drawable.purplepicked);
        } else {
            ((ImageView) view).setImageResource(R.drawable.purplepeg);
            state[PURPLE] = false;
        }
    }

    public void onRed(View view) {
        if (!state[RED]) {
            checkState();
            state[RED] = true;
            ((ImageView) view).setImageResource(R.drawable.redpicked);
        } else {
            ((ImageView) view).setImageResource(R.drawable.redpeg);
            state[RED] = false;
        }
    }

    public void onWhite(View view) {
        if (!state[WHITE]) {
            checkState();
            state[WHITE] = true;
            ((ImageView) view).setImageResource(R.drawable.whitepicked);
        } else {
            state[WHITE] = false;
            ((ImageView) view).setImageResource(R.drawable.whitepeg);
        }
    }

    public void onYellow(View view) {
        if (!state[YELLOW]) {
            checkState();
            ((ImageView) view).setImageResource(R.drawable.yellowpicked);
            state[YELLOW] = true;
        } else {
            state[YELLOW] = false;
            ((ImageView) view).setImageResource(R.drawable.yellowpeg);
        }
    }

    protected static boolean checkGuess(int viewID) {
        for (int i = 0; i < pegSlots[guess].length; i++)
            if (pegSlots[guess][i] == viewID)
                return true;
        return false;
    }

    protected void checkPegs() {
        boolean check = true;
        ImageView view = null;
        switch (guess) {
        case 0:
            for (int i = 0; i < 4; i++)
                if (((ImageView) findViewById(slotPosition[i])).getDrawable() == null) {
                    check = false;
                    break;
                }
            view = (ImageView) findViewById(R.id.confirm10);
            break;

        case 1:
            for (int i = 4; i < 8; i++)
                if (((ImageView) findViewById(slotPosition[i])).getDrawable() == null) {
                    check = false;
                    break;
                }
            view = (ImageView) findViewById(R.id.confirm09);
            break;

        case 2:
            for (int i = 8; i < 12; i++)
                if (((ImageView) findViewById(slotPosition[i])).getDrawable() == null) {
                    check = false;
                    break;
                }
            view = (ImageView) findViewById(R.id.confirm08);
            break;

        case 3:
            for (int i = 12; i < 16; i++)
                if (((ImageView) findViewById(slotPosition[i])).getDrawable() == null) {
                    check = false;
                    break;
                }
            view = (ImageView) findViewById(R.id.confirm07);
            break;

        case 4:
            for (int i = 16; i < 20; i++)
                if (((ImageView) findViewById(slotPosition[i])).getDrawable() == null) {
                    check = false;
                    break;
                }
            view = (ImageView) findViewById(R.id.confirm06);
            break;

        case 5:
            for (int i = 20; i < 24; i++)
                if (((ImageView) findViewById(slotPosition[i])).getDrawable() == null) {
                    check = false;
                    break;
                }
            view = (ImageView) findViewById(R.id.confirm05);
            break;

        case 6:
            for (int i = 24; i < 28; i++)
                if (((ImageView) findViewById(slotPosition[i])).getDrawable() == null) {
                    check = false;
                    break;
                }
            view = (ImageView) findViewById(R.id.confirm04);
            break;

        case 7:
            for (int i = 28; i < 32; i++)
                if (((ImageView) findViewById(slotPosition[i])).getDrawable() == null) {
                    check = false;
                    break;
                }
            view = (ImageView) findViewById(R.id.confirm03);
            break;

        case 8:
            for (int i = 32; i < 36; i++)
                if (((ImageView) findViewById(slotPosition[i])).getDrawable() == null) {
                    check = false;
                    break;
                }
            view = (ImageView) findViewById(R.id.confirm02);
            break;

        case 9:
            for (int i = 36; i < 40; i++)
                if (((ImageView) findViewById(slotPosition[i])).getDrawable() == null) {
                    check = false;
                    break;
                }
            view = (ImageView) findViewById(R.id.confirm01);
            break;
        default:
            break;
        }
        if (check) {
            view.setImageDrawable(res.getDrawable(R.drawable.confirm));
            view.setClickable(true);
        } else {
            view.setImageDrawable(res.getDrawable(R.drawable.row));
            view.setClickable(false);
        }

    }

    public void select(View view) {
        if (!checkGuess(view.getId()))
            return;
        for (int i = 0; i < state.length; i++)
            if (state[i]) {
                switch (i) {
                case BLUE:
                    ((ImageView) view).setImageDrawable(res
                            .getDrawable(R.drawable.bluepeg));
                    view.setTag(R.drawable.bluepeg);
                    onBlue(findViewById(R.id.bluePeg));
                    break;
                case RED:
                    ((ImageView) view).setImageDrawable(res
                            .getDrawable(R.drawable.redpeg));
                    view.setTag(R.drawable.redpeg);
                    onRed(findViewById(R.id.redPeg));
                    break;
                case WHITE:
                    ((ImageView) view).setImageDrawable(res
                            .getDrawable(R.drawable.whitepeg));
                    view.setTag(R.drawable.whitepeg);
                    onWhite(findViewById(R.id.whitePeg));
                    break;
                case YELLOW:
                    ((ImageView) view).setImageDrawable(res
                            .getDrawable(R.drawable.yellowpeg));
                    view.setTag(R.drawable.yellowpeg);
                    onYellow(findViewById(R.id.yellowPeg));
                    break;
                case GREEN:
                    ((ImageView) view).setImageDrawable(res
                            .getDrawable(R.drawable.greenpeg));
                    view.setTag(R.drawable.greenpeg);
                    onGreen(findViewById(R.id.greenPeg));
                    break;
                case PURPLE:
                    ((ImageView) view).setImageDrawable(res
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
    }

    protected int[] parseAttempt(int[] tags) {
        int[] attempt = new int[MAX_PEGS];
        for (int i = 0; i < MAX_PEGS; i++)
            if (tags[i] == pegs[0])
                attempt[i] = pegs[0];
            else if (tags[i] == pegs[1])
                attempt[i] = pegs[1];
            else if (tags[i] == pegs[2])
                attempt[i] = pegs[2];
            else if (tags[i] == pegs[3])
                attempt[i] = pegs[3];
            else if (tags[i] == pegs[4])
                attempt[i] = pegs[4];
            else if (tags[i] == pegs[5])
                attempt[i] = pegs[5];

        return attempt;
    }

    protected boolean parseResponse(int[] resp) {
        Arrays.sort(resp);
        for (int i = 0; i < MAX_PEGS; i++) {
            if (resp[i] == 2)
                ((ImageView) findViewById(smallPegSlots[guess][i]))
                        .setImageResource(R.drawable.smallred);
            else if (resp[i] == 1)
                ((ImageView) findViewById(smallPegSlots[guess][i]))
                        .setImageResource(R.drawable.smallwhite);
        }
        // Sorted in ascending order, therefore if first element is correct peg
        // colour and position, all are correct
        if (resp[0] == 2)
            return true;
        else
            return false;
    }

    protected int[] getTags() {
        int[] tags = new int[MAX_PEGS];
        for (int i = 0; i < MAX_PEGS; i++)
            tags[i] = (Integer) findViewById(pegSlots[guess][i]).getTag();
        return tags;
    }

    protected int[] makeResponse(boolean[] pos, boolean[] col) {
        int[] resp = new int[MAX_PEGS];
        for (int i = 0; i < MAX_PEGS; i++)
            // If in right position, return 2
            if (pos[i])
                resp[i] = 2;
            else
            // If in wrong position, return 1
            if (col[i])
                resp[i] = 1;

        return resp;
    }

    public void confirm(View view) {
        view.setClickable(false);
        // Get the tags for the current guess and
        // Parse the combination provided by the user into an array
        int[] attempt = parseAttempt(getTags());

        // Check the attempt against the solution
        // First check if a peg is the same colour and in the right position
        boolean[] pos = new boolean[MAX_PEGS], col = new boolean[MAX_PEGS];
        for (int i = 0; i < MAX_PEGS; i++)
            pos[i] = mGame.checkPos(attempt[i], i);

        // Then check if the peg is in the wrong position
        for (int i = 0; i < MAX_PEGS; i++)
            if (!pos[i])
                col[i] = mGame.checkPeg(attempt[i]);

        // Pass the result onto the user through use of the smaller pegs
        boolean correct = parseResponse(makeResponse(pos, col));

        guess++;
        // If the attempt is correct, end the game
        if (correct) endGame();
        else {
            // Lose
            if (guess == MAX_GUESSES) {loseGame();}
            else {
                checkPegs();
                mGame.resetStates();
            }
        } // else
    } // confirm

    protected void loseGame(){
        final Intent again = new Intent(this, this.getClass());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You failed to crack the code!")
                .setCancelable(false)
                .setPositiveButton("Try Again",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int id) {
                                startActivity(again);
                                finish();
                            }
                        })
                .setNegativeButton("Quit to Main Menu",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int id) {
                                finish();
                            }
                        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    protected void endGame() {
        final Intent again = new Intent(this, Mastermind.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(
                "Congratulations! You cracked the code in " + guess
                        + " attempts!")
                .setCancelable(false)
                .setPositiveButton("Start Again",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(again);
                                finish();
                            }
                        })
                .setNegativeButton("Quit to Main Menu",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });

        AlertDialog alert = builder.create();
        alert.show();
    }

} // class Mastermind