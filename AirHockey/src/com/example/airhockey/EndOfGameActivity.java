package com.example.airhockey;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class EndOfGameActivity extends Activity implements OnClickListener
{

	private TextView winnerTextView;
	private String winnerName, score;

	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.end_of_game_view);
		Intent intent = getIntent();
		if (intent.hasExtra(GameScene.winner) && intent.hasExtra(GameScene.score)) {
			winnerName = intent.getStringExtra(GameScene.winner);
			score = intent.getStringExtra(GameScene.score);
		}
		initializeUI();
		configureActionBar();
	}



	private void initializeUI() {
		winnerTextView = (TextView) findViewById(R.id.winnerTextView);
		winnerTextView.setText(winnerName + " won!" + "\nScore: " + score);
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
			this.finish();
			break;
		}
	}
}
