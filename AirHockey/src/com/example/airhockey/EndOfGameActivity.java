package com.example.airhockey;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
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
		setContentView(R.layout.end_of_game_view);
		fetchPreferences();
		initializeUI();
		selectWinner();
		configureActionBar();
	}
	
	private void selectWinner() {
		/*
		 pseudokode:
		 if(player1 is the winner) {
		 	winnerTextView.setText(player1 + ": " + player1points + " vs " + player2 + ": " + player2points + "\n" 
		 					+ player1 + " is the winner!!!")
		 } else {
		 	winnerTextView.setText(player1 + ": " + player1points + " vs " + player2 + ": " + player2points + "\n\n" 
		 					+ player2 + " is the winner!!!")
		 }
		 
		 
		 */
		
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
			Intent intent = new Intent(this, GameActivity.class);
			startActivity(intent);
			break;
		case R.id.mainMenuButton:
			//Virker dette? :s
			this.finish();
			break;
		}
	}
}
