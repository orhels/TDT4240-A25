package com.example.airhockey;


import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.util.Log;


public class Mallet {
	
	private BitmapTextureAtlas malletAtlas;
	private ITextureRegion malletTexture;
	private Sprite sprite;
	
	private Camera mCamera;
	private boolean moveable;
	
	private double size;
	
	
	/**
	 * Constructor
	 * @param size The size of the mallet
	 */
	public Mallet(String size, int player) 
	{
		setSize(size);
		malletAtlas = new BitmapTextureAtlas(GameActivity.getInstance().getTextureManager(), 125, 125);
		malletTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(malletAtlas, GameActivity.getInstance(), "game/mallet.png", 0, 0);
		sprite = new Sprite(0, 0, malletTexture, GameActivity.getInstance().getVertexBufferObjectManager());
		malletAtlas.load();
		moveable = true;
		mCamera = GameActivity.getInstance().mCamera;
		float spriteWidth = sprite.getWidth() / 2;
		float spriteHeight = sprite.getHeight() / 2;
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
			this.size = 0.5;
		}
		else if(size.equals("Medium"))
		{
			this.size = 1.0;
		}
		else if(size.equals("Large"))
		{
			this.size = 1.5;
		}
	}
	
	/**
	 * Sets the position of the Mallet sprite
	 * @param x
	 * @param y
	 */
	public void setPosition(float x, float y){
		sprite.setPosition(x, y);
	}
	
	public void setPosition(TouchEvent event) {
		
		float radius = sprite.getHeight() / 2; 
		float x = getDifference(event.getX(), sprite.getX() + radius);
		float y = getDifference(event.getY(), sprite.getY() + radius);
		double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)); // Pythagoras' theorem
		debug("Distance: " + distance);
		debug("Radius: " + radius);
		debug("X: " + x + ". Y: " + y);
		if (distance <= radius) {
			setPosition(event.getX() + radius, event.getY() + radius);
		}
	}
	
	private float getDifference(float x, float y) {
		if (x > y) {
			return x - y;
		}
		return y - x;
	}
	
	/**
	 * Moves the mallet to the position, considers the wall bounds of the field
	 * @param x
	 * @param y
	 */
	public void moveMallet(float x, float y) {
		if(!moveable)
			return;
		
		//også her må veggene begrenses til om det er player 1 eller 2, slik at de kun kan bevege seg på sin egen side.
		int leftWall = 0;
        int rightWall = (int) (mCamera.getWidth() - (int) sprite.getWidth());
        int lowerWall = (int) (mCamera.getHeight() - (int) sprite.getHeight());
        int upperWall = 0;
        
        float newX;
        float newY;

        // Set New X,Y Coordinates within Limits
        if (sprite.getX() >= leftWall)
            newX = x;
        else
            newX = leftWall;
        
        if (sprite.getY() >= upperWall)
            newY = y;
        else
            newY = upperWall;
        
        if (newX <= rightWall)
            newX = x;
        else
            newX = rightWall;
        
        if (newY <= lowerWall)
            newY = y;
        else
            newY = rightWall;

        setPosition(newX, newY);
			
	}

	public Sprite getSprite() {
		return this.sprite;
	}
	
	private void debug(String msg) {
		Log.d("Mallet", msg);
	}
}