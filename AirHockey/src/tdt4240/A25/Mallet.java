package tdt4240.A25;

import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Mallet implements OnTouchListener {

	private Point position;
	
	public Mallet(Point position) {
		this.position = position;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public boolean checkCollision(Ball ball) {
		return false;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}

}
