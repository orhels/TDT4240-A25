package com.example.airhockey;

import java.util.ArrayList;
import java.util.SortedMap;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.color.Color;


public class MatchHistoryScene extends MenuScene implements OnClickListener{

	static final int CAMERA_WIDTH = 800;
	static final int CAMERA_HEIGHT = 480;
	private static MainActivity instance;
	
	private BitmapTextureAtlas titleAtlas;
	private ITextureRegion titleTexture;
	
	private ITextureRegion backgroundTexture;
	
	private BuildableBitmapTextureAtlas backButtonAtlas;
	private ITextureRegion backButtonTexture;
	private ITextureRegion backButtonPressedTexture;
	private ButtonSprite backButton;
	
	private Text highscoresText1;
	
	private DatabaseHelper dbh;

	public MatchHistoryScene(){
		super(MainActivity.getInstance().mCamera);
		
		instance = MainActivity.getInstance();
		setBackground(new Background(Color.WHITE));
		
		//Main title
		titleAtlas = new BitmapTextureAtlas(instance.getTextureManager(), 2048, 1024);
		titleTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(titleAtlas, instance, "highscores/highscores_title.png", 0, 0);
		Sprite titleItem = new Sprite(mCamera.getWidth()/2 - titleTexture.getWidth()/2, 2, titleTexture, instance.getVertexBufferObjectManager());
		attachChild(titleItem);
		titleAtlas.load();
		
		createMenu();
		
		getMatchHistory();
	}
	
	/**
	 * Creates and displays the items for the screen
	 */
	private void createMenu()
	{
		// BACKGROUND
		setBackground(new Background(Color.WHITE));
		BitmapTextureAtlas backgroundAtlas = new BitmapTextureAtlas(instance.getTextureManager(), 2048, 1024);
		backgroundTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundAtlas, instance, "titlescreen/menu_background.png", 0, 0);
		IMenuItem backgroundMenuItem = new SpriteMenuItem(11, backgroundTexture, instance.getVertexBufferObjectManager());
		backgroundMenuItem.setPosition(mCamera.getWidth()/2 - backgroundMenuItem.getWidth()/2, mCamera.getHeight()/2 - backgroundMenuItem.getHeight()/2);
		attachChild(backgroundMenuItem);
		backgroundAtlas.load();
		
		//Create back button
		backButtonAtlas = new BuildableBitmapTextureAtlas(instance.getTextureManager(), 512, 512);
		backButtonPressedTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backButtonAtlas, instance, "back-icon-pressed.png");
		backButtonTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backButtonAtlas, instance, "back-icon.png");
		try {
			backButtonAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
			backButtonAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		backButton = new ButtonSprite(10, 365, backButtonTexture, backButtonPressedTexture, backButtonTexture, instance.getVertexBufferObjectManager(), this);
		registerTouchArea(backButton);
		attachChild(backButton);
		setTouchAreaBindingOnActionDownEnabled(true);
		
	}
	
	/**
	 * Fetch the match history from the database.
	 * Display them on the screen.
	 */
	public void getMatchHistory(){
		dbh = new DatabaseHelper(instance, "MatchHistory");
		ArrayList<Match> matches = dbh.getHighScores();
		
		//TODO: Get match history from db, then make a string
		
		String matchesString = "";
		
		if(matchesString.length()==0){
			matchesString = "There are no mathces in history";
		}
		highscoresText1 = new Text(0, 0, instance.mFont, matchesString, instance.getVertexBufferObjectManager());
		highscoresText1.setPosition(CAMERA_WIDTH/2 - highscoresText1.getWidth()/2, CAMERA_HEIGHT/2 - highscoresText1.getHeight()/2);
		attachChild(highscoresText1);
	}

	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		if(pButtonSprite.equals(backButton)){
			instance.setScene(new TitleScene());
		}
	}
	
	
}
