package com.example.androiddemo1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.androiddemo1.model.Ball;
import com.example.androiddemo1.model.Vector2D;

public class TestView2 extends SurfaceView implements SurfaceHolder.Callback {

	RectF rect = new RectF();
	SensorManager sensorMgr;
	Sensor sensor;
	Thread thread;
	float sensorX;
	float sensorY;
	float sensorZ;
	// float ax = 0f;
	// float ay = 0f;
	SurfaceHolder holder;
	int fps = 50;
	boolean isRunning = false;
	final List<Ball> balls = new ArrayList<Ball>();
	Timer timer = new Timer();
	float factor = 20;
	float lostRate = 0.5f;
	int ballCount = 3;

	SensorEventListener lsn = new SensorEventListener() {
		public void onSensorChanged(SensorEvent e) {
			sensorX = e.values[SensorManager.DATA_X] * factor;
			sensorY = e.values[SensorManager.DATA_Y] * factor;
			sensorZ = e.values[SensorManager.DATA_Z] * factor;
			// updateAccelerate();
		}

		public void onAccuracyChanged(Sensor s, int accuracy) {
		}
	};

	public TestView2(Context context, SensorManager sensorMgr) {
		super(context);
		// this.setBackgroundColor(Color.WHITE);
		this.setFocusable(true);
		this.sensorMgr = sensorMgr;
		this.holder = this.getHolder();
		this.holder.addCallback(this);
		sensor = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		sensorMgr
				.registerListener(lsn, sensor, SensorManager.SENSOR_DELAY_GAME);

	}

	public void updateAccelerate() {
		Log.i("AndroidDemo1.sensor", "x=" + sensorX + ", y=" + sensorY + ", z="
				+ sensorZ);
	}

	private void initBalls() {
		for (int i = 0; i < ballCount; i++) {
			Ball ball = new Ball();
			Random ran = new Random();

			int r = ran.nextInt(255);
			int g = ran.nextInt(255);
			int b = ran.nextInt(255);
			ball.radius = 40f;
			ball.color = Color.argb(255, r, g, b);
			Point point = this.getNextBallPos(ball.radius);
			ball.px = point.x;
			ball.py = point.y;
			this.balls.add(ball);
		}
	}

	private Point getNextBallPos(float radius) {
		Random ran = new Random();
		for (;;) {
			int px = (int) (ran.nextInt(this.getWidth() - (int) radius) + 2 / radius);
			int py = (int) (ran.nextInt(this.getHeight() - (int) radius) + 2 / radius);
			boolean success = true;
			for (Ball ball : this.balls) {
				double f = Math.pow(px - ball.px, 2)
						+ Math.pow(py - ball.py, 2);
				double d = Math.pow(f, 0.5);
				if (d < radius + ball.radius) {
					success = false;
				}
			}
			if (success) {
				return new Point(px, py);
			}
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		this.holder = holder;
		this.isRunning = true;
		initBalls();
		this.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		this.isRunning = false;
		timer.cancel();
	}

	public void start() {
		// thread = new Thread(new MyThread());
		// thread.run();
		timer = new Timer();
		timer.schedule(new MyTimerTask(), new Date(), 1000 / fps);

	}

	public void draw() {

		Canvas canvas = holder.lockCanvas();
		canvas.drawColor(Color.WHITE);
		this.checkBorder();
		for (Ball ball : this.balls) {
			this.drawBall(ball, canvas);
		}
		holder.unlockCanvasAndPost(canvas);
	}

	public void drawBall(Ball ball, Canvas canvas) {

		// this.checkBorder(ball);

		float dTime = (float) (1.0 / fps);

		float vx = ball.vx;
		float vy = ball.vy;

		ball.vx += (-this.sensorX) * dTime;
		ball.vy += (this.sensorY) * dTime;

		float dx = (vx + ball.vx) * dTime / 2;
		float dy = (vy + ball.vy) * dTime / 2;

		ball.px += dx;
		ball.py += dy;

		float pX = ball.px;
		float pY = ball.py;
		float r = ball.radius;
		float left = pX - r / 2;
		float top = pY - r / 2;
		float bottom = top + 2 * r;
		float right = left + 2 * r;
		rect.left = left;
		rect.right = right;
		rect.top = top;
		rect.bottom = bottom;
		canvas.drawArc(rect, 0, 360, true, ball.paint());
	}

	private void checkBorder() {

		int width = this.getWidth();
		int height = this.getHeight();

		for (Ball ball : this.balls) {
			if ((ball.px < 0 && ball.vx < 0)
					|| (ball.px + 2 * ball.radius > width && ball.vx > 0)) {
				ball.vx = -ball.vx * (1 - this.lostRate);
				if (Math.abs(ball.vx) < 1) {
					ball.vx = 0;
				}
			}
			if ((ball.py < 0 && ball.vy < 0)
					|| (ball.py + 2 * ball.radius > height && ball.vy > 0)) {
				ball.vy = -ball.vy * (1 - this.lostRate);
				if (Math.abs(ball.vy) < 1) {
					ball.vy = 0;
				}
			}
		}

		for (int i = 0; i < this.balls.size(); i++) {

			Ball ball = this.balls.get(i);

			Vector2D v1 = new Vector2D();
			v1.x = ball.vx;
			v1.y = ball.vy;

			for (Ball b : balls) {
				if (b == ball) {
					continue;
				}

				float mx = ball.px - b.px;
				float my = ball.py - b.py;

				Vector2D v0 = new Vector2D();
				v0.x = mx;
				v0.y = my;
				if (v0.length() > b.radius + ball.radius) {
					continue;
				}

				Vector2D v2 = new Vector2D();
				v2.x = b.vx;
				v2.y = b.vy;
				// Vector2D v11 = this.getVectorReflection(v1, v0);
				// Vector2D v22 = this.getVectorReflection(v2, v0a);
				// Vector2D vs = v11.add(v22);
				// double angle = Vector2D.angle(vs, v0);
				//
				// if (angle == 0) {
				// continue;
				// }
				// else {
				// Log.i("AndroidDemo1", "angle -> " + angle * 180 / Math.PI);
				// }

				Vector2D p1 = getMirrorVector(v0, v1);
				Vector2D p2 = getMirrorVector(v0, v2);

				// Log.i("AndroidDemo1", "p1 -> " + p1.x + ", " + p1.y);
				ball.vx = (float) p1.x;
				ball.vy = (float) p1.y;
				// Log.i("AndroidDemo1", "p2 -> " + p2.x + ", " + p2.y);
				b.vx = (float) p2.x;
				b.vy = (float) p2.y;
			}
		}
	}

	public Vector2D getVectorReflection(Vector2D v1, Vector2D v2) {
		double d1 = v1.x * v2.x + v1.y * v2.y;
		double d2 = d1 / Math.pow(v2.length(), 2);
		Vector2D v = new Vector2D();
		v.x = v2.x * d2;
		v.y = v2.y * d2;
		return v;
	}

	private Vector2D getMirrorVector(Vector2D vm, Vector2D vv) {
		double mx = vm.x;
		double my = vm.y;
		double vx = vv.x;
		double vy = vv.y;
		double M = Math.sqrt(Math.pow(mx, 2) + Math.pow(my, 2));
		double D = -vx * mx - vy * my;
		double A = 2 * D / Math.pow(M, 2);
		double x = vx + mx * A;
		double y = vy + my * A;
		Vector2D vec = new Vector2D(x, y);
		return vec;
	}

	// private PointF getMirrorVectory(double mx, double my, double vx, double
	// vy) {
	// double M = Math.sqrt(Math.pow(mx, 2) + Math.pow(my, 2));
	// double D = -vx * mx - vy * my;
	// double A = 2 * D / Math.pow(M, 2);
	// double x = vx + mx * A;
	// double y = vy + my * A;
	// return new PointF((float) x, (float) y);
	// }

	class MyTimerTask extends TimerTask {

		@Override
		public void run() {
			draw();
		}

	}
}
