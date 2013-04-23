package com.example.airhockey;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

/**
 * @author G25
 * @version 1.0
 */
public class HistoryActivity extends Activity implements
		OnItemLongClickListener {

	private ListView matchList;
	private DatabaseHelper db;
	private MatchAdapter adapter;

	@Override
	public void onCreate(final Bundle bundle) {
		super.onCreate(bundle);
		this.setContentView(R.layout.highscore_view);
		this.db = new DatabaseHelper(this, "AirHockeyDB");
		this.initializeUI();
		this.configureActionBar();
	}

	private void initializeUI() {
		this.matchList = (ListView) this.findViewById(R.id.highscore_list);
		this.matchList.setOnItemLongClickListener(this);
		this.adapter = new MatchAdapter(this, this.fetchMatches());
		this.matchList.setAdapter(this.adapter);
	}

	private List<Match> fetchMatches() {
		final List<Match> matches = this.db.getHighScores();
		if (matches.size() < 1) {
			this.debug("No matches in DB");
		}
		matches.add(new Match("Tor Kristian", "Christian", 1, 239, "Dafq?"));
		return matches;
	}

	private void configureActionBar() {
		final ActionBar ab = this.getActionBar();
		ab.setTitle("Match History");
		ab.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		final MenuInflater inflater = this.getMenuInflater();
		inflater.inflate(R.menu.history_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			this.finish();
		}
		if (item.getItemId() == R.id.history_menu_clear) {
			this.db.clearHistory();
		}
		return true;
	}

	private void debug(final String msg) {
		Log.d("HistoryActivity", msg);
	}

	@Override
	public boolean onItemLongClick(final AdapterView<?> adapter,
			final View view, final int pos, final long id) {
		this.showDeleteDialog(pos);
		return true;
	}

	private void deleteMatch(final int pos) {
		final Match match = this.adapter.remove(pos);
		this.db.removeHighScore(match);
		this.adapter.notifyDataSetChanged();
		this.debug("Deleting #" + pos + " match");
	}

	public void showDeleteDialog(final int pos) {
		// Use the Builder class for convenient dialog construction
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final Resources res = this.getResources();
		builder.setMessage(res.getString(R.string.history_dialog_prompt))
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(final DialogInterface dialog,
									final int id) {
								HistoryActivity.this.deleteMatch(pos);
								dialog.dismiss();
							}
						})
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(final DialogInterface dialog,
									final int id) {
								// User cancels
								dialog.dismiss();
							}
						});
		// Create the AlertDialog object and show
		builder.create().show();
	}
}
