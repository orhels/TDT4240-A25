package com.example.airhockey;

public class Goal {

	private float leftCorner, rightCorner;
	
	public Goal(float leftCorner, float rightCorner) {
		this.leftCorner = leftCorner;
		this.rightCorner = rightCorner;
	}
	
	public boolean isInside(float left, float right) {
		return left > leftCorner && right < rightCorner;
	}
	
	public String toString() {
		return "" + leftCorner + "-" + rightCorner;
	}
}
