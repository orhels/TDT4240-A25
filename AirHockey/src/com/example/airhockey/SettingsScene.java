package com.example.airhockey;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import android.app.Activity;

public class SettingsScene extends MenuScene{

	static final int CAMERA_WIDTH = 800;
	static final int CAMERA_HEIGHT = 480;
	private static MainActivity instance;
	
	private Text settingsText1;
	
	private BitmapTextureAtlas backButtonAtlas;
	private ITextureRegion backButtonTexture;
	private ITextureRegion backButtonPressedTexture;
	private ButtonSprite backButton;

	public SettingsScene(){
		super(MainActivity.getInstance().mCamera);
		
		instance = MainActivity.getInstance();
		setBackground(new Background(Color.WHITE));
		
		//Create main title
		settingsText1 = new Text(0, 0, instance.mFont, "Settings", instance.getVertexBufferObjectManager());
		settingsText1.setPosition(CAMERA_WIDTH/2 - settingsText1.getWidth()/2, 10);
		attachChild(settingsText1);
		
		
		createMenu();
	}
	
	private void createMenu(){
		//Create Back button
		backButtonAtlas = new BitmapTextureAtlas(instance.getTextureManager(), 1024, 1024);
		backButtonTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backButtonAtlas, instance, "back-icon.png", 10, 400);
		backButtonPressedTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backButtonAtlas, instance, "back-icon-pressed.png", 10, 400);
		backButton = new ButtonSprite(10, 350, backButtonTexture, backButtonPressedTexture, instance.getVertexBufferObjectManager());
		attachChild(backButton);
		backButton.setOnClickListener(new OnClickListener() {
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				instance.setScene(new TitleScreen());
			}
		});
		
		backButtonAtlas.load();
	}
	
}
