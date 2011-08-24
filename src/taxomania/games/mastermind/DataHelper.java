package taxomania.games.mastermind;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataHelper {

	private static final String DATABASE_NAME = "mastermind_free.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "tbl_scores";

	private Context context;
	private SQLiteDatabase db;

	private OpenHelper openHelper;
	private int count;

	public DataHelper(Context context) {
		this.context = context;
		openHelper = new OpenHelper(this.context);
		this.db = openHelper.getReadableDatabase();
		count = selectAllNums().size();
		this.db.close();
	}

	public int getCount() {
		return this.count;
	}

	public SQLiteDatabase getDb() {
		return this.db;
	}

	public void insert(String name, int time, int guesses) {
		this.db = openHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("name", name);
		cv.put("time", time);
		cv.put("num", count);
		cv.put("guesses", guesses);
		count++;
		this.db.insert(TABLE_NAME, null, cv);
		this.db.close();
	}

	public void deleteAll() {
		this.db = openHelper.getWritableDatabase();
		this.db.delete(TABLE_NAME, null, null);
		this.db.close();
		count = 0;
	}

	public List<Integer> selectAllGuesses() {
		this.db = openHelper.getReadableDatabase();
		List<Integer> list = new ArrayList<Integer>();
		Cursor cursor = this.db.query(TABLE_NAME, new String[] { "guesses" }, null,
				null, null, null, "time asc");
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getInt(0));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		this.db.close();
		return list;
	}
	
	public List<Integer> selectAllNums() {
		this.db = openHelper.getReadableDatabase();
		List<Integer> list = new ArrayList<Integer>();
		Cursor cursor = this.db.query(TABLE_NAME, new String[] { "num" }, null,
				null, null, null, "time asc");
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getInt(0));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		this.db.close();
		return list;
	}

	public List<Integer> selectAllTimes() {
		this.db = openHelper.getReadableDatabase();
		List<Integer> list = new ArrayList<Integer>();
		Cursor cursor = this.db.query(TABLE_NAME, new String[] { "time" },
				null, null, null, null, "time asc");
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getInt(0));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		this.db.close();
		return list;
	}

	public List<String> selectAllNames() {
		this.db = openHelper.getReadableDatabase();
		List<String> list = new ArrayList<String>();
		Cursor cursor = this.db.query(TABLE_NAME, new String[] { "name" },
				null, null, null, null, "time asc");
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		this.db.close();
		return list;
	}

	private static class OpenHelper extends SQLiteOpenHelper {

		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE "
					+ TABLE_NAME
					+ " (id INTEGER PRIMARY KEY, name TEXT, time INTEGER, num INTEGER, guesses INTEGER)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("Example",
					"Upgrading database, this will drop tables and recreate.");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
	}
}
