package taxomania.games.mastermind;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tariq on 19/04/15.
 */
public class HighScoresActivity extends ActionBarActivity {
    private RecyclerView mRecyclerView;
    private HighScoreAdapter mAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_scores);
        mRecyclerView = (RecyclerView) findViewById(R.id.highScores);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter = new HighScoreAdapter());
        new PrintHighScores().execute(false);
    } // onCreate(Bundle)

    private static final int MENU_RESET = Menu.FIRST;

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_RESET, 0, "Reset High Scores");
        return true;
    } // onCreateOptionsMenu(Menu)

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case MENU_RESET:
                new PrintHighScores().execute(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } // switch
    } // onOptionsItemSelected(MenuItem)

    private final class PrintHighScores extends AsyncTask<Boolean, Void, List<HighScore>> {
        @Override
        protected List<HighScore> doInBackground(final Boolean... delete) {
            final DataHelper dh = DataHelper.getInstance(HighScoresActivity.this);
            if (delete[0]) {
                dh.deleteAll();
            } // if
            return dh.selectAllScores();
        } // doInBackground(Boolean)

        @Override
        protected void onPostExecute(final List<HighScore> scores) {
            mAdapter.setScores(scores);
        } // onPostExecute(List<HighScore>)
    } // class PrintHighScores

    public static class HighScoreAdapter extends RecyclerView.Adapter<HighScoreAdapter.HighScoreViewHolder> {
        private final List<HighScore> mScores = new ArrayList<>();

        public void setScores(@NonNull List<HighScore> scores) {
            mScores.clear();
            mScores.addAll(scores);
            notifyDataSetChanged();
        }

        private HighScoreAdapter() {
        }

        @Override
        public HighScoreViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            switch (viewType){
                case 0: {
                    View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.high_score_header, parent, false);
                    return new HighScoreViewHolder(cardView);
                }
                case 1: {
                    View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.high_score_item, parent, false);
                    return new HighScoreViewHolder(cardView);
                }
                default:
                    return null;
            }
        }

        @Override
        public void onBindViewHolder(final HighScoreViewHolder holder, final int position) {
            if (position==0) return;
            HighScore score = mScores.get(position-1);
            score.rank = position;
            holder.setHighScore(score);
        }

        @Override
        public int getItemCount() {
            return 1 + mScores.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position == 0) ? 0 : 1;
        }

        @Override
        public long getItemId(final int position) {
            return position;
        }

        public static class HighScoreViewHolder extends RecyclerView.ViewHolder {

            public HighScoreViewHolder(final View itemView) {
                super(itemView);
            }

            void setHighScore(HighScore score){
                ((TextView)itemView.findViewById(R.id.rank)).setText(""+score.rank);
                ((TextView)itemView.findViewById(R.id.name)).setText(score.name);
                ((TextView)itemView.findViewById(R.id.time)).setText(score.getTime());
                ((TextView)itemView.findViewById(R.id.attempts)).setText(""+score.attempts);
            }
        }
    }

}
