package com.example.airhockey;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.graphics.PointF;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * @author G25
 * @version 1.0
 */
public enum Puck {
	PUCK;

	// Fields
	// private int puckID = PUCKID_NOT_Set;
	private Camera mCamera;
	private BitmapTextureAtlas puckAtlas;
	private ITextureRegion puckTexture;
	private Sprite sprite;
	private GameActivity instance;

	/* The total velocity of the puck, a vector of speed and direction */
	private PointF velocity;
	/* The size of the puck */
	private float size;
	/* The max allowed velocity of the puck */
	private float maxVelocity;
	/* The factor the speed should be scaled with */
	private float speedMultiplier = 1;

	/**
	 * Constructor
	 */
	private Puck() {
		this.instance = GameActivity.getInstance();
		this.mCamera = this.instance.mCamera;
		if (this.instance != null) {
			this.initPuck();
		}
	}

	/**
	 * Initializes the puck's variables
	 */

	public void initPuck() {
		this.instance = GameActivity.getInstance();
		this.puckAtlas = new BitmapTextureAtlas(
				this.instance.getTextureManager(), 256, 256);
		this.puckTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.puckAtlas, this.instance,
						"game/puck.png", 60, 60);
		this.sprite = new Sprite(this.mCamera.getCenterX()
				- (this.puckTexture.getHeight() / 2), this.mCamera.getCenterY()
				- (this.puckTexture.getWidth() / 2), this.puckTexture,
				this.instance.getVertexBufferObjectManager());
		final String size = PreferenceManager.getDefaultSharedPreferences(
				this.instance).getString("Puck", "Medium");
		this.setSize(size);
		this.sprite.setScale(this.size);
		this.puckAtlas.load();

		this.setVelocity(0, 0);
	}

	/**
	 * Set the size of the puck to a float size which corresponds to the
	 * parameter string.
	 * 
	 * @param size
	 *            "Small", "Medium" or "Large" string
	 */
	private void setSize(final String size) {

		if (size.equals("Small")) {
			this.size = 0.5f;
		} else if (size.equals("Medium")) {
			this.size = 1.0f;
		} else if (size.equals("Large")) {
			this.size = 1.5f;
		}
	}

	/**
	 * Update the puck position according to the speed.
	 */
	public void updatePuck() {
		this.setPosition(this.sprite.getX() + this.velocity.x,
				this.sprite.getY() + this.velocity.y);

	}

	/**
	 * Resets the puck back to original position
	 */
	public void resetPuck() {
		this.sprite.setPosition(
				this.mCamera.getCenterX() - (this.puckTexture.getHeight() / 2),
				this.mCamera.getCenterY() - (this.puckTexture.getWidth() / 2));
		this.setVelocity(0, 0);
	}

	/**
	 * Sets the x and y positions of the puck
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition(float x, float y) {
		final float radius = this.sprite.getHeight() * this.size;
		if (((x + radius) >= this.mCamera.getWidth()) || (x < 0)) {
			x = this.sprite.getX();
			this.velocity.x *= -0.95f;
		}
		// Check if the ball is inside the goal
		Goal goal;
		if (y <= 0) { // Player 2 may have the ball inside player 1's goal
			goal = this.instance.getScene().getPlayerOneGoal();
			if (!goal.isInside(x, x + radius)) {
				y = 0;
				this.velocity.y *= -0.95;
			}
		} else if ((y + radius) >= this.mCamera.getHeight()) { // Player 1 may
																// have the ball
																// inside player
																// 2's goal
			goal = this.instance.getScene().getPlayerTwoGoal();
			if (!goal.isInside(x, x + radius)) {
				y = this.mCamera.getHeight() - radius;
				this.velocity.y *= -0.95;
			}
		}

		this.sprite.setPosition(x, y);
	}

	public void addToPosition(final float dx, final float dy) {
		this.setPosition(this.sprite.getX() + dx, this.sprite.getY() + dy);
	}

	/**
	 * Set the total velocity of the puck, within the bounds of the allowed
	 * velocities.
	 * 
	 * @param velocity
	 */
	public void setVelocity(float dx, float dy) {
		dx *= this.speedMultiplier;
		dy *= this.speedMultiplier;
		if (dx > this.maxVelocity) {
			dx = this.maxVelocity;
		} else if (dx < -this.maxVelocity) {
			dx = -this.maxVelocity;
		}
		if (dy > this.maxVelocity) {
			dy = this.maxVelocity;
		} else if (dy < -this.maxVelocity) {
			dy = -this.maxVelocity;
		}

		if (this.velocity != null) {
			this.velocity.x = dx;
			this.velocity.y = dy;
		} else {
			this.velocity = new PointF(dx, dy);
		}
	}

	/**
	 * Get the total velocity of the puck
	 * 
	 * @return
	 */
	public PointF getVelocity() {
		return this.velocity;
	}

	/**
	 * Get the maximum allowed velocity of the puck
	 * 
	 * @return
	 */
	public float getMaxVelocity() {
		return this.maxVelocity;
	}

	/**
	 * Set the maximum allowed velocity of the puck
	 * 
	 * @param maxVelocity
	 */
	public void setMaxVelocity(final float maxVelocity) {
		if (maxVelocity > 0) {
			this.maxVelocity = maxVelocity;
		}
	}

	/**
	 * Set the direction to x and y coordinates. The speed will normalize itself
	 * according to the velocity
	 * 
	 * @param dx
	 * @param dy
	 */
	public void setDirection(final float dx, final float dy) {
		this.velocity.x = dx;
		this.velocity.y = dy;
	}

	/**
	 * Returns the X coordinate of the origo of the mallet
	 * 
	 * @return
	 */

	public float getOrigoX() {
		final float origoX = this.sprite.getX()
				+ ((this.sprite.getWidth() / 2) * this.size);
		return origoX;
	}

	/**
	 * Returns the Y coordinate of the origo of the mallet
	 * 
	 * @return
	 */
	public float getOrigoY() {
		final float origoY = this.sprite.getY()
				+ ((this.sprite.getHeight() / 2) * this.size);
		return origoY;
	}

	/**
	 * Returns the Puck X position
	 * 
	 * @return
	 */
	public float getPuckPosX() {
		return this.sprite.getX();
	}

	/**
	 * Returns the Puck Y position
	 * 
	 * @return
	 */
	public float getPuckPosY() {
		return this.sprite.getY();
	}

	/**
	 * Returns the graphic sprite contained in this object
	 * 
	 * @return
	 */
	public Sprite getSprite() {
		return this.sprite;
	}

	public float getRadius() {
		return (sprite.getHeight() / 2) * size;
	}

	/**
	 * Sets the speed multiplier for the puck.
	 * 
	 * @param speedMultiplier
	 *            Number between 1 and 10
	 */
	public void setSpeedMultiplier(float speedMultiplier) {
		Log.d("Puck", "Speed multiplier: " + speedMultiplier);
		Log.d("Puck", "Max speed: " + this.maxVelocity);
		if ((speedMultiplier > 0) && (speedMultiplier <= 10)) {
			this.maxVelocity = speedMultiplier * 2;
			speedMultiplier /= 10;
			this.speedMultiplier = 0.5f + speedMultiplier;
			Log.d("Puck", "New speed multiplier: " + speedMultiplier);
			Log.d("Puck", "New max speed: " + this.maxVelocity);
		}
	}
}
