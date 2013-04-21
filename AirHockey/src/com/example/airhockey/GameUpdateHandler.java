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
		double distance = mallet1.getDistanceFromPoint(puck.getOrigoX(), puck.getOrigoY());
		if (distance <= mallet1.getRadius() + puck.getRadius()){
			debug("Player 1 - Collided with puck");
			handleCollision(mallet1, distance);
			puck.updatePuck();
			//Set puck velocity to opposite direction of mallet
		}
		distance = mallet2.getDistanceFromPoint(puck.getOrigoX(), puck.getOrigoY());
		if (distance <= mallet2.getRadius() + puck.getRadius()){
			debug("Player 2 - Collided with puck");
			handleCollision(mallet2, distance);
			puck.updatePuck();
			//Set puck velocity to opposite direction of mallet
		}
		GameActivity.getInstance().getCurrentScene().update();

	}
	
	private void handleCollision(Mallet player, double distance) {
		double angle = getAngle(new PointF(player.getOrigoX(), player.getOrigoY()), 
				new PointF(puck.getOrigoX(), puck.getOrigoY()));
		PointF normal = new PointF();
		normal.x =  player.getOrigoX() - puck.getOrigoX();
		normal.y =  player.getOrigoY() - puck.getOrigoY();
		double divider = Math.sqrt(Math.pow(normal.x, 2) + Math.pow(normal.y, 2));
		// Create a unit vector of the normal.
		normal.x /= divider;
		normal.y /= divider;
		// Find the tangent for the normal
		PointF tangent = new PointF();
		tangent.x = -normal.y;
		tangent.y = normal.x;
		// Projecting the velocities onto the normal of the balls and the balls' tangent.
		double vel1N = dot(normal, new PointF(player.getSpeedX(), player.getSpeedY()), angle);
		double vel2T = dot(tangent, puck.getVelocity(), angle);
		double newVel2N = vel1N;
		
		// Calculate the puck's speed along the normal.
		PointF newNormal2 = new PointF();
		newNormal2.x = (float) (normal.x * newVel2N);
		newNormal2.y = (float) (normal.y * newVel2N);
		
		// Calculate the puck's speed along the tangent.
		PointF newTanget2 = new PointF();
		newTanget2.x = (float) (tangent.x * vel2T);
		newTanget2.y = (float) (tangent.y * vel2T);
		
		// Project it back to the puck.
		PointF newPuckVel = new PointF();
		newPuckVel.x = newNormal2.x + newTanget2.x;
		newPuckVel.y = newNormal2.y + newTanget2.y;
		puck.setVelocity(newPuckVel.x, newPuckVel.y);
	}
	
    // return the inner product of this Vector a and b
    public double dot(PointF one, PointF two, double angle) {
        return (one.x + two.x + one.y + two.y) * Math.cos(angle);
    }
    
    public float getAngle(PointF one, PointF two) {
        float angle = (float) Math.toDegrees(Math.atan2(one.x - two.x, one.y - two.y));

        if(angle < 0){
            angle += 360;
        }

        return angle;
    }

	@Override
	public void reset() {

	}

	private void debug(String msg) {
		Log.d("GameUpdateHandler", msg);
	}

}
