package com.example.airhockey;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.graphics.Typeface;

/**
 * @author G25
 * @version 1.0
 */
public class GameActivity extends SimpleBaseGameActivity {

	static GameActivity instance;
	public Camera mCamera;
	private GameScene scene;
	static final int CAMERA_WIDTH = 480;
	static final int CAMERA_HEIGHT = 800;
	public Font mFont;
	


	@Override
	public EngineOptions onCreateEngineOptions() {
		instance = this;
    	mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
    	EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
    			new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
    	engineOptions.getTouchOptions().setNeedsMultiTouch(true);
        return engineOptions;
	}

	@Override
	protected void onCreateResources() 
	{
		mFont = FontFactory.create(this.getFontManager(),this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 64);
	    mFont.load();
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
	
	@Override
	public void onBackPressed() {
		// TODO: Add "Do you want to quit?" graphics
		super.onBackPressed();
	}
	public void onStop() {
		super.onStop();
		if (scene != null) {
			scene.destroySprites();
		}
	}
	
	public void onPause() {
		super.onPause();
		if (scene != null) {
			scene.destroySprites();
		}
	}
	
	public GameScene getScene() {
		return scene;
	}
	
}
