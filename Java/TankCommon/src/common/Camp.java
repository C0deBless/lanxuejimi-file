package common;

import java.awt.Rectangle;

public class Camp {

	private int width = 36;
	private int height = 36;
	private float x;
	private float y;
	private boolean live = true;
	
	public Camp() {
	}
	
	public Camp(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void update(){
	}
	
	public Rectangle getRectangle() {
		return new Rectangle((int) x, (int) y, width, height);
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	
}
