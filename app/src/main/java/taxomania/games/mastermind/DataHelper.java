package taxomania.games.mastermind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;
import android.util.Log;

final class DataHelper {
    static final class Scores implements BaseColumns {
        private static final String TABLE_NAME = "tbl_scores";

        static final String NAME = "name";
        static final String TIME = "time";
        static final String GUESSES = "guesses";

        private Scores() {
            throw new AssertionError();
        } // Scores()
    } // class Scores

    private volatile SQLiteDatabase mDb = null;
    private static DataHelper sDataHelper = null;

    static synchronized DataHelper getInstance(final Context context) {
        if (sDataHelper == null) {
            sDataHelper = new DataHelper(context);
        } // if
        return sDataHelper;
    } // getInstance(Context)

    private DataHelper(final Context context) {
        mDb = new OpenHelper(context.getApplicationContext()).getWritableDatabase();
    } // DataHelper(Context)

    int getCount() {
        return (int) count();
    } // getCount()

    long insert(final String name, final int time, final int guesses) {
        final ContentValues cv = new ContentValues(3);
        cv.put(Scores.NAME, name);
        cv.put(Scores.TIME, time);
        cv.put(Scores.GUESSES, guesses);
        return mDb.insert(Scores.TABLE_NAME, null, cv);
    } // insert(String, int, int)

    void deleteAll() {
        mDb.delete(Scores.TABLE_NAME, null, null);
    } // deleteAll()

    List<HighScore> selectAllScores() {
        final List<HighScore> list = new ArrayList<>();
        final Cursor cursor = mDb.query(Scores.TABLE_NAME, new String[] { Scores.NAME, Scores.TIME,
                Scores.GUESSES }, null, null, null, null, Scores.TIME + " asc, " + Scores.GUESSES
                + " asc");
        if (cursor.moveToFirst()) {
            do {
                final HighScore score = new HighScore();
                score.name = cursor.getString(0);
                score.time = cursor.getInt(1);
                score.attempts = cursor.getInt(2);
                list.add(score);
            } while (cursor.moveToNext());
        } // if
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        } // if
        return list;
    } // selectAllScores(List<HighScore>)


    List<Integer> selectAllTimes() {
        final List<Integer> list = new ArrayList<Integer>();
        final Cursor cursor = mDb.query(Scores.TABLE_NAME, new String[] { Scores.TIME }, null,
                null, null, null, Scores.TIME + " asc, " + Scores.GUESSES + " asc");
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        } // if
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        } // if
        return list;
    } // selectAllTimes(List<Integer>)

    private long count() {
        final String countStatement = "SELECT count(*) from " + Scores.TABLE_NAME;
        final SQLiteStatement s = mDb.compileStatement(countStatement);
        return s.simpleQueryForLong();
    } // count()

    private static final class OpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "mastermind_free.db";
        private static final int DATABASE_VERSION = 2;

        OpenHelper(final Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        } // OpenHelper(Context)

        @Override
        public void onCreate(final SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + Scores.TABLE_NAME + " (" + Scores._ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Scores.NAME + " TEXT, "
                    + Scores.TIME + " INTEGER, " + Scores.GUESSES + " INTEGER)");
        } // onCreate(SQLiteDatabase)

        @Override
        public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
            Log.w("Example", "Upgrading database, this will drop tables and recreate.");
            db.execSQL("DROP TABLE IF EXISTS " + Scores.TABLE_NAME);
            onCreate(db);
        } // onUpgrade(SQLiteDatabase, int, int)
    } // class OpenHelper
} // class DataHelper
