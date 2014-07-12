package common;

import java.awt.Rectangle;
import java.nio.ByteBuffer;

public class Missile {

	private float x;
	private float y;
	private int width = 10;
	private int height = 16;
	private float missileSpeed;
	private int angle;
	private int team;
	private boolean live = true;
	private int id;
	private final MissileType type;

	public Missile(int id, float x, float y, int angle, int team,
			MissileType type) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.team = team;
		this.id = id;
		this.type = type;
	}

	public void update(long deltaTime) {
		int factorX = 0;
		int factorY = 0;

		switch (angle) {
		case 0:
			factorY = -2;
			break;
		case 1:
			factorX = 2;
			break;
		case 2:
			factorY = 2;
			break;
		case 3:
			factorX = -2;
			break;
		}

		float deltaPos = missileSpeed * (deltaTime / 1000.0f);

		x += deltaPos * factorX;
		y += deltaPos * factorY;

	}

	public void serialize(ByteBuffer buffer) {
		buffer.putInt(id);
		buffer.putFloat(x);
		buffer.putFloat(y);
		buffer.putFloat(missileSpeed);
		buffer.putInt(angle);
		buffer.putInt(width);
		buffer.putInt(height);
		buffer.putInt(team);
	}

	public static Missile deserialize(ByteBuffer buffer) {
		int id = buffer.getInt();
		float x = buffer.getFloat();
		float y = buffer.getFloat();
		float currentSpeed = buffer.getFloat();
		int angle = buffer.getInt();
		int width = buffer.getInt();
		int height = buffer.getInt();
		int team = buffer.getInt();
		MissileType type = MissileType.parse(buffer.get());

		Missile missile = new Missile(id, x, y, angle, team, type);
		missile.missileSpeed = currentSpeed;
		missile.width = width;
		missile.height = height;
		return missile;
	}

	public boolean hitTank(Tank t) {
		if (getRectangle().intersects(t.getRectangle())
				&& t.getTeam() != this.team && this.live && t.isLive()) {
			setLive(false);
			t.setLive(false);
			return true;
		}
		return false;
	}

	public boolean hitCamp(Camp camp) {
		if (getRectangle().intersects(camp.getRectangle()) && this.live
				&& camp.isLive()) {
			setLive(false);
			camp.setLive(false);
			return true;

		}
		return false;
	}

	public Rectangle getRectangle() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public void setMissileSpeed(float missileSpeed) {
		this.missileSpeed = missileSpeed;
	}

	public int getTeam() {
		return team;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getId() {
		return id;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public int getAngle() {
		return angle;
	}

	public MissileType getType() {
		return type;
	}

}
