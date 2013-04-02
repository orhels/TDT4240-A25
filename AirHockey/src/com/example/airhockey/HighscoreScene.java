package com.example.airhockey;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import android.app.Activity;

public class HighscoreScene extends MenuScene implements OnClickListener{

	static final int CAMERA_WIDTH = 800;
	static final int CAMERA_HEIGHT = 480;
	private static MainActivity instance;
	
	private BitmapTextureAtlas backButtonAtlas;
	private BitmapTextureAtlas pressedButtonsAtlas;
	private ITextureRegion backButtonTexture;
	private ITextureRegion backButtonPressedTexture;
	private ButtonSprite backButton;
	private Text settingsText1;

	public HighscoreScene(){
		super(MainActivity.getInstance().mCamera);
		
		instance = MainActivity.getInstance();
		setBackground(new Background(Color.WHITE));
		
		createMenu();
	}
	
	private void createMenu(){
		//Create main title
		settingsText1 = new Text(0, 0, instance.mFont, "Highscores", instance.getVertexBufferObjectManager());
		settingsText1.setPosition(CAMERA_WIDTH/2 - settingsText1.getWidth()/2, 10);
		attachChild(settingsText1);
		
		//Create back button
		backButtonAtlas = new BitmapTextureAtlas(instance.getTextureManager(), 512, 512);
		backButtonPressedTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backButtonAtlas, instance, "back-icon-pressed.png", 0, 0);
		backButtonTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backButtonAtlas, instance, "back-icon.png", 0, 0);
		backButtonAtlas.load();
		
		backButton = new ButtonSprite(10, 365, backButtonTexture, backButtonPressedTexture, instance.getVertexBufferObjectManager(), this);
		registerTouchArea(backButton);
		attachChild(backButton);
		setTouchAreaBindingOnActionDownEnabled(true);
		
	}

	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		System.out.println("CLICKED ON "+pButtonSprite);
	}
	
	
}
