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
	/* Player one's score */
	private int playerOneScore;
	/* Player two's score */
	private int playerTwoScore;
	/* Number of points needed to win the game */
	private int goalsToWin;
	/* True if player one has won */
	private boolean playerOneWin;
	/* True if player two has won */
	private boolean playerTwoWin;
	
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
		this.goalsToWin = preference.getInt("goalsToWin", 5);
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
		playerOneScore = 0;
		playerTwoScore = 0;
		playerOneWin = false;
		playerTwoWin = false;
	}
	
	/**
	 * Method for moving the puck. Called on every update 
	 */
	public void update(){
		puck.updatePuck();
		checkScore();
	}
	/**
	 * Checks if the puck is outside the Y bounds, and assigns a winner.
	 * return True if there is a winner
	 */
	public void checkScore(){
		float yPos = puck.getSprite().getY();
		if(yPos<0){
			//increment player one score, display it on the screen
			playerOneScore ++;
			System.out.println("Player One scored: "+playerOneScore);
			if(playerOneScore==goalsToWin){
				playerOneWin = true;
				gameOver();
			}
		}
		else if(yPos>instance.mCamera.getHeight()){
			//increment player two score, display it on the screen
			playerTwoScore ++;
			System.out.println("Player two scored: "+playerTwoScore);
			if(playerTwoScore==goalsToWin){
				playerTwoWin = true;
				gameOver();
			}
		}
	}
	
	/**
	 * Ends the game, declares a winner. Then it saves the game in the match history..
	 */
	private void gameOver(){
		System.out.println("GAME OVER, END THE GAME FFS");
		
		
	}
	/**
	 * Creates the graphic background in the match
	 */
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
	
	public void destroySprites() {
		playerOneMallet.getSprite().detachSelf();
		playerTwoMallet.getSprite().detachSelf();
		puck.getSprite().detachSelf();
		backgroundSprite.detachSelf();
	}
}
