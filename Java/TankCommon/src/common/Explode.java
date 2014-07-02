package common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

public class Explode {
	static Logger logger = LoggerFactory.getLogger(Explode.class);

	public static int explodeIndex = 0;

	private float x;
	private float y;

	private boolean live = true;

	private int id;

	private int step = 0;

	private static final int DIAMETER = 9; 
	
	public Explode(float x, float y) {
		this.x = x;
		this.y = y;
		this.id = (++explodeIndex);
		logger.debug("create Explode, x:{}, y:{}, id:" + this.id, x, y);
	}
	
	
	
	public Explode(float x, float y, int id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}



	public void serialize(ByteBuffer buffer) {
		buffer.putInt(id);
		buffer.putFloat(x);
		buffer.putFloat(y);
		
	}

	public static Explode deserialize(ByteBuffer buffer) {
		int id = buffer.getInt();
		float x = buffer.getFloat();
		float y = buffer.getFloat();
		

		Explode explode = new Explode(x, y, id);
		
		return explode;
	}

	public void update() {
		if (step == DIAMETER) {
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

	public int getDiameter() {
		return DIAMETER;
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
