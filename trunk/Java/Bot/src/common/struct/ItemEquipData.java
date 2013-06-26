package common.struct;

import java.nio.ByteBuffer;

import common.serialize.Serializable;
import common.tools.StringUtil;

public class ItemEquipData implements Serializable {
	public static final int LOCK_PART = -2;
	String part;
	Item equipRemainedItem;
	Item unequipRemainedItem;
	public ItemEquipData(Item equipRemainedItem,  String part) {
		this.equipRemainedItem = equipRemainedItem;
		this.part = part;
	}
	
	public ItemEquipData(Item equipRemainedItem, Item unequipRemainedItem,  String part) {
		this.equipRemainedItem = equipRemainedItem;
		this.unequipRemainedItem = unequipRemainedItem;
		this.part = part;
	}
		
	public int getEquipItemId() {
		return equipRemainedItem.getId();
	}

	public int getUnequipItemId() {
		return unequipRemainedItem.getId();
	}

	public String getPart() {
		return part;
	}
	
	public Item getEquipRemainedItem() {
		return equipRemainedItem;
	}

	public void setEquipRemainedItem(Item equipRemainedItem) {
		this.equipRemainedItem = equipRemainedItem;
	}

	public Item getUnequipRemainedItem() {
		return unequipRemainedItem;
	}

	public void setUnequipRemainedItem(Item unequipRemainedItem) {
		this.unequipRemainedItem = unequipRemainedItem;
	}

	public boolean invalid() {
		if( (0 > equipRemainedItem.getId()) || (0 > unequipRemainedItem.getId()) ) {
			return true;
		}
		if( (null == part) || ( 1 != part.length()) ) {
			return true;
		}
		return false;
	}
	
	@Override
	public void serialize(ByteBuffer bb) {
		equipRemainedItem.serialize(bb);
		unequipRemainedItem.serialize(bb);
		StringUtil.putString(bb, this.part);
		
	}
	
	public static ItemEquipData deserialize(ByteBuffer bb) {
		Item e = Item.deserialize(bb);
		Item u = Item.deserialize(bb);
		String part = StringUtil.getString(bb);		
		return new ItemEquipData( e, u, part);
		
	}
	
	
}
