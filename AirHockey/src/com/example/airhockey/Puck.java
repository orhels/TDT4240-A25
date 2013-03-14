package com.example.airhockey;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Puck {

	//Constants
//	protected final int PUCKID_NOT_Set = -1;
	protected final int PUCK_WIDTH = 20;
	protected final int PUCK_HEIGHT = 20;
	
	//Fields
	private Rectangle puck;
//	private int puckID = PUCKID_NOT_Set;
	private final int puckID;
	private float posX;
	private float posY;
	
	
	
	public Puck(){
		
		this.puckID = (int) (100*Math.random());
//		this.puck = new Rectangle(0, 0, PUCK_WIDTH, PUCK_HEIGHT, this.getVertexBufferObjectManager);
		
	}
	
	public void updatePuck(int puckID, float posX, float posY){
		if(this.puckID == puckID){
			this.posX = posX;
			this.posY = posY;			
		}
	}
	
	public float getPuckPosX(){		
		return this.posX;
	}
	public float getPuckPosY(){
		return this.posY;
	}
}
