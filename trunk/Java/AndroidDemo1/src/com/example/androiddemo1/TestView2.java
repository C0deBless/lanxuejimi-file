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
import android.graphics.PointF;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.androiddemo1.model.Ball;

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

	int ballCount = 10;

	private void initBalls() {
		for (int i = 0; i < ballCount; i++) {
			Ball ball = new Ball();
			Random ran = new Random();

			int r = ran.nextInt(255);
			int g = ran.nextInt(255);
			int b = ran.nextInt(255);
			ball.radius = 20f;
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
		for (Ball ball : this.balls) {
			this.drawBall(ball, canvas);
		}
		holder.unlockCanvasAndPost(canvas);
	}

	public void drawBall(Ball ball, Canvas canvas) {

		this.checkBorder(ball);

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
		rect.left = Math.round(left);
		rect.right = Math.round(right);
		rect.top = Math.round(top);
		rect.bottom = Math.round(bottom);
		canvas.drawArc(rect, 0, 360, true, ball.paint());
	}

	private void checkBorder(Ball ball) {
		int width = this.getWidth();
		int height = this.getHeight();
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

		for (Ball b : balls) {
			if (b == ball) {
				continue;
			}
			double f = Math.pow(b.px - ball.px, 2)
					+ Math.pow(b.py - ball.py, 2);
			double d = Math.pow(f, 0.5);
			if (d > b.radius + ball.radius) {
				continue;
			}
			double mx = ball.px - b.px;
			double my = ball.py - b.py;
			PointF p1 = getMirrorVectory(mx, my, ball.vx, ball.vy);
			PointF p2 = getMirrorVectory(-mx, -my, b.vx, b.vy);

			Log.i("AndroidDemo1", "p1 -> " + p1.x + ", " + p1.y);
			ball.vx = p1.x;
			ball.vy = p1.y;
			Log.i("AndroidDemo1", "p2 -> " + p2.x + ", " + p2.y);
			b.vx = p2.x;
			b.vy = p2.y;
			break;
		}
	}

	private PointF getMirrorVectory(double mx, double my, double vx, double vy) {
		double M = Math.sqrt(Math.pow(mx, 2) + Math.pow(my, 2));
		double D = -my * vx / M + mx * vy / M;
		double x = vx - 2 * my * D / M;
		double y = vy + 2 * mx * D / M;
		return new PointF((float) x, (float) y);
	}

	class MyTimerTask extends TimerTask {

		@Override
		public void run() {
			draw();
		}

	}

	class MyThread implements Runnable {

		public void run() {
			while (isRunning) {
				draw();
				try {
					// long time = 1000 / this.fps;
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
