package common.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import common.serialize.Serializable;
import common.tools.StringUtil;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PCBase implements Serializable {

	protected int pcId;
	protected int id;
	protected int combat;
	protected int sailing;
	protected int luck;
	protected int xp;
	protected int level;
	protected int sp;
	protected int max_sp;
	protected int knowledge;
	protected int skill_point;
	protected int skill_b1;
	protected int skill_b2;
	protected int skill_p1;
	protected int skill_p2;
	protected int skill_slot;
	protected String dismiss;
	protected int skill_tmp;

	public PCBase() {
	}

	public PCBase(int pcId, int id, int combat, int sailing, int luck, int xp,
			int level, String dismiss, int sp, int max_sp, int knowledge,
			int skill_point, int skill_b1, int skill_b2, int skill_p1,
			int skill_p2, int skill_slot, int skill_tmp) {
		super();
		this.pcId = pcId;
		this.id = id;
		this.combat = combat;
		this.sailing = sailing;
		this.luck = luck;
		this.xp = xp;
		this.level = level;
		this.dismiss = dismiss;
		this.sp = sp;
		this.max_sp = max_sp;
		this.knowledge = knowledge;
		this.skill_point = skill_point;
		this.skill_b1 = skill_b1;
		this.skill_b2 = skill_b2;
		this.skill_p1 = skill_p1;
		this.skill_p2 = skill_p2;
		this.skill_slot = skill_slot;
		this.skill_tmp = skill_tmp;
	}

	public PCBase(PCBase base) {
		this(base.getPcId(), base.getId(), base.getCombat(), base.getSailing(),
				base.getLuck(), base.getXp(), base.getLevel(), base
						.getDismiss(), base.getSp(), base.getMax_sp(), base
						.getKnowledge(), base.getSkill_point(), base
						.getSkill_b1(), base.getSkill_b2(), base.getSkill_p1(),
				base.getSkill_p2(), base.getSkill_slot(), base.getSkill_tmp());
	}

	public PCBase(int pcId, int id, int combat, int sailing, int luck, int xp,
			int level, int sp, int max_sp, int knowledge, int skill_point,
			int skill_b1, int skill_b2, int skill_p1, int skill_p2,
			int skill_slot, String dismiss, int skill_tmp) {
		super();
		this.pcId = pcId;
		this.id = id;
		this.combat = combat;
		this.sailing = sailing;
		this.luck = luck;
		this.xp = xp;
		this.level = level;
		this.sp = sp;
		this.max_sp = max_sp;
		this.knowledge = knowledge;
		this.skill_point = skill_point;
		this.skill_b1 = skill_b1;
		this.skill_b2 = skill_b2;
		this.skill_p1 = skill_p1;
		this.skill_p2 = skill_p2;
		this.skill_slot = skill_slot;
		this.dismiss = dismiss;
		this.skill_tmp = skill_tmp;
	}

	public int getSkill_tmp() {
		return skill_tmp;
	}

	public void setSkill_tmp(int skill_tmp) {
		this.skill_tmp = skill_tmp;
	}

	public int getPcId() {
		return pcId;
	}

	public void setPcId(int pcId) {
		this.pcId = pcId;
	}

	public int getCombat() {
		return combat;
	}

	public int getSailing() {
		return sailing;
	}

	public int getLuck() {
		return luck;
	}

	public int getKnowledge() {
		return knowledge;
	}

	public void setMax_sp(int max_sp) {
		this.max_sp = max_sp;
	}

	public int getSkill_slot() {
		return skill_slot;
	}

	public void setSkill_slot(int skill_slot) {
		this.skill_slot = skill_slot;
	}

	public int getSkill_b1() {
		return skill_b1;
	}

	public void setSkill_b1(int skill_b1) {
		this.skill_b1 = skill_b1;
	}

	public int getSkill_b2() {
		return skill_b2;
	}

	public void setSkill_b2(int skill_b2) {
		this.skill_b2 = skill_b2;
	}

	public int getSkill_p1() {
		return skill_p1;
	}

	public void setSkill_p1(int skill_p1) {
		this.skill_p1 = skill_p1;
	}

	public int getSkill_p2() {
		return skill_p2;
	}

	public void setSkill_p2(int skill_p2) {
		this.skill_p2 = skill_p2;
	}

	public int getSkill_point() {
		return skill_point;
	}

	public void setSkill_point(int skill_point) {
		this.skill_point = skill_point;
	}

	public int getSp() {
		return sp;
	}

	public int getMax_sp() {
		return this.max_sp;
	}

	public void setSp(int sp) {
		this.sp = sp;
	}

	public int getBaseKnowledge() {
		return this.knowledge;
	}

	public void setKnowledge(int knowledge) {
		this.knowledge = knowledge;
	}

	public int getPc_id() {
		return pcId;
	}

	public void setPc_id(int pc_id) {
		this.pcId = pc_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCombat(int combat) {
		this.combat = combat;
	}

	public void setSailing(int sailing) {
		this.sailing = sailing;
	}

	public void setLuck(int luck) {
		this.luck = luck;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getDismiss() {
		return dismiss;
	}

	public void setDismiss(String dismiss) {
		this.dismiss = dismiss;
	}

	public boolean firePossible() {
		return this.dismiss.equalsIgnoreCase("y");
	}

	public void serialize(ByteBuffer bb) {
		bb.putInt(pcId);
		bb.putInt(id);
		bb.putInt(combat);
		bb.putInt(sailing);
		bb.putInt(luck);
		bb.putInt(xp);
		bb.putInt(level);
		bb.putInt(sp);
		bb.putInt(max_sp);
		bb.putInt(knowledge);
		bb.putInt(skill_point);
		bb.putInt(skill_b1);
		bb.putInt(skill_b2);
		bb.putInt(skill_p1);
		bb.putInt(skill_p2);
		bb.putInt(skill_slot);
		bb.putInt(skill_tmp);
		StringUtil.putString(bb, dismiss);
	}

	public static PCBase deserialize(ByteBuffer bb) {
		int pcId = bb.getInt();
		int id = bb.getInt();
		int combat = bb.getInt();
		int sailing = bb.getInt();
		int luck = bb.getInt();
		int xp = bb.getInt();
		int level = bb.getInt();
		int sp = bb.getInt();
		int max_sp = bb.getInt();
		int knowledge = bb.getInt();
		int skill_point = bb.getInt();
		int skill_b1 = bb.getInt();
		int skill_b2 = bb.getInt();
		int skill_p1 = bb.getInt();
		int skill_p2 = bb.getInt();
		int skill_slot = bb.getInt();
		int skill_tmp = bb.getInt();
		String dismiss = StringUtil.getString(bb);

		return new PCBase(pcId, id, combat, sailing, luck, xp, level, sp,
				max_sp, knowledge, skill_point, skill_b1, skill_b2, skill_p1,
				skill_p2, skill_slot, dismiss, skill_tmp);
	}

	public int addSp(int mp) {
		this.sp += sp;
		if (0 > this.sp) {
			this.sp = 0;
		} else if (getMax_sp() < this.sp) {
			this.sp = getMax_sp();
		}
		return sp;
	}

	public static List<PCBase> deserializeList(ByteBuffer bb) {
		int size = bb.getInt();
		List<PCBase> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			PCBase b = PCBase.deserialize(bb);
			list.add(b);
		}
		return list;
	}
	

	public List<Integer> getEquipedSkill() {
		List<Integer> skills = new ArrayList<>(4);
		if (this.getSkill_b1() > 0) {
			skills.add(this.getSkill_b1());
		}
		if (this.getSkill_b2() > 0) {
			skills.add(this.getSkill_b2());
		}
		if (this.getSkill_p1() > 0) {
			skills.add(this.getSkill_p1());
		}
		if (this.getSkill_p2() > 0) {
			skills.add(this.getSkill_p2());
		}
		return skills;

	}
}
