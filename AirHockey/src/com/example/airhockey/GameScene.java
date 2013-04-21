package com.example.airhockey;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.color.Color;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.preference.PreferenceManager;
import android.util.Log;

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
	/* Text graphic item showing player ones score */
	private Text playerOneGoalsText;
	/* Text graphic item showing player twos score */
	private Text playerTwoGoalsText;
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
		this.preference = PreferenceManager.getDefaultSharedPreferences(instance);
		this.goalsToWin = preference.getInt("goalsToWin", 5);
		
		this.createBackground();
		addGoalText();
		initializePlayers();
		
		this.registerUpdateHandler(new GameUpdateHandler(playerOneMallet, playerTwoMallet, puck));
	}
	
	/**
	 * Adds text graphic displayig the goal scores
	 */
	private void addGoalText(){
		this.playerOneGoalsText = new Text(0, 0, instance.mFont, "0", instance.getVertexBufferObjectManager());
		this.playerTwoGoalsText = new Text(0, 0, instance.mFont, "0", instance.getVertexBufferObjectManager());
		playerOneGoalsText.setPosition(mCamera.getWidth()-40, mCamera.getHeight()*1/4-playerOneGoalsText.getWidth()/2);
		playerTwoGoalsText.setPosition(mCamera.getWidth()-45, mCamera.getHeight()*3/4-playerTwoGoalsText.getWidth()/2);
		playerOneGoalsText.setRotation(180);
		playerTwoGoalsText.setRotation(0);
		//TODO: Add "WIN" text n stuff
		attachChild(playerOneGoalsText);
		attachChild(playerTwoGoalsText);
	}
	
	/**
	 * Initializes the players and puck
	 */
	private void initializePlayers() {
		String size = preference.getString(SettingsActivity.malletSize, SettingsActivity.medium);
		this.playerOneMallet = new Mallet(size, 1);
		this.playerTwoMallet = new Mallet(size, 2);
		this.attachChild(this.playerOneMallet.getSprite());
		this.attachChild(this.playerTwoMallet.getSprite());
		this.puck.initPuck();
		this.attachChild(this.puck.getSprite());
		this.playerOneScore = 0;
		this.playerTwoScore = 0;
		this.playerOneWin = false;
		this.playerTwoWin = false;
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
			playerOneGoalsText.setText(""+playerOneScore);
			if(playerOneScore==goalsToWin){
				playerOneWin = true;
				gameOver();
			}
			puck.resetPuck();
		}
		else if(yPos>instance.mCamera.getHeight()){
			//increment player two score, display it on the screen
			playerTwoScore ++;
			playerTwoGoalsText.setText(""+playerTwoScore);
			if(playerTwoScore==goalsToWin){
				playerTwoWin = true;
				gameOver();
			}
			puck.resetPuck();
		}
	}
	
	/**
	 * Ends the game, declares a winner. Then it saves the game in the match history..
	 */
	private void gameOver(){
		System.out.println("GAME OVER, END THE GAME FFS");
		if(playerOneWin){
			//TODO: Display player one has won
		}
		if(playerTwoWin){
			//TODO: Display player two has won
		}
		//TODO: Save match in match history
		instance.startActivity(new Intent(instance, EndOfGameActivity.class));
		instance.finish();
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
		PointF touch = new PointF(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
		PointF ball = new PointF(playerOneMallet.getOrigoX(), playerOneMallet.getOrigoY());
		Log.d("GameScene", "Angle: " + getAngle(touch, ball));
		return false;
	}
	
    public float getAngle(PointF one, PointF two) {
        float angle = (float) Math.toDegrees(Math.atan2(one.x - two.x, one.y - two.y));

        if(angle < 0){
            angle += 360;
        }

        return angle;
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
