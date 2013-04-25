package com.example.airhockey;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

/**
 * @author G25
 * @version 1.0
 */
public class NewGameActivity extends Activity implements OnClickListener,
		OnSeekBarChangeListener, TextWatcher {

	private EditText player1NameInput;
	private EditText player2NameInput;
	private TextView goalTextView;
	private SeekBar goalSeekBar;
	private SharedPreferences preferences;

	@Override
	public void onCreate(final Bundle bundle) {
		super.onCreate(bundle);
		this.setContentView(R.layout.new_game_view);
		this.fetchPreferences();
		this.initializeUI();
		this.configureActionBar();
	}

	private void initializeUI() {
		// TODO Auto-generated method stub
		this.player1NameInput = (EditText) this
				.findViewById(R.id.player1NameInput);
		this.player1NameInput.setText(this.preferences.getString(
				Constants.PLAYER1NAME, ""));
		this.player1NameInput.addTextChangedListener(this);
		this.player2NameInput = (EditText) this
				.findViewById(R.id.player2NameInput);
		this.player2NameInput.setText(this.preferences.getString(
				Constants.PLAYER2NAME, ""));
		this.player2NameInput.addTextChangedListener(this);
		this.goalTextView = (TextView) this.findViewById(R.id.goalTextView);
		this.goalSeekBar = (SeekBar) this.findViewById(R.id.goalSeekBar);
		this.goalSeekBar.setProgress(Integer.parseInt(this.preferences
				.getString(Constants.GOALSTOWIN, "5")) - 5);
		this.goalTextView.setText("" + (this.goalSeekBar.getProgress() + 5));
		this.goalSeekBar.setOnSeekBarChangeListener(this);
		((ImageButton) this.findViewById(R.id.startButton))
				.setOnClickListener(this);
	}

	private void fetchPreferences() {
		this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
	}

	private void writePreference(final String key, final String value) {
		final SharedPreferences.Editor editor = this.preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void configureActionBar() {
		final ActionBar ab = this.getActionBar();
		ab.setTitle("New Game");
		ab.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			this.finish();
		}
		return true;
	}

	@Override
	public void onClick(final View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.startButton:
			final Intent intent = new Intent(this, GameActivity.class);
			intent.putExtra(Constants.PLAYER1NAME, this.getPlayer1Name());
			intent.putExtra(Constants.PLAYER2NAME, this.getPlayer2Name());
			this.startActivity(intent);
			this.finish();
			break;
		}
	}

	@Override
	public void onProgressChanged(final SeekBar seekBar, final int progress,
			final boolean fromUser) {
		// TODO Auto-generated method stub
		this.goalTextView.setText("" + (progress + 5));
		this.writePreference(Constants.GOALSTOWIN, "" + (progress + 5));
	}

	@Override
	public void onStartTrackingTouch(final SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(final SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTextChanged(final Editable s) {
		this.writePreference(Constants.PLAYER1NAME, this.getPlayer1Name());
		this.writePreference(Constants.PLAYER2NAME, this.getPlayer2Name());
	}

	private String getPlayer1Name() {
		return this.player1NameInput.getText().toString();
	}

	private String getPlayer2Name() {
		return this.player2NameInput.getText().toString();
	}

	@Override
	public void beforeTextChanged(final CharSequence s, final int start,
			final int count, final int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(final CharSequence s, final int start,
			final int before, final int count) {
		// TODO Auto-generated method stub

	}

}
