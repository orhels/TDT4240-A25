package com.example.airhockey;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;

public class GameActivity extends SimpleBaseGameActivity {

	static GameActivity instance;
	public Camera mCamera;
	private GameScene scene;
	static final int CAMERA_WIDTH = 480;
	static final int CAMERA_HEIGHT = 800;
	


	@Override
	public EngineOptions onCreateEngineOptions() {
		instance = this;
    	mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
    	EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
    			new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
    	engineOptions.getTouchOptions().setNeedsMultiTouch(true);
    	/*
        if(MultiTouch.isSupported(this)) {
            if(MultiTouch.isSupportedDistinct(this)) {
                Toast.makeText(this, "MultiTouch detected --> Both controls will work properly!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "MultiTouch detected, but your device has problems distinguishing between fingers.\n\nControls are placed at different vertical locations.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Sorry your device does NOT support MultiTouch!\n\n(Falling back to SingleTouch.)\n\nControls are placed at different vertical locations.", Toast.LENGTH_LONG).show();
        }
        */
        return engineOptions;
	}

	@Override
	protected void onCreateResources() 
	{
		
		
	}
	
	public static GameActivity getInstance(){
		return instance;
	}

	@Override
	protected Scene onCreateScene() 
	{
		scene = new GameScene();
		scene.setOnSceneTouchListener(scene);

		return scene;
	}
	
	public void setCurrentScene(GameScene scene){
		this.scene = scene;
	}
	
	public GameScene getCurrentScene(){
		return this.scene;
	}
	
}
