package common.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import common.exception.ListSizeOverException;
import common.serialize.Serializable;

public class AllyHelpModel implements Serializable {

	private long targetUserId;
	private int type;
	private long time;

	public AllyHelpModel(long targetUserId, int type, long time) {
		this.targetUserId = targetUserId;
		this.type = type;
		this.time = time;
	}

	@Override
	public void serialize(ByteBuffer bb) {
		bb.putLong(targetUserId);
		bb.putInt(type);
		bb.putLong(time);
	}

	public static AllyHelpModel deserialize(ByteBuffer bb) {
		long targetUserId = bb.getLong();
		int type = bb.getInt();
		long time = bb.getLong();
		AllyHelpModel data = new AllyHelpModel(targetUserId, type, time);
		return data;
	}

	public static List<AllyHelpModel> deserializeList(ByteBuffer bb) {
		int size = bb.getInt();
		if (size > Short.MAX_VALUE) {
			throw new ListSizeOverException();
		}
		List<AllyHelpModel> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			list.add(AllyHelpModel.deserialize(bb));
		}
		return list;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(long targetUserId) {
		this.targetUserId = targetUserId;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
