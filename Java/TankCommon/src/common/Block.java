package common;

import java.awt.Rectangle;
import java.nio.ByteBuffer;

public class Block {
	private static int tankIndex = 0;
	private int width = 36;
	private int height = 36;
	private float x;
	private float y;
	private boolean live = true;
	private final int id;

	public Block(float x, float y) {
		this.x = x;
		this.y = y;
		this.id = (++tankIndex);
	}

	public Block(float x, float y, int id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}

	public void update() {
	}

	public void serialize(ByteBuffer buffer) {
		buffer.putInt(id);
		buffer.putFloat(x);
		buffer.putFloat(y);
		buffer.putInt(width);
		buffer.putInt(height);
	}

	public static Block deserialize(ByteBuffer buffer) {
		int id = buffer.getInt();
		float x = buffer.getFloat();
		float y = buffer.getFloat();
		int width = buffer.getInt();
		int height = buffer.getInt();

		Block wall = new Block(x, y, id);
		wall.width = width;
		wall.height = height;
		return wall;
	}

	public Rectangle getRectangle() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
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
