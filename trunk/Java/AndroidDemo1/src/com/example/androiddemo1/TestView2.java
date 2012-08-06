package com.example.androiddemo1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
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
	Paint paint = new Paint();
	Thread thread;
	float sensorX;
	float sensorY;
	float sensorZ;
	// float ax = 0f;
	// float ay = 0f;
	SurfaceHolder holder;
	int fps = 30;
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
		paint.setAntiAlias(true);
		paint.setStyle(Style.FILL);
		paint.setColor(Color.WHITE);
		// this.setBackgroundColor(Color.WHITE);
		this.setFocusable(true);
		this.sensorMgr = sensorMgr;
		this.holder = this.getHolder();
		this.holder.addCallback(this);
		sensor = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		sensorMgr
				.registerListener(lsn, sensor, SensorManager.SENSOR_DELAY_GAME);
		initBalls();
	}

	public void updateAccelerate() {
		Log.i("AndroidDemo1.sensor", "x=" + sensorX + ", y=" + sensorY + ", z="
				+ sensorZ);
	}

	private void initBalls() {
		Ball ball = new Ball();
		ball.px = 20f;
		ball.py = 20f;
		ball.radius = 20f;
		this.balls.add(ball);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		this.holder = holder;
		this.isRunning = true;
		this.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		this.isRunning = false;
		timer.cancel();
	}

	public void start() {
		// thread = new Thread(new MyThread());
		// thread.run();

		timer.schedule(new MyTimerTask(), new Date(), 1000 / fps);

	}

	public void draw() {

		Canvas canvas = holder.lockCanvas();
		canvas.drawColor(Color.BLACK);
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
		canvas.drawArc(rect, 0, 360, true, paint);
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
