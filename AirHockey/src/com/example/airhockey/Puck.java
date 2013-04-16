package com.example.airhockey;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

public enum Puck 
{
	PUCK;
	
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
	private Puck()
	{
		puckID = (int) (100*Math.random());
		puckAtlas = new BitmapTextureAtlas(GameActivity.getInstance().getTextureManager(),256,256);
		puckTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(puckAtlas, GameActivity.getInstance(), "game/puck.png", 60, 60);
		sprite = new Sprite(60, 60, puckTexture, GameActivity.getInstance().getVertexBufferObjectManager());
		
			
		moveable = true;
	}
		
	
	/**
	 * Update the puck position
	 * @param puckID
	 * @param posX
	 * @param posY
	 */
	public void updatePuck(){
//		if(!moveable){
//			return;
//		}
//		
//		int leftWall = 0;
//        int rightWall = (int) (mCamera.getWidth() - (int) sprite.getWidth());
//        int lowerWall = (int) (mCamera.getHeight() - (int) sprite.getHeight());
//        int upperWall = 0;
//        
//        float newX;
//        float newY;
//
//        // Set New X,Y Coordinates within Limits
//        if (sprite.getX() >= leftWall){
//            newX = sprite.getX() + speedX;
//        }
//        else{
//            newX = leftWall;
//        }
//        if (sprite.getY() >= upperWall){
//            newY = sprite.getY() + speedY;
//        }
//        else{
//            newY = upperWall;
//        }
//        if (newX <= rightWall){
//            newX = sprite.getX() + speedX;
//        }
//        else{
//            newX = rightWall;
//        }
//        if (newY <= lowerWall){
//            newY = sprite.getY() + speedY;
//        }
//        else{
//            newY = rightWall;
//        }
//        sprite.setPosition(newX, newY);
        
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
