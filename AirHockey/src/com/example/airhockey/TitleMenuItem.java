package com.example.airhockey;

import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

public class TitleMenuItem extends SpriteMenuItem {

	public TitleMenuItem(int pID, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObject) {
		super(pID, pTextureRegion, pVertexBufferObject);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onSelected() {
		System.out.println("SELECTED!");
		this.setColor(Color.BLACK);
	}
	
	@Override
	public void onUnselected() {
		this.setColor(Color.WHITE);
	}
	
	
	
}
