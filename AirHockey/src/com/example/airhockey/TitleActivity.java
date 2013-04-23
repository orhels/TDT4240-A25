package com.example.airhockey;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/**
 * @author G25
 * @version 1.0
 */
public class TitleActivity extends Activity implements OnClickListener {

	/**
	 * Called when the activity is started.
	 * 
	 * Sets up the layout and initializes the ui.
	 */
	@Override
	public void onCreate(final Bundle bundle) {
		super.onCreate(bundle);
		this.setContentView(R.layout.title_view);
		this.initializeUI();
		this.configureActionBar();
	}

	/**
	 * Initializes the ui.
	 */
	private void initializeUI() {
		((ImageButton) this.findViewById(R.id.title_new_game_button))
				.setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.title_match_history_button))
				.setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.title_settings_button))
				.setOnClickListener(this);
		((ImageButton) this.findViewById(R.id.title_quit_button))
				.setOnClickListener(this);
	}

	/**
	 * Sets the title of the action bar.
	 */
	private void configureActionBar() {
		final ActionBar ab = this.getActionBar();
		ab.setTitle("Air Hockey!");
	}

	/**
	 * Button listener.
	 * 
	 * Listens for onClick events on buttons. Starts new activities based on
	 * which button was clicked.
	 */
	@Override
	public void onClick(final View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.title_new_game_button:
			intent = new Intent(this, NewGameActivity.class);
			this.startActivity(intent);
			break;
		case R.id.title_match_history_button:
			intent = new Intent(this, HistoryActivity.class);
			this.startActivity(intent);
			break;
		case R.id.title_settings_button:
			intent = new Intent(this, SettingsActivity.class);
			this.startActivity(intent);
			break;
		case R.id.title_quit_button:
			this.finish();
			break;

		}
	}
}
