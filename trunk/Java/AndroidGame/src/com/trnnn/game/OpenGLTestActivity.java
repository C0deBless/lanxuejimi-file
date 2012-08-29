package com.trnnn.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Window;
import android.view.WindowManager;

import com.trnnn.game.gl.AndroidGLSurfaceView;
import com.trnnn.game.gl.OpenGLRenderer;

public class OpenGLTestActivity extends Activity {

	GestureDetector dector;

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
		setContentView(view);
	}
}
