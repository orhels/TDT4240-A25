package com.example.airhockey;

/**
 * @author G25
 * @version 1.0
 */
public class Goal {

	private final float leftCorner, rightCorner;

	public Goal(final float leftCorner, final float rightCorner) {
		this.leftCorner = leftCorner;
		this.rightCorner = rightCorner;
	}

	public boolean isInside(final float left, final float right) {
		return (left > this.leftCorner) && (right < this.rightCorner);
	}

	@Override
	public String toString() {
		return "" + this.leftCorner + "-" + this.rightCorner;
	}
}
