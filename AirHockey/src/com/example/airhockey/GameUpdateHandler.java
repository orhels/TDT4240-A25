package com.example.airhockey;

import org.andengine.engine.handler.IUpdateHandler;

import android.graphics.PointF;
import android.util.Log;

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
		mallet1.updateSpeed();
		mallet2.updateSpeed();
//		PointF normal = new PointF();
		double distance = mallet1.getDistanceFromPoint(puck.getOrigoX(), puck.getOrigoY());
//		normal.x = (float) ((mallet1.getOrigoX() - puck.getOrigoX()) / distance);
//		normal.y = (float) ((mallet1.getOrigoY() - puck.getOrigoY()) / distance);
//		PointF tanget = new PointF();
//		tanget.x = -normal.y;
//		tanget.y = normal.x;
//		// Projecting the velocities onto the normal balls and the normal's tangent.
//		double scalarVelN1 = dot(normal, new PointF(mallet1.getSpeedX(), mallet1.getSpeedY()));
//		double scalarVelT1 = dot(tanget, new PointF(mallet1.getSpeedX(), mallet1.getSpeedY()));
//		double scalarVelN2 = dot(normal, puck.getVelocity());
//		double scalarVelT2 = dot(tanget, puck.getVelocity());
//		PointF newVel = new PointF();
//		newMalletVel.x = 
		
		if (distance <= mallet1.getRadius() + puck.getRadius()){
			debug("Player 1 - Collided with puck");
			//Set puck velocity to opposite direction of mallet
			if (mallet1.isIdle()) {
				puck.setVelocity(puck.getVelocity().x * -1.9f, puck.getVelocity().y * -1.9f);
			} else {
				puck.setVelocity(mallet1.getSpeedX(), mallet1.getSpeedY());
			}
		}
		distance = mallet2.getDistanceFromPoint(puck.getOrigoX(), puck.getOrigoY());
		if (distance <= mallet2.getRadius() + puck.getRadius()){
			debug("Player 2 - Collided with puck");
			//Set puck velocity to opposite direction of mallet
			if (mallet2.isIdle()) {
				puck.setVelocity(puck.getVelocity().x * -1.9f, puck.getVelocity().y * -1.9f);
			} else {
				puck.setVelocity(mallet2.getSpeedX(), mallet2.getSpeedY());
			}
		}
		GameActivity.getInstance().getCurrentScene().update();

	}
	
    // return the inner product of this Vector a and b
    public double dot(PointF one, PointF two) {
        return one.x + two.x + one.y + two.y;
    }

	@Override
	public void reset() {

	}

	private void debug(String msg) {
		Log.d("GameUpdateHandler", msg);
	}

}
