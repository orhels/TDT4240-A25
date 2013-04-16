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
	
	private float size;
	
	/**
	 * Constructor
	 */
	private Puck()
	{
		String size = PreferenceManager.getDefaultSharedPreferences(GameActivity.getInstance()).getString("Puck", "Medium");
		setSize(size);
		this.mCamera = GameActivity.getInstance().mCamera;
		this.puckAtlas = new BitmapTextureAtlas(GameActivity.getInstance().getTextureManager(),256,256);
		this.puckTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(puckAtlas, GameActivity.getInstance(), "game/puck.png", 60, 60);
		this.sprite = new Sprite(mCamera.getCenterX()- puckTexture.getHeight()/2, mCamera.getCenterY() - puckTexture.getWidth()/2, puckTexture, GameActivity.getInstance().getVertexBufferObjectManager());
		this.sprite.setScale(this.size);
		this.puckAtlas.load();
			
		moveable = true;
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


	public IEntity getSprite() {
		return this.sprite;
	}
}
