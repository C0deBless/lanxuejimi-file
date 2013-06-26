package common.struct;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.Column;
import common.serialize.Serializable;

public class PortProduct implements Serializable {
	private int port_id;
	private int id;
	private int price;
	private char special = 'n';
	private int basePrice;

	public PortProduct(ResultSet rs) throws SQLException {
		this.port_id = rs.getInt(Column.PORT_CODE);
		this.id = rs.getInt(Column.ITEM_CODE);
		this.price = rs.getInt(Column.ITEM_PRICE);
	}

	public PortProduct(int port_id, int id, int price) {
		super();
		this.port_id = port_id;
		this.id = id;
		this.price = price;
		this.basePrice = price;
	}

	@Override
	public void serialize(ByteBuffer buffer) {
		buffer.putInt(port_id);
		buffer.putInt(id);
		buffer.putInt(price);
	}

	public static PortProduct deserialize(ByteBuffer buffer) {
		int portId = buffer.getInt();
		int id = buffer.getInt();
		int price = buffer.getInt();

		return new PortProduct(portId, id, price);
	}

	public PortProduct(PortProduct item) {
		port_id = item.getPort_id();
		id = item.getId();
		price = item.getPrice();
		special = item.getSpecial();
		basePrice = item.getB_price();
	}

	public int getB_price() {
		return basePrice;
	}
	
	public char getSpecial() {
		return special;
	}

	public void setSpecial(char special) {
		this.special = special;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getPort_id() {
		return port_id;
	}

	public void setPort_id(int port_id) {
		this.port_id = port_id;
	}

}
