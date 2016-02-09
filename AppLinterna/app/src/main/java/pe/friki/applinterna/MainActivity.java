package pe.friki.applinterna;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import pe.friki.utils.InitViews;

public class MainActivity extends AppCompatActivity {

	public View root;
	private boolean isLighOn = false;
	private Camera camera;
	private CameraView mCameraView = null;
	private Button buttonFlashlight;
	private FrameLayout camera_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		root = findViewById(android.R.id.content).getRootView();
		InitViews.whichClass(this);

		//adBanner Top
		AdView mAdViewTop = (AdView) findViewById(R.id.adViewTop);
		AdRequest adRequestTop = new AdRequest.Builder().build();
		mAdViewTop.loadAd(adRequestTop);

	}


	@Override
	protected void onResume() {
		super.onResume();

		camera = Camera.open();
		Context context = this;
		PackageManager pm = context.getPackageManager();

		// if device support camera?
		if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			Log.e("err", "Device has no camera!");
			return;
		}

		final Camera.Parameters p = camera.getParameters();

		buttonFlashlight.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				mCameraView = new CameraView(MainActivity.this, camera);

				if (isLighOn) {

					if (camera != null) {
						Log.i("info", "torch is turn off!");
						p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
						camera.setParameters(p);
						camera.stopPreview();
						isLighOn = false;
					}

				} else {

					if (camera != null) {
						Log.i("info", "torch is turn on!");

						p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
						camera.setParameters(p);
						camera.startPreview();

						//if (mCameraView.getParent() != null)
						//	((ViewGroup) mCameraView.getParent()).removeView(mCameraView);

						camera_view.addView(mCameraView);

						isLighOn = true;

					}


				}

			}

		});

	}
}
