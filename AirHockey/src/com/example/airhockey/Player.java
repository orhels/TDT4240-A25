package com.example.airhockey;

import org.andengine.input.touch.TouchEvent;

public class Player {
	
	private Mallet mallet;
	private int score = 0;
	private boolean won = false;
	private String name = "";
	
	
	public Player(Mallet mallet) {
		this.mallet = mallet;
	}
	
	public Mallet getMallet() {
		return mallet;
	}
	
	public void setMallet(Mallet mallet) {
		this.mallet = mallet;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int incrementScore() {
		return ++score;
	}
	
	public void reset() {
		mallet.reset();
	}
	
	public void onTouch(TouchEvent pSceneTouchEvent) {
		mallet.setPosition(pSceneTouchEvent);
	}
	
	public void setWon(boolean won) {
		this.won = won;
	}
	
	public boolean hasWon() {
		return won;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	

}
