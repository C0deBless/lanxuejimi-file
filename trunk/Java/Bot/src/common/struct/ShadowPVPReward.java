package common.struct;

import java.nio.ByteBuffer;

import common.serialize.Serializable;

public class ShadowPVPReward implements Serializable {

	protected long userId;
	protected int itemId;
	protected int count;
	protected int energy;
	protected int gold;
	protected int cash;
	protected Item item;

	public ShadowPVPReward() {
	}

	public ShadowPVPReward(long userId, int itemId, int count, int energy,
			int gold, int cash, Item item) {
		super();
		this.userId = userId;
		this.itemId = itemId;
		this.count = count;
		this.energy = energy;
		this.gold = gold;
		this.cash = cash;

		this.item = item;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	public void serialize(ByteBuffer bb) {
		bb.putLong(userId);
		bb.putInt(itemId);
		bb.putInt(count);
		bb.putInt(energy);
		bb.putInt(gold);
		bb.putInt(cash);

		if (null == this.item) {
			this.item = new Item();
		}

		item.serialize(bb);
	}

	public static ShadowPVPReward deserialize(ByteBuffer bb) {
		long userId = bb.getLong();
		int itemId = bb.getInt();
		int count = bb.getInt();
		int energy = bb.getInt();
		int gold = bb.getInt();
		int cash = bb.getInt();
		Item item = Item.deserialize(bb);

		return new ShadowPVPReward(userId, itemId, count, energy, gold, cash,
				item);

	}
}
