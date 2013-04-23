package com.example.airhockey;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author G25
 * @version 1.0
 */
public class EndOfGameActivity extends Activity implements OnClickListener {

	private TextView winnerTextView;
	private String winnerName, score;

	@Override
	public void onCreate(final Bundle bundle) {
		super.onCreate(bundle);
		this.setContentView(R.layout.end_of_game_view);
		final Intent intent = this.getIntent();
		if (intent.hasExtra(Constants.WINNER)
				&& intent.hasExtra(Constants.SCORE)) {
			this.winnerName = intent.getStringExtra(Constants.WINNER);
			this.score = intent.getStringExtra(Constants.SCORE);
		}
		this.initializeUI();
		this.configureActionBar();
	}

	private void initializeUI() {
		this.winnerTextView = (TextView) this.findViewById(R.id.winnerTextView);
		this.winnerTextView.setText(this.winnerName + " won!" + "\nScore: "
				+ this.score);
		((Button) this.findViewById(R.id.rematchButton))
				.setOnClickListener(this);
		((Button) this.findViewById(R.id.mainMenuButton))
				.setOnClickListener(this);
	}

	private void configureActionBar() {
		final ActionBar ab = this.getActionBar();
		ab.setTitle("End of Game");
		ab.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onClick(final View v) {
		switch (v.getId()) {
		case R.id.rematchButton:
			final Intent intent = new Intent(this, GameActivity.class);
			this.startActivity(intent);
			break;
		case R.id.mainMenuButton:
			this.finish();
			break;
		}
	}
}
