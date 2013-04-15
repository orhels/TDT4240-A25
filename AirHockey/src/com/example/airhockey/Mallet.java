package com.example.airhockey;


import junit.framework.TestFailure;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;


public class Mallet {
	
	public BitmapTextureAtlas malletAtlas;
	public ITextureRegion malletTexture;
	public Sprite sprite;
	
	public static Mallet instance;
	Camera mCamera;
	boolean moveable;
	
	
	/**
	 * Constructor
	 * @param size The size of the mallet
	 */
	public Mallet(int size) {
		//size må settes et sted :)
		malletAtlas = new BitmapTextureAtlas(GameActivity.getInstance().getTextureManager(), 256, 256);
		malletTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(malletAtlas, GameActivity.getInstance(), "game/Mallet.png", 10, 10);
		sprite = new Sprite(0, 0, null, GameActivity.getInstance().getVertexBufferObjectManager());
		moveable = true;
		
		mCamera = GameActivity.getInstance().mCamera;
		
		
		//Må gjøre noe slik at det blir laget en mallet på player 1 sin side, og en på player 2 sin side.
		setPosition((mCamera.getWidth() / 2), mCamera.getHeight() / 4);

		
	}
	
	/**
	 * Sets the position of the Mallet sprite
	 * @param x
	 * @param y
	 */
	public void setPosition(float x, float y){
		sprite.setPosition(x, y);
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

	
}
