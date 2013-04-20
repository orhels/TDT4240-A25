package com.example.airhockey;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.preference.PreferenceManager;

public enum Puck 
{
	PUCK;
	
	//Fields
//	private int puckID = PUCKID_NOT_Set;
	private Camera mCamera;
	private BitmapTextureAtlas puckAtlas;
	private ITextureRegion puckTexture;
	private Sprite sprite;
	
	private float posX;
	private float posY;
	private boolean moveable;
	
	/* The total velocity of the puck, a sum of the speed in x and y direction */
	private float velocity;
	/* Speed in X direction*/
	private float speedX;
	/* Speed in Y direction*/
	private float speedY;
	/* The size of the puck */
	private float size;
	/*The max allowed velocity of the puck */
	private float maxVelocity;
	/*The minimum allowed velocity of the puck */
	private float minVelocity;
	

	/**
	 * Constructor
	 */
	private Puck()
	{
		speedX = 0;
		speedY = 0;
		String size = PreferenceManager.getDefaultSharedPreferences(GameActivity.getInstance()).getString("Puck", "Medium");
		setSize(size);
		this.mCamera = GameActivity.getInstance().mCamera;
		this.puckAtlas = new BitmapTextureAtlas(GameActivity.getInstance().getTextureManager(),256,256);
		this.puckTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(puckAtlas, GameActivity.getInstance(), "game/Puck.png", 60, 60);
		this.sprite = new Sprite(mCamera.getCenterX()- puckTexture.getHeight()/2, mCamera.getCenterY() - puckTexture.getWidth()/2, puckTexture, GameActivity.getInstance().getVertexBufferObjectManager());
		this.sprite.setScale(this.size);
		this.puckAtlas.load();
		
		moveable = true;
		
		setVelocity(2);
		setMaxVelocity(10);
		setMinVelocity(1);
	}

	/**
	 * Set the size of the puck to a float size which corresponds to the parameter string.
	 * @param size "Small", "Medium" or "Large" string
	 */
	private void setSize(String size) {

		if(size.equals("Small"))
		{
			this.size = 0.5f;
		}
		else if(size.equals("Medium"))
		{
			this.size = 1.0f;
		}
		else if(size.equals("Large"))
		{
			this.size = 1.5f;
		}
	}
		
	/**
	 * Update the puck position according to the speed
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
        	speedX = -speedX;
            newX = sprite.getX() + speedX;
        }
        if (newX <= rightWall){
            newX = sprite.getX() + speedX;
        }
        else{
        	speedX = -speedX;
            newX = sprite.getX() + speedX;
        }
        
        newY = sprite.getY() + speedY;
        
        sprite.setPosition(newX, newY);
        
	}
	
	/**
	 * Set the total velocity of the puck, within the bounds of the allowed velocities.
	 * @param velocity
	 */
	public void setVelocity(float v){
		if(v>maxVelocity){v=maxVelocity;}
		if(v<minVelocity){v=minVelocity;}
		this.velocity = v;
	}
	/**
	 * Get the total velocity of the puck
	 * @return
	 */
	public float getVelocity(){
		return velocity;
	}
	/**
	 * Get the maximum allowed velocity of the puck
	 * @return
	 */
	public float getMaxVelocity() {
		return maxVelocity;
	}
	/**
	 * Set the maximum allowed velocity of the puck
	 * @param maxVelocity
	 */
	public void setMaxVelocity(float maxVelocity) {
		this.maxVelocity = maxVelocity;
	}
	/**
	 * Get the minimum allowed velocyt of the puck
	 * @return
	 */
	public float getMinVelocity() {
		return minVelocity;
	}
	/**
	 * Set the minimum allowed velocity of the puck
	 * @param minVelocity
	 */
	public void setMinVelocity(float minVelocity) {
		this.minVelocity = minVelocity;
	}
	/**
	 * Set the direction to x and y coordinates. The speed will normalize itself according to the velocity
	 * @param dx
	 * @param dy
	 */
	public void setDirection(float dx, float dy){
		float sum = Math.abs(dx)+Math.abs(dy);
		speedX = (dx/sum) * velocity;
		speedY = (dy/sum) * velocity;
	}
	/**
	 * Returns the X coordinate of the origo of the mallet
	 * @return
	 */
	public float getOrigoX(){
		float origoX = sprite.getX() + (sprite.getWidth()/2);
		return origoX;
	}
	/**
	 * Returns the Y coordinate of the origo of the mallet
	 * @return
	 */
	public float getOrigoY(){
		float origoY = sprite.getY() + (sprite.getHeight()/2);
		return origoY;
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
	/**
	 * Returns the graphic sprite contained in this object
	 * @return
	 */
	public Sprite getSprite() {
		return this.sprite;
	}
}
