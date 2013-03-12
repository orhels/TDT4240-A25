package tdt4240.A25;

import android.graphics.Point;

public class Player {
	
	private int score, id;
	private Point position;
	
	public Player(int id, Point position) {
		this.id = id;
		this.position = position;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}
}