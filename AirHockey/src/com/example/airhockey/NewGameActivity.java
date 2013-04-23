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
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class NewGameActivity extends Activity implements OnClickListener, OnSeekBarChangeListener, TextWatcher
{
	
	private EditText player1NameInput;
	private EditText player2NameInput;
	private TextView goalTextView;
	private SeekBar goalSeekBar;
	private SharedPreferences preferences;
	
	public static final String player1Name = "player1Name", player2Name = "player2Name", goalsToWin = "goalsToWin";
	
	
	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.new_game_view);
		fetchPreferences();
		initializeUI();
		configureActionBar();
	}
	
	
	private void initializeUI() 
	{
		// TODO Auto-generated method stub
		player1NameInput = (EditText) findViewById(R.id.player1NameInput);
		player1NameInput.setText(preferences.getString(player1Name, ""));
		player1NameInput.addTextChangedListener(this);
		player2NameInput = (EditText) findViewById(R.id.player2NameInput);
		player2NameInput.setText(preferences.getString(player2Name, ""));
		player2NameInput.addTextChangedListener(this);
		goalTextView = (TextView) findViewById(R.id.goalTextView);
		goalSeekBar = (SeekBar) findViewById(R.id.goalSeekBar);
		goalSeekBar.setProgress(Integer.parseInt(preferences.getString(goalsToWin, "5"))-5);
		goalTextView.setText("" + (goalSeekBar.getProgress() + 5));
		goalSeekBar.setOnSeekBarChangeListener(this);
		((Button) findViewById(R.id.startButton)).setOnClickListener(this);
	}
	
	private void fetchPreferences() 
	{
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
	}

	private void writePreference(String key, String value) 
	{
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void configureActionBar() {
		ActionBar ab = getActionBar();
		ab.setTitle("New Game");
		ab.setDisplayHomeAsUpEnabled(true);
	}
	
	
	public boolean onOptionsItemSelected (MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			this.finish();
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.startButton:
			Intent intent = new Intent(this, GameActivity.class);
			intent.putExtra(player1Name, getPlayer1Name());
			intent.putExtra(player2Name, getPlayer2Name());
			startActivity(intent);
			finish();
			break;
		}
	}


	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		goalTextView.setText("" + (progress+5));
		writePreference(goalsToWin, ""+(progress+5));
	}


	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void afterTextChanged(Editable s) 
	{
		writePreference(player1Name, getPlayer1Name());
		writePreference(player2Name, getPlayer2Name());
	}
	
	private String getPlayer1Name() {
		return this.player1NameInput.getText().toString();
	}
	
	private String getPlayer2Name() {
		return this.player2NameInput.getText().toString();
	}


	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}
	
}
