package tdt4240.A25;

import android.graphics.Point;

public class Ball {

	private Point position;
	
	public Ball(Point position) {
		this.position = position;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}
}
