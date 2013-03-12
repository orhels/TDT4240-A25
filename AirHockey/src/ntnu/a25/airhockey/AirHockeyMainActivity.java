package ntnu.a25.airhockey;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;


public class AirHockeyMainActivity extends SimpleBaseGameActivity {

	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;
	
	private Camera mCamera;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		this.mCamera = new Camera(0, 0 , CAMERA_WIDTH, CAMERA_HEIGHT );
		this.mCamera.setCenter(0, 0);
		return null;
	}

	@Override
	protected void onCreateResources() {
		// TODO Auto-generated method stub

	}

	@Override
	protected Scene onCreateScene() {
		// TODO Auto-generated method stub
		return null;
	}

}
