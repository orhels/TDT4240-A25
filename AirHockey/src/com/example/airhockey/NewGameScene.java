package com.example.airhockey;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.text.Text;
import org.andengine.helperclasses.InputText;
import org.andengine.helperclasses.Slider;
import org.andengine.helperclasses.Slider.OnSliderValueChangeListener;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.color.Color;

import android.widget.EditText;

public class NewGameScene extends Scene implements OnClickListener, OnSliderValueChangeListener
{
	
	private static MainActivity instance;
	//TITLE
	private BitmapTextureAtlas titleAtlas;
	private ITextureRegion titleTexture;
	
	//BACKGROUND
	protected ITextureRegion backgroundTexture;
	
	//BACK BUTTON
	private BuildableBitmapTextureAtlas backButtonAtlas;
	private ITextureRegion backButtonTexture;
	private ITextureRegion backButtonPressedTexture;
	private ButtonSprite backButton;
	
	//START BUTTON
	private BuildableBitmapTextureAtlas startButtonAtlas;
	private ITextureRegion startButtonTexture;
	private ITextureRegion startButtonPressedTexture;
	private ButtonSprite startButton;
	
	//PLAYER1 NAMEFIELD
	private BitmapTextureAtlas player1Atlas;
	private TiledTextureRegion player1TiledTextureRegion;
	private ITextureRegion player1TextureRegion;
	private InputText player1NameField;
	
	//PLAYER2 NAMEFIELD
	private BitmapTextureAtlas player2Atlas;
	private TiledTextureRegion player2TiledTextureRegion;
	private ITextureRegion player2TextureRegion;
	private InputText player2NameField;
	
	//GOALSLIDER
	private Text goalText;
	private Text goalText2;
	private BitmapTextureAtlas sliderTextureAtlas;
	private ITextureRegion sliderTextureRegion;
	private BitmapTextureAtlas thumbTextureAtlas;
	private ITextureRegion thumbTextureRegion;
	private Slider slider;
	
	
	
	/* TODO:
	 * Validate input for goalfield
	 * Create game activity
	 * Load the names of the last played match from the match history db
	 */
	
	
	public NewGameScene() 
	{
		instance = MainActivity.getInstance();
		
		createUI();
	}
	
	public void createUI()
	{
		// BACKGROUND
		setBackground(new Background(Color.WHITE));
		BitmapTextureAtlas backgroundAtlas = new BitmapTextureAtlas(instance.getTextureManager(), 2048, 1024);
		backgroundTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundAtlas, instance, "titlescreen/menu_background.png", 0, 0);
		IMenuItem backgroundMenuItem = new SpriteMenuItem(11, backgroundTexture, instance.getVertexBufferObjectManager());
		backgroundMenuItem.setPosition(instance.mCamera.getWidth()/2 - backgroundMenuItem.getWidth()/2, instance.mCamera.getHeight()/2 - backgroundMenuItem.getHeight()/2);
		attachChild(backgroundMenuItem);
		backgroundAtlas.load();
		
		// TITLE
		titleAtlas = new BitmapTextureAtlas(instance.getTextureManager(), 2048, 1024);
		titleTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(titleAtlas, instance, "newgame/new_game_title.png", 0, 0);
		Sprite titleItem = new Sprite(instance.mCamera.getWidth()/2 - titleTexture.getWidth()/2, 2, titleTexture, instance.getVertexBufferObjectManager());
		attachChild(titleItem);
		titleAtlas.load();
		
		// PLAYER1 NAME FIELD
		player1Atlas = new BitmapTextureAtlas(instance.getTextureManager(), 512, 512);
		player1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(player1Atlas, instance, "newgame/player1_textfield_bg.png", 0 , 0);
		player1TiledTextureRegion = new TiledTextureRegion(player1Atlas, player1TextureRegion);
		player1NameField = new InputText(0, 75, "Player 1 Name", "Name of the player", player1TiledTextureRegion, instance.mFont, 100, 50, instance.getVertexBufferObjectManager(), instance);
		player1NameField.setText("Player1");
		attachChild(player1NameField);
		registerTouchArea(player1NameField);
		player1Atlas.load();
		
		// PLAYER2 NAME FIELD
		player2Atlas = new BitmapTextureAtlas(instance.getTextureManager(), 512, 512);
		player2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(player2Atlas, instance, "newgame/player2_textfield_bg.png", 0 , 0);
		player2TiledTextureRegion = new TiledTextureRegion(player2Atlas, player2TextureRegion);
		player2NameField = new InputText(500, 75, "Player 2 Name", "Name of the player", player2TiledTextureRegion, instance.mFont, 100, 50, instance.getVertexBufferObjectManager(), instance);
		player2NameField.setText("Player2");
		attachChild(player2NameField);
		registerTouchArea(player2NameField);
		player2Atlas.load();
		
		
		// GOALSLIDER
		goalText = new Text(10, 180, instance.mFont, "Goals to win:", instance.getVertexBufferObjectManager());
		attachChild(goalText);
		sliderTextureAtlas = new BitmapTextureAtlas(instance.getTextureManager(), 512, 512);
		sliderTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sliderTextureAtlas, instance, "newgame/slider.png" , 0 , 0);
		thumbTextureAtlas = new BitmapTextureAtlas(instance.getTextureManager(), 512, 512);
		thumbTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(thumbTextureAtlas, instance, "newgame/thumb.png", 0 ,0);
		slider = new Slider(sliderTextureRegion, thumbTextureRegion, instance.getVertexBufferObjectManager());
		slider.setOnSliderValueChangeListener(this);
		slider.setUserData(0);
		sliderTextureAtlas.load();
		thumbTextureAtlas.load();
		slider.setX(280);
		slider.setY(185);
		this.attachChild(slider);
		this.registerTouchArea(slider.getmThumb());
		goalText2 = new Text(220, 180, instance.mFont, ""+slider.getUserData() , instance.getVertexBufferObjectManager());
		this.attachChild(goalText2);
		
		
		// START BUTTON
		startButtonAtlas = new BuildableBitmapTextureAtlas(instance.getTextureManager(), 512, 512);
		startButtonPressedTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(startButtonAtlas, instance, "newgame/start_button_pressed.png");
		startButtonTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(startButtonAtlas, instance, "newgame/start_button.png");
		try
		{
			startButtonAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
			startButtonAtlas.load();
		} catch (TextureAtlasBuilderException e)
		{
			e.printStackTrace();
		}
		startButton = new ButtonSprite(525, 380, startButtonTexture, startButtonPressedTexture, startButtonTexture, instance.getVertexBufferObjectManager(), this);
		registerTouchArea(startButton);
		attachChild(startButton);
		
		// BACK BUTTON
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

	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		if(pButtonSprite.equals(backButton)){
			instance.setScene(new TitleScene());
		}
		else if(pButtonSprite.equals(startButton))
		{
			// Create GameActivity
		}
	}

	@Override
	public void onSliderValueChanged(float value) 
	{
		// TODO Auto-generated method stub
		goalText2.setText("" + (int) value);
	}

}
