package com.example.airhockey;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.util.color.Color;

public class GameScene extends Scene{
	
	Camera mCamera;
	GameActivity gameInstance;
	
	public void GameScene(){
		gameInstance = GameActivity.getInstance();
		setBackground(new Background(Color.WHITE));
		mCamera = gameInstance.mCamera;
	}
}
