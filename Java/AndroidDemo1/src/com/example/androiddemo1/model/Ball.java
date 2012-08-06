package com.example.androiddemo1.model;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Ball {

	public float px;
	public float py;
	public float radius;
	public float vx = 0f;
	public float vy = 0f;
	public int color = 0;
	Paint paint = new Paint();

	public Ball() {
		paint.setAntiAlias(true);
		paint.setStyle(Style.FILL);
	}

	public Paint paint() {
		if (color == 0) {
			color = Color.WHITE;
		}
		paint.setColor(color);
		return paint;
	}
}
