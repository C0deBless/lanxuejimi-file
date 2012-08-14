package com.example.androiddemo1;

import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.WindowManager;

public class MainActivity extends Activity {

	TestView3 view;
	SensorManager sensorMgr;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);

		view = new TestView3(this, sensorMgr);
		// setContentView(R.layout.activity_main);
		setContentView(view);
		this.getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate()
		return true;
	}
}
