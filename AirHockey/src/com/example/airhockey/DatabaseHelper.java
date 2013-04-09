package com.example.airhockey;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	private final String createHighScoreSQL = "CREATE TABLE IF NOT EXISTS highscores(_id INTEGER PRIMARY KEY ASC, "
			+ "player1name TEXT, player2name TEXT, score2 INTEGER, score1 INTEGER)";
	private String matchhistoryTableName = "matchhistory";
	private static int version = 1;
	private int noHighScores = 10;
	private int lowestScore = Integer.MAX_VALUE;
	private SortedMap<Integer, String> cachedHighScores;

	public DatabaseHelper(Context context, String name) {
		super(context, name, null, version);
		initializeCachedScores();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		initializeDB(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d("DatabaseHelper", "Upgrading from version " + oldVersion + " to version " + newVersion);
		db.execSQL("DROP TABLE " + matchhistoryTableName + ";");
		initializeDB(db);

	}

	private void initializeDB(SQLiteDatabase db) {
		db.execSQL(createHighScoreSQL);
	}

	public void insertNewHighScore(String name, int score) {
		if (score < 0) return;

		if (cachedHighScores == null) {
			initializeCachedScores();
		} 
		if (score > lowestScore) {
			cachedHighScores.put(score, name);
			updateHighScores();
			saveHighScore(name, score);
		}

	}
	/**
	 * The returned hashmap is mapped using <Score, Name>.
	 * NB: The hash map is sorted on the scores from lowest to highest.
	 * @return Returns a sorted hash map with highscores. 
	 */
	public SortedMap<Integer, String> getHighScores() {
		if (cachedHighScores == null) {
			initializeCachedScores();
		} 
		return cachedHighScores;
	}

	private void initializeCachedScores() {
		SQLiteDatabase db = getWritableDatabase();
		cachedHighScores = new TreeMap<Integer, String>();
		Cursor cursor = db.rawQuery("SELECT name, score FROM " + matchhistoryTableName + " ORDER BY score DESC LIMIT " + noHighScores, null);
		cursor.moveToFirst();
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("name"));
			int score = cursor.getInt(cursor.getColumnIndex("score"));
			if (score < lowestScore) {
				lowestScore = score;
			}
			cachedHighScores.put(score, name);
		}
		cursor.close();
		db.close();
	}

	private void saveHighScore(String name, int score) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("score", score);
		if (db.insert(matchhistoryTableName, null, values) > 0) {
			Log.d("DatabaseHelper", "Successfully inserted new highscore (" + name + ", " + score + ")");
		}
		db.close();
	}

	private void removeHighScore(String name, int score, SQLiteDatabase db) {
		db.delete(matchhistoryTableName, "name = ? AND score = ?", new String[] {name, String.valueOf(score)});
	}

	private void updateHighScores() {
		removeLowestScores();
		findLowestScore();
	}

	private void findLowestScore() {
		lowestScore = cachedHighScores.keySet().iterator().next();
	}

	private void removeLowestScores() {
		Iterator<Integer> iterator = cachedHighScores.keySet().iterator();
		int counter = cachedHighScores.keySet().size() - noHighScores;
		if (counter <= 0) {
			return;
		}
		SQLiteDatabase db = getWritableDatabase();
		Integer score = 0;
		while (iterator.hasNext()) {
			score = iterator.next();
			if (counter > 0) {
				String name = cachedHighScores.get(score);
				removeHighScore(name, score, db);
				iterator.remove();
			} 
			counter--;
		}
		
		db.close();
	}

	public int getNoHighScores() {
		return noHighScores;
	}

	public void setNoHighScores(int noHighScores) {
		this.noHighScores = noHighScores;
	}


}
