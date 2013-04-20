package com.example.airhockey;


import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

public class Mallet {
	
	private BitmapTextureAtlas malletAtlas;
	private ITextureRegion malletTexture;
	private Sprite sprite;
	private GameActivity instance;
	private Camera mCamera;
	private boolean moveable;
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
	
	/**
	 * Constructor
	 * @param size The size of the mallet
	 */
	public Mallet(String size, int player) 
	{
		setSize(size);
		instance = GameActivity.getInstance();
		malletAtlas = new BitmapTextureAtlas(instance.getTextureManager(), 125, 125);
		malletTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(malletAtlas, instance, "game/mallet.png", 0, 0);
		sprite = new Sprite(0, 0, malletTexture, instance.getVertexBufferObjectManager());
		sprite.setScale(this.size);
		malletAtlas.load();
		moveable = true;
		mCamera = instance.mCamera;
		spriteWidth = sprite.getWidth() / 2;
		spriteHeight = sprite.getHeight() / 2;
		if(player == 1) {
			setPosition(mCamera.getWidth() * 0.5f - spriteWidth, mCamera.getHeight() * 0.15f - spriteHeight);
		}
		else if (player == 2) {
			setPosition(mCamera.getWidth() * 0.5f - spriteWidth, mCamera.getHeight() * 0.85f - spriteHeight);
		}
		
	}
	
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
	 * Sets the position of the Mallet sprite
	 * @param x
	 * @param y
	 */
	public void setPosition(float x, float y){
		float radius = sprite.getHeight();
		if (x + radius >= mCamera.getWidth() || x < 0 ) {
			x = sprite.getX();
		}
		if (y + radius >= mCamera.getHeight() || y < 0 ) {
			y = sprite.getY();
		}
		sprite.setPosition(x, y);
	}
	
	/**
	 * Sets the position of the mallet according to the touch event.
	 * @param event
	 */
	public void setPosition(TouchEvent event) {
		
		float radius = (float) ((sprite.getHeight() / 2) * size); 
		float x = event.getX() - (sprite.getX() + radius);
		float y = event.getY() - (sprite.getY() + radius);
		double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)); // Pythagoras' theorem
		if (distance <= radius) {
			setPosition(sprite.getX() + x, sprite.getY() + y);
		}
	}

	/**
	 * Returns the sprite graphic object representing the mallet.
	 * @return
	 */
	public Sprite getSprite() {
		return this.sprite;
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
	 * Method for updating the movement speed of the mallet.
	 * Calculates the speed as the difference between the previous positions and the current positions.
	 * It then sets the previous position as the current ones.
	 */
	public void updateSpeed(){
		float currentX = sprite.getX();
		float currentY = sprite.getY();
		speedX = previousX-currentX;
		speedY = previousY-currentY;
		previousX = currentX;
		previousY = currentY;
	}
	public float getSpeedX(){
		return speedX;
	}
	public float getSpeedY(){
		return speedY;
	}
//	private void debug(String msg) {
//		Log.d("Mallet", msg);
//	}
}