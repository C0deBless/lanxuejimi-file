package common;

import java.awt.Color;
import java.awt.Graphics;

public class Explode {
	public static int explodeIndex = 0;
	
	
	private float x;
	private float y;

	private boolean live = true;

	private int id;

	private int step = 0;

	private int[] diameter = { 7, 16, 20, 30, 40, 55, 30, 16, 7 };

	public Explode(float x, float y) {
		this.x = x;
		this.y = y;
		this.id = (++explodeIndex);
	}

	public void update() {
		if (step == diameter.length) {
			live = false;
			step = 0;
			return;
		}
		step++;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int[] getDiameter() {
		return diameter;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getId() {
		return id;
	}
	
}
