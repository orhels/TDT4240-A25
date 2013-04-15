package com.example.airhockey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TitleActivity extends Activity implements OnClickListener {
	

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.title_view);
		initializeUI();
	}
	
	private void initializeUI() {
		((Button) findViewById(R.id.title_new_game_button)).setOnClickListener(this);
		((Button) findViewById(R.id.title_match_history_button)).setOnClickListener(this);
		((Button) findViewById(R.id.title_settings_button)).setOnClickListener(this);
		((Button) findViewById(R.id.title_quit_button)).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		
		case R.id.title_new_game_button:
			Intent intent = new Intent(this, NewGameActivity.class);
			startActivity(intent);
			break;
		case R.id.title_match_history_button:
			break;
		case R.id.title_settings_button:
			break;
		case R.id.title_quit_button:
			break;
		
		}
	}
}
