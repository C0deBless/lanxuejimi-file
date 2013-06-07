package com.trnnn.imanhua;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork() // 这里可以替换为detectAll()
																		// 就包括了磁盘读写和网络I/O
				.penaltyLog() // 打印logcat，当然也可以定位到dropbox，通过文件保存相应的log
				.build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects() // 探测SQLite数据库操作
				.penaltyLog() // 打印logcat
				.penaltyDeath().build());
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
		ImageView view = (ImageView) this.findViewById(R.id.imageView1);
		view.setImageBitmap(this
				.getHttpBitmap("http://c4.mangafiles.com/Files/Images/54/82968/JOJO_002.png"));
		view.refreshDrawableState();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public Bitmap getHttpBitmap(String url) {

		URL myFileURL;
		Bitmap bitmap = null;
		try {
			myFileURL = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) myFileURL
					.openConnection();
			conn.setConnectTimeout(6000);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;

	}
}
