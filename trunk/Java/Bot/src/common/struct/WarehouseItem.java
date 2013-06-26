package common.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import common.serialize.Serializable;

public class WarehouseItem implements Serializable {
	protected int port_id;
	protected int item_id;
	protected int count;
	protected int buy_price;

	public WarehouseItem() {

	}

	public WarehouseItem(int port_id, int item_id, int count, int buy_price) {
		super();
		this.port_id = port_id;
		this.item_id = item_id;
		this.count = count;
		this.buy_price = buy_price;
		if( Short.MAX_VALUE < this.count  ) {
			this.count = Short.MAX_VALUE; 
		}
	}

	public int getBuy_price() {
		return buy_price;
	}

	public void setBuy_price(int buy_price) {
		this.buy_price = buy_price;
	}

	public int getPort_id() {
		return port_id;
	}

	public void setPort_id(int port_id) {
		this.port_id = port_id;
	}

	public int getItem_id() {
		return item_id;
	}

	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}

	public int getCount() {
		if( Short.MAX_VALUE < this.count  ) {
			this.count = Short.MAX_VALUE; 
		}
		return count;
	}

	public void setCount(int count) {
		this.count = count;
		if( Short.MAX_VALUE < this.count  ) {
			this.count = Short.MAX_VALUE; 
		}
	}

	public void addCount(int count) {
		this.count += count;
		if( Short.MAX_VALUE < this.count  ) {
			this.count = Short.MAX_VALUE; 
		}
	}

	public void addCount(int count, int price) {
		setCount(this.count + count, price);
	}

	public void setCount(int count, int price) {
		int dCount = count - this.count;
		int sum = this.buy_price * this.count + dCount * price;
		this.count = count;
		if( Short.MAX_VALUE < this.count  ) {
			this.count = Short.MAX_VALUE; 
		}
		if (count > 0) {
			this.buy_price = sum / count;
		}
	}

	public void serialize(ByteBuffer bb) {
		bb.putInt(port_id);
		bb.putInt(item_id);
		if( Short.MAX_VALUE < this.count  ) {
			this.count = Short.MAX_VALUE; 
		}
		bb.putInt(count);
		bb.putInt(buy_price);
	}

	public static WarehouseItem deserialize(ByteBuffer bb) {
		int port_id = bb.getInt();
		int item_id = bb.getInt();
		int count = bb.getInt();
		if( Short.MAX_VALUE < count  ) {
			count = Short.MAX_VALUE; 
		}
		int buy_price = bb.getInt();

		return new WarehouseItem(port_id, item_id, count, buy_price);
	}

	public static List<WarehouseItem> deserializeList(ByteBuffer bb) {
		int size = bb.getInt();
		List<WarehouseItem> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			WarehouseItem b = WarehouseItem.deserialize(bb);
			list.add(b);
		}
		return list;
	}
}
