package common.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import common.serialize.Serializable;

public class ShipCaptianInfo implements Serializable {
	int shipId;
	long captain;
	char flag;

	public ShipCaptianInfo(int shipId, long captain, char flag) {
		super();
		this.shipId = shipId;
		this.captain = captain;
		this.flag = flag;
	}

	public int getShipId() {
		return shipId;
	}

	public void setShipId(int shipId) {
		this.shipId = shipId;
	}

	public long getCaptain() {
		return captain;
	}

	public void setCaptain(long captain) {
		this.captain = captain;
	}

	public char getFlag() {
		return flag;
	}

	public void setFlag(char flag) {
		this.flag = flag;
	}

	public void serialize(ByteBuffer bb) {
		bb.putInt(shipId);
		bb.putLong(captain);
		bb.putChar(flag);
	}

	public static ShipCaptianInfo deserialize(ByteBuffer bb) {
		int shipId = bb.getInt();
		long captain = bb.getLong();
		char flag = bb.getChar();
		return new ShipCaptianInfo(shipId, captain, flag);
	}

	public static List<ShipCaptianInfo> deserializeList(ByteBuffer bb) {
		int size = bb.getInt();
		List<ShipCaptianInfo> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			ShipCaptianInfo b = ShipCaptianInfo.deserialize(bb);
			list.add(b);
		}
		return list;
	}

}
