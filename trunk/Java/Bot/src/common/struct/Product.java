package common.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.serialize.Serializable;

public class Product implements Serializable {

	private int building_seq;
	private int item_id;
	private int count;
	private long tick;
	private int need_point;
	private int current_point;
	private int d_point;
	private boolean isFullChecked = false;

	public boolean isFullChecked() {
		return isFullChecked;
	}

	public void setFullChecked(boolean isFullChecked) {
		this.isFullChecked = isFullChecked;
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
		if (current_point < 0) {
			current_point = 0;
		}
		this.current_point = current_point;
	}

	public int getD_point() {
		return d_point;
	}

	public void setD_point(int d_point) {
		this.d_point = d_point;
	}

	public Product() {
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getBuilding_seq() {
		return building_seq;
	}

	public void setBuilding_seq(int building_seq) {
		this.building_seq = building_seq;
	}

	public int getItem_id() {
		return item_id;
	}

	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}

	public void serialize(ByteBuffer bb) {
		bb.putInt(building_seq);
		bb.putInt(item_id);
		bb.putInt(count);
		bb.putDouble(tick);
		bb.putInt(need_point);
		bb.putInt(current_point);
		bb.putInt(d_point);
	}

	public static Product deserialize(ByteBuffer bb) {
		int building_seq = bb.getInt();
		int item_id = bb.getInt();
		int count = bb.getInt();
		long tick = (long) bb.getDouble();
		int need_point = bb.getInt();
		int current_point = bb.getInt();
		int d_point = bb.getInt();
		Product product = new Product();
		product.setBuilding_seq(building_seq);
		product.setItem_id(item_id);
		product.setCount(count);
		product.setTick(tick);
		product.setNeed_point(need_point);
		product.setCurrent_point(current_point);
		product.setD_point(d_point);
		return product;
	}

	public static List<Product> deserializeList(ByteBuffer bb) {
		int size = bb.getInt();
		List<Product> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			Product b = Product.deserialize(bb);
			list.add(b);
		}
		return list;
	}

	public Map<String, Object> prepareData() {
		Map<String, Object> values = new HashMap<>();
		values.put("res", 1);
		values.put("building_seq", this.getBuilding_seq());
		values.put("item_id", this.getItem_id());
		values.put("count", this.getCount());
		values.put("push", 1);
		values.put("tick", this.getTick());
		values.put("need_point", this.getNeed_point());
		values.put("current_point", this.getCurrent_point());
		values.put("d_point", this.getD_point());
		return values;
	}
}
