package common.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import common.serialize.Serializable;
import common.tools.BooleanUtil;
import common.tools.StringUtil;

public class EnemyModel implements Serializable {

	private long user_id;
	private int portrait_id;
	private String name = "";
	private boolean isProtected;
	private int port_id;

	public int getPortrait_id() {
		return portrait_id;
	}

	public void setPortrait_id(int portrait_id) {
		this.portrait_id = portrait_id;
	}

	public int getPort_id() {
		return port_id;
	}

	public void setPort_id(int port_id) {
		this.port_id = port_id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long id) {
		this.user_id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isProtected() {
		return isProtected;
	}

	public void setProtected(boolean isProtected) {
		this.isProtected = isProtected;
	}

	@Override
	public void serialize(ByteBuffer buffer) {
		buffer.putLong(user_id);
		StringUtil.putString(buffer, name);
		BooleanUtil.putBoolean(buffer, isProtected);
		buffer.putInt(port_id);
		buffer.putInt(portrait_id);
	}

	public static EnemyModel deserialize(ByteBuffer buffer) {
		long id = buffer.getLong();
		String name = StringUtil.getString(buffer);
		boolean isProtected = BooleanUtil.getBoolean(buffer);
		int portId = buffer.getInt();
		int portrait_id = buffer.getInt();
		EnemyModel model = new EnemyModel();
		model.setUser_id(id);
		model.setPort_id(portId);
		model.setProtected(isProtected);
		model.setName(name);
		model.setPortrait_id(portrait_id);
		return model;
	}

	private static final EnemyModel EMPTY_INSTANCE = new EnemyModel();

	public static EnemyModel emptyModel() {
		return EMPTY_INSTANCE;
	}

	public static List<EnemyModel> deserializeList(ByteBuffer bb) {
		int size = bb.getInt();
		List<EnemyModel> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			EnemyModel b = EnemyModel.deserialize(bb);
			list.add(b);
		}
		return list;
	}
}
