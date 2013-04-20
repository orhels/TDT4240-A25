package com.example.airhockey;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.color.Color;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class GameScene extends Scene implements IOnSceneTouchListener {
	
	
	private Camera mCamera;
	private GameActivity instance;
	
	/* Player one mallet */
	private Mallet playerOneMallet;
	/* Player two mallet */
	private Mallet playerTwoMallet;
	/* The Puck */
	private Puck puck = Puck.PUCK;
	
	// BACKGROUND
	private BitmapTextureAtlas backgroundTextureAtlas;
	private TextureRegion backgroundTextureRegion;
	private Sprite backgroundSprite;
	
	/* The container for the game preferences */
	private SharedPreferences preference;
	/* Preference keys */

	/**
	 * Constructor
	 */
	public GameScene(){
		this.instance = GameActivity.getInstance();
		this.mCamera = instance.mCamera;
		this.createBackground();
		this.preference = PreferenceManager.getDefaultSharedPreferences(instance);
		initializePlayers();
		this.registerUpdateHandler(new GameUpdateHandler(playerOneMallet, playerTwoMallet, puck));
	}
	
	private void initializePlayers() {
		String size = preference.getString(SettingsActivity.malletSize, SettingsActivity.medium);
		this.playerOneMallet = new Mallet(size, 1);
		this.playerTwoMallet = new Mallet(size, 2);
		this.attachChild(this.playerOneMallet.getSprite());
		this.attachChild(this.playerTwoMallet.getSprite());
		this.attachChild(this.puck.getSprite());
	}
	
	/**
	 * Method for moving the puck. Called on every update 
	 */
	public void update(){
		puck.updatePuck();
		playerOneMallet.updateSpeed();
		playerTwoMallet.updateSpeed();
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

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		float yPos = pSceneTouchEvent.getY();
		if (yPos < mCamera.getHeight() / 2) {
			if (pSceneTouchEvent.isActionMove()) {
				playerOneMallet.setPosition(pSceneTouchEvent);
			}
			
		} else if (yPos > mCamera.getHeight() / 2) {
			if (pSceneTouchEvent.isActionMove()) {
				playerTwoMallet.setPosition(pSceneTouchEvent);
			}			
		}
		return false;
	}
	
	/**
	 * Returns the mallet object of player one.
	 * @return
	 */
	public Mallet getPlayerOne(){
		return playerOneMallet;
	}
	/**
	 * Returns the mallet object of player two.
	 * @return
	 */
	public Mallet getPlayerTwo(){
		return playerTwoMallet;
	}
}
