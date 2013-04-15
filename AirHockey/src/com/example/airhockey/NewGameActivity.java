package com.example.airhockey;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class NewGameActivity extends Activity implements OnClickListener, OnSeekBarChangeListener
{
	
	private EditText player1NameInput;
	private EditText player2NameInput;
	private TextView goalTextView;
	private SeekBar goalSeekBar;
	private SharedPreferences preferences;
	
	
	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.new_game_view);
		fetchPreferences();
		initializeUI();
		configureActionBar();
	}
	
	
	private void initializeUI() {
		// TODO Auto-generated method stub
		player1NameInput = (EditText) findViewById(R.id.player1NameInput);
		player2NameInput = (EditText) findViewById(R.id.player2NameInput);
		goalTextView = (TextView) findViewById(R.id.goalTextView);
		goalSeekBar = (SeekBar) findViewById(R.id.goalSeekBar);
		goalSeekBar.setOnSeekBarChangeListener(this);
		((Button) findViewById(R.id.startButton)).setOnClickListener(this);
	}
	
	private void fetchPreferences() 
	{
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
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
			startActivity(intent);
			break;
		}
	}


	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		goalTextView.setText("" + (progress+5));
	}


	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
	
}
