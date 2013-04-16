package com.example.airhockey;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

public class Puck {

	//Constants
//	protected final int PUCKID_NOT_Set = -1;
	protected static final float STANDARD_VELOCITY = 50.0f;
	protected float speedX;
	protected float speedY;
			
	//Fields
//	private int puckID = PUCKID_NOT_Set;
	private Camera mCamera;
	private BitmapTextureAtlas puckAtlas;
	private ITextureRegion puckTexture;
	private Sprite sprite;
	
	private final int puckID;
	private float posX;
	private float posY;
	private boolean moveable;
	
	/**
	 * Constructor
	 */
	public Puck(){
		puckID = (int) (100*Math.random());
		puckAtlas = new BitmapTextureAtlas(GameActivity.getInstance().getTextureManager(),256,256);
		puckTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(puckAtlas, GameActivity.getInstance(), "game/Puck.png", 60, 60);
		sprite = new Sprite(60, 60, puckTexture, GameActivity.getInstance().getVertexBufferObjectManager());
		
		speedX = STANDARD_VELOCITY;
		speedY = STANDARD_VELOCITY;
			
		moveable = true;
	}
	
	
//	public Engine onLoadEngine(){
//		this.mCamera = new Camera(0, 0, Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT);
//		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, 
//				new RatioResolutionPolicy(Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT), 
//				this.mCamera) );		
//	}
//	
//	public void onLoadResources(){
//		 this.mBitmapTextureAtlas = new BitmapTextureAtlas(166, 166, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
//		 this.mBitmapTextureAtlas = new BitmapTextureAtlas(null, 166, 166, null, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
//	}
	
	
	/**
	 * Update the puck position
	 * @param puckID
	 * @param posX
	 * @param posY
	 */
	public void updatePuck(){
		if(!moveable){
			return;
		}
		
		int leftWall = 0;
        int rightWall = (int) (mCamera.getWidth() - (int) sprite.getWidth());
        int lowerWall = (int) (mCamera.getHeight() - (int) sprite.getHeight());
        int upperWall = 0;
        
        float newX;
        float newY;

        // Set New X,Y Coordinates within Limits
        if (sprite.getX() >= leftWall){
            newX = sprite.getX() + speedX;
        }
        else{
            newX = leftWall;
        }
        if (sprite.getY() >= upperWall){
            newY = sprite.getY() + speedY;
        }
        else{
            newY = upperWall;
        }
        if (newX <= rightWall){
            newX = sprite.getX() + speedX;
        }
        else{
            newX = rightWall;
        }
        if (newY <= lowerWall){
            newY = sprite.getY() + speedY;
        }
        else{
            newY = rightWall;
        }
        sprite.setPosition(newX, newY);		
		
	}
	
	/**
	 * Returns the Puck X position
	 * @return
	 */
	public float getPuckPosX(){		
		return this.posX;
	}
	/**
	 * Returns the Puck Y position
	 * @return
	 */
	public float getPuckPosY(){
		return this.posY;
	}
}
