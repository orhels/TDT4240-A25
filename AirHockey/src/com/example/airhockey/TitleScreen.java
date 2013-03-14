package com.example.airhockey;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.AnimatedSpriteMenuItem;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import android.opengl.GLES20;
import android.os.Bundle;
import android.widget.Toast;

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
	
	private static SimpleBaseGameActivity instance;
	
	private AnimatedSpriteMenuItem newgameMenuItem;
	/**
	 * If we want to use standard Android layout.
	 */
//	@Override
//	protected void onCreate(Bundle pSavedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(pSavedInstanceState);
//		setContentView(R.layout.title_view);
//	}
	
	public TitleScreen(){
		super(MainActivity.getInstance().mCamera);
		
		instance = MainActivity.getInstance();
		setBackground(new Background(Color.WHITE));
		
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
		
		
		//Menu button textures
		menuTexture = new BitmapTextureAtlas(instance.getTextureManager(), 1024, 512);
		newgameTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTexture, instance, "new_game.png", 0, 1);
		highscoreTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTexture, instance, "highscores.png", 0, 230);
		settingsTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTexture, instance, "settings.png", 0, 310);
		quitTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTexture, instance, "quit.png", 0, 390);
		
		createMenu();
		
		menuTexture.load();
		setOnMenuItemClickListener(this);
	}
	
//	@Override
//	public EngineOptions onCreateEngineOptions() {
//		instance = this;
//		this.camera = new Camera(0,0, CAMERA_WIDTH, CAMERA_HEIGHT);
//		
//		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.camera);
//	}
//
//	@Override
//	protected void onCreateResources() {
//		this.menuTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
//		this.titleTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.menuTexture, this, "airhockey_title.png", 0,0);
//		this.newgameTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.menuTexture, this, "new_game.png", 0,50);
//		this.highscoreTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.menuTexture, this, "highscores.png", 0,100);
//		this.settingsTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.menuTexture, this, "settings.png", 0,150);
//		this.quitTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.menuTexture, this, "quit.png", 0,200);
//		this.menuTexture.load();
//		
//	}
//
//	@Override
//	protected Scene onCreateScene() {
//		this.mEngine.registerUpdateHandler(new FPSLogger());
//		
//		this.createMenu();
//		
//		this.currentScene = new Scene();
//		this.currentScene.setBackground(new Background(Color.BLUE));
//		
//		
//		// DO MORE?!?!?!
//		return this.currentScene;
//	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		System.out.println("CLICKED ON THE SCREEN! "+pMenuItem.getID());
		switch (pMenuItem.getID()) {
		case MENU_NEWGAME:
			System.out.println("Pressed newgame");
			return true;
		case MENU_HIGHSCORES:
			System.out.println("Pressed highscores");
			return true;
		case MENU_SETTINGS:
			System.out.println("Pressed settings");
			return true;
		case MENU_QUIT:
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
		System.out.println("CREATING MENU ITEMS");
		
		final SpriteMenuItem newgameMenuItem = new SpriteMenuItem(MENU_NEWGAME, this.newgameTexture, instance.getVertexBufferObjectManager());
		newgameMenuItem.setPosition( mCamera.getWidth()/2 - newgameMenuItem.getWidth()/2, 150 );
		addMenuItem(newgameMenuItem);
		
		final SpriteMenuItem highscoreMenuItem = new SpriteMenuItem(MENU_HIGHSCORES, this.highscoreTexture, instance.getVertexBufferObjectManager());
		highscoreMenuItem.setPosition( mCamera.getWidth()/2 - highscoreMenuItem.getWidth()/2, 230 );
		addMenuItem(highscoreMenuItem);
		
		final SpriteMenuItem settingsMenuItem = new SpriteMenuItem(MENU_SETTINGS, this.settingsTexture, instance.getVertexBufferObjectManager());
		settingsMenuItem.setPosition( mCamera.getWidth()/2 - settingsMenuItem.getWidth()/2, 310 );
		addMenuItem(settingsMenuItem);
		
		final SpriteMenuItem quitMenuItem = new SpriteMenuItem(MENU_QUIT, this.quitTexture, instance.getVertexBufferObjectManager());
		quitMenuItem.setPosition( mCamera.getWidth()/2 - quitMenuItem.getWidth()/2, 390 );
		addMenuItem(quitMenuItem);
		

	}
//	
//	public void setCurrentScene(Scene scene){
//		currentScene = scene;
//		getEngine().setScene(currentScene);
//	}
//	
//	public static SimpleBaseGameActivity getSharedInstance(){
//		return instance;
//	}
	
}
