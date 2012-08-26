package com.trnnn.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.trnnn.game.gl.AndroidGLSurfaceView;
import com.trnnn.game.gl.OpenGLRenderer;

public class OpenGLTestActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)
		AndroidGLSurfaceView view = new AndroidGLSurfaceView(this);
		view.setRenderer(new OpenGLRenderer());
		setContentView(view);
	}
}
