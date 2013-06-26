package common.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import common.serialize.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FactionBase implements Serializable {

	protected int f_id;
	protected int relationship;
	protected int stamp_count;
	protected long f_time;

	public FactionBase() {
	}

	public FactionBase(int f_id, int relationship, int stamp_count, long f_time) {
		super();
		this.f_id = f_id;
		this.relationship = relationship;
		this.stamp_count = stamp_count;
		this.f_time = f_time;
	}

	public int getF_id() {
		return f_id;
	}

	public void setF_id(int f_id) {
		this.f_id = f_id;
	}

	public int getRelationship() {
		return relationship;
	}

	public void setRelationship(int relationship) {
		this.relationship = relationship;
	}

	public int getStamp_count() {
		return stamp_count;
	}

	public void setStamp_count(int stamp_count) {
		this.stamp_count = stamp_count;
	}

	public long getF_time() {
		return f_time;
	}

	public void setF_time(long f_time) {
		this.f_time = f_time;
	}

	public void serialize(ByteBuffer bb) {
		bb.putInt(f_id);
		bb.putInt(relationship);
		bb.putInt(stamp_count);
		bb.putLong(f_time);
	}

	public static FactionBase deserialize(ByteBuffer bb) {
		int f_id = bb.getInt();
		int relationship = bb.getInt();
		int stamp_count = bb.getInt();
		long f_time = bb.getLong();

		return new FactionBase(f_id, relationship, stamp_count, f_time);
	}

	public static List<FactionBase> deserializeList(ByteBuffer bb) {
		int size = bb.getInt();
		List<FactionBase> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			FactionBase b = FactionBase.deserialize(bb);
			list.add(b);
		}
		return list;
	}
}
