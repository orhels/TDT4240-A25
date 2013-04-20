package com.example.airhockey;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.sprite.Sprite;

import android.hardware.Camera.PreviewCallback;

public class GameUpdateHandler implements IUpdateHandler{

	/* The mallet objects */
	private Mallet mallet1, mallet2;
	/* The puck object */
	private Puck puck;
	
	/**
	 * Constructor taking in the mallets and the puck objects 
	 */
	public GameUpdateHandler(Mallet mallet1, Mallet mallet2, Puck puck){
		this.mallet1 = mallet1;
		this.mallet2 = mallet2;
		this.puck = puck;
	}
	/**
	 * Checks if the mallets collide with the puck. If so, reverses the trajectory of the puck.
	 * Then updates the puck position.
	 * Sets the velocity of the puck to correspond to the speed of the mallet which hit it.
	 */
	@Override
	public void onUpdate(float pSecondsElapsed) {
		if (mallet1.getSprite().collidesWith(puck.getSprite())){
			System.out.println("Player one collided with puck");
			//Set puck velocity to opposite direction of mallet
			float dx = puck.getOrigoX() - mallet1.getOrigoX();
			float dy = puck.getOrigoY() - mallet1.getOrigoY();
			puck.setDirection(dx, dy);
			puck.setVelocity( Math.abs(mallet1.getSpeedX())+Math.abs(mallet1.getSpeedY()) );
		}
		if (mallet2.getSprite().collidesWith(puck.getSprite())){
			System.out.println("Player two collided with puck");
			//Set puck velocity to opposite direction of mallet
			float dx = puck.getOrigoX() - mallet2.getOrigoX();
			float dy = puck.getOrigoY() - mallet2.getOrigoY();
			puck.setDirection(dx, dy);
			puck.setVelocity( Math.abs(mallet2.getSpeedX())+Math.abs(mallet2.getSpeedY()) );
		}
		GameActivity.getInstance().getCurrentScene().update();
		
	}
	
	@Override
	public void reset() {
		
	}

}
