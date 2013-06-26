package common.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import common.serialize.Serializable;
import common.tools.BooleanUtil;

public class BaseInfo implements Serializable {
	private int port_id;
	private int level;
	private int manager;
	private int defence_m;
	private int product_m;
	private int building_m;
	private int crafting_m;
	private int total_win;
	private int total_lose;
	private boolean isProtected;
	private long p_time;
	private int continuousLose;
	private long protectStartTime;

	public boolean isProtected() {
		return isProtected;
	}

	public void setProtected(boolean isProtected) {
		this.isProtected = isProtected;
	}

	public long getP_time() {
		return p_time;
	}

	public void setP_time(long p_time) {
		this.p_time = p_time;
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

	public int getDefence_m() {
		return defence_m;
	}

	public void setDefence_m(int defence_m) {
		this.defence_m = defence_m;
	}

	public int getProduct_m() {
		return product_m;
	}

	public void setProduct_m(int product_m) {
		this.product_m = product_m;
	}

	public int getBuilding_m() {
		return building_m;
	}

	public void setBuilding_m(int building_m) {
		this.building_m = building_m;
	}

	public int getCrafting_m() {
		return crafting_m;
	}

	public void setCrafting_m(int crafting_m) {
		this.crafting_m = crafting_m;
	}

	public int getManager() {
		return manager;
	}

	public void setManager(int manager) {
		this.manager = manager;
	}

	public BaseInfo() {
	}

	public BaseInfo(int port_id, int level, int manager) {
		super();
		this.port_id = port_id;
		this.level = level;
		this.manager = manager;
	}

	public int getPort_id() {
		return port_id;
	}

	public void setPort_id(int port_id) {
		this.port_id = port_id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void serialize(ByteBuffer bb) {
		bb.putInt(port_id);
		bb.putInt(level);
		bb.putInt(this.manager);
		bb.putInt(defence_m);
		bb.putInt(product_m);
		bb.putInt(building_m);
		bb.putInt(crafting_m);
		bb.putInt(this.total_lose);
		bb.putInt(this.total_win);
		bb.putLong(this.p_time);
		BooleanUtil.putBoolean(bb, this.isProtected);
		bb.putInt(this.continuousLose);
		bb.putLong(protectStartTime);
	}

	public static BaseInfo deserialize(ByteBuffer bb) {
		int port_id = bb.getInt();
		int level = bb.getInt();
		int manager = bb.getInt();
		int defence_m = bb.getInt();
		int product_m = bb.getInt();
		int building_m = bb.getInt();
		int crafting_m = bb.getInt();
		int total_lose = bb.getInt();
		int total_win = bb.getInt();
		long p_time = bb.getLong();
		boolean isProtected = BooleanUtil.getBoolean(bb);
		int continuousLose = bb.getInt();
		long protectStartTime = bb.getLong();

		BaseInfo base = new BaseInfo(port_id, level, manager);
		base.setDefence_m(defence_m);
		base.setProduct_m(product_m);
		base.setBuilding_m(building_m);
		base.setCrafting_m(crafting_m);
		base.setTotal_lose(total_lose);
		base.setTotal_win(total_win);
		base.setP_time(p_time);
		base.setProtected(isProtected);
		base.continuousLose = continuousLose;
		base.setProtectStartTime(protectStartTime);
		return base;
	}

	@JsonIgnore
	public List<Integer> getHiredPCs() {
		List<Integer> list = new ArrayList<>(5);
		if (manager > 0)
			list.add(manager);
		if (defence_m > 0)
			list.add(defence_m);
		if (product_m > 0)
			list.add(product_m);
		if (building_m > 0)
			list.add(building_m);
		if (crafting_m > 0)
			list.add(crafting_m);
		return list;
	}

	public static List<BaseInfo> deserializeList(ByteBuffer bb) {
		int size = bb.getInt();
		List<BaseInfo> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			BaseInfo b = BaseInfo.deserialize(bb);
			list.add(b);
		}
		return list;
	}

	public int getContinuousLose() {
		return continuousLose;
	}

	public void resetContinuousLose() {
		this.continuousLose = 0;
	}

	public void addContinuousLose() {
		this.continuousLose++;
	}

	public void setContinuousLose(int lose) {
		this.continuousLose = lose;
	}

	public long getProtectStartTime() {
		return protectStartTime;
	}

	public void setProtectStartTime(long protectStartTime) {
		this.protectStartTime = protectStartTime;
	}
}
