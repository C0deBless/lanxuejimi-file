package common.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import common.serialize.Serializable;

public class BattleCategory implements Serializable {

	public static final int PVP_CATEGORY_NORMAL = 0;
	public static final int PVP_CATEGORY_MIDDLE = 1;
	public static final int PVP_CATEGORY_HARD = 2;

	private int fieldId;
	private int category;

	public int getFieldId() {
		return fieldId;
	}

	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	@Override
	public void serialize(ByteBuffer bb) {
		bb.putInt(fieldId);
		bb.putInt(category);
	}

	public static BattleCategory deserialize(ByteBuffer bb) {
		int fieldId = bb.getInt();
		int category = bb.getInt();
		BattleCategory data = new BattleCategory();
		data.fieldId = fieldId;
		data.category = category;
		return data;
	}

	public static List<BattleCategory> deserializeList(ByteBuffer bb) {
		int size = bb.getInt();
		List<BattleCategory> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			BattleCategory bc = deserialize(bb);
			list.add(bc);
		}
		return list;
	}
}
