package com.example.airhockey;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.util.color.Color;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class GameScene extends Scene{
	
	
	private Camera mCamera;
	private GameActivity gameInstance;
	
	/* Player one mallet */
	private Mallet playerOneMallet;
	/* Player two mallet */
	private Mallet playerTwoMallet;
	/* The Puck */
	private Puck puck;
	
	/* The container for the game preferences */
	private SharedPreferences pref;
	/* Preference keys */
	public static final String ballSpeed = "Speed", malletSize = "Mallet", ballSize = "Ball"; 

	/**
	 * Constructor
	 */
	public GameScene(){
		gameInstance = GameActivity.getInstance();
		setBackground(new Background(Color.WHITE));
		mCamera = gameInstance.mCamera;
		
		pref = PreferenceManager.getDefaultSharedPreferences(gameInstance);
		
		//Create mallets
		int size = Integer.parseInt(pref.getString(malletSize, "Medium"));
		playerOneMallet = new Mallet(size);
		playerTwoMallet = new Mallet(size);
		playerOneMallet.setPosition(10, 10);
		playerTwoMallet.setPosition(gameInstance.CAMERA_HEIGHT-10, gameInstance.CAMERA_WIDTH-10);
		attachChild(playerOneMallet.sprite);
		attachChild(playerTwoMallet.sprite);
		
		registerUpdateHandler(new GameUpdateHandler());
	}
	
	/**
	 * Method for mocing the puck. Called on every update 
	 */
	public void movePuck(){
		puck.updatePuck();
	}
	
}
