package common.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import common.serialize.Serializable;
import common.tools.BooleanUtil;

public class BaseProtectedInfo implements Serializable {

	private long userId;
	private boolean isProtected;
	private long pTime;
	private int win;
	private int lose;
	private int level;

	@Override
	public void serialize(ByteBuffer bb) {
		bb.putDouble(userId);
		BooleanUtil.putBoolean(bb, isProtected);
		bb.putDouble(pTime);
		bb.putInt(win);
		bb.putInt(lose);
		bb.putInt(level);
	}

	public static BaseProtectedInfo deserialize(ByteBuffer bb) {
		long userId = (long) bb.getDouble();
		boolean isProtected = BooleanUtil.getBoolean(bb);
		long pTime = (long) bb.getDouble();
		int win = bb.getInt();
		int lose = bb.getInt();
		int level = bb.getInt();

		BaseProtectedInfo data = new BaseProtectedInfo();
		data.userId = userId;
		data.isProtected = isProtected;
		data.pTime = pTime;
		data.win = win;
		data.lose = lose;
		data.level = level;
		return data;
	}

	public static List<BaseProtectedInfo> deserializeList(ByteBuffer bb) {
		int size = bb.getInt();
		List<BaseProtectedInfo> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			BaseProtectedInfo b = BaseProtectedInfo.deserialize(bb);
			list.add(b);
		}
		return list;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getLose() {
		return lose;
	}

	public void setLose(int lose) {
		this.lose = lose;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public boolean isProtected() {
		return isProtected;
	}

	public void setProtected(boolean isProtected) {
		this.isProtected = isProtected;
	}

	public long getpTime() {
		return pTime;
	}

	public void setpTime(long pTime) {
		this.pTime = pTime;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
