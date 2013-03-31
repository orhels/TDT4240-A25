package com.example.airhockey;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.text.Text;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import android.app.Activity;

public class HighscoreScene extends MenuScene{

	static final int CAMERA_WIDTH = 800;
	static final int CAMERA_HEIGHT = 480;
	private static MainActivity instance;
	
	private Text settingsText1;

	public HighscoreScene(){
		super(MainActivity.getInstance().mCamera);
		
		instance = MainActivity.getInstance();
		setBackground(new Background(Color.WHITE));
		
		createMenu();
	}
	
	private void createMenu(){
		settingsText1 = new Text(0, 0, instance.mFont, "Highscores", instance.getVertexBufferObjectManager());
		settingsText1.setPosition(CAMERA_WIDTH/2 - settingsText1.getWidth()/2, 10);
		attachChild(settingsText1);
	}
	
	
}
