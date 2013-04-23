package com.example.airhockey;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author G25
 * @version 1.0
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	private final String createMatchHistorySQL = "CREATE TABLE IF NOT EXISTS highscores(_id INTEGER PRIMARY KEY ASC, "
			+ "player1name TEXT, player2name TEXT, score2 INTEGER, score1 INTEGER, date TEXT)";
	private final String matchhistoryTableName = "highscores";
	private static int version = 2;
	private int noHighScores = 10;
	private ArrayList<Match> cachedMatches;

	public DatabaseHelper(final Context context, final String name) {
		super(context, name, null, DatabaseHelper.version);
		this.initializeCachedScores();
	}

	@Override
	public void onCreate(final SQLiteDatabase db) {
		this.initializeDB(db);
	}

	@Override
	public void onUpgrade(final SQLiteDatabase db, final int oldVersion,
			final int newVersion) {
		Log.d("DatabaseHelper", "Upgrading from version " + oldVersion
				+ " to version " + newVersion);
		db.execSQL("DROP TABLE " + this.matchhistoryTableName + ";");
		this.initializeDB(db);

	}

	private void initializeDB(final SQLiteDatabase db) {
		db.execSQL(this.createMatchHistorySQL);
	}

	public void insertNewMatch(final String name1, final String name2,
			final int score1, final int score2) {
		if (this.cachedMatches == null) {
			this.initializeCachedScores();
		}
		final String date = String.valueOf(System.currentTimeMillis());
		this.cachedMatches.add(new Match(name1, name2, score1, score2, date));
		this.saveMatch(name1, name2, score1, score2, date);

	}

	/**
	 * The returned array list is mapped using <Match>. NB: The array is not
	 * sorted on the scores from lowest to highest.
	 * 
	 * @return Returns a sorted hash map with highscores.
	 */
	public ArrayList<Match> getHighScores() {
		if (this.cachedMatches == null) {
			this.initializeCachedScores();
		}
		return this.cachedMatches;
	}

	private void initializeCachedScores() {
		final SQLiteDatabase db = this.getWritableDatabase();
		this.cachedMatches = new ArrayList<Match>();
		final Cursor cursor = db.rawQuery(
				"SELECT player1name, player2name, score1, score2, date FROM "
						+ this.matchhistoryTableName
						+ " ORDER BY date DESC LIMIT " + this.noHighScores,
				null);
		cursor.moveToFirst();
		while (cursor.moveToNext()) {
			final Match match = new Match(cursor.getString(cursor
					.getColumnIndex("player1name")), cursor.getString(cursor
					.getColumnIndex("player2name")), cursor.getInt(cursor
					.getColumnIndex("score1")), cursor.getInt(cursor
					.getColumnIndex("score2")), cursor.getString(cursor
					.getColumnIndex("date")));

			this.cachedMatches.add(match);
		}
		cursor.close();
		db.close();
	}

	public void saveMatch(final String name1, final String name2,
			final int score1, final int score2, final String date) {
		final SQLiteDatabase db = this.getWritableDatabase();
		final ContentValues values = new ContentValues();
		values.put("player1name", name1);
		values.put("player2name", name2);
		values.put("score1", score1);
		values.put("score2", score2);
		values.put("date", date);
		if (db.insert(this.matchhistoryTableName, null, values) > 0) {
			this.debug("Successfully inserted new match (" + name1 + " vs. "
					+ name2 + ", (" + score1 + "-" + score2 + "))");
		}
		db.close();
	}

	public void removeHighScore(final Match match) {
		final String name1 = match.getPlayer1name();
		final String name2 = match.getPlayer2name();
		final int score1 = match.getPlayer1score();
		final int score2 = match.getPlayer2score();

		final SQLiteDatabase db = this.getWritableDatabase();
		db.delete(
				this.matchhistoryTableName,
				"player1name = ? AND score1 = ? AND player2name = ? AND score2 = ?",
				new String[] { name1, String.valueOf(score1), name2,
						String.valueOf(score2) });
		db.close();
	}

	public int getNoHighScores() {
		return this.noHighScores;
	}

	public void setNoHighScores(final int noHighScores) {
		this.noHighScores = noHighScores;
	}

	public void clearHistory() {
		this.debug("Deleting the entire " + this.matchhistoryTableName
				+ " table");
		final SQLiteDatabase db = this.getWritableDatabase();
		db.delete(this.matchhistoryTableName, "date=?", new String[] { "*" });
		db.close();
	}

	private void debug(final String msg) {
		Log.d("DatabaseHelper", msg);
	}
}
