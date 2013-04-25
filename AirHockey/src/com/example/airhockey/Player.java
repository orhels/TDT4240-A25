package com.example.airhockey;

import org.andengine.input.touch.TouchEvent;

/**
 * @author G25
 * @version 1.0
 */
public class Player {

	private Mallet mallet;
	private int score = 0;
	private boolean won = false;
	private String name = "";

	public Player(final Mallet mallet) {
		this.mallet = mallet;
	}

	public Mallet getMallet() {
		return this.mallet;
	}

	public void setMallet(final Mallet mallet) {
		this.mallet = mallet;
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(final int score) {
		this.score = score;
	}

	public int incrementScore() {
		return ++this.score;
	}

	public void reset() {
		this.mallet.reset();
	}

	public void onTouch(final TouchEvent pSceneTouchEvent) {
		this.mallet.setPosition(pSceneTouchEvent);
	}

	public void setWon(final boolean won) {
		this.won = won;
	}

	public boolean hasWon() {
		return this.won;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
