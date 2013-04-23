package com.example.airhockey;

/**
 * @author G25
 * @version 1.0
 */
public class Goal {

	private float leftCorner, rightCorner;
	
	public Goal(float leftCorner, float rightCorner) {
		this.leftCorner = leftCorner;
		this.rightCorner = rightCorner;
	}
	
	public boolean isInside(float x) {
		return x > leftCorner && x < rightCorner;
	}
	
	public String toString() {
		return "" + leftCorner + "-" + rightCorner;
	}
}
