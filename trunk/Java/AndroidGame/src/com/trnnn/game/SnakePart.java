package com.trnnn.game;

public class SnakePart {
	public int x, y;
	private int angle = 0;
	final public int changeDelta;
	public boolean isRotating = false;

	enum PartType {
		Head, Tail
	}

	public SnakePart(int x, int y, PartType type, boolean isRotating) {
		this.x = x;
		this.y = y;
		if (type == PartType.Head) {
			changeDelta = 1;
		} else {
			changeDelta = 2;
		}
		this.isRotating = isRotating;
	}

	public void rotate() {
		this.angle++;
		if (this.angle >= 4) {
			this.angle = 0;
		}
	}

	public int getAngle() {
		return this.angle;
	}
}
