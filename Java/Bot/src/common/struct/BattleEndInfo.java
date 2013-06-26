package common.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.serialize.Serializable;

public class BattleEndInfo implements Serializable {
	public static final int BATTLE_TYPE_NORMAL = 0; 
	public static final int BATTLE_TYPE_SHADOW_PVP = 1;
	public static final int BATTLE_TYPE_BASE_PVP = 2;
	
	int battleType;
	long userId;
	int win;
	int xp;
	int gold;
	int energy;
	int victory;
	int max_victory;
	int total_win;
	int total_lose;
	List<Long> xpUpList = new ArrayList<>();
	List<BattleUpdateShip> ship = new ArrayList<>();
	List<Item> rewardItems = new ArrayList<>();
	Map<Long, Long> allysBattleTime = new HashMap<>();

	public BattleEndInfo() {
	}

	public BattleEndInfo(int battleType, long userId, int win, int xp,
			int gold, int energy, int victory, int max_victory, int total_win,
			int total_lose, List<Long> xpUpList,
			List<BattleUpdateShip> ship, List<Item> items, Map<Long, Long> allysBattleTime) {
		super();
		this.battleType = battleType;
		this.userId = userId;
		this.win = win;
		this.xp = xp;
		this.gold = gold;
		this.energy = energy;
		this.victory = victory;
		this.max_victory = max_victory;
		this.total_win = total_win;
		this.total_lose = total_lose;
		this.xpUpList = xpUpList;
		this.ship = ship;
		this.rewardItems = items;
		this.allysBattleTime = allysBattleTime;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getBattleType() {
		return battleType;
	}

	public void setBattleType(int battleType) {
		this.battleType = battleType;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public int getVictory() {
		return victory;
	}

	public void setVictory(int victory) {
		this.victory = victory;
	}

	public int getMax_victory() {
		return max_victory;
	}

	public void setMax_victory(int max_victory) {
		this.max_victory = max_victory;
	}

	public int getTotal_win() {
		return total_win;
	}

	public void setTotal_win(int total_win) {
		this.total_win = total_win;
	}

	public int getTotal_lose() {
		return total_lose;
	}

	public void setTotal_lose(int total_lose) {
		this.total_lose = total_lose;
	}

	public List<BattleUpdateShip> getShip() {
		return ship;
	}

	public void setShip(List<BattleUpdateShip> ship) {
		if (null == ship) {
			ship = new ArrayList<>();
		}
		this.ship = ship;
	}

	public List<Long> getXpUpList() {
		return xpUpList;
	}

	public void setXpUpList(List<Long> xpUpList) {
		if (null == xpUpList) {
			xpUpList = new ArrayList<>();
		}
		this.xpUpList = xpUpList;
	}

	public List<Item> getRewardItems() {
		return rewardItems;
	}

	public void setRewardItems(List<Item> items) {
		this.rewardItems = items;
	}

	public Map<Long, Long> getAllysBattleTime() {
		return allysBattleTime;
	}

	public void setAllysBattleTime(Map<Long, Long> allysBattleTime) {
		this.allysBattleTime = allysBattleTime;
	}

	public void serialize(ByteBuffer buffer) {
		buffer.putInt(battleType);
		buffer.putLong(userId);
		buffer.putInt(win);
		buffer.putInt(xp);
		buffer.putInt(gold);
		buffer.putInt(energy);
		buffer.putInt(victory);
		buffer.putInt(max_victory);
		buffer.putInt(total_win);
		buffer.putInt(total_lose);

		buffer.putShort((short) xpUpList.size()); // 길이
		for (long id : xpUpList) {
			buffer.putLong(id);
		}

		buffer.putShort((short) ship.size()); // 길이
		for (BattleUpdateShip c : ship) {
			c.serialize(buffer);
		}

		buffer.putShort((short) rewardItems.size()); // 길이
		for (Item i : rewardItems) {
			i.serialize(buffer);
		}

		buffer.putShort((short) allysBattleTime.size()); // 길이
		for (long allyId : allysBattleTime.keySet()) {
			buffer.putLong(allyId);
			buffer.putLong(allysBattleTime.get(allyId));
		}
	}

	public static BattleEndInfo deserialize(ByteBuffer bb) {
		int battleType = bb.getInt();
		long userId = bb.getLong();
		int win = bb.getInt();
		int xp = bb.getInt();
		int gold = bb.getInt();
		int energy = bb.getInt();
		int victory = bb.getInt();
		int max_victory = bb.getInt();
		int total_win = bb.getInt();
		int total_lose = bb.getInt();

		final short length = bb.getShort();
		List<Long> xpUpList = new ArrayList<>();
		for (int i = 0; i < length; ++i) {
			xpUpList.add(bb.getLong());
		}

		final short shipLength = bb.getShort();
		List<BattleUpdateShip> shipList = new ArrayList<>();
		for (int i = 0; i < shipLength; ++i) {
			shipList.add(BattleUpdateShip.deserialize(bb));
		}

		final short itemLength = bb.getShort();
		List<Item> rewardItems = new ArrayList<>();
		for (int i = 0; i < itemLength; ++i) {
			rewardItems.add(Item.deserialize(bb));
		}
		
		final short allyLength = bb.getShort();
		Map<Long, Long> allys = new HashMap<>();
		for (int i = 0; i < allyLength; ++i) {
			long key = bb.getLong();
			long value = bb.getLong();
			allys.put(key, value);
		}

		return new BattleEndInfo(battleType, userId, win, xp, gold, energy,
				victory, max_victory, total_win, total_lose,
				xpUpList, shipList, rewardItems, allys);
	}

}
