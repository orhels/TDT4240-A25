package com.example.airhockey;

import android.app.ActionBar; 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class TitleActivity extends Activity implements OnClickListener {
	

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.title_view);
		initializeUI();
		configureActionBar();
	}
	
	private void initializeUI() {
		((ImageButton) findViewById(R.id.title_new_game_button)).setOnClickListener(this);
		((ImageButton) findViewById(R.id.title_match_history_button)).setOnClickListener(this);
		((ImageButton) findViewById(R.id.title_settings_button)).setOnClickListener(this);
		((ImageButton) findViewById(R.id.title_quit_button)).setOnClickListener(this);
	}
	
	private void configureActionBar() {
		ActionBar ab = getActionBar();
		ab.setTitle("Air Hockey!");
	}


	@Override
	public void onClick(View v) {
		
		Intent intent;
		
		switch (v.getId()) {
		
		case R.id.title_new_game_button:
			intent = new Intent(this, NewGameActivity.class);
			startActivity(intent);
			break;
		case R.id.title_match_history_button:
			intent = new Intent(this, HistoryActivity.class);
			startActivity(intent);
			break;
		case R.id.title_settings_button:
			intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			break;
		case R.id.title_quit_button:
			this.finish();
			break;
		
		}
	}
}
