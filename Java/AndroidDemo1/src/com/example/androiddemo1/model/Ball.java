package com.example.androiddemo1.model;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Ball {

	public float px;
	public float py;
	public float x;
	public float y;
	public float radius;
	public float vx = 0f;
	public float vy = 0f;
	public float ax = 0f;
	public float ay = 0f;
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

	public void accelerate(double dTime) {
		this.x += this.ax * dTime * dTime;
		this.y += this.ay * dTime * dTime;
		this.ax = 0;
		this.ay = 0;
	}

	public void inertia(double dTime) {
		float x = this.x * 2 - this.px;
		float y = this.y * 2 - this.py;
		this.px = this.x;
		this.py = this.y;
		this.x = x;
		this.y = y;
	}
}
