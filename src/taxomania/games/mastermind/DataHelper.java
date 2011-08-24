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
import android.provider.BaseColumns;
import android.util.Log;

public class DataHelper {
    public static final class Scores implements BaseColumns
    {
        private static final String TABLE_NAME = "tbl_scores";

        public static final String NAME = "name";
        public static final String TIME = "time";
        public static final String GUESSES = "guesses";

        private Scores()
        {
            super();
            throw new AssertionError();
        } // Scores

    } // Scores

    private Context mContext;
    private volatile SQLiteDatabase mDb = null;

    private static DataHelper sDataHelper = null;

    public static synchronized DataHelper getInstance(final Context context)
    {
        if (sDataHelper == null)
        {
            sDataHelper = new DataHelper(context);
        } // if
        return sDataHelper;
    } // getInstance

    private DataHelper(final Context context) {
        mContext = context;
        mDb = new OpenHelper(mContext).getWritableDatabase();
    }

    public int getCount() {
        return count().size();
    }

    public void insert(String name, int time, int guesses) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("time", time);
        cv.put("guesses", guesses);
        mDb.insert(Scores.TABLE_NAME, null, cv);
    }

    public void deleteAll() {
        mDb.delete(Scores.TABLE_NAME, null, null);
    }

    public List<Map<String, Object>> selectAll(){
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        Cursor cursor = mDb.query(Scores.TABLE_NAME, new String[] { Scores.NAME, Scores.TIME,
                Scores.GUESSES }, null,
                null, null, null, Scores.TIME + " asc, " + Scores.GUESSES + " asc");
        if (cursor.moveToFirst()){
            do{
                Map<String, Object> map = new HashMap<String, Object>();
                map.put(cursor.getColumnName(0), cursor.getString(0));
                map.put(cursor.getColumnName(1), cursor.getInt(1));
                map.put(cursor.getColumnName(2), cursor.getInt(2));
                list.add(map);
            } while (cursor.moveToNext());
        } if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }

    public List<Integer> selectAllTimes() {
        List<Integer> list = new ArrayList<Integer>();
        Cursor cursor = mDb.query(Scores.TABLE_NAME, new String[] { Scores.TIME }, null,
                null, null, null, Scores.TIME + " asc, " + Scores.GUESSES + " asc");
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }

    private List<Integer> count() {
        List<Integer> list = new ArrayList<Integer>();
        Cursor cursor = mDb.query(Scores.TABLE_NAME, new String[] { Scores._ID }, null,
                null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }

    private static class OpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "mastermind_free.db";
        private static final int DATABASE_VERSION = 2;

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE "
                    + Scores.TABLE_NAME + " ("
                    + Scores._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Scores.NAME + " TEXT, "
                    + Scores.TIME + " INTEGER, "
                    + Scores.GUESSES + " INTEGER)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("Example",
                    "Upgrading database, this will drop tables and recreate.");
            db.execSQL("DROP TABLE IF EXISTS " + Scores.TABLE_NAME);
            onCreate(db);
        }
    }
}
