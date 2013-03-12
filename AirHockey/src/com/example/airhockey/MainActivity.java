package com.example.airhockey;

import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.view.Menu;


public class MainActivity extends SimpleBaseGameActivity {

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onCreateResources() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Scene onCreateScene() {
		// TODO Auto-generated method stub
		return null;
	}

}
