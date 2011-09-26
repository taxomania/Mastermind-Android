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

public class DataHelper {
    public static final class Scores implements BaseColumns {
        private static final String TABLE_NAME = "tbl_scores";

        public static final String NAME = "name";
        public static final String TIME = "time";
        public static final String GUESSES = "guesses";

        private Scores() {
            super();
            throw new AssertionError();
        } // Scores()

    } // Scores

    private final Context mContext;

    private volatile SQLiteDatabase mDb = null;

    private static DataHelper sDataHelper = null;

    public static synchronized DataHelper getInstance(final Context context) {
        if (sDataHelper == null) {
            sDataHelper = new DataHelper(context);
        } // if
        return sDataHelper;
    } // getInstance(Context)

    private DataHelper(final Context context) {
        mContext = context;
        mDb = new OpenHelper(mContext).getWritableDatabase();
    } // DataHelper(Context)

    public int getCount() {
        return (int) count();
    } // getCount()

    public long insert(final String name, final int time, final int guesses) {
        final ContentValues cv = new ContentValues(3);
        cv.put("name", name);
        cv.put("time", time);
        cv.put("guesses", guesses);
        return mDb.insert(Scores.TABLE_NAME, null, cv);
    } // insert(String, int, int)

    public void deleteAll() {
        mDb.delete(Scores.TABLE_NAME, null, null);
    } // deleteAll()

    public List<Map<String, Object>> selectAll() {
        final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        final Cursor cursor = mDb.query(Scores.TABLE_NAME, new String[] { Scores.NAME, Scores.TIME,
                Scores.GUESSES }, null, null, null, null, Scores.TIME + " asc, " + Scores.GUESSES
                + " asc");
        if (cursor.moveToFirst()) {
            do {
                final Map<String, Object> map = new HashMap<String, Object>();
                map.put(cursor.getColumnName(0), cursor.getString(0));
                map.put(cursor.getColumnName(1), cursor.getInt(1));
                map.put(cursor.getColumnName(2), cursor.getInt(2));
                list.add(map);
            } while (cursor.moveToNext());
        } // if
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        } // if
        return list;
    } // selectAll()

    public List<Integer> selectAllTimes() {
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
    } // selectAllTimes()

    private long count() {
        final String countStatement = "SELECT count(*) from " + Scores.TABLE_NAME;
        final SQLiteStatement s = mDb.compileStatement(countStatement);
        return s.simpleQueryForLong();
    } // count()

    private static class OpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "mastermind_free.db";
        private static final int DATABASE_VERSION = 2;

        OpenHelper(final Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        } // OpenHelper(Context)

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + Scores.TABLE_NAME + " (" + Scores._ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Scores.NAME + " TEXT, "
                    + Scores.TIME + " INTEGER, " + Scores.GUESSES + " INTEGER)");
        } // onCreate(SQLiteDatabase)

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("Example", "Upgrading database, this will drop tables and recreate.");
            db.execSQL("DROP TABLE IF EXISTS " + Scores.TABLE_NAME);
            onCreate(db);
        } // onUpgrade(SQLiteDatabase, int, int)
    } // OpenHelper
} // DataHelper
