package com.example.airhockey;

import org.andengine.engine.handler.IUpdateHandler;

import android.graphics.PointF;
import android.util.Log;

/**
 * @author G25
 * @version 1.0
 */
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
		double minDistance = mallet1.getRadius() + puck.getRadius();
		if (distance <= minDistance){
			handleCollision(mallet1, distance);
		}

		distance = mallet2.getDistanceFromPoint(puck.getOrigoX(), puck.getOrigoY());
		if (distance <= mallet2.getRadius() + puck.getRadius()){
			handleCollision(mallet2, distance);
		}
		
		GameActivity.getInstance().getCurrentScene().update();

	}
	
	private void handleCollision(Mallet player, double distance) {
//		double angle = getAngle(new PointF(player.getOrigoX(), player.getOrigoY()), 
//				new PointF(puck.getOrigoX(), puck.getOrigoY()));
		PointF normal = new PointF();
		normal.x =  player.getOrigoX() - puck.getOrigoX();
		normal.y =  player.getOrigoY() - puck.getOrigoY();
		double divider = Math.sqrt(Math.pow(normal.x, 2) + Math.pow(normal.y, 2));
		// Create a unit vector of the normal.
		normal.x /= divider;
		normal.y /= divider;
		// Find the tangent for the normal
		PointF tangent = new PointF(-normal.y, normal.x);
		// Projecting the velocities onto the normal of the balls and the balls' tangent.
		double vel1N = dot(normal, new PointF(player.getSpeedX(), player.getSpeedY()));
		double vel2T = dot(tangent, puck.getVelocity());
		double newVel2N = vel1N;
		
		// Calculate the puck's speed along the normal.
		PointF newNormal2 = new PointF((float) (normal.x * newVel2N), (float) (normal.y * newVel2N));
		
		// Calculate the puck's speed along the tangent.
		PointF newTanget2 = new PointF((float) (tangent.x * vel2T), (float) (tangent.y * vel2T));
		
		// Project it back to the puck.
		PointF newPuckVel = new PointF();
		newPuckVel.x = newNormal2.x + newTanget2.x;
		newPuckVel.y = newNormal2.y + newTanget2.y;
		puck.setVelocity(newPuckVel.x, newPuckVel.y);
		preventOverlapping(player, distance);
		puck.updatePuck();
	}
	
	private void preventOverlapping(Mallet mallet, double distance) {
		PointF delta = new PointF(mallet.getOrigoX() - puck.getOrigoX(), mallet.getOrigoY() - puck.getOrigoY());
		float diff = (float) ((mallet.getRadius() + puck.getRadius() - distance) / distance);
		PointF mtd = new PointF(delta.x * diff, delta.y * diff);
		
		// Move them away from each other
		mallet.setPosition(mallet.getSprite().getX() + mtd.x, mallet.getSprite().getY() + mtd.y);
		puck.setPosition(puck.getPuckPosX() - mtd.x, puck.getPuckPosY() - mtd.y);
		
	}
	
    // return the inner product of this Vector a and b
    public double dot(PointF one, PointF two) {
    	
        return (one.x + two.x + one.y + two.y) * Math.cos(getAngle(one, two));
    }
    
    public double getAngle(PointF one, PointF two) {
       return Math.atan2(one.x - two.x, one.y - two.y);
    }
    
    public PointF normalize(PointF one) {
    	PointF result = new PointF();
    	double divisor = Math.sqrt(Math.pow(one.x, 2) + Math.pow(one.y, 2));
    	result.x = (float) (one.x / divisor);
    	result.y = (float) (one.y / divisor);
    	return result;
    }
    
    public PointF multiply(PointF one, double scalar) {
    	PointF result = new PointF();
    	result.x = (float) (one.x * scalar);
    	result.y = (float) (one.y * scalar);
    	return result;
    }

	@Override
	public void reset() {

	}
}
