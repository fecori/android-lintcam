package pe.friki.applinterna;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by Fcordova on 08/02/2016.
 */
public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

	private SurfaceHolder mHolder;
	private Camera mCamera;

	public CameraView(Context context, Camera camera) {
		super(context);

		mCamera = camera;
		mCamera.setDisplayOrientation(90);
		mHolder = getHolder();
		mHolder.addCallback(this);
		//mHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);

	}

	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder) {

		try {
			//when the surface is created, we can set the camera to draw images in this surfaceholder
			mCamera.setPreviewDisplay(surfaceHolder);
			mCamera.startPreview();
		} catch (IOException e) {
			Log.d("ERROR", "Camera error on surfaceCreated " + e.getMessage());
		}

	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
		//before changing the application orientation, you need to stop the preview, rotate and then start it again
		/*if (mHolder.getSurface() == null)//check if the surface is ready to receive camera data
			return;

		try {
			mCamera.stopPreview();
		} catch (Exception e) {
			//this will happen when you are trying the camera if it's not running
			Log.d("ERROR", "Camera error on surfaceChanged 1 " + e.getMessage());
		}

		//now, recreate the camera preview
		try {
			mCamera.setPreviewDisplay(mHolder);
			mCamera.startPreview();
		} catch (IOException e) {
			Log.d("ERROR", "Camera error on surfaceChanged 2 " + e.getMessage());
		}*/

		if (mHolder.getSurface() == null) {
			// preview surface does not exist
			return;
		}

		// stop preview before making changes
		try {
			mCamera.stopPreview();
		} catch (Exception e) {
			// ignore: tried to stop a non-existent preview
		}

		// set preview size and make any resize, rotate or
		// reformatting changes here

		// start preview with new settings
		try {
			//Turn flash on
			Camera.Parameters p = mCamera.getParameters();
			p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
			mCamera.setParameters(p);

			mCamera.setPreviewDisplay(mHolder);
			mCamera.startPreview();

		} catch (Exception e) {
			Log.d("ERROR", "Error starting camera preview: " + e.getMessage());
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
		mCamera.stopPreview();
		mCamera.release();
	}
}
