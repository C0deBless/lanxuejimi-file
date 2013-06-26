package common.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import common.serialize.Serializable;

public class Dock implements Serializable {

	public static int UPGRADING = 1;
	public static int NORMAL = 0;

	private int port_id;
	private int ship_id;
	private int index;
	private int status;

	public Dock() {
	}

	public Dock(int port_id, int ship_id, int index, int status) {
		super();
		this.port_id = port_id;
		this.ship_id = ship_id;
		this.index = index;
		this.status = status;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPort_id() {
		return port_id;
	}

	public void setPort_id(int port_id) {
		this.port_id = port_id;
	}

	public int getShip_id() {
		return ship_id;
	}

	public void setShip_id(int ship_id) {
		this.ship_id = ship_id;
	}

	public void serialize(ByteBuffer bb) {
		bb.putInt(port_id);
		bb.putInt(ship_id);
		bb.putInt(index);
		bb.putInt(status);
	}

	public static Dock deserialize(ByteBuffer bb) {
		int port_id = bb.getInt();
		int ship_id = bb.getInt();
		int index = bb.getInt();
		int status = bb.getInt();
		return new Dock(port_id, ship_id, index, status);
	}

	public static List<Dock> deserializeList(ByteBuffer bb) {
		int size = bb.getInt();
		List<Dock> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			Dock b = Dock.deserialize(bb);
			list.add(b);
		}
		return list;
	}
}
