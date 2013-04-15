package com.example.airhockey;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Puck {

	//Constants
//	protected final int PUCKID_NOT_Set = -1;
	private final int PUCK_WIDTH = 20;
	private final int PUCK_HEIGHT = 20;
	protected static final float STANDARD_VELOCITY = 50.0f;
			
	//Fields
//	private int puckID = PUCKID_NOT_Set;
	private Camera mCamera;
	private BitmapTextureAtlas mBitmapTextureAtlas;
	private TiledTextureRegion mFaceTextureRegion;
	private final int puckID;
	private float posX;
	private float posY;
	
	
	
	public Puck(){
		this.puckID = (int) (100*Math.random());		
//		this.puck = new Rectangle(0, 0, PUCK_WIDTH, PUCK_HEIGHT, this.getVertexBufferObjectManager);
			
	}
	
	
	public Engine onLoadEngine(){
		this.mCamera = new Camera(0, 0, Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, 
				new RatioResolutionPolicy(Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT), 
				this.mCamera) );		
	}
	
	public void onLoadResources(){
//		 this.mBitmapTextureAtlas = new BitmapTextureAtlas(166, 166, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		 this.mBitmapTextureAtlas = new BitmapTextureAtlas(null, 166, 166, null, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
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
