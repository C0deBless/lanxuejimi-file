package com.example.androiddemo1;

import java.util.Date;
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
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TestView2 extends SurfaceView implements SurfaceHolder.Callback {

	float pX = 20;
	float pY = 20;
	RectF rect = new RectF();
	SensorManager sensorMgr;
	Sensor sensor;
	Paint paint = new Paint();
	Thread thread;
	float x;
	float y;
	float z;
	SurfaceHolder holder;
	int fps = 30;
	boolean isRunning = false;

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
		SensorEventListener lsn = new SensorEventListener() {
			public void onSensorChanged(SensorEvent e) {
				x = e.values[SensorManager.DATA_X];
				y = e.values[SensorManager.DATA_Y];
				z = e.values[SensorManager.DATA_Z];
			}

			public void onAccuracyChanged(Sensor s, int accuracy) {
			}
		};

		sensorMgr
				.registerListener(lsn, sensor, SensorManager.SENSOR_DELAY_GAME);
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
	}

	public void start() {
		// thread = new Thread(new MyThread());
		// thread.run();
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(), new Date(), 1000 / fps);
	}

	public void draw() {

		Canvas canvas = holder.lockCanvas();
		canvas.drawColor(Color.BLACK);
		pX++;
		pY++;
		float r = 20f;
		float left = pX - r / 2;
		float top = pY - r / 2;
		float bottom = top + 2 * r;
		float right = left + 2 * r;
		rect.left = left;
		rect.right = right;
		rect.top = top;
		rect.bottom = bottom;
		canvas.drawArc(rect, 0, 360, true, paint);
		holder.unlockCanvasAndPost(canvas);
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
