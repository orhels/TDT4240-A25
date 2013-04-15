package com.example.airhockey;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import android.app.Activity;

public class GameActivity extends SimpleBaseGameActivity {

	GameActivity instance;
	public Camera mCamera;
	private Scene scene;
//	static final int CAMERA_WIDTH = 800;
//	static final int CAMERA_HEIGHT = 480;
	
	public void moveMallet() {
		//mallet.moveMallet(speedX, speedY);
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		instance = this;
    	mCamera = new Camera(0, 0, Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT);
    	return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
    			new RatioResolutionPolicy(Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT), mCamera);


	}

	@Override
	protected void onCreateResources() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Scene onCreateScene() {
		scene = new Scene();
		scene.setBackground(new Background(Color.WHITE));
		return scene;
	}
}
