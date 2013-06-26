package common.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import common.serialize.Serializable;
import common.tools.BooleanUtil;
import common.tools.StringUtil;

public class BasePvPModel implements Serializable {

	@JsonIgnore
	UserBase user;
	@JsonIgnore
	BaseInfo baseinfo;
	private long user_id;
	private int portrait_id;
	private String name;
	private int level;
	private int port_id;
	private int attack_rating;
	private boolean isprotected;
	private long p_time;
	private int objectId;

	public int getPortrait_id() {
		return portrait_id;
	}

	public void setPortrait_id(int portrait_id) {
		this.portrait_id = portrait_id;
	}

	public UserBase getUser() {
		return user;
	}

	public BaseInfo getBaseinfo() {
		return baseinfo;
	}

	public void setBaseinfo(BaseInfo baseinfo) {
		this.baseinfo = baseinfo;
	}

	public void setUser(UserBase user) {
		this.user = user;
	}

	@Override
	public void serialize(ByteBuffer buffer) {
		buffer.putDouble(getUser_id());
		buffer.putInt(getPortrait_id());
		buffer.putInt(getLevel());
		buffer.putInt(getPort_id());
		buffer.putInt(getAttack_rating());
		BooleanUtil.putBoolean(buffer, isProtected());
		buffer.putDouble(getP_time());
		StringUtil.putString(buffer, getName());
		buffer.putInt(objectId);
	}

	public static BasePvPModel deserialize(ByteBuffer buffer) {
		long user_id = (long) buffer.getDouble();
		int portrait_id = buffer.getInt();
		int level = buffer.getInt();
		int port_id = buffer.getInt();
		int attack_rating = buffer.getInt();
		boolean isProtected = BooleanUtil.getBoolean(buffer);
		long p_time = (long) buffer.getDouble();
		String name = StringUtil.getString(buffer);
		int objectId = buffer.getInt();
		BasePvPModel model = new BasePvPModel();
		model.setUser_id(user_id);
		model.setLevel(level);
		model.setPort_id(port_id);
		model.setAttack_rating(attack_rating);
		model.setProtected(isProtected);
		model.setP_time(p_time);
		model.setName(name);
		model.setPortrait_id(portrait_id);
		model.setObjectId(objectId);
		return model;
	}

	public String getName() {
		if (this.user != null) {
			return this.user.getCommander().getName();
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAttack_rating() {

		if (this.user != null) {
			return this.user.getCommander().getAttack_rating();
		}
		return attack_rating;
	}

	public void setAttack_rating(int attack_rating) {
		this.attack_rating = attack_rating;
	}

	public long getUser_id() {
		if (this.user != null) {
			return this.user.getCommander().getUser_id();
		}
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public int getLevel() {
		if (this.user != null) {
			return this.user.getCommander().getLevel();
		}
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPort_id() {
		if (this.baseinfo != null) {
			return this.baseinfo.getPort_id();
		}
		return port_id;
	}

	public void setPort_id(int port_id) {
		this.port_id = port_id;
	}

	public boolean isProtected() {
		if (this.baseinfo != null) {
			return this.baseinfo.isProtected();
		}
		return isprotected;
	}

	public void setProtected(boolean isprotected) {
		this.isprotected = isprotected;
	}

	public long getP_time() {
		if (this.baseinfo != null) {
			return this.baseinfo.getP_time();
		}
		return p_time;
	}

	public void setP_time(long p_time) {
		this.p_time = p_time;
	}

	public static List<BasePvPModel> deserializeList(ByteBuffer bb) {
		int size = bb.getInt();
		List<BasePvPModel> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			BasePvPModel b = BasePvPModel.deserialize(bb);
			list.add(b);
		}
		return list;
	}

	public int getObjectId() {
		return objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}
}
