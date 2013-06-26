package common.struct;

import java.nio.ByteBuffer;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import common.serialize.Serializable;
import common.tools.StringUtil;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommanderBase implements Serializable {

	protected long user_id;
	protected int energy;
	protected int gold;
	protected int country;
	protected int level;
	protected int combat;
	protected int sailing;
	protected int luck;
	protected int knowledge;
	protected int xp;
	protected int sp;
	protected int bonus_stat;
	protected int pc_slot;
	protected int ship_slot;
	protected int portrait_id;
	protected int license;
	protected int skill_b1;
	protected int skill_b2;
	protected int skill_p1;
	protected int skill_p2;
	protected int skill_p3;
	protected int required_xp;
	protected String name = "";
	protected int attack_rating;
	protected int tutorial;
	protected int guild_srl;
	protected int inventorySlot;
	protected int maxEnergy;

	public void setMaxEnergy(int maxEnergy) {
		this.maxEnergy = maxEnergy;
	}

	public int getMaxEnergy() {
		return this.maxEnergy;
	}

	public int getInventorySlot() {
		return inventorySlot;
	}

	public void setInventorySlot(int inventorySlot) {
		this.inventorySlot = inventorySlot;
	}

	public int increaseInventorySlot() {
		this.inventorySlot++;
		return this.inventorySlot;
	}

	public int getGuild_srl() {
		return guild_srl;
	}

	public void setGuild_srl(int guild_srl) {
		this.guild_srl = guild_srl;
	}

	public int getTutorial() {
		return tutorial;
	}

	public void setTutorial(int tutorial) {
		this.tutorial = tutorial;
	}

	public int getAttack_rating() {
		return attack_rating;
	}

	public void setAttack_rating(int attack_rating) {
		this.attack_rating = attack_rating;
	}

	public int getRequired_xp() {
		return required_xp;
	}

	public void setRequired_xp(int required_xp) {
		this.required_xp = required_xp;
	}

	public CommanderBase() {
	}

	public CommanderBase(long user_id, int energy, int gold, int country,
			String name, int level, int combat, int sailing, int luck, int xp,
			int pcSlot, int portrait_id, int license,
			int guild_srl, int invenSlot) {

		this.user_id = user_id;
		this.energy = energy;
		this.gold = gold;
		this.country = country;
		this.name = name;
		this.level = level;
		this.combat = combat;
		this.sailing = sailing;
		this.luck = luck;
		this.xp = xp;
		this.pc_slot = pcSlot;
		this.portrait_id = portrait_id;
		this.license = license;
		this.guild_srl = guild_srl;
		this.inventorySlot = invenSlot;
	}

	public CommanderBase(long user_id, int energy, int gold, int country,
			int level, int combat, int sailing, int luck, int knowledge,
			int xp, int sp, int bonus_stat, int pc_slot, int ship_slot,
			int portrait_id, int license, int skill_b1, int skill_b2,
			int skill_p1, int skill_p2, int skill_p3,
			int required_xp, String name, int guild_srl,
			int inventorySlot) {
		this.user_id = user_id;
		this.energy = energy;
		this.gold = gold;
		this.country = country;
		this.level = level;
		this.combat = combat;
		this.sailing = sailing;
		this.luck = luck;
		this.knowledge = knowledge;
		this.xp = xp;
		this.sp = sp;
		this.bonus_stat = bonus_stat;
		this.pc_slot = pc_slot;
		this.ship_slot = ship_slot;
		this.portrait_id = portrait_id;
		this.license = license;
		this.skill_b1 = skill_b1;
		this.skill_b2 = skill_b2;
		this.skill_p1 = skill_p1;
		this.skill_p2 = skill_p2;
		this.skill_p3 = skill_p3;
		this.required_xp = required_xp;
		this.name = name;
		this.guild_srl = guild_srl;
		this.inventorySlot = inventorySlot;
	}

	public int getShip_slot() {
		return ship_slot;
	}

	public void setShip_slot(int ship_slot) {
		this.ship_slot = ship_slot;
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

	public int getSkill_p3() {
		return skill_p3;
	}

	public void setSkill_p3(int skill_p3) {
		this.skill_p3 = skill_p3;
	}

	public int getSp() {
		return sp;
	}

	public void setSp(int sp) {
		if (sp < 0)
			sp = 0;
		this.sp = sp;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		if (energy < 0)
			energy = 0;
		this.energy = energy;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		if (gold < 0) {
			gold = 0;
		}

		this.gold = gold;
	}

	public int getCountry() {
		return country;
	}

	public void setCountry(int country) {
		this.country = country;
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

	public int getBaseCombat() {
		return combat;
	}

	public int getCombat() {
		return combat;
	}

	public void setCombat(int combat) {
		this.combat = combat;
	}

	public void addCombat(int combat) {
		
		this.combat += combat;
	}

	public int getBaseSailing() {
		return sailing;
	}

	public int getSailing() {
		return sailing;
	}

	public void setSailing(int sailing) {
		this.sailing = sailing;
	}

	public void addSailing(int sailing) {
		this.sailing += sailing;
	}

	public int getBaseLuck() {
		return luck;
	}

	public int getLuck() {
		return luck;
	}

	public void setLuck(int luck) {
		this.luck = luck;
	}

	public void addLuck(int luck) {
		this.luck += luck;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public int getPc_slot() {
		return pc_slot;
	}

	public void setPc_slot(int pc_slot) {
		this.pc_slot = pc_slot;
	}

	public int getBonus_stat() {
		return bonus_stat;
	}

	public void setBonus_stat(int bonus_stat) {
		this.bonus_stat = bonus_stat;
	}

	public void addBonus_stat(int d) {
		this.bonus_stat += d;
	}

	public void addPcSlot(int bouns) {
		this.pc_slot += bouns;
	}

	public int addGold(int gold) {
		setGold(this.gold + gold);
		return this.getGold();
	}

	public int getPortrait_id() {
		return portrait_id;
	}

	public void setPortrait_id(int portrait_id) {
		this.portrait_id = portrait_id;
	}

	public void addXp(int xp) {
		this.xp += xp;
	}

	public int getLicense() {
		return license;
	}

	public void setLicense(int license) {
		this.license = license;
	}

	public int getBaseKnowledge() {
		return knowledge;
	}

	public int getKnowledge() {
		return knowledge;
	}

	public void addKnowledge(int knowledge) {
		this.knowledge += knowledge;
	}

	public void setKnowledge(int knowledge) {
		this.knowledge = knowledge;
	}

	public int increasePcSlot() {
		this.pc_slot++;
		return this.pc_slot;
	}

	public void serialize(ByteBuffer bb) {
		bb.putLong(user_id);
		bb.putInt(energy);
		bb.putInt(gold);
		bb.putInt(country);
		bb.putInt(level);
		bb.putInt(combat);
		bb.putInt(sailing);
		bb.putInt(luck);
		bb.putInt(knowledge);
		bb.putInt(xp);
		bb.putInt(sp);
		bb.putInt(bonus_stat);
		bb.putInt(pc_slot);
		bb.putInt(ship_slot);
		bb.putInt(portrait_id);
		bb.putInt(license);
		bb.putInt(skill_b1);
		bb.putInt(skill_b2);
		bb.putInt(skill_p1);
		bb.putInt(skill_p2);
		bb.putInt(skill_p3);
		bb.putInt(required_xp);
		bb.putInt(attack_rating);
		bb.putInt(tutorial);
		bb.putInt(guild_srl);
		bb.putInt(inventorySlot);
		StringUtil.putString(bb, name);
	}

	public static CommanderBase deserialize(ByteBuffer bb) {
		long user_id = bb.getLong();
		int energy = bb.getInt();
		int gold = bb.getInt();
		int country = bb.getInt();
		int level = bb.getInt();
		int combat = bb.getInt();
		int sailing = bb.getInt();
		int luck = bb.getInt();
		int knowledge = bb.getInt();
		int xp = bb.getInt();
		int sp = bb.getInt();
		int bonus_stat = bb.getInt();
		int pc_slot = bb.getInt();
		int ship_slot = bb.getInt();
		int portrait_id = bb.getInt();
		int license = bb.getInt();
		int skill_b1 = bb.getInt();
		int skill_b2 = bb.getInt();
		int skill_p1 = bb.getInt();
		int skill_p2 = bb.getInt();
		int skill_p3 = bb.getInt();
		int required_xp = bb.getInt();
		int attack_rating = bb.getInt();
		int tutorial = bb.getInt();
		int guild_srl = bb.getInt();
		int inventorySlot = bb.getInt();
		String name = StringUtil.getString(bb);

		CommanderBase commander = new CommanderBase(user_id, energy, gold,
				country, level, combat, sailing, luck, knowledge, xp, sp,
				bonus_stat, pc_slot, ship_slot, portrait_id, license, skill_b1,
				skill_b2, skill_p1, skill_p2, skill_p3, required_xp,
				name, guild_srl, inventorySlot);
		commander.setAttack_rating(attack_rating);
		commander.setTutorial(tutorial);
		return commander;
	}	

	public void setGuildName(String name) {

	}

	public String getGuildName() {
		return "empty";
	}
}
