package common.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import common.serialize.Serializable;
import common.tools.StringUtil;

public class PubNPC implements Serializable {
	long userId;
	int portId;
	int id;
	int combat;
	int sailing;
	int luck;
	int knowledge;
	long c_time;
	String hire;
	List<Integer> skill;
	
	public PubNPC() {		
	}

	public PubNPC(long userId, int portId, int id, int combat, int sailing,
			int luck, int knowledge, List<Integer> skill, long c_time,
			String hire) {
		this.userId = userId;
		this.portId = portId;
		this.id = id;
		this.combat = combat;
		this.sailing = sailing;
		this.luck = luck;
		this.knowledge = knowledge;
		this.skill = skill;
		this.c_time = c_time;
		this.hire = hire;
	}

	public long getUser_id() {
		return userId;
	}

	public void setUser_id(long userId) {
		this.userId = userId;
	}

	public int getPort_id() {
		return portId;
	}

	public void setPort_id(int portId) {
		this.portId = portId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCombat() {
		return combat;
	}

	public void setCombat(int combat) {
		this.combat = combat;
	}

	public int getSailing() {
		return sailing;
	}

	public void setSailing(int sailing) {
		this.sailing = sailing;
	}

	public int getLuck() {
		return luck;
	}

	public void setLuck(int luck) {
		this.luck = luck;
	}

	public int getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(int knowledge) {
		this.knowledge = knowledge;
	}

	public long getC_time() {
		return c_time;
	}

	public void setC_time(long c_time) {
		this.c_time = c_time;
	}

	public String getHire() {
		return hire;
	}

	public void setHire(String hire) {
		this.hire = hire;
	}

	public List<Integer> getSkill() {
		return skill;
	}

	public void setSkill(List<Integer> skill) {
		this.skill = skill;
	}

	@Override
	public void serialize(ByteBuffer buffer) {
		buffer.putLong(userId);
		buffer.putInt(portId);
		buffer.putInt(id);
		buffer.putInt(combat);
		buffer.putInt(sailing);
		buffer.putInt(luck);
		buffer.putInt(knowledge);
		buffer.putLong(c_time);
		StringUtil.putString(buffer, hire);

		buffer.putShort((short) skill.size()); // 길이
		for (int i = 0; i < skill.size(); i++) {
			buffer.putInt(skill.get(i));
		}
	}

	public static PubNPC deserialize(ByteBuffer bb) {
		long userId = bb.getLong();
		int portId = bb.getInt();
		int id = bb.getInt();
		int combat = bb.getInt();
		int sailing = bb.getInt();
		int luck = bb.getInt();
		int knowledge = bb.getInt();
		long c_time = bb.getLong();
		String hire = StringUtil.getString(bb);

		final short length = bb.getShort();
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < length; ++i) {
			list.add(bb.getInt());
		}

		return new PubNPC(userId, portId, id, combat, sailing, luck, knowledge,
				list, c_time, hire);

	}

	public static List<PubNPC> deserializeList(ByteBuffer bb) {
		int size = bb.getInt();
		List<PubNPC> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			PubNPC b = PubNPC.deserialize(bb);
			list.add(b);
		}
		return list;
	}
}
