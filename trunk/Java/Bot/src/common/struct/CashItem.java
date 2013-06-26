package common.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import common.serialize.Serializable;

public class CashItem implements Serializable {
	Cash cash;
	List<Item> items = new ArrayList<>();
	
	
	
	public CashItem(Cash cash, List<Item> items) {
		super();
		this.cash = cash;
		this.items = items;
	}

	public Cash getCash() {
		return cash;
	}

	public List<Item> getItems() {
		return items;
	}

	public void serialize(ByteBuffer bb) {
		cash.serialize(bb);
		
		if( null == items ) {
			bb.putShort((short)0);
		}
		else {
			bb.putShort((short) items.size()); // 길이
			for (int i = 0; i < items.size(); ++i) {
				Item obj = items.get(i);
				obj.serialize(bb);
			}
		}

	}

	public static CashItem deserialize(ByteBuffer bb) {
		Cash cash = Cash.deserialize(bb);
		
		final short length = bb.getShort();
		List<Item> list = new ArrayList<>();
		for (int i = 0; i < length; ++i) {
			Item obj = Item.deserialize(bb);
			list.add(obj);
		}
		
		return new CashItem(cash, list);
	}
}
