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
import android.util.FloatMath;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.androiddemo1.model.Ball;

public class TestView3 extends SurfaceView implements SurfaceHolder.Callback {

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
	int ballCount = 1;

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

	public TestView3(Context context, SensorManager sensorMgr) {
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
		double steps = 2;
		double delta = 1 / steps;
		for (int i = 0; i < steps; i++) {
			gravity();
			accelerate(delta);
			collide();
			borderCollide();
			inertia(delta);
		}

		Canvas canvas = holder.lockCanvas();
		canvas.drawColor(Color.WHITE);
		// this.checkBorder();
		for (Ball ball : this.balls) {
			this.drawBall(ball, canvas);
		}
		holder.unlockCanvasAndPost(canvas);
	}

	public void collide() {
		for (int i = 0, l = balls.size(); i < l; i++) {
			Ball body1 = balls.get(i);
			for (int j = i + 1; j < l; j++) {
				Ball body2 = balls.get(j);
				float x = body1.x - body2.x;
				float y = body1.y - body2.y;
				float slength = x * x + y * y;
				float length = FloatMath.sqrt(slength);
				float target = body1.radius + body2.radius;

				if (length < target) {
					float factor = (length - target) / length;
					body1.x -= x * factor * 0.5;
					body1.y -= y * factor * 0.5;
					body2.x += x * factor * 0.5;
					body2.y += y * factor * 0.5;
				}
			}
		}
	}

	public void gravity() {
		for (Ball ball : this.balls) {
			ball.ay += 0.5;
		}
	}

	public void accelerate(double dTime) {
		for (Ball ball : this.balls) {
			ball.accelerate(dTime);
		}
	}

	public void inertia(double dTime) {
		for (Ball ball : this.balls) {
			ball.inertia(dTime);
		}
	}

	public void borderCollide() {
		for (int i = 0, l = balls.size(); i < l; i++) {
			Ball body = balls.get(i);
			float radius = body.radius;
			float x = body.x;
			float y = body.y;

			if (x - radius < 0) {
				body.x = radius;
			} else if (x + radius > this.getWidth()) {
				body.x = this.getWidth() - radius;
			}
			if (y - radius < 0) {
				body.y = radius;
			} else if (y + radius > this.getHeight()) {
				body.y = this.getHeight() - radius;
			}
		}
	}

	public void drawBall(Ball ball, Canvas canvas) {

		// this.checkBorder(ball);

		// float dTime = (float) (1.0 / fps);

		// float vx = ball.vx;
		// float vy = ball.vy;

		// ball.vx += (-this.sensorX) * dTime;
		// ball.vy += (this.sensorY) * dTime;
		//
		// float dx = (vx + ball.vx) * dTime / 2;
		// float dy = (vy + ball.vy) * dTime / 2;

		// ball.px += dx;
		// ball.py += dy;

		float pX = ball.x;
		float pY = ball.y;
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

	class MyTimerTask extends TimerTask {

		@Override
		public void run() {
			draw();
		}

	}
}
