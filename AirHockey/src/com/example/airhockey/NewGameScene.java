package com.example.airhockey;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.color.Color;

public class NewGameScene extends MenuScene implements OnClickListener
{
	
	private static MainActivity instance;
	
	private BitmapTextureAtlas titleAtlas;
	private ITextureRegion titleTexture;
	
	protected ITextureRegion backgroundTexture;
	
	private BuildableBitmapTextureAtlas backButtonAtlas;
	private ITextureRegion backButtonTexture;
	private ITextureRegion backButtonPressedTexture;
	private ButtonSprite backButton;
	
	/*
	 * 1. 2 PlayerName input field
	 * 2. Slider for goal to win
	 * 4. Start button
	 */
	
	
	public NewGameScene() 
	{
		super(MainActivity.getInstance().mCamera);
		instance = MainActivity.getInstance();
		
		createUI();
	}
	
	public void createUI()
	{
		// BACKGROUND
		setBackground(new Background(Color.WHITE));
		BitmapTextureAtlas backgroundAtlas = new BitmapTextureAtlas(instance.getTextureManager(), 2048, 1024);
		backgroundTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundAtlas, instance, "titlescreen/menu_background.png", 0, 0);
		IMenuItem backgroundMenuItem = new SpriteMenuItem(11, backgroundTexture, instance.getVertexBufferObjectManager());
		backgroundMenuItem.setPosition(mCamera.getWidth()/2 - backgroundMenuItem.getWidth()/2, mCamera.getHeight()/2 - backgroundMenuItem.getHeight()/2);
		attachChild(backgroundMenuItem);
		backgroundAtlas.load();
		
		// TITLE
		titleAtlas = new BitmapTextureAtlas(instance.getTextureManager(), 2048, 1024);
		titleTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(titleAtlas, instance, "titlescreen/new_game.png", 0, 0);
		Sprite titleItem = new Sprite(mCamera.getWidth()/2 - titleTexture.getWidth()/2, 2, titleTexture, instance.getVertexBufferObjectManager());
		attachChild(titleItem);
		titleAtlas.load();
		
		// PLAYER1 NAME FIELD
		
		// PLAYER2 NAME FIELD
		
		// START BUTTON
		
		
		// BACK BUTTON
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
		if(pButtonSprite.equals(backButton)){
			instance.setScene(new TitleScene());
		}
	}

}
