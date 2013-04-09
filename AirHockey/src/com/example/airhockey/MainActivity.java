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

public class MainActivity extends SimpleBaseGameActivity {


	static final int CAMERA_WIDTH = 800;

    static final int CAMERA_HEIGHT = 480;

    private Scene scene;
    public Camera mCamera;
    protected Font mFont;
    
    private static MainActivity instance;
    @Override
    public EngineOptions onCreateEngineOptions() {
    	instance = this;
    	mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
    	return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,
    			new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);

    }

    @Override
    protected void onCreateResources() {
    	mFont = FontFactory.create(getFontManager(), getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
    	mFont.load();
    }

    @Override
    protected Scene onCreateScene() {
    	scene = new TitleScreen();
    	return scene;
    }

    public static MainActivity getInstance(){
    	return instance;
    }
    
    public void setScene(Scene scene){
    	this.scene = scene;
    	mEngine.setScene(this.scene);
    }
    
    @Override
    public void onBackPressed() {
    	if(scene instanceof TitleScreen){
    		super.onBackPressed();
    	}
    	else if ((scene instanceof MatchHistoryScene) || (scene instanceof SettingsScene)) {
    		setScene(new TitleScreen());
    	}
    }
}
