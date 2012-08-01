package com.example.androiddemo1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class TestView extends View {

	float pX = 20;
	float pY = 20;
	Drawable drawable;
	Matrix matrix;
	SensorManager sensorMgr;
	Sensor sensor;
	float x;
	float y;
	float z;

	private TestView(Context context) {
		super(context);
	}

	public TestView(Context context, SensorManager sensorMgr) {
		super(context);
		this.sensorMgr = sensorMgr;
		sensor = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		SensorEventListener lsn = new SensorEventListener() {
			public void onSensorChanged(SensorEvent e) {
				x = e.values[SensorManager.DATA_X];
				y = e.values[SensorManager.DATA_Y];
				z = e.values[SensorManager.DATA_Z];
				// TestView.this.setTitle("x=" + (int) x + "," + "y=" + (int) y
				// + "," + "z=" + (int) z);
				Log.i("AndroidDemo1", "x=" + x + ", y=" + y + ", z=" + z);
			}

			public void onAccuracyChanged(Sensor s, int accuracy) {
			}
		};

		sensorMgr
				.registerListener(lsn, sensor, SensorManager.SENSOR_DELAY_GAME);

		this.setBackgroundColor(Color.WHITE);
		paint.setAntiAlias(true);
		paint.setStyle(Style.FILL);
		drawable = this.getResources().getDrawable(R.drawable.ball);
		bitmap = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.ball);
		matrix = new Matrix();
		matrix.postScale(0.2f, 0.2f);
		System.out.println("bitmap size width=" + bitmap.getWidth()
				+ ", height=" + bitmap.getHeight());
	}

	Paint paint = new Paint();
	Bitmap bitmap;

	@Override
	protected void onDraw(Canvas canvas) {
		paint.setColor(Color.BLACK);

		// matrix.postTranslate((canvas.getWidth() - bitmap.getWidth()) / 2,
		// (canvas.getHeight() - bitmap.getHeight()) / 2);
		matrix.postTranslate(dx, dy);
		canvas.drawBitmap(bitmap, matrix, paint);
		super.onDraw(canvas);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			pY -= 10;
			invalidate();
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			pY += 10;
			invalidate();
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			pX -= 10;
			invalidate();
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			pX += 10;
			invalidate();
		}
		return super.onKeyDown(keyCode, event);
	}

	/*
	 * @Override public boolean onTouchEvent(MotionEvent event) { // TODO
	 * Auto-generated method stub float x=event.getX(); float y=event.getY();
	 * //玩家手指点击屏幕的动作 if(event.getAction()==MotionEvent.ACTION_DOWN){ this.pX=x;
	 * this.pY=y; } //玩家手指抬起离开时的动作 else
	 * if(event.getAction()==MotionEvent.ACTION_MOVE){ this.pX=x; this.pY=y; }
	 * //玩家手指在屏幕上移动的动作 else if(event.getAction()==MotionEvent.ACTION_UP){
	 * this.pX=x; this.pY=y; } invalidate(); return super.onTouchEvent(event); }
	 */

	float dx = pX;
	float dy = pY;

	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float x = event.getX();
		float y = event.getY();
		this.dx = x - pX;
		this.dy = y - pY;
		this.pX = x;
		this.pY = y;
		invalidate();
		return super.onTouchEvent(event);
	}
}
