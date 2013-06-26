package common.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import common.serialize.Serializable;

public class ShadowInfo implements Serializable {
	static final ShadowInfo  DUMMY = new ShadowInfo( new CommanderBase(), new FleetBase(), 
														new ArrayList<PCBase>(),
														new ArrayList<ShipBase>(),
														new ArrayList<UserSkill>());
	CommanderBase commander;
	FleetBase fleet;
	List<PCBase> pc = new ArrayList<>();
	List<ShipBase> ship = new ArrayList<>();
	List<UserSkill> skills = new ArrayList<>();

	public ShadowInfo(CommanderBase commander, FleetBase fleet,
			List<PCBase> pc, List<ShipBase> ship, List<UserSkill> skills) {
		super();
		this.commander = commander;
		this.fleet = fleet;
		this.pc = pc;
		this.ship = ship;
		this.skills = skills;
	}

	public void setSkills(List<UserSkill> skills) {
		this.skills = skills;
	}

	public List<UserSkill> getSkills() {
		return skills;
	}

	public CommanderBase getCommander() {
		return commander;
	}

	public void setCommander(CommanderBase commander) {
		this.commander = commander;
	}

	public FleetBase getFleet() {
		return fleet;
	}

	public void setFleet(FleetBase fleet) {
		this.fleet = fleet;
	}

	public List<PCBase> getPc() {
		return pc;
	}

	public void setPc(List<PCBase> pcBases) {
		if (null == pcBases) {
			return;
		}
		this.pc = pcBases;
	}

	public List<ShipBase> getShip() {
		return ship;
	}

	public void setShip(List<ShipBase> ship) {
		if (null == ship) {
			return;
		}
		this.ship = ship;
	}

	public long getUserId() {
		return commander.getUser_id();
	}

	public void serialize(ByteBuffer bb) {
		commander.serialize(bb);
		fleet.serialize(bb);

		bb.putShort((short) pc.size()); // 길이
		for (int i = 0; i < pc.size(); i++) {
			PCBase obj = pc.get(i);
			obj.serialize(bb);
		}

		bb.putShort((short) ship.size()); // 길이
		for (int i = 0; i < ship.size(); i++) {
			ShipBase obj = ship.get(i);
			obj.serialize(bb);
		}

		bb.putShort((short) skills.size()); // 길이
		for (int i = 0; i < skills.size(); i++) {
			UserSkill obj = skills.get(i);
			obj.serialize(bb);
		}
	}

	public static ShadowInfo deserialize(ByteBuffer bb) {
		CommanderBase commander = CommanderBase.deserialize(bb);
		FleetBase fleet = FleetBase.deserialize(bb);

		final short length = bb.getShort();
		List<PCBase> pcList = new ArrayList<>();
		for (int i = 0; i < length; ++i) {
			PCBase t = PCBase.deserialize(bb);
			pcList.add(t);
		}

		final short shipLength = bb.getShort();
		List<ShipBase> shipList = new ArrayList<>();
		for (int i = 0; i < shipLength; ++i) {
			ShipBase t = ShipBase.deserialize(bb);
			shipList.add(t);
		}

		final short skillLength = bb.getShort();
		List<UserSkill> skillList = new ArrayList<>();
		for (int i = 0; i < skillLength; ++i) {
			UserSkill t = UserSkill.deserialize(bb);
			skillList.add(t);
		}

		return new ShadowInfo(commander, fleet, pcList, shipList, skillList);

	}

	public static List<ShadowInfo> deserializeList(ByteBuffer bb) {
		int size = bb.getInt();
		List<ShadowInfo> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			ShadowInfo b = ShadowInfo.deserialize(bb);
			list.add(b);
		}
		return list;
	}

	public static ShadowInfo dummy() { 
		return DUMMY;
	}
}
