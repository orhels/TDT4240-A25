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

/**
 * @author G25
 * @version 1.0
 */
public class GameScene extends Scene implements IOnSceneTouchListener {


	private Camera mCamera;
	private GameActivity instance;

	/* The Puck */
	private Puck puck = Puck.PUCK;
	private Player playerOne, playerTwo;
	private Goal playerOneGoal, playerTwoGoal;
	/* Text graphic item showing the players' scores */
	private Text playerTwoGoalsText, playerOneGoalsText;
	/* Number of points needed to win the game */
	private int goalsToWin;

	// BACKGROUND
	private BitmapTextureAtlas backgroundTextureAtlas;
	private TextureRegion backgroundTextureRegion;
	private Sprite backgroundSprite;
	private DatabaseHelper db;

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
		try {
			this.goalsToWin = Integer.parseInt(preference.getString("goalsToWin", "5"));
		} catch (Exception e) {
			goalsToWin = 5;
		}

		this.createBackground();
		initializeGoals();
		addGoalText();
		initializePlayers();

		this.registerUpdateHandler(new GameUpdateHandler(playerOne.getMallet(), playerTwo.getMallet(), puck));
	}

	/**
	 * Adds text graphic displayig the goal scores
	 */
	private void addGoalText(){
		this.playerTwoGoalsText = new Text(0, 0, instance.mFont, "00", instance.getVertexBufferObjectManager());
		this.playerOneGoalsText = new Text(0, 0, instance.mFont, "00", instance.getVertexBufferObjectManager());
		playerTwoGoalsText.setRotation(180);
		playerOneGoalsText.setRotation(0);
		playerTwoGoalsText.setPosition(30, mCamera.getHeight()*1/4-playerTwoGoalsText.getWidth()/2);
		playerOneGoalsText.setPosition(mCamera.getWidth()-75, mCamera.getHeight()*3/4-playerOneGoalsText.getWidth()/2);
		//TODO: Add "WIN" text n stuff
		attachChild(playerTwoGoalsText);
		attachChild(playerOneGoalsText);
		
	}
	
	private void initializeGoals() {
		playerOneGoal = new Goal(100, mCamera.getWidth() - 100);
		playerTwoGoal = new Goal(100, mCamera.getWidth() - 100);
	}

	/**
	 * Initializes the players and puck
	 */
	private void initializePlayers() {
		String size = preference.getString(Constants.MALLETSIZE, Constants.MEDIUM);
		playerOne = new Player(new Mallet(size, 1));
		playerTwo = new Player(new Mallet(size, 2));
		this.attachChild(playerOne.getMallet().getSprite());
		this.attachChild(playerTwo.getMallet().getSprite());
		this.puck.initPuck();
		this.attachChild(this.puck.getSprite());
		Intent intent = instance.getIntent();
		if (intent.hasExtra(NewGameActivity.player1Name) && intent.hasExtra(NewGameActivity.player2Name)) {
			playerOne.setName(intent.getStringExtra(NewGameActivity.player1Name));
			playerTwo.setName(intent.getStringExtra(NewGameActivity.player2Name));
		}
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
		if(yPos + (puck.getSprite().getHeight() / 2) < 0){
			playerScored(playerOne);
			int score = playerOne.getScore();
			if (score < 10)
			{
				playerOneGoalsText.setText("0" + score);
			}
			else
			{
				playerOneGoalsText.setText("" + score);
			}
			
		}
		else if(yPos + (puck.getSprite().getHeight() / 2) > instance.mCamera.getHeight()){
			playerScored(playerTwo);
			int score = playerTwo.getScore();
			if (score < 10)
			{
				playerTwoGoalsText.setText("0"+score);
			}
			else
			{
				playerTwoGoalsText.setText(""+ score);				
			}
			
		}
	}

	private void playerScored(Player scored) {
		if (scored.incrementScore() >= goalsToWin) {
			scored.setWon(true);
			gameOver();
		}
		resetScreenEntities();
	}

	/**
	 * 
	 */
	private void resetScreenEntities() 
	{
		puck.resetPuck();
		playerOne.reset();
		playerTwo.reset();
	}

	/**
	 * Ends the game, declares a winner. Then it saves the game in the match history..
	 */
	private void gameOver(){
		String winnerName = "", score = "", loserName = "";
		if(playerOne.hasWon()){
			winnerName = playerOne.getName();
			loserName = playerTwo.getName();
			score = playerOne.getScore() + " - " + playerTwo.getScore();
		}
		if(playerTwo.hasWon()){
			winnerName = playerTwo.getName();
			loserName = playerOne.getName();
			score = playerTwo.getScore() + " - " + playerOne.getScore();
		}
		//TODO: Save match in match history
		Intent intent = new Intent(instance, EndOfGameActivity.class);
		intent.putExtra(Constants.WINNER, winnerName);
		intent.putExtra(Constants.SCORE, score);
		intent.putExtra(Constants.LOSER, loserName);
		saveMatch();
		instance.startActivity(intent);
		instance.finish();
	}
	
	private void saveMatch() {
		db = new DatabaseHelper(instance, "AirHockeyDB");
		Log.d("GameScene", "Saving the scores, lzm");
		db.saveMatch(playerOne.getName(), playerTwo.getName(), playerOne.getScore(), playerTwo.getScore(), "EEFOEKF");
		db.close();
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
				playerOne.onTouch(pSceneTouchEvent);
			}

		} else if (yPos > mCamera.getHeight() / 2) {
			if (pSceneTouchEvent.isActionMove()) {
				playerTwo.onTouch(pSceneTouchEvent);
			}			
		}
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
		return playerOne.getMallet();
	}
	/**
	 * Returns the mallet object of player two.
	 * @return
	 */
	public Mallet getPlayerTwo(){
		return playerTwo.getMallet();
	}

	public void destroySprites() {
		playerOne.getMallet().getSprite().detachSelf();
		playerTwo.getMallet().getSprite().detachSelf();
		puck.getSprite().detachSelf();
		backgroundSprite.detachSelf();
	}
	
	public Goal getPlayerOneGoal() {
		return playerOneGoal;
	}

	public Goal getPlayerTwoGoal() {
		return playerTwoGoal;
	}
}
