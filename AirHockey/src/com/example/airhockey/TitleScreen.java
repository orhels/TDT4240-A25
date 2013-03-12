package com.example.airhockey;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

public class TitleScreen extends SimpleBaseGameActivity implements IOnMenuItemClickListener {

	Scene mainMenu;
	
	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;
	
	protected static final int MENU_NEWGAME = 0;
	protected static final int MENU_HIGHSCORES = 1;
	protected static final int MENU_SETTINGS = 2;
	protected static final int MENU_QUIT = 3;
	
	protected Camera camera;
	
	private BitmapTextureAtlas menuTexture;
	protected ITextureRegion titleTexture;
	protected ITextureRegion newgameTexture;
	protected ITextureRegion highscoreTexture;
	protected ITextureRegion settingsTexture;
	protected ITextureRegion quitTexture;
	
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		this.camera = new Camera(0,0, CAMERA_WIDTH, CAMERA_HEIGHT);
		
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.camera);
	}

	@Override
	protected void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("res/");
		this.menuTexture = new BitmapTextureAtlas(this.getTextureManager(), 64, 64);
		this.titleTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.menuTexture, this, ".png", 0,0);
		this.newgameTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.menuTexture, this, ".png", 0,50);
		this.highscoreTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.menuTexture, this, ".png", 0,100);
		this.settingsTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.menuTexture, this, ".png", 0,150);
		this.quitTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.menuTexture, this, ".png", 0,200);
		this.menuTexture.load();
		
	}

	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		
		this.createMenu();
		
		this.mainMenu = new Scene();
		this.mainMenu.setBackground(new Background(0.09804f, 0.62722f, 0.87642f));
		
		// DO MORE?!?!?!
		return this.mainMenu;
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		// TODO Auto-generated method stub
		return false;
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
		
	}
	
}
