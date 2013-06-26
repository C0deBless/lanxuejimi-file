package common.struct;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.Column;
import common.serialize.Serializable;

public class TradeItem implements Serializable {
	int shipId;
	int itemId;
	int count;
	int buy_price;
	boolean traded;

	public TradeItem(int shipId, int itemId, int count, int buy_price) {
		this.shipId = shipId;
		this.itemId = itemId;
		this.count = count;
		this.buy_price = buy_price;
		this.traded = false;
	}

	public TradeItem(int shipId, int itemId, int count, int buyPrice,
			boolean traded) {
		this(shipId, itemId, count, buyPrice);
		this.traded = traded;
	}

	public TradeItem(ResultSet rs) throws SQLException {
		this.shipId = rs.getInt(Column.SHIP_CODE);
		this.itemId = rs.getInt(Column.ITEM_CODE);
		this.count = rs.getInt(Column.ITEM_COUNT);
		this.buy_price = rs.getInt(Column.BUY_PRICE);
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int id) {
		this.itemId = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getBuy_price() {
		return buy_price;
	}

	public void setBuy_price(int buy_price) {
		this.buy_price = buy_price;
	}

	public void add(int count, int unitPrice) {
		int prevTotal = this.buy_price * this.count;
		this.count += count;
		this.buy_price = (prevTotal + (unitPrice * count)) / this.count;
	}

	public boolean traded() {
		return this.traded;
	}

	public void traded(boolean traded) {
		this.traded = traded;
	}

	public void setShipId(int shipId) {
		this.shipId = shipId;
	}

	public int getShipId() {
		return this.shipId;
	}

	public void serialize(ByteBuffer bb) {
		bb.putInt(shipId);
		bb.putInt(itemId);
		bb.putInt(count);
		bb.putInt(buy_price);
	}

	public static TradeItem deserialize(ByteBuffer bb) {
		int shipId = bb.getInt();
		int id = bb.getInt();
		int count = bb.getInt();
		int buyPrice = bb.getInt();
		return new TradeItem(shipId, id, count, buyPrice);
	}
}
