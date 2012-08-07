package com.example.androiddemo1.model;

import android.util.Log;

public class Vector2D {
	public double x;
	public double y;

	public Vector2D() {

	}

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double length() {
		double d = Math.pow(x, 2) + Math.pow(y, 2);
		return Math.pow(d, 0.5);
	}

	public Vector2D add(Vector2D v) {
		Vector2D vec = new Vector2D();
		vec.x = this.x + v.x;
		vec.y = this.y + v.y;
		return vec;
	}

	public static double angle(Vector2D v1, Vector2D v2) {
		double d = v1.x * v2.x + v1.y * v2.y;
		float cos = (float) (d / (v1.length() * v2.length()));
		if (cos > 1) {
			Log.i("AndroidDemo1", "cos -> " + cos);
		}
		return Math.acos(cos);
	}
}
