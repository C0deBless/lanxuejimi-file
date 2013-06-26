package common.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Constants;
import common.serialize.Serializable;

public class Item implements Serializable {
	private int id;
	private int count;
	private int buy_price = 0;
	private int item_to_mail = 0;
	public Item() {
	}

	public Item(Item item) {
		this.id = item.getId();
		this.count = item.getCount();
		this.buy_price = item.getBuy_price();
		if( Short.MAX_VALUE < count  ) {
			count = Short.MAX_VALUE; 
		}
	}

	public Item(int id, int count) {
		this.id = id;
		this.count = count;
		if( Short.MAX_VALUE < count  ) {
			count = Short.MAX_VALUE; 
		}
	}

	public Item(int id, int count, int buy_price) {
		this(id, count);
		this.buy_price = buy_price;
	}

	public int getBuy_price() {
		if( 0 > this.buy_price ) {
			return 0;
		}
		return buy_price;
	}

	public void setBuy_price(int buy_price) {		
		this.buy_price = buy_price;
	}

	public int getId() {
		return id;
	}

	public void setId(int item_id) {
		this.id = item_id;
	}

	public int getCount() {
		if( Short.MAX_VALUE < this.count  ) {
			this.count = Short.MAX_VALUE; 
		}
		return count;
	}

	public int addCount(int dcount) {
		this.count += dcount;
		if( Short.MAX_VALUE < this.count  ) {
			this.count = Short.MAX_VALUE; 
		}
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
		if( Short.MAX_VALUE < this.count  ) {
			this.count = Short.MAX_VALUE; 
		}
	}

	public void serialize(ByteBuffer bb) {
		bb.putInt(id);
		bb.putInt(count);
		bb.putInt(buy_price);
	}

	public static Item deserialize(ByteBuffer bb) {
		int id = bb.getInt();
		int count = bb.getInt();
		if( Short.MAX_VALUE < count  ) {
			count = Short.MAX_VALUE; 
		}
		int buy_price = bb.getInt();
		return new Item(id, count, buy_price);
	}

	public void setCount(int count, int price) {
		int dCount = count - this.count;
		int sum = this.buy_price * this.count + dCount * price;
		if( Short.MAX_VALUE < count  ) {
			count = Short.MAX_VALUE;
		}
		this.count = count;
		
		if (count > 0) {
			this.buy_price = sum / count;
		}		
	}

	public Map<String, Object> prepare() {
		Map<String, Object> info = new HashMap<>();
		info.put(Constants.ITEM_ID, this.id);
		info.put(Constants.COUNT, this.count);
		info.put(Constants.BUY_PRICE, this.buy_price);
		info.put(Constants.ITEM_TO_MAIL, this.item_to_mail);
		return info;
	}

	public Object prepareWithoutPrice() {
		Map<String, Object> info = new HashMap<>();
		info.put(Constants.ITEM_ID, this.id);
		info.put(Constants.COUNT, this.count);
		info.put(Constants.ITEM_TO_MAIL, this.item_to_mail);
		return info;
	}

	public static List<Item> deserializeList(ByteBuffer bb) {
		int size = bb.getInt();
		List<Item> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			Item b = Item.deserialize(bb);
			list.add(b);
		}
		return list;
	}

	public int getItem_to_mail() {
		return item_to_mail;
	}

	public void setItem_to_mail(int item_to_mail) {
		this.item_to_mail = item_to_mail;
	}
	
	
}
