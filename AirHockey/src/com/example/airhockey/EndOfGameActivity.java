package com.example.airhockey;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class EndOfGameActivity extends Activity implements OnClickListener
{

	private SharedPreferences preferences;
	private TextView winnerTextView;

	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.new_game_view);
		fetchPreferences();
		initializeUI();
		configureActionBar();
	}
	
	private void fetchPreferences() 
	{
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
	}
	
	private void initializeUI() 
	{
		winnerTextView = (TextView) findViewById(R.id.winnerTextView);
		((Button) findViewById(R.id.rematchButton)).setOnClickListener(this);
		((Button) findViewById(R.id.mainMenuButton)).setOnClickListener(this);
	}
	
	private void configureActionBar() {
		ActionBar ab = getActionBar();
		ab.setTitle("End of Game");
		ab.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId())
		{
		case R.id.rematchButton:
			// TODO: Start a new gameActivity
			break;
		case R.id.mainMenuButton:
			// TODO: Jump back to the main manu
			break;
		}
	}
}
