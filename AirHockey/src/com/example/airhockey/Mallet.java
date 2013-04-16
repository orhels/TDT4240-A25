package com.example.airhockey;


import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;

import android.graphics.Point;


public class Mallet {
	public Sprite sprite;
	private Point position;
	public static Mallet instance;
	Camera mCamera;
	boolean moveable;
	
	
	public static Mallet getSharedInstance(int size) {
		if( instance == null) {
			instance = new Mallet(size);
		}
		return instance;
	}
	
	public Mallet(int size) {
		//size m� settes et sted :)
		//null skal v�re en TiledTextureRegion. 
		//sprite = new Sprite(0, 0, null, MainActivity.getInstance().getVertexBufferObjectManager());
		moveable = true;
		position = new Point(0, 0);
		//mCamera = MainActivity.getInstance().mCamera;
		
		
		//M� gj�re noe slik at det blir laget en mallet p� player 1 sin side, og en p� player 2 sin side.
		//sprite.setPosition((mCamera.getWidth() / 2), mCamera.getHeight() / 4);

		
	}
	
	public void moveMallet(float speedX, float speedY) {
		if(!moveable)
			return;
		
		//ogs� her m� veggene begrenses til om det er player 1 eller 2, slik at de kun kan bevege seg p� sin egen side.
		if(speedX != 0 || speedY != 0) {
			int leftWall = 0;
	        int rightWall = (int) (mCamera.getWidth() - (int) sprite.getWidth());
	        int lowerWall = (int) (mCamera.getHeight() - (int) sprite.getHeight());
	        int upperWall = 0;
	        
	        float newX;
	        float newY;

	        // Calculate New X,Y Coordinates within Limits
	        if (sprite.getX() >= leftWall)
	            newX = sprite.getX() + speedX;
	        else
	            newX = leftWall;
	        
	        if (sprite.getY() >= upperWall)
	            newY = sprite.getY() + speedY;
	        else
	            newY = upperWall;
	        
	        if (newX <= rightWall)
	            newX = sprite.getX() + speedX;
	        else
	            newX = rightWall;
	        
	        if (newY <= lowerWall)
	            newY = sprite.getY() + speedY;
	        else
	            newY = rightWall;

	        sprite.setPosition(newX, newY);
			
		}
	}
	
	public int getX() {
		return position.x;
	}
	
	public int getY() {
		return position.y;
	}
	
	public void setX(int x) {
		position.x = x;
	}
	
	public void setY(int y) {
		position.y = y;
	}

	
}
