package com.example.airhockey;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.sprite.Sprite;

public class GameUpdateHandler implements IUpdateHandler{

	private Mallet mallet1, mallet2;
	Puck puck;
	
	public GameUpdateHandler(Mallet mallet1, Mallet mallet2, Puck puck){
		this.mallet1 = mallet1;
		this.mallet2 = mallet2;
		this.puck = puck;
	}
	@Override
	public void onUpdate(float pSecondsElapsed) {
		if (mallet1.getSprite().collidesWith(puck.getSprite())){
			System.out.println("Player one collided with puck");
			//Set puck velocity to opposite direction of mallet
			float dx = puck.getOrigoX() - mallet1.getOrigoX();
			float dy = puck.getOrigoY() - mallet1.getOrigoY();
			puck.setSpeed(dx, dy);
		}
		if (mallet2.getSprite().collidesWith(puck.getSprite())){
			System.out.println("Player two collided with puck");
			//Set puck velocity to opposite direction of mallet 
		}
		GameActivity.getInstance().getCurrentScene().movePuck();
		
	}

	@Override
	public void reset() {
		
	}

}
