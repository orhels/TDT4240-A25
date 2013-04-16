package com.example.airhockey;

import org.andengine.engine.handler.IUpdateHandler;

public class GameUpdateHandler implements IUpdateHandler{

	@Override
	public void onUpdate(float pSecondsElapsed) {
		GameActivity.getInstance().getCurrentScene().movePuck();
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
