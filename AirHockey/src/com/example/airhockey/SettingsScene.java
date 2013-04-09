package com.example.airhockey;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.color.Color;

public class SettingsScene extends MenuScene implements OnClickListener{

	static final int CAMERA_WIDTH = 800;
	static final int CAMERA_HEIGHT = 480;
	private static MainActivity instance;
	
	private BitmapTextureAtlas titleAtlas;
	private ITextureRegion titleTexture;
	
	private BuildableBitmapTextureAtlas backButtonAtlas;
	private ITextureRegion backButtonTexture;
	private ITextureRegion backButtonPressedTexture;
	private ButtonSprite backButton;
	
	private Text settingsText1;
	private ITextureRegion backgroundTexture;

	public SettingsScene(){
		super(MainActivity.getInstance().mCamera);
		
		instance = MainActivity.getInstance();
		setBackground(new Background(Color.WHITE));

		//Main title 
		titleAtlas = new BitmapTextureAtlas(instance.getTextureManager(), 2048, 1024);
		titleTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(titleAtlas, instance, "settings/settings_title.png", 0, 0);
		Sprite titleItem = new Sprite(mCamera.getWidth()/2 - titleTexture.getWidth()/2, 2, titleTexture, instance.getVertexBufferObjectManager());
		attachChild(titleItem);
		titleAtlas.load();
		
		createMenu();
	}
	
	private void createMenu()
	{
		
		// BACKGROUND
		setBackground(new Background(Color.WHITE));
		BitmapTextureAtlas backgroundAtlas = new BitmapTextureAtlas(instance.getTextureManager(), 2048, 1024);
		backgroundTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundAtlas, instance, "titlescreen/menu_background.png", 0, 0);
		IMenuItem backgroundMenuItem = new SpriteMenuItem(11, backgroundTexture, instance.getVertexBufferObjectManager());
		backgroundMenuItem.setPosition(mCamera.getWidth()/2 - backgroundMenuItem.getWidth()/2, mCamera.getHeight()/2 - backgroundMenuItem.getHeight()/2);
		attachChild(backgroundMenuItem);
		backgroundAtlas.load();
		
		//SETTINGS!
		settingsText1 = new Text(0, 0, instance.mFont, "U change settings? ... Why u change settings?", instance.getVertexBufferObjectManager());
		settingsText1.setPosition(CAMERA_WIDTH/2 - settingsText1.getWidth()/2, CAMERA_HEIGHT/2 - settingsText1.getHeight()/2);
		attachChild(settingsText1);
		
		//Create Back button
		backButtonAtlas = new BuildableBitmapTextureAtlas(instance.getTextureManager(), 512, 512);
		backButtonPressedTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backButtonAtlas, instance, "back-icon-pressed.png");
		backButtonTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backButtonAtlas, instance, "back-icon.png");
		try {
			backButtonAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
			backButtonAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		backButton = new ButtonSprite(10, 365, backButtonTexture, backButtonPressedTexture, backButtonTexture, instance.getVertexBufferObjectManager(), this);
		registerTouchArea(backButton);
		attachChild(backButton);
		setTouchAreaBindingOnActionDownEnabled(true);
	}

	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		// TODO Auto-generated method stub
		if(pButtonSprite.equals(backButton)){
			instance.setScene(new TitleScene());
		}
	}
	
}
