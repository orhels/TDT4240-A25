package com.example.airhockey;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {


	static final int CAMERA_WIDTH = 800;

    static final int CAMERA_HEIGHT = 480;

    
    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
    	System.out.println("LAUNCHING TITLESCREEN");
    	startActivity(new Intent(this, TitleScreen.class));
    	
    	super.onCreate(pSavedInstanceState);
    }
    
    
}
