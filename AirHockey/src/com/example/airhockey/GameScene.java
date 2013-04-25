package com.example.airhockey;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
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


/**
 * @author G25
 * @version 1.0
 */
public class GameScene extends Scene implements IOnSceneTouchListener, IOnAreaTouchListener{

	private final Camera mCamera;
	private final GameActivity instance;

	/* The Puck */
	private final Puck puck = Puck.PUCK;
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
	private final SharedPreferences preference;

	/*Goal graphic items*/
	private Rectangle goaltem1;
	private Rectangle goaltem2;
	private Rectangle goaltem3;
	private Rectangle goaltem4;
	
	private Text yesQuitText;
	private Text noQuitText;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD

	private PlayState playState;

	private Boolean quitBoxIsUp;

=======
	private Boolean quitBoxIsUp;
>>>>>>> f6f7caefbc6c75339b754482e9661aaa5861aba7
=======
	private Boolean quitBoxIsUp;
>>>>>>> f6f7caefbc6c75339b754482e9661aaa5861aba7
=======
	private Boolean quitBoxIsUp;
>>>>>>> f6f7caefbc6c75339b754482e9661aaa5861aba7
	private Rectangle quitBox;
	
	/**
	 * Constructor
	 */
	public GameScene() {
		this.instance = GameActivity.getInstance();
		this.mCamera = this.instance.mCamera;
		this.preference = PreferenceManager.getDefaultSharedPreferences(this.instance);
		try {
			this.goalsToWin = Integer.parseInt(this.preference.getString( "goalsToWin", "5"));
			this.puck.setSpeedMultiplier(Float.parseFloat(this.preference.getString(Constants.BALLSPEED, "5")));
		} catch (final Exception e) {
			// Catches formatting errors
			this.goalsToWin = 5;
			this.puck.setSpeedMultiplier(1f);
		}

		this.createBackground();
		this.initializeGoals();
		this.addGoalText();
		this.initializePlayers();
<<<<<<< HEAD
<<<<<<< HEAD
		this.registerUpdateHandler(new GameUpdateHandler(this.playerOne.getMallet(), this.playerTwo.getMallet(), this.puck));
		this.playState = PlayState.PLAYING;

<<<<<<< HEAD
=======
		this.registerUpdateHandler(new GameUpdateHandler(this.playerOne.getMallet(), this.playerTwo.getMallet(), this.puck));
		this.quitBoxIsUp = false;
>>>>>>> f6f7caefbc6c75339b754482e9661aaa5861aba7
=======

		this.registerUpdateHandler(new GameUpdateHandler(this.playerOne.getMallet(), this.playerTwo.getMallet(), this.puck));
		this.quitBoxIsUp = false;
>>>>>>> f6f7caefbc6c75339b754482e9661aaa5861aba7
=======

		this.registerUpdateHandler(new GameUpdateHandler(this.playerOne.getMallet(), this.playerTwo.getMallet(), this.puck));
		this.quitBoxIsUp = false;
>>>>>>> f6f7caefbc6c75339b754482e9661aaa5861aba7
	}

	/**
	 * Adds text graphic displayig the goal scores
	 */
	private void addGoalText() {
		this.playerTwoGoalsText = new Text(0, 0, this.instance.mFont, "00",
				this.instance.getVertexBufferObjectManager());
		this.playerOneGoalsText = new Text(0, 0, this.instance.mFont, "00",
				this.instance.getVertexBufferObjectManager());
		this.playerTwoGoalsText.setRotation(180);
		this.playerOneGoalsText.setRotation(0);
		this.playerTwoGoalsText.setPosition(
				30,
				((this.mCamera.getHeight() * 1) / 4)
						- (this.playerTwoGoalsText.getWidth() / 2));
		this.playerOneGoalsText.setPosition(
				this.mCamera.getWidth() - 75,
				((this.mCamera.getHeight() * 3) / 4)
						- (this.playerOneGoalsText.getWidth() / 2));
		this.attachChild(this.playerTwoGoalsText);
		this.attachChild(this.playerOneGoalsText);
	}

	private void initializeGoals() {
		this.playerOneGoal = new Goal(100, this.mCamera.getWidth() - 100);
		this.playerTwoGoal = new Goal(100, this.mCamera.getWidth() - 100);
		
		/*Goal graphics*/
		this.goaltem1 = new Rectangle(0, 0, 110, 5, instance.getVertexBufferObjectManager());
		this.goaltem2 = new Rectangle(this.mCamera.getWidth()-110, 0, 110, 5, instance.getVertexBufferObjectManager());
		this.goaltem3 = new Rectangle(0, this.mCamera.getHeight()-5, 110, 5, instance.getVertexBufferObjectManager());
		this.goaltem4 = new Rectangle(this.mCamera.getWidth()-110, this.mCamera.getHeight()-5, 110, 5, instance.getVertexBufferObjectManager());
		this.goaltem1.setColor(Color.BLACK);
		this.goaltem2.setColor(Color.BLACK);
		this.goaltem3.setColor(Color.BLACK);
		this.goaltem4.setColor(Color.BLACK);
		this.attachChild(this.goaltem1);
		this.attachChild(this.goaltem2);
		this.attachChild(this.goaltem3);
		this.attachChild(this.goaltem4);
	}

	/**
	 * Initializes the players and puck
	 */
	private void initializePlayers() {
		final String size = this.preference.getString(Constants.MALLETSIZE,
				Constants.MEDIUM);
		this.puck.initPuck();
		this.playerOne = new Player(new Mallet(size, 1));
		this.playerTwo = new Player(new Mallet(size, 2));
		this.attachChild(this.playerOne.getMallet().getSprite());
		this.attachChild(this.playerTwo.getMallet().getSprite());
		this.attachChild(this.puck.getSprite());
		final Intent intent = this.instance.getIntent();
		if (intent.hasExtra(Constants.PLAYER1NAME) && intent.hasExtra(Constants.PLAYER2NAME)) {
			this.playerOne.setName(intent.getStringExtra(Constants.PLAYER1NAME));
			this.playerTwo.setName(intent.getStringExtra(Constants.PLAYER2NAME));
		}
	}

	/**
	 * Method for moving the puck. Called on every update
	 */
	public void update() {
		if(playState==playState.PAUSED){
			return;
		}
		this.puck.updatePuck();
		this.checkScore();			
	}

	/**
	 * Checks if the puck is outside the Y bounds, and assigns a winner. return
	 * True if there is a winner
	 */
	public void checkScore() {
		final float yPos = this.puck.getSprite().getY();
		if ((yPos + (this.puck.getSprite().getHeight() / 2)) < 0) {
			this.playerScored(this.playerOne);
			final int score = this.playerOne.getScore();
			if (score < 10) {
				this.playerOneGoalsText.setText("0" + score);
			} else {
				this.playerOneGoalsText.setText("" + score);
			}

		} else if ((yPos + (this.puck.getSprite().getHeight() / 2)) > this.instance.mCamera
				.getHeight()) {
			this.playerScored(this.playerTwo);
			final int score = this.playerTwo.getScore();
			if (score < 10) {
				this.playerTwoGoalsText.setText("0" + score);
			} else {
				this.playerTwoGoalsText.setText("" + score);
			}

		}
	}

	private void playerScored(final Player scored) {
		if (scored.incrementScore() >= this.goalsToWin) {
			scored.setWon(true);
			this.gameOver();
		}
		this.resetScreenEntities();
	}

	/**
	 * 
	 */
	private void resetScreenEntities() {
		this.puck.resetPuck();
		this.playerOne.reset();
		this.playerTwo.reset();
	}

	/**
	 * Ends the game, declares a winner. Then it saves the game in the match
	 * history..
	 */
	private void gameOver() {
		String winnerName = "", score = "", loserName = "";
		if (this.playerOne.hasWon()) {
			winnerName = this.playerOne.getName();
			loserName = this.playerTwo.getName();
			score = this.playerOne.getScore() + " - "
					+ this.playerTwo.getScore();
		}
		if (this.playerTwo.hasWon()) {
			winnerName = this.playerTwo.getName();
			loserName = this.playerOne.getName();
			score = this.playerTwo.getScore() + " - "
					+ this.playerOne.getScore();
		}
		final Intent intent = new Intent(this.instance, EndOfGameActivity.class);
		intent.putExtra(Constants.WINNER, winnerName);
		intent.putExtra(Constants.SCORE, score);
		intent.putExtra(Constants.LOSER, loserName);
		this.saveMatch();
		this.instance.startActivity(intent);
		this.instance.finish();
	}

	private void saveMatch() {
		this.db = new DatabaseHelper(this.instance, "AirHockeyDB");
		this.db.saveMatch(this.playerOne.getName(), this.playerTwo.getName(),
				this.playerOne.getScore(), this.playerTwo.getScore(), "EEFOEKF");
		this.db.close();
	}

	/**
	 * Creates the graphic background in the match
	 */
	private void createBackground() {
		this.setBackground(new Background(Color.WHITE));
		this.setBackgroundEnabled(true);
		this.backgroundTextureAtlas = new BitmapTextureAtlas(
				this.instance.getTextureManager(), 1048, 1048);
		this.backgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.backgroundTextureAtlas, this.instance,
						"game/background.png", 0, 0);
		this.backgroundSprite = new Sprite(0,
				(this.instance.mCamera.getHeight() / 2)
						- (this.backgroundTextureRegion.getHeight() / 2),
				this.backgroundTextureRegion,
				this.instance.getVertexBufferObjectManager());
		this.attachChild(this.backgroundSprite);
		this.backgroundTextureAtlas.load();
	}

	@Override
	public boolean onSceneTouchEvent(final Scene pScene,
			final TouchEvent pSceneTouchEvent) {
		if(playState==PlayState.PAUSED){
			return false;
		}
		final float yPos = pSceneTouchEvent.getY();
		if (yPos < (this.mCamera.getHeight() / 2)) {
			if (pSceneTouchEvent.isActionMove()) {
				this.playerOne.onTouch(pSceneTouchEvent);
			}

		} else if (yPos > (this.mCamera.getHeight() / 2)) {
			if (pSceneTouchEvent.isActionMove()) {
				this.playerTwo.onTouch(pSceneTouchEvent);
			}
		}
		return false;
	}

	public float getAngle(final PointF one, final PointF two) {
		float angle = (float) Math.toDegrees(Math.atan2(one.x - two.x, one.y
				- two.y));

		if (angle < 0) {
			angle += 360;
		}

		return angle;
	}

	/**
	 * Returns the mallet object of player one.
	 * 
	 * @return
	 */
	public Mallet getPlayerOne() {
		return this.playerOne.getMallet();
	}

	/**
	 * Returns the mallet object of player two.
	 * 
	 * @return
	 */
	public Mallet getPlayerTwo() {
		return this.playerTwo.getMallet();
	}

	public void destroySprites() {
		this.playerOne.getMallet().getSprite().detachSelf();
		this.playerTwo.getMallet().getSprite().detachSelf();
		this.puck.getSprite().detachSelf();
		this.backgroundSprite.detachSelf();
		this.goaltem1.detachSelf();
		this.goaltem2.detachSelf();
		this.goaltem3.detachSelf();
		this.goaltem4.detachSelf();
	}

	public Goal getPlayerOneGoal() {
		return this.playerOneGoal;
	}

	public Goal getPlayerTwoGoal() {
		return this.playerTwoGoal;
	}
	
	public void showQuitBox(){
		// TODO: Add "Do you want to quit?" graphics
		playState = PlayState.PAUSED;
		quitBox = new Rectangle(0, 0, 300, 200, instance.getVertexBufferObjectManager());
		quitBox.setPosition(mCamera.getWidth()/2 - quitBox.getWidth()/2, mCamera.getHeight()/2 - quitBox.getHeight()/2);
		Text quitText= new Text(0, 0, instance.mFont, "Quit?", instance.getVertexBufferObjectManager());
		yesQuitText = new Text(0, 0, instance.mFont, "Yes", instance.getVertexBufferObjectManager());
		noQuitText = new Text(0, 0, instance.mFont, "No", instance.getVertexBufferObjectManager());
		quitBox.attachChild(quitText);
		quitBox.attachChild(yesQuitText);
		quitBox.attachChild(noQuitText);
		setOnAreaTouchListener(this);
		registerTouchArea(yesQuitText);
		registerTouchArea(noQuitText);
		quitText.setPosition(quitBox.getWidth()/2-quitText.getWidth()/2, 0);
		yesQuitText.setPosition(10,quitBox.getHeight()-yesQuitText.getHeight()-10);
		noQuitText.setPosition(quitBox.getWidth()-noQuitText.getWidth()-10, quitBox.getHeight()-noQuitText.getHeight()-10);
		attachChild(quitBox);
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			ITouchArea pTouchArea, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		if(pSceneTouchEvent.getX()<mCamera.getWidth()/2){
			destroySprites();
			instance.finish();
		}else{
			playState = PlayState.PLAYING;
			detachChild(quitBox);
			unregisterTouchArea(yesQuitText);
			unregisterTouchArea(noQuitText);
		}
		return false;
	}
}
