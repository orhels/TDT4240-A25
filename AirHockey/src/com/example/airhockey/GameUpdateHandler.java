package com.example.airhockey;

import org.andengine.engine.handler.IUpdateHandler;

import android.graphics.PointF;

/**
 * @author G25
 * @version 1.0
 */
public class GameUpdateHandler implements IUpdateHandler {

	/* The mallet objects */
	private final Mallet mallet1, mallet2;
	/* The puck object */
	private final Puck puck;

	/**
	 * Constructor taking in the mallets and the puck objects
	 */
	public GameUpdateHandler(final Mallet mallet1, final Mallet mallet2,
			final Puck puck) {
		this.mallet1 = mallet1;
		this.mallet2 = mallet2;
		this.puck = puck;
	}

	/**
	 * Checks if the mallets collide with the puck. If so, reverses the
	 * trajectory of the puck. Then updates the puck position. Sets the velocity
	 * of the puck to correspond to the speed of the mallet which hit it.
	 */
	@Override
	public void onUpdate(final float pSecondsElapsed) {
		this.mallet1.updateSpeed();
		this.mallet2.updateSpeed();
		double distance = this.mallet1.getDistanceFromPoint(
				this.puck.getOrigoX(), this.puck.getOrigoY());
		double minDistance = this.mallet1.getRadius()
				+ this.puck.getRadius();
		if (distance <= minDistance) {
			this.handleCollision(this.mallet1, distance);
		}
		distance = mallet2.getDistanceFromPoint(puck.getOrigoX(), puck.getOrigoY());
		minDistance = mallet2.getRadius() + puck.getRadius();
		if (distance <= minDistance){
			handleCollision(mallet2, distance);
		}

		GameActivity.getInstance().getCurrentScene().update();

	}

	private void handleCollision(final Mallet player, final double distance) {
		// double angle = getAngle(new PointF(player.getOrigoX(),
		// player.getOrigoY()),
		// new PointF(puck.getOrigoX(), puck.getOrigoY()));
		final PointF normal = new PointF();
		normal.x = player.getOrigoX() - this.puck.getOrigoX();
		normal.y = player.getOrigoY() - this.puck.getOrigoY();
		final double divider = Math.sqrt(Math.pow(normal.x, 2)
				+ Math.pow(normal.y, 2));
		// Create a unit vector of the normal.
		normal.x /= divider;
		normal.y /= divider;
		// Find the tangent for the normal
		final PointF tangent = new PointF(-normal.y, normal.x);
		// Projecting the velocities onto the normal of the balls and the balls'
		// tangent.
		final double vel1N = this.dot(normal, new PointF(player.getSpeedX(),
				player.getSpeedY()));
		final double vel2T = this.dot(tangent, this.puck.getVelocity());
		final double newVel2N = vel1N;

		// Calculate the puck's speed along the normal.
		final PointF newNormal2 = new PointF((float) (normal.x * newVel2N),
				(float) (normal.y * newVel2N));

		// Calculate the puck's speed along the tangent.
		final PointF newTanget2 = new PointF((float) (tangent.x * vel2T),
				(float) (tangent.y * vel2T));

		// Project it back to the puck.
		final PointF newPuckVel = new PointF();
		newPuckVel.x = newNormal2.x + newTanget2.x;
		newPuckVel.y = newNormal2.y + newTanget2.y;
		this.puck.setVelocity(newPuckVel.x, newPuckVel.y);
		this.preventOverlapping(player, distance);
		this.puck.updatePuck();
	}

	private void preventOverlapping(final Mallet mallet, final double distance) {
		final PointF delta = new PointF(mallet.getOrigoX()
				- this.puck.getOrigoX(), mallet.getOrigoY()
				- this.puck.getOrigoY());
		final float diff = (float) (((mallet.getRadius() + this.puck
				.getRadius()) - distance) / distance);
		final PointF mtd = new PointF(delta.x * diff, delta.y * diff);

		// Move them away from each other
		mallet.setPosition(mallet.getSprite().getX() + mtd.x, mallet
				.getSprite().getY() + mtd.y);
		this.puck.setPosition(this.puck.getPuckPosX() - mtd.x,
				this.puck.getPuckPosY() - mtd.y);

	}

	// return the inner product of this Vector a and b
	public double dot(final PointF one, final PointF two) {

		return (one.x + two.x + one.y + two.y)
				* Math.cos(this.getAngle(one, two));
	}

	public double getAngle(final PointF one, final PointF two) {
		return Math.atan2(one.x - two.x, one.y - two.y);
	}

	public PointF normalize(final PointF one) {
		final PointF result = new PointF();
		final double divisor = Math.sqrt(Math.pow(one.x, 2)
				+ Math.pow(one.y, 2));
		result.x = (float) (one.x / divisor);
		result.y = (float) (one.y / divisor);
		return result;
	}

	public PointF multiply(final PointF one, final double scalar) {
		final PointF result = new PointF();
		result.x = (float) (one.x * scalar);
		result.y = (float) (one.y * scalar);
		return result;
	}

	@Override
	public void reset() {

	}
}
