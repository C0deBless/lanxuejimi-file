package common.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Constants;
import common.serialize.Serializable;
import common.tools.StringUtil;

public class UserSkill implements Serializable {
	public static final int EFFECT_DURATION_TIME_CHECK_NONE = -1;
	public static final int EFFECT_DURATION_TIME_CHECK_INFINITY = 0;
	
	private int skill_id;
	private int pc_id;
	private long s_time;
	private int effectDuration;

	public UserSkill(int skill_id, int pc_id, long s_time, int effectDuration) {
		this.skill_id = skill_id;
		this.pc_id = pc_id;
		this.s_time = s_time;
		this.effectDuration = effectDuration;
	}

	public int getPc_id() {
		return pc_id;
	}

	public int getSkill_id() {
		return skill_id;
	}

	public long getS_time() {
		return s_time;
	}

	public void setS_time(long s_time) {
		this.s_time = s_time;
	}
	
	public int getEffectDuration() {
		return effectDuration;
	}
	
	public int addEffectDuration( final int sec ) {
		effectDuration += sec ;
		return effectDuration;
	}

	public void setEffectDuration(int effectDuration) {
		this.effectDuration = effectDuration;
	}

	public void serialize(ByteBuffer bb) {
		bb.putInt(skill_id);
		bb.putInt(pc_id);
		bb.putLong(s_time);
		bb.putInt(effectDuration);
	}

	public static UserSkill deserialize(ByteBuffer bb) {
		int skill_id = bb.getInt();
		int pc_id = bb.getInt();
		long s_time = bb.getLong();
		int effectDuration = bb.getInt();

		return new UserSkill(skill_id, pc_id, s_time, effectDuration);
	}

	public static List<UserSkill> deserializeList(ByteBuffer bb) {
		int size = bb.getInt();
		List<UserSkill> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			UserSkill b = UserSkill.deserialize(bb);
			list.add(b);
		}
		return list;
	}
	
	public Map<String, Object> prepare() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constants.SKILL_ID, this.skill_id);
		map.put(Constants.PC_ID, this.pc_id);
		map.put(Constants.B_TIME, this.s_time);
		map.put(Constants.SEC, this.effectDuration);
		return map;
	}
	
	public void serializeToClient(ByteBuffer bb) {
		bb.putInt(skill_id);
		bb.putInt(pc_id);
		StringUtil.putString(bb, s_time+"");
		bb.putInt(effectDuration);
	}

}
