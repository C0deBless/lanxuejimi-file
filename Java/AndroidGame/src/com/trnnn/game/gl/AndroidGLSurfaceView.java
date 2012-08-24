package com.trnnn.game.gl;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class AndroidGLSurfaceView extends GLSurfaceView {

	public AndroidGLSurfaceView(Context context) {
		super(context);
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {

	}
}
