package com.example.airhockey;


import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;


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
		
		this.malletAtlas = new BitmapTextureAtlas(GameActivity.getInstance().getTextureManager(), 256, 256);
		this.malletTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(malletAtlas, GameActivity.getInstance(), "game/mallet.png", 10, 10);
		this.sprite = new Sprite(0, 0, malletTexture, GameActivity.getInstance().getVertexBufferObjectManager());
		
		this.moveable = true;
		this.mCamera = GameActivity.getInstance().mCamera;
		
		if(player == 1) setPosition(mCamera.getWidth() * 0.5f, mCamera.getHeight() * 0.25f);
		else if (player == 2) setPosition(mCamera.getWidth() * 0.5f, mCamera.getHeight() * 0.75f);
		
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

	public Sprite getSprite() {
		return this.sprite;
	}
}