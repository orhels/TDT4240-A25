package com.example.airhockey;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.color.Color;
import android.content.Intent;

public class TitleScreen extends MenuScene implements OnClickListener {

	Scene currentScene;
	
	 
	protected static final int MENU_NEWGAME = 0;
	protected static final int MENU_HIGHSCORES = 1;
	protected static final int MENU_SETTINGS = 2;
	protected static final int MENU_QUIT = 3;
	
	private BitmapTextureAtlas titleMenuTexture;
	protected ITextureRegion titleTexture;
	protected ITextureRegion backgroundTexture;
	
	private BuildableBitmapTextureAtlas menuTexture;
	protected ITextureRegion newgameTexture;
	protected ITextureRegion newgamePressedTexture;
	protected ITextureRegion highscoreTexture;
	protected ITextureRegion highscorePressedTexture;
	protected ITextureRegion settingsTexture;
	protected ITextureRegion settingsPressedTexture;
	protected ITextureRegion quitTexture;
	protected ITextureRegion quitPressedTexture;
	
	protected ButtonSprite newgameMenuItem;
	protected ButtonSprite highscoreMenuItem;
	protected ButtonSprite settingsMenuItem;
	protected ButtonSprite quitMenuItem;
	
	
	private static MainActivity instance;
	
	/**
	 * If we want to use standard Android layout.
	 */
	
	public TitleScreen(){
		super(MainActivity.getInstance().mCamera);
		
		instance = MainActivity.getInstance();
		setBackground(new Background(Color.WHITE));
		
		//Background
		BitmapTextureAtlas backgroundAtlas = new BitmapTextureAtlas(instance.getTextureManager(), 2048, 1024);
		backgroundTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundAtlas, instance, "titlescreen/menu_background.png", 0, 0);
		IMenuItem backgroundMenuItem = new SpriteMenuItem(11, backgroundTexture, instance.getVertexBufferObjectManager());
		backgroundMenuItem.setPosition(mCamera.getWidth()/2 - backgroundMenuItem.getWidth()/2, mCamera.getHeight()/2 - backgroundMenuItem.getHeight()/2);
		attachChild(backgroundMenuItem);
		backgroundAtlas.load();
		//Title texture and item
		titleMenuTexture = new BitmapTextureAtlas(instance.getTextureManager(), 2048, 1024);
		titleTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(titleMenuTexture, instance, "titlescreen/airhockey_title2.png", 0, 0);
		IMenuItem titleMenuItem = new SpriteMenuItem(10, titleTexture, instance.getVertexBufferObjectManager());
		titleMenuItem.setPosition( mCamera.getWidth()/2 - titleMenuItem.getWidth()/2, 0);
		attachChild(titleMenuItem);
		titleMenuTexture.load();
		
		createMenu();
		
	}
	

	/**
	 * Method for adding graphical menu items to the menu.
	 * - Main title
	 * - New game
	 * - Highscores
	 * - Settings
	 * - Quit
	 */
	protected void createMenu(){
		//Menu button textures
		menuTexture = new BuildableBitmapTextureAtlas(instance.getTextureManager(), 1024, 512);
		
		newgamePressedTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTexture, instance, "titlescreen/new_game_pressed.png");
		newgameTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTexture, instance, "titlescreen/new_game.png");
		highscorePressedTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTexture, instance, "titlescreen/highscores_pressed.png");
		highscoreTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTexture, instance, "titlescreen/highscores.png");
		settingsPressedTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTexture, instance, "titlescreen/settings_pressed.png");
		settingsTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTexture, instance, "titlescreen/settings.png");
		quitPressedTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTexture, instance, "titlescreen/quit_pressed.png");
		quitTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTexture, instance, "titlescreen/quit.png");
		
		try {
			menuTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));
			menuTexture.load();
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
		
		System.out.println("CREATING MENU ITEMS");
		
		newgameMenuItem = new ButtonSprite(mCamera.getWidth()/2 - newgameTexture.getWidth()/2, 150, newgameTexture, newgamePressedTexture, newgameTexture, instance.getVertexBufferObjectManager(), this);
		registerTouchArea(newgameMenuItem);
		attachChild(newgameMenuItem);
		setTouchAreaBindingOnActionDownEnabled(true);
		
		highscoreMenuItem = new ButtonSprite(mCamera.getWidth()/2 - highscoreTexture.getWidth()/2, 230, highscoreTexture, highscorePressedTexture, highscoreTexture, instance.getVertexBufferObjectManager(), this);
		registerTouchArea(highscoreMenuItem);
		attachChild(highscoreMenuItem);
		setTouchAreaBindingOnActionDownEnabled(true);
		
		settingsMenuItem = new ButtonSprite(mCamera.getWidth()/2 - settingsTexture.getWidth()/2, 310, settingsTexture, settingsPressedTexture, settingsTexture, instance.getVertexBufferObjectManager(), this);
		registerTouchArea(settingsMenuItem);
		attachChild(settingsMenuItem);
		setTouchAreaBindingOnActionDownEnabled(true);
		
		quitMenuItem = new ButtonSprite(mCamera.getWidth()/2 - quitTexture.getWidth()/2, 390, quitTexture, quitPressedTexture, quitTexture, instance.getVertexBufferObjectManager(), this);
		registerTouchArea(quitMenuItem);
		attachChild(quitMenuItem);
		setTouchAreaBindingOnActionDownEnabled(true);

	}

	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		System.out.println("CLICKED");
		if(pButtonSprite.equals(newgameMenuItem)){
			System.out.println("Pressed newgame");
			instance.startActivity(new Intent(instance, GameActivity.class));
		}
		else if(pButtonSprite.equals(highscoreMenuItem)){
			System.out.println("Pressed Highscores");
			instance.setScene(new HighscoreScene());
		}
		else if(pButtonSprite.equals(settingsMenuItem)){
			System.out.println("Pressed settngs");
			instance.setScene(new SettingsScene());
		}
		else if(pButtonSprite.equals(quitMenuItem)){
			instance.finish();
		}
	}
	
}
