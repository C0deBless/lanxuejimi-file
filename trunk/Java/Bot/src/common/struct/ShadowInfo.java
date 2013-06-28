package common.struct;

import java.util.ArrayList;
import java.util.List;

public class ShadowInfo {
	static final ShadowInfo DUMMY = new ShadowInfo(new CommanderBase(),
			new FleetBase(), new ArrayList<PCBase>(),
			new ArrayList<ShipBase>(), new ArrayList<UserSkill>());
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

	public static ShadowInfo dummy() {
		return DUMMY;
	}
}
