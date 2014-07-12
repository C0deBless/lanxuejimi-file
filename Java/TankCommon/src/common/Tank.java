package common;

import java.awt.Rectangle;
import java.nio.ByteBuffer;
import java.util.List;

public class Tank {

	private static int tankIndex = 0;
	private int clientId;

	private int width = 36;
	private int height = 36;

	private float x;
	private float y;

	private float oldX;
	private float oldY;

	private float currentSpeed;
	private final int id;
	private boolean good;
	private boolean live = true;
	private int angle; // 0,1,2,3
	private int team;

	private final TankType type;

	public Tank(float x, float y, int team, TankType type) {
		this.x = x;
		this.y = y;
		this.team = team;
		this.id = (++tankIndex);
		this.type = type;
	}

	public Tank(float x, float y, int team, int id, TankType type) {
		this.x = x;
		this.y = y;
		this.team = team;
		this.id = id;
		this.type = type;
	}

	public void update(long deltaTime) {
		oldX = x;
		oldY = y;

		int factorX = 0;
		int factorY = 0;

		switch (angle) {
		case 0:
			factorY = -1;
			break;
		case 1:
			factorX = 1;
			break;
		case 2:
			factorY = 1;
			break;
		case 3:
			factorX = -1;
			break;
		}

		float deltaPos = currentSpeed * (deltaTime / 1000.0f);

		x += deltaPos * factorX;
		y += deltaPos * factorY;

		if (x < 0)
			x = 0;
		if (y < 0)
			y = 0;
		if (x > Constants.GAME_WIDTH - width)
			x = Constants.GAME_WIDTH - width;
		if (y > Constants.GAME_HEIGHT - height)
			y = Constants.GAME_HEIGHT - height;
	}

	public void serialize(ByteBuffer buffer) {
		buffer.putInt(clientId);
		buffer.putInt(id);
		buffer.putFloat(x);
		buffer.putFloat(y);
		buffer.putFloat(currentSpeed);
		buffer.putInt(angle);
		buffer.putInt(width);
		buffer.putInt(height);
		buffer.putInt(team);
		buffer.put(this.type.getValue());
	}

	public static Tank deserialize(ByteBuffer buffer) {
		int clientId = buffer.getInt();
		int id = buffer.getInt();
		float x = buffer.getFloat();
		float y = buffer.getFloat();
		float currentSpeed = buffer.getFloat();
		int angle = buffer.getInt();
		int width = buffer.getInt();
		int height = buffer.getInt();
		int team = buffer.getInt();
		TankType type = TankType.parse(buffer.get());

		Tank tank = new Tank(x, y, team, id, type);
		tank.clientId = clientId;
		tank.currentSpeed = currentSpeed;
		tank.angle = angle;
		tank.width = width;
		tank.height = height;
		return tank;
	}

	public void stay() {
		x = oldX;
		y = oldY;
	}

	public boolean collidesWithTank(Tank tank) {
		if (this != tank) {
			if (getRectangle().intersects(tank.getRectangle()) && this.live
					&& tank.isLive()) {
				stay();
				tank.stay();
				return true;
			}
		}
		return false;

	}

	public boolean collidesWithTanks(List<Tank> tanks) {
		for (Tank tank : tanks) {
			this.collidesWithTank(tank);
			return true;
		}
		return false;

	}

	public Rectangle getRectangle() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public Missile makeMissile(int missileId) {
		Missile m = new Missile(missileId, this.getX() + this.getWidth() / 2
				- 3, this.getY() + this.getHeight() / 2 - 3, this.getAngle(),
				this.getTeam(), MissileType.parse(this.type.getValue()));
		m.setMissileSpeed(300);
		return m;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isGood() {
		return good;
	}

	public int getId() {
		return id;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	public float getCurrentSpeed() {
		return currentSpeed;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getAngle() {
		return angle;
	}

	public int getTeam() {
		return team;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public void setCurrentSpeed(float currentSpeed) {
		this.currentSpeed = currentSpeed;
	}

	public TankType getType() {
		return type;
	}
}
