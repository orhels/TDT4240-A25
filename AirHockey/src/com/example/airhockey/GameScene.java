package com.example.airhockey;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.color.Color;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class GameScene extends Scene{
	
	
	private Camera mCamera;
	private GameActivity instance;
	
	/* Player one mallet */
	private Mallet playerOneMallet;
	/* Player two mallet */
	private Mallet playerTwoMallet;
	/* The Puck */
	private Puck puck;
	
	// BACKGROUND
	private BitmapTextureAtlas backgroundTextureAtlas;
	private TextureRegion backgroundTextureRegion;
	private Sprite backgroundSprite;
	
	/* The container for the game preferences */
	private SharedPreferences preference;
	/* Preference keys */
	public static final String ballSpeed = "Speed", malletSize = "Mallet", ballSize = "Ball"; 

	/**
	 * Constructor
	 */
	public GameScene(){
		this.instance = GameActivity.getInstance();
		this.mCamera = this.instance.mCamera;
		
		this.createBackground();
		
		this.preference = PreferenceManager.getDefaultSharedPreferences(instance);
		
		//Create mallets
		String size = preference.getString(this.malletSize, "Medium");
		this.playerOneMallet = new Mallet(size, 1);
		this.playerTwoMallet = new Mallet(size, 2);
		this.playerOneMallet.setPosition(10, 10);
		this.playerTwoMallet.setPosition(GameActivity.CAMERA_HEIGHT-10, GameActivity.CAMERA_WIDTH-10);
		this.attachChild(this.playerOneMallet.getSprite());
		this.attachChild(this.playerTwoMallet.getSprite());
		
		this.registerUpdateHandler(new GameUpdateHandler());
	}
	
	/**
	 * Method for moving the puck. Called on every update 
	 */
	public void movePuck(){
		//puck.updatePuck();
	}
	
	private void createBackground()
	{
		this.setBackground(new Background(Color.WHITE));
		this.setBackgroundEnabled(true);
		this.backgroundTextureAtlas = new BitmapTextureAtlas(this.instance.getTextureManager(), 1048, 1048);
		this.backgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.backgroundTextureAtlas, this.instance, "game/background.png", 0, 0);
		this.backgroundSprite = new Sprite(0, (this.instance.mCamera.getHeight()/2) - (this.backgroundTextureRegion.getHeight()/2), this.backgroundTextureRegion, this.instance.getVertexBufferObjectManager());
		this.attachChild(this.backgroundSprite);
		this.backgroundTextureAtlas.load();
	}

}
