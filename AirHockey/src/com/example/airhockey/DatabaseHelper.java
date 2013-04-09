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
	private SortedMap<String, String> cachedMatches;

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

	public void insertNewMatch(String name1, String name2, int score1, int score2) {
		if (cachedMatches == null) {
			initializeCachedScores();
		} 
		cachedMatches.put(score1+"-"+score2, name1+"-"+name2);
		updateHighScores();
		saveMatch(name1, name2, score1, score2);

	}
	/**
	 * The returned hashmap is mapped using <Score, Name>.
	 * NB: The hash map is sorted on the scores from lowest to highest.
	 * @return Returns a sorted hash map with highscores. 
	 */
	public SortedMap<String, String> getHighScores() {
		if (cachedMatches == null) {
			initializeCachedScores();
		} 
		return cachedMatches;
	}

	private void initializeCachedScores() {
		SQLiteDatabase db = getWritableDatabase();
		cachedMatches = new TreeMap<String, String>();
		Cursor cursor = db.rawQuery("SELECT name, score FROM " + matchhistoryTableName + " ORDER BY score DESC LIMIT " + noHighScores, null);
		cursor.moveToFirst();
		while (cursor.moveToNext()) {
			String name1 = cursor.getString(cursor.getColumnIndex("player1name"));
			int score1 = cursor.getInt(cursor.getColumnIndex("score1"));
			String name2 = cursor.getString(cursor.getColumnIndex("player2name"));
			String score2 = cursor.getString(cursor.getColumnIndex("score2")); 
			cachedMatches.put(score1+"-"+score2, name1+"-"+name2);
		}
		cursor.close();
		db.close();
	}

	private void saveMatch(String name1, String name2, int score1, int score2) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("player1name", name1);
		values.put("score1", score1);
		values.put("player2name", score2);
		values.put("score2", score2);
		if (db.insert(matchhistoryTableName, null, values) > 0) {
			Log.d("DatabaseHelper", "Successfully inserted new match (" + name1+"-"+name2 + ", " + score1+"-"+score2 + ")");
		}
		db.close();
	}

	private void removeHighScore(String name1, String name2, int score1, int score2, SQLiteDatabase db) {
		db.delete(matchhistoryTableName, "player1name = ? AND player2name = ? score1 = ? AND score2 = ?", new String[] {name1, String.valueOf(score1), name2, String.valueOf(score2)});
	}

	private void updateHighScores() {
		//Remove the oldest match in the history
		
//		removeLowestScores();
//		findLowestScore();
	}

//	private void findLowestScore() {
//		lowestScore = cachedMatches.keySet().iterator().next();
//	}
//
//	private void removeLowestScores() {
//		Iterator<Integer> iterator = cachedMatches.keySet().iterator();
//		int counter = cachedMatches.keySet().size() - noHighScores;
//		if (counter <= 0) {
//			return;
//		}
//		SQLiteDatabase db = getWritableDatabase();
//		Integer score = 0;
//		while (iterator.hasNext()) {
//			score = iterator.next();
//			if (counter > 0) {
//				String name = cachedMatches.get(score);
//				removeHighScore(name, score, db);
//				iterator.remove();
//			} 
//			counter--;
//		}
//		
//		db.close();
//	}

	public int getNoHighScores() {
		return noHighScores;
	}

	public void setNoHighScores(int noHighScores) {
		this.noHighScores = noHighScores;
	}


}
