package com.example.airhockey;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.graphics.PointF;
import android.preference.PreferenceManager;
import android.util.Log;

public enum Puck 
{
	PUCK;

	//Fields
	//	private int puckID = PUCKID_NOT_Set;
	private Camera mCamera;
	private BitmapTextureAtlas puckAtlas;
	private ITextureRegion puckTexture;
	private Sprite sprite;
	private GameActivity instance;

	/* The total velocity of the puck, a vector of speed and direction */
	private PointF velocity;
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
		instance = GameActivity.getInstance();
		this.mCamera = instance.mCamera;
		initPuck();
	}

	/**
	 * Initializes the puck's variables
	 */
	public void initPuck(){
		this.puckAtlas = new BitmapTextureAtlas(instance.getTextureManager(),256,256);
		this.puckTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(puckAtlas, instance, "game/puck.png", 60, 60);
		this.sprite = new Sprite(mCamera.getCenterX()- puckTexture.getHeight()/2, mCamera.getCenterY() - puckTexture.getWidth()/2, puckTexture, instance.getVertexBufferObjectManager());
		String size = PreferenceManager.getDefaultSharedPreferences(instance).getString("Puck", "Medium");
		setSize(size);
		this.sprite.setScale(this.size);
		this.puckAtlas.load();

		setVelocity(0, 0);
		setMaxVelocity(10);

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
	 * Update the puck position according to the speed.
	 */
	public void updatePuck(){
		setPosition(sprite.getX() + velocity.x, sprite.getY() + velocity.y);

	}

	/**
	 * Resets the puck back to original position
	 */
	public void resetPuck(){
		sprite.setPosition(mCamera.getCenterX()- puckTexture.getHeight()/2, mCamera.getCenterY() - puckTexture.getWidth()/2);
		setVelocity(0, 0);
	}
	/**
	 * Sets the x and y positions of the puck
	 * @param x
	 * @param y
	 */
	public void setPosition(float x, float y){
		float radius = sprite.getHeight();
		if (x + radius >= mCamera.getWidth() || x < 0 ) {
			x = sprite.getX();
			velocity.x *= -0.95f;
		}
		// Check if the ball is inside the goal
		Goal goal;
		if (y <= 0) { // Player 2 may have the ball inside player 1's goal
			goal = instance.getScene().getPlayerOneGoal();
			if (!goal.isInside(x)) {
				y -= velocity.y;
				velocity.y *= -0.95; 
			}
		} else if (y + radius >= mCamera.getHeight()) {  // Player 1 may have the ball inside player 2's goal
			goal = instance.getScene().getPlayerTwoGoal();
			if (!goal.isInside(x)) {
				y -= velocity.y;
				velocity.y *= -0.95; 
			}
		}

		sprite.setPosition(x, y);
	}

	public void addToPosition(float dx, float dy) {
		setPosition(sprite.getX() + dx, sprite.getY() + dy);
	}

	/**
	 * Set the total velocity of the puck, within the bounds of the allowed velocities.
	 * @param velocity
	 */
	public void setVelocity(float dx, float dy){
		Log.d("Puck", "DX: " + dx + ". DY: " + dy);
		if (dx > maxVelocity) {
			dx = maxVelocity;
		} else if (dx < -maxVelocity) {
			dx = -maxVelocity;
		}
		if (dy > maxVelocity) {
			dy = maxVelocity;
		} else if (dy < -maxVelocity) {
			dy = -maxVelocity;
		}

		if (velocity != null) {
			velocity.x = dx;
			velocity.y = dy;
		} else {
			velocity = new PointF(dx, dy);
		}
	}
	/**
	 * Get the total velocity of the puck
	 * @return
	 */
	public PointF getVelocity(){
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
		if (maxVelocity > 0) {
			this.maxVelocity = maxVelocity;
		}
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
		velocity.x = dx;
		velocity.y = dy;
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
		return this.sprite.getX();
	}
	/**
	 * Returns the Puck Y position
	 * @return
	 */
	public float getPuckPosY(){
		return this.sprite.getY();
	}
	/**
	 * Returns the graphic sprite contained in this object
	 * @return
	 */
	public Sprite getSprite() {
		return this.sprite;
	}
	public float getRadius() {
		return (sprite.getHeight() / 2) - 12;
	}
}
