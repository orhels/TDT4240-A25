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
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import android.opengl.GLES20;
import android.os.Bundle;

public class TitleScreen extends SimpleBaseGameActivity implements IOnMenuItemClickListener {

	Scene mainScene;
	
	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;
	
	protected static final int MENU_NEWGAME = 0;
	protected static final int MENU_HIGHSCORES = 1;
	protected static final int MENU_SETTINGS = 2;
	protected static final int MENU_QUIT = 3;
	
	protected Camera camera;
	
	private MenuScene menuScene;
	
	private BitmapTextureAtlas menuTexture;
	protected ITextureRegion titleTexture;
	protected ITextureRegion newgameTexture;
	protected ITextureRegion highscoreTexture;
	protected ITextureRegion settingsTexture;
	protected ITextureRegion quitTexture;
	
	/**
	 * If we want to use standard Android layout.
	 */
//	@Override
//	protected void onCreate(Bundle pSavedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(pSavedInstanceState);
//		setContentView(R.layout.title_view);
//	}
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		this.camera = new Camera(0,0, CAMERA_WIDTH, CAMERA_HEIGHT);
		
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.camera);
	}

	@Override
	protected void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		this.menuTexture = new BitmapTextureAtlas(this.getTextureManager(), 200, 400);
		this.titleTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.menuTexture, this, "airhockey_title.png", 0,0);
		this.newgameTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.menuTexture, this, "new_game.png", 0,50);
		this.highscoreTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.menuTexture, this, "highscores.png", 0,100);
		this.settingsTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.menuTexture, this, "settings.png", 0,150);
		this.quitTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.menuTexture, this, "quit.png", 0,200);
		this.menuTexture.load();
		
	}

	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		
		this.createMenu();
		
		this.mainScene = new Scene();
		this.mainScene.setBackground(new Background(Color.BLUE));
		
		
		// DO MORE?!?!?!
		return this.mainScene;
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		System.out.println("CLICKED ON THE SCREEN! "+pMenuItem.getID());
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
		this.menuScene = new MenuScene(this.camera);

		final SpriteMenuItem newgameMenuItem = new SpriteMenuItem(MENU_NEWGAME, this.newgameTexture, this.getVertexBufferObjectManager());
		newgameMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		this.menuScene.addMenuItem(newgameMenuItem);
		
		final SpriteMenuItem highscoreMenuItem = new SpriteMenuItem(MENU_HIGHSCORES, this.highscoreTexture, this.getVertexBufferObjectManager());
		highscoreMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		this.menuScene.addMenuItem(highscoreMenuItem);
		
		final SpriteMenuItem settingsMenuItem = new SpriteMenuItem(MENU_SETTINGS, this.settingsTexture, this.getVertexBufferObjectManager());
		settingsMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		this.menuScene.addMenuItem(settingsMenuItem);
		
		final SpriteMenuItem quitMenuItem = new SpriteMenuItem(MENU_QUIT, this.quitTexture, this.getVertexBufferObjectManager());
		quitMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		this.menuScene.addMenuItem(quitMenuItem);
		
		this.menuScene.buildAnimations();
		this.menuScene.setBackgroundEnabled(false);
		this.menuScene.setOnMenuItemClickListener(this);

	}
	
}
