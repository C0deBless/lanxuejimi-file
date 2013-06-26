package common.struct;

import java.nio.ByteBuffer;

import common.serialize.Serializable;
import common.tools.StringUtil;

public class UserNameAndLevelModel implements Serializable {

	public UserNameAndLevelModel(long userId, String name, int level) {
		super();
		this.userId = userId;
		this.name = name;
		this.level = level;
	}

	private long userId;
	private String name;
	private int level;

	@Override
	public void serialize(ByteBuffer bb) {
		bb.putDouble(userId);
		StringUtil.putString(bb, name);
		bb.putInt(level);
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
