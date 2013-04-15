package com.example.airhockey;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

public class HistoryActivity extends Activity {

	private ListView matchList;
	private DatabaseHelper db;
	
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.highscore_view);
		db = new DatabaseHelper(this, "AirHockeyDB");
		initializeUI();
		configureActionBar();
	}
	
	private void initializeUI() {
		matchList = (ListView) findViewById(R.id.highscore_list);
		MatchAdapter adapter = new MatchAdapter(this, fetchMatches());
		matchList.setAdapter(adapter);
	}
	
	private List<Match> fetchMatches() {
		List<Match> matches = db.getHighScores();
		if (matches.size() < 1) {
			debug("No matches in DB");
		}
		matches.add(new Match("Per", "Kai", 1, 3, "Dafq?"));
		matches.add(new Match("Tor Kristian", "Christian", 1, 239, "Dafq?"));
		matches.add(new Match("Tor Kristian", "Barrack \"Killah\"", 1, 2, "Dafq?"));
		return matches;
	}
	
	private void configureActionBar() {
		ActionBar ab = getActionBar();
		ab.setTitle("Match History");
		ab.setDisplayHomeAsUpEnabled(true);
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.history_menu, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected (MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			this.finish();
		}
		
		if (item.getItemId() == R.id.history_menu_clear) {
			db.clearHistory();
		}
		
		return true;
	}
	
	private void debug(String msg) {
		Log.d("HistoryActivity", msg);
	}
}
