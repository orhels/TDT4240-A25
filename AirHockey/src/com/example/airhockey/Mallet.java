package com.example.airhockey;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * @author G25
 * @version 1.0
 */
public class Mallet {

	private BitmapTextureAtlas malletAtlas;
	private ITextureRegion malletTexture;
	private Sprite sprite;
	private final GameActivity instance;
	private final Camera mCamera;
	private float size;
	public float spriteHeight, spriteWidth;
	/* The mallets previous X position */
	private float previousX;
	/* The mallets previous Y position */
	private float previousY;
	/* The mallets speed in X direction */
	private float speedX;
	/* The mallets seed in Y direction */
	private float speedY;
	private final int playerID;

	/**
	 * Constructor
	 * 
	 * @param size
	 *            The size of the mallet
	 */
	public Mallet(final String size, final int playerID) 
	{
		this.setSize(size);
		this.playerID = playerID;
		this.instance = GameActivity.getInstance();
		this.mCamera = this.instance.mCamera;
		this.loadTextures();
		this.initializePosition();
	}

	private void initializePosition() 
	{
		this.spriteWidth = this.sprite.getWidth() / 2;
		this.spriteHeight = this.sprite.getHeight() / 2;
		if (this.playerID == 1) 
		{
			this.setPosition((this.mCamera.getWidth() * 0.5f) - this.spriteWidth, (this.mCamera.getHeight() * 0.15f) - this.spriteHeight);
		} 
		else if (this.playerID == 2) 
		{
			this.setPosition((this.mCamera.getWidth() * 0.5f) - this.spriteWidth, (this.mCamera.getHeight() * 0.85f) - this.spriteHeight);
		}
	}

	private void loadTextures() {
		this.malletAtlas = new BitmapTextureAtlas(this.instance.getTextureManager(), 125, 125);
		this.malletTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.malletAtlas, this.instance, "game/mallet.png", 0, 0);
		this.sprite = new Sprite(0, 0, this.malletTexture, this.instance.getVertexBufferObjectManager());
		this.sprite.setScale(this.size);
		this.malletAtlas.load();
	}

	private void setSize(final String size) {

		if (size.equals(Constants.SMALL)) 
		{
			this.size = 0.5f;
		} 
		else if (size.equals(Constants.MEDIUM)) 
		{
			this.size = 1.0f;
		} 
		else if (size.equals(Constants.LARGE)) 
		{
			this.size = 1.5f;
		}
	}

	/**
	 * Sets the position of the Mallet sprite
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition(float x, float y) {
		final float radius = this.sprite.getHeight();
		if (((x + radius) >= this.mCamera.getWidth()) || (x < 0)) 
		{
			x = this.sprite.getX();
		}
		if (((y + radius) >= this.mCamera.getHeight()) || (y < 0)) 
		{
			y = this.sprite.getY();
		}
		this.sprite.setPosition(x, y);
	}

	/**
	 * Sets the position of the mallet according to the touch event.
	 * 
	 * @param event
	 */
	public void setPosition(final TouchEvent event) {

<<<<<<< HEAD
		float radius = getRadius(); 
		float x = event.getX() - (sprite.getX() + radius);
		float y = event.getY() - (sprite.getY() + radius);
		double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)); // Pythagoras' theorem
		if (distance <= radius) { 
=======
		final float radius = (this.sprite.getHeight() / 2) * this.size;
		final float x = event.getX() - (this.sprite.getX() + radius);
		final float y = event.getY() - (this.sprite.getY() + radius);
		final double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)); // Pythagoras' theorem
		if (distance <= radius) {
>>>>>>> 7a35e629824b8d252ec277222107a62ba7404772
			// Only respond to touches close to the mallet
			this.setPosition(this.sprite.getX() + x, this.sprite.getY() + y);
		}
	}

<<<<<<< HEAD
	public double getDistanceFromPoint(float posX, float posY) {
		//float radius = getRadius();
		//float x = posX - (sprite.getX() + radius);
		float x = posX - getOrigoX();
		float y = posY - getOrigoY();
		//float y = posY - (sprite.getY() + radius);
=======
	public double getDistanceFromPoint(final float posX, final float posY) {
		final float radius = (this.sprite.getHeight() / 2) * this.size;
		final float x = posX - (this.sprite.getX() + radius);
		final float y = posY - (this.sprite.getY() + radius);
>>>>>>> 7a35e629824b8d252ec277222107a62ba7404772
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)); // Pythagoras' theorem
	}

	/**
	 * Returns the sprite graphic object representing the mallet.
	 * 
	 * @return
	 */
	public Sprite getSprite() {
		return this.sprite;
	}

	/**
	 * Returns the X coordinate of the origo of the mallet
	 * 
	 * @return
	 */
<<<<<<< HEAD
	public float getOrigoX(){
		float origoX = sprite.getX() + ((sprite.getWidth() / 2) * size);
=======
	public float getOrigoX() {
		final float origoX = this.sprite.getX()
				+ ((this.sprite.getWidth() / 2) * this.size);
>>>>>>> 7a35e629824b8d252ec277222107a62ba7404772
		return origoX;
	}

	/**
	 * Returns the Y coordinate of the origo of the mallet
	 * 
	 * @return
	 */
<<<<<<< HEAD
	public float getOrigoY(){
		float origoY = sprite.getY() + ((sprite.getHeight() / 2) * size);
=======
	public float getOrigoY() {
		final float origoY = this.sprite.getY()
				+ ((this.sprite.getHeight() / 2) * this.size);
>>>>>>> 7a35e629824b8d252ec277222107a62ba7404772
		return origoY;
	}

	/**
	 * Method for updating the movement speed of the mallet. Calculates the
	 * speed as the difference between the previous positions and the current
	 * positions. It then sets the previous position as the current ones.
	 */
	public void updateSpeed() {
		final float currentX = this.sprite.getX();
		final float currentY = this.sprite.getY();
		this.speedX = this.previousX - currentX;
		this.speedY = this.previousY - currentY;
		this.previousX = currentX;
		this.previousY = currentY;
	}

	public float getSpeedX() {
		return this.speedX;
	}

	public float getSpeedY() {
		return this.speedY;
	}

	public float getRadius() {
<<<<<<< HEAD
		return (sprite.getHeight() / 2) * size;
=======
		return this.sprite.getHeight() / 2;
>>>>>>> 7a35e629824b8d252ec277222107a62ba7404772
	}

	public boolean isIdle() {
		return (this.speedX == 0) && (this.speedY == 0);
	}

	public void reset() {
		this.initializePosition();
	}
}