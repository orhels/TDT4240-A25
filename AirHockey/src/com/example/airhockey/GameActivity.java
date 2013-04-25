package com.example.airhockey;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.graphics.Typeface;

/**
 * @author G25
 * @version 1.0
 */
public class GameActivity extends SimpleBaseGameActivity {

	static GameActivity instance;
	public Camera mCamera;
	private GameScene scene;
	static final int CAMERA_WIDTH = 480;
	static final int CAMERA_HEIGHT = 800;
	public Font mFont;

	@Override
	public EngineOptions onCreateEngineOptions() {
		GameActivity.instance = this;
		this.mCamera = new Camera(0, 0, GameActivity.CAMERA_WIDTH,
				GameActivity.CAMERA_HEIGHT);
		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(
						GameActivity.CAMERA_WIDTH, GameActivity.CAMERA_HEIGHT),
				this.mCamera);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		return engineOptions;
	}

	@Override
	protected void onCreateResources() {
		this.mFont = FontFactory.create(this.getFontManager(),
				this.getTextureManager(), 256, 256,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 64);
		this.mFont.load();
	}

	public static GameActivity getInstance() {
		return GameActivity.instance;
	}

	@Override
	protected Scene onCreateScene() {
		this.scene = new GameScene();
		this.scene.setOnSceneTouchListener(this.scene);

		return this.scene;
	}

	public void setCurrentScene(final GameScene scene) {
		this.scene = scene;
	}

	public GameScene getCurrentScene() {
		return this.scene;
	}

	@Override
	public void onBackPressed() {
		this.scene.showQuitBox();

	}

	@Override
	public void onStop() {
		super.onStop();
		if (this.scene != null) {
			this.scene.destroySprites();
			this.scene = null;
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (this.scene != null) {
			this.scene.destroySprites();
			this.scene = null;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	public GameScene getScene() {
		return this.scene;
	}

}
