package com.example.airhockey;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	private final String createMatchHistorySQL = "CREATE TABLE IF NOT EXISTS highscores(_id INTEGER PRIMARY KEY ASC, "
			+ "player1name TEXT, player2name TEXT, score2 INTEGER, score1 INTEGER, date TEXT)";
	private String matchhistoryTableName = "highscores";
	private static int version = 2;
	private int noHighScores = 10;
	private ArrayList<Match> cachedMatches;

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
		db.execSQL(createMatchHistorySQL);
	}

	public void insertNewMatch(String name1, String name2, int score1, int score2) {
		if (cachedMatches == null) {
			initializeCachedScores();
		} 
		String date = String.valueOf(System.currentTimeMillis());
		cachedMatches.add(new Match(name1, name2, score1, score2, date));
		saveMatch(name1, name2, score1, score2, date);

	}
	/**
	 * The returned array list is mapped using <Match>.
	 * NB: The array is not sorted on the scores from lowest to highest.
	 * @return Returns a sorted hash map with highscores. 
	 */
	public ArrayList<Match> getHighScores() {
		if (cachedMatches == null) {
			initializeCachedScores();
		} 
		return cachedMatches;
	}

	private void initializeCachedScores() {
		SQLiteDatabase db = getWritableDatabase();
		cachedMatches = new ArrayList<Match>();
		Cursor cursor = db.rawQuery("SELECT player1name, player2name, score1, score2, date FROM " + matchhistoryTableName + " ORDER BY date DESC LIMIT " + noHighScores, null);
		cursor.moveToFirst();
		while (cursor.moveToNext()) {
			Match match = new Match(
					cursor.getString(cursor.getColumnIndex("player1name")), 
					cursor.getString(cursor.getColumnIndex("player2name")), 
					cursor.getInt(cursor.getColumnIndex("score1")), 
					cursor.getInt(cursor.getColumnIndex("score2")), 
					cursor.getString(cursor.getColumnIndex("date"))
					);
			
			cachedMatches.add(match);
		}
		cursor.close();
		db.close();
	}

	public void saveMatch(String name1, String name2, int score1, int score2, String date) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("player1name", name1);
		values.put("player2name", name2);
		values.put("score1", score1);
		values.put("score2", score2);
		values.put("date", date);
		if (db.insert(matchhistoryTableName, null, values) > 0) {
			debug("Successfully inserted new match (" + name1+" vs. "+name2 + ", (" + score1+"-"+score2 + "))");
		}
		db.close();
	}

	public void removeHighScore(Match match) {
		String name1 = match.getPlayer1name();
		String name2 = match.getPlayer2name();
		int score1 = match.getPlayer1score();
		int score2 = match.getPlayer2score();
		
		SQLiteDatabase db = getWritableDatabase();
		db.delete(matchhistoryTableName, "player1name = ? AND score1 = ? AND player2name = ? AND score2 = ?", new String[] {name1, String.valueOf(score1), name2, String.valueOf(score2)});
		db.close();
	}

	public int getNoHighScores() {
		return noHighScores;
	}

	public void setNoHighScores(int noHighScores) {
		this.noHighScores = noHighScores;
	}

	public void clearHistory() {
		debug("Deleting the entire " + matchhistoryTableName + " table");
		SQLiteDatabase db = getWritableDatabase();
		db.delete(matchhistoryTableName, "date=?", new String[] {"*"});
		db.close();
	}
	
	private void debug(String msg) {
		Log.d("DatabaseHelper", msg);
	}
}
