package common.struct;

import java.nio.ByteBuffer;

import common.serialize.Serializable;

public class ShipHpModel implements Serializable {

	private long userId;
	private int shipId;
	private int hp;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getShipId() {
		return shipId;
	}

	public void setShipId(int shipId) {
		this.shipId = shipId;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	@Override
	public void serialize(ByteBuffer buffer) {
		buffer.putLong(userId);
		buffer.putInt(shipId);
		buffer.putInt(hp);
	}

	public static ShipHpModel deserialize(ByteBuffer buffer) {
		long userId = buffer.getLong();
		int shipId = buffer.getInt();
		int hp = buffer.getInt();
		ShipHpModel model = new ShipHpModel();
		model.setUserId(userId);
		model.setShipId(shipId);
		model.setHp(hp);
		return model;
	}
}
