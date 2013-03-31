package com.example.airhockey;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;
import android.content.Intent;

public class TitleScreen extends MenuScene implements IOnMenuItemClickListener {

	Scene currentScene;
	
	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;
	
	protected static final int MENU_NEWGAME = 0;
	protected static final int MENU_HIGHSCORES = 1;
	protected static final int MENU_SETTINGS = 2;
	protected static final int MENU_QUIT = 3;
	
	private BitmapTextureAtlas titleMenuTexture;
	protected ITextureRegion titleTexture;
	protected ITextureRegion backgroundTexture;
	
	private BitmapTextureAtlas menuTexture;
	protected ITextureRegion newgameTexture;
	protected ITextureRegion highscoreTexture;
	protected ITextureRegion settingsTexture;
	protected ITextureRegion quitTexture;
	
	protected SpriteMenuItem newgameMenuItem;
	protected SpriteMenuItem highscoreMenuItem;
	protected SpriteMenuItem settingsMenuItem;
	protected SpriteMenuItem quitMenuItem;
	
	
	private static MainActivity instance;
	
	/**
	 * If we want to use standard Android layout.
	 */
	
	public TitleScreen(){
		super(MainActivity.getInstance().mCamera);
		
		instance = MainActivity.getInstance();
		setBackground(new Background(Color.WHITE));
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("titlescreen/");
		//Background
		BitmapTextureAtlas backgroundAtlas = new BitmapTextureAtlas(instance.getTextureManager(), 2048, 1024);
		backgroundTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundAtlas, instance, "menu_background.png", 0, 0);
		IMenuItem backgroundMenuItem = new SpriteMenuItem(11, backgroundTexture, instance.getVertexBufferObjectManager());
		backgroundMenuItem.setPosition(mCamera.getWidth()/2 - backgroundMenuItem.getWidth()/2, mCamera.getHeight()/2 - backgroundMenuItem.getHeight()/2);
		addMenuItem(backgroundMenuItem);
		backgroundAtlas.load();
		//Title texture and item
		titleMenuTexture = new BitmapTextureAtlas(instance.getTextureManager(), 2048, 1024);
		titleTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(titleMenuTexture, instance, "airhockey_title2.png", 0, 0);
		IMenuItem titleMenuItem = new SpriteMenuItem(10, titleTexture, instance.getVertexBufferObjectManager());
		titleMenuItem.setPosition( mCamera.getWidth()/2 - titleMenuItem.getWidth()/2, 0);
		addMenuItem(titleMenuItem);
		titleMenuTexture.load();
		
		createMenu();
		
		menuTexture.load();
		setOnMenuItemClickListener(this);
	}
	


	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		System.out.println("CLICKED ON THE SCREEN! "+pMenuItem.getID());
		switch (pMenuItem.getID()) {
		case MENU_NEWGAME:
			newgameMenuItem.setColor(Color.BLACK);
			System.out.println("Pressed newgame");
			instance.startActivity(new Intent(instance, GameActivity.class));
			return true;	
		case MENU_HIGHSCORES:
			highscoreMenuItem.setColor(Color.BLACK);
			System.out.println("Pressed highscores");
			instance.setScene(new HighscoreScene());
			return true;	
		case MENU_SETTINGS:
			settingsMenuItem.setColor(Color.BLACK);
			System.out.println("Pressed settings");
			instance.setScene(new SettingsScene());
			return true;
		case MENU_QUIT:
			quitMenuItem.setColor(Color.BLACK);
			instance.finish();
			return true;
		default:
			break;
		}
		return false;
	}
//	
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
		menuTexture = new BitmapTextureAtlas(instance.getTextureManager(), 1024, 512);
		newgameTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTexture, instance, "new_game.png", 0, 1);
		highscoreTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTexture, instance, "highscores.png", 0, 230);
		settingsTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTexture, instance, "settings.png", 0, 310);
		quitTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTexture, instance, "quit.png", 0, 390);
		
		System.out.println("CREATING MENU ITEMS");
		
		newgameMenuItem = new SpriteMenuItem(MENU_NEWGAME, this.newgameTexture, instance.getVertexBufferObjectManager());
		newgameMenuItem.setPosition( mCamera.getWidth()/2 - newgameMenuItem.getWidth()/2, 150 );
		addMenuItem(newgameMenuItem);
		
		highscoreMenuItem = new SpriteMenuItem(MENU_HIGHSCORES, this.highscoreTexture, instance.getVertexBufferObjectManager());
		highscoreMenuItem.setPosition( mCamera.getWidth()/2 - highscoreMenuItem.getWidth()/2, 230 );
		addMenuItem(highscoreMenuItem);
		
		settingsMenuItem = new SpriteMenuItem(MENU_SETTINGS, this.settingsTexture, instance.getVertexBufferObjectManager());
		settingsMenuItem.setPosition( mCamera.getWidth()/2 - settingsMenuItem.getWidth()/2, 310 );
		addMenuItem(settingsMenuItem);
		
		quitMenuItem = new SpriteMenuItem(MENU_QUIT, this.quitTexture, instance.getVertexBufferObjectManager());
		quitMenuItem.setPosition( mCamera.getWidth()/2 - quitMenuItem.getWidth()/2, 390 );
		addMenuItem(quitMenuItem);
		

	}
	
}
