package com.trnnn.game;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.GestureDetector;
import android.view.Window;
import android.view.WindowManager;

import com.trnnn.game.gl.AndroidGLSurfaceView;
import com.trnnn.game.gl.OpenGLRenderer;

public class OpenGLTestActivity extends Activity {

	GestureDetector dector;
	WakeLock wakeLock;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)
		AndroidGLSurfaceView view = new AndroidGLSurfaceView(this);
		OpenGLRenderer render = new OpenGLRenderer(this);

		dector = new GestureDetector(this, render);
		view.setRenderer(render);
		render.setGestrueDetector(dector);
		view.setOnTouchListener(render);
		view.setClickable(true);
		view.setLongClickable(true);
		setContentView(view);

		PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,
				"GLGame");
		wakeLock.acquire();
	}

	@Override
	public void onResume() {
		super.onResume();
		wakeLock.acquire();
	}

	@Override
	public void onPause() {
		super.onPause();
		wakeLock.release();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		wakeLock.release();
	}
}
