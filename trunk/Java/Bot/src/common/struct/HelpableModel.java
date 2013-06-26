package common.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import common.exception.ListSizeOverException;
import common.serialize.Serializable;
import common.tools.StringUtil;

public class HelpableModel implements Serializable {

	private long csn;
	private String name;
	private int level;

	@Override
	public void serialize(ByteBuffer bb) {
		bb.putDouble(csn);
		StringUtil.putString(bb, name);
		bb.putInt(level);
	}

	public static HelpableModel deserialize(ByteBuffer bb) {
		long csn = (long) bb.getDouble();
		String name = StringUtil.getString(bb);
		int level = bb.getInt();
		HelpableModel data = new HelpableModel();
		data.csn = csn;
		data.name = name;
		data.level = level;
		return data;
	}

	public static List<HelpableModel> deserializeList(ByteBuffer bb) {
		int size = bb.getInt();
		if (size > Short.MAX_VALUE) {
			throw new ListSizeOverException();
		}
		List<HelpableModel> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			HelpableModel model = HelpableModel.deserialize(bb);
			list.add(model);
		}
		return list;
	}

	public long getCsn() {
		return csn;
	}

	public void setCsn(long csn) {
		this.csn = csn;
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
