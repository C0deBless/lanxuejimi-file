package com.example.game.framework;

import android.graphics.Bitmap;
import android.graphics.PixelFormat;

public class PixMap {

	Bitmap bitmap;

	PixelFormat format;

	public PixMap(Bitmap bitmap, PixelFormat format) {
		super();
		this.bitmap = bitmap;
		this.format = format;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public PixelFormat getFormat() {
		return format;
	}

	public void setFormat(PixelFormat format) {
		this.format = format;
	}

}
