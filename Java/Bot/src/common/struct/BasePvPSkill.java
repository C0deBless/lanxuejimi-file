package common.struct;

import java.nio.ByteBuffer;

import common.serialize.Serializable;

public class BasePvPSkill implements Serializable {

	public enum SkillType {
		SHIP_CAPTAIN, BASE_MANAGER, USER_CHARACTOR
	}

	public BasePvPSkill(SkillType skillType, int skillId) {
		this.type = skillType;
		this.skillId = skillId;
	}

	final private SkillType type;
	final private int skillId;
	private int portId;
	private int shipId;

	public int getPortId() {
		return portId;
	}

	public void setPortId(int portId) {
		this.portId = portId;
	}

	public int getShipId() {
		return shipId;
	}

	public void setShipId(int shipId) {
		this.shipId = shipId;
	}

	public SkillType getType() {
		return type;
	}

	public int getSkillId() {
		return skillId;
	}

	@Override
	public void serialize(ByteBuffer buffer) {
		buffer.putInt(skillId);
		buffer.putInt(portId);
		buffer.putInt(shipId);
		switch (type) {
		case SHIP_CAPTAIN:
			buffer.putShort((short) 1);
			break;
		case BASE_MANAGER:
			buffer.putShort((short) 2);
			break;
		case USER_CHARACTOR:
			buffer.putShort((short) 3);
			break;
		default:
			buffer.putShort((short) 0);
			break;
		}
	}

	public static BasePvPSkill deserialize(ByteBuffer buffer) {
		int skillId = buffer.getInt();
		int portId = buffer.getInt();
		int shipId = buffer.getInt();
		int type = buffer.getShort();
		SkillType skillType = null;
		switch (type) {
		case 1:
			skillType = SkillType.SHIP_CAPTAIN;
			break;
		case 2:
			skillType = SkillType.BASE_MANAGER;
			break;
		case 3:
			skillType = SkillType.USER_CHARACTOR;
			break;
		default:
			skillType = SkillType.USER_CHARACTOR;
			break;
		}
		BasePvPSkill skill = new BasePvPSkill(skillType, skillId);
		skill.setPortId(portId);
		skill.setShipId(shipId);
		return skill;
	}
}
