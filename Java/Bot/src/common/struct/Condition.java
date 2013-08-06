package common.struct;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Constants;

public class Condition {
	static final Logger logger = LoggerFactory.getLogger(Condition.class);

	public static final int DIRECTION_ERR = -1;
	public static final int DIRECTION_ALL = 0;
	public static final int DIRECTION_EAST = 1;
	public static final int DIRECTION_WEST = 2;
	public static final int DIRECTION_SOUTH = 3;
	public static final int DIRECTION_NORTH = 4;
	public static final int DIRECTION_MAP = 5;
	// public static final int DIRECTION_PORT = 6;

	public static final int MOVE_ALL = 0;
	public static final int MOVE_MAP_IN = 1;
	public static final int MOVE_MAP_OUT = 2;

	private static final int NOT_CREATE_TIME = -1;
	private static final int MIDNIGHT_TIME = 0;

	List<Integer> realtime;
	int mission;
	int no_mission;
	int npc;
	int item;
	int min_lv;
	int max_lv;
	int direction;
	int move;
	int prob;
	int chance;
	int trans;
	int transId;
	int faction_id;
	int faction_min;
	int faction_max;

	public Condition() {
		this.realtime = new ArrayList<>();
	}

	public void addRealtime(int time) {
		if (0 == time) {
			time = Condition.NOT_CREATE_TIME;
		} else if (24 == time) {
			time = MIDNIGHT_TIME;
		}
		this.realtime.add(time);
	}

	public int getMission() {
		return mission;
	}

	public void setMission(int mission) {
		this.mission = mission;
	}

	public int getNo_mission() {
		return no_mission;
	}

	public void setNo_mission(int no_mission) {
		this.no_mission = no_mission;
	}

	public int getNpc() {
		return npc;
	}

	public void setNpc(int npc) {
		this.npc = npc;
	}

	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}

	public int getMin_lv() {
		return min_lv;
	}

	public void setMin_lv(int min_lv) {
		this.min_lv = min_lv;
	}

	public int getMax_lv() {
		return max_lv;
	}

	public void setMax_lv(int max_lv) {
		this.max_lv = max_lv;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getMove() {
		return move;
	}

	public void setMove(int move) {
		this.move = move;
	}

	public int getProb() {
		return prob;
	}

	public void setProb(int prob) {
		this.prob = prob;
	}

	public int getChance() {
		return chance;
	}

	public void setChance(int chance) {
		this.chance = chance;
	}

	public int getTrans() {
		return trans;
	}

	public void setTrans(int transform) {
		this.trans = transform;
	}

	public int getTransId() {
		return transId;
	}

	public void setTransId(int transId) {
		this.transId = transId;
	}

	public boolean isTransform() {
		if ((0 == this.trans) || (0 == this.transId)) {
			return false;
		}

		Random rand = new Random();
		int randValue = rand.nextInt(Constants.RAND_PER) + 1;
		logger.error("Condition.isTransform, transId:{}, trans:{}, rand:"
				+ randValue, transId, trans);
		if (randValue <= this.trans) {
			return true;
		}
		return false;
	}

	public int getFaction_id() {
		return faction_id;
	}

	public void setFaction_id(int faction_id) {
		this.faction_id = faction_id;
	}

	public int getFaction_min() {
		return faction_min;
	}

	public void setFaction_min(int faction_min) {
		this.faction_min = faction_min;
	}

	public int getFaction_max() {
		return faction_max;
	}

	public void setFaction_max(int faction_max) {
		this.faction_max = faction_max;
	}

	public Condition clon() {
		Condition obj = new Condition();

		obj.mission = this.mission;
		obj.no_mission = this.no_mission;
		obj.npc = this.npc;
		obj.item = this.item;
		obj.min_lv = this.min_lv;
		obj.max_lv = this.max_lv;
		obj.direction = this.direction;
		obj.move = this.move;
		obj.prob = this.prob;
		obj.chance = this.chance;
		obj.trans = this.trans;
		obj.transId = this.transId;
		obj.faction_id = this.faction_id;
		obj.faction_min = this.faction_min;
		obj.faction_max = this.faction_max;

		if (null == this.realtime) {
			return obj;
		}

		obj.realtime.addAll(this.realtime);
		return obj;
	}
}
