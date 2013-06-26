package common.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.serialize.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Building implements Serializable {

	static Logger logger = LoggerFactory.getLogger(Building.class);

	private int port_id;
	private int building_seq;
	private int building_id;
	private int level;
	private int pos;
	private long tick = 0;
	private int need_point = 0;
	private int current_point = 0;
	private int d_point = 0;
	private int hp = 0;
	private int revengeSrl;

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public long getTick() {
		return tick;
	}

	public void setTick(long tick) {
		this.tick = tick;
	}

	public int getNeed_point() {
		return need_point;
	}

	public void setNeed_point(int need_point) {
		this.need_point = need_point;
	}

	public int getCurrent_point() {
		return current_point;
	}

	public void setCurrent_point(int current_point) {
		this.current_point = current_point;
	}

	public int getD_point() {
		return d_point;
	}

	public void setD_point(int d_point) {
		this.d_point = d_point;
	}

	public Building() {
	}

	public int getBuilding_seq() {
		return building_seq;
	}

	public void setBuilding_seq(int building_seq) {
		this.building_seq = building_seq;
	}

	public int getBuilding_id() {
		return building_id;
	}

	public void setBuilding_id(int building_id) {
		this.building_id = building_id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPort_id() {
		return port_id;
	}

	public void setPort_id(int port_id) {
		this.port_id = port_id;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public void serialize(ByteBuffer bb) {
		bb.putInt(port_id);
		bb.putInt(building_seq);
		bb.putInt(building_id);
		bb.putInt(level);
		bb.putInt(pos);
		bb.putDouble(tick);
		bb.putInt(need_point);
		bb.putInt(current_point);
		bb.putInt(d_point);
		bb.putInt(this.hp);
		bb.putInt(this.revengeSrl);
	}

	public static Building deserialize(ByteBuffer bb) {
		int port_id = bb.getInt();
		int building_seq = bb.getInt();
		int building_id = bb.getInt();
		int level = bb.getInt();
		int pos = bb.getInt();
		long tick = (long) bb.getDouble();
		int need_point = bb.getInt();
		int current_point = bb.getInt();
		int d_point = bb.getInt();
		int hp = bb.getInt();
		int revengeSrl = bb.getInt();

		Building building = new Building();
		building.setBuilding_id(building_id);
		building.setPort_id(port_id);
		building.setBuilding_seq(building_seq);
		building.setLevel(level);
		building.setPos(pos);
		building.setTick(tick);
		building.setNeed_point(need_point);
		building.setCurrent_point(current_point);
		building.setD_point(d_point);
		building.setHp(hp);
		building.setRevengeSrl(revengeSrl);
		return building;
	}

	public static List<Building> deserializeList(ByteBuffer bb) {
		int size = bb.getInt();
		List<Building> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			Building b = Building.deserialize(bb);
			list.add(b);
		}
		return list;
	}

	public Map<String, Object> prepareData() {
		Map<String, Object> values = new HashMap<>();
		values.put("res", 1);
		values.put("push", 1);
		values.put("building_id", this.getBuilding_id());
		values.put("building_seq", this.getBuilding_seq());
		values.put("need_point", this.getNeed_point());
		values.put("current_point", this.getCurrent_point());
		values.put("d_point", this.getD_point());
		values.put("tick", this.getTick());
		values.put("port_id", this.getPort_id());
		values.put("pos", getPos());
		values.put("level", this.getLevel());
		values.put("hp", getHp());
		return values;
	}

	public int getRevengeSrl() {
		return revengeSrl;
	}

	public void setRevengeSrl(int revengeSrl) {
		this.revengeSrl = revengeSrl;
	}
}
