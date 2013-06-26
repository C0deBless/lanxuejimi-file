package common.struct;

import java.nio.ByteBuffer;

import common.serialize.Serializable;
import common.tools.StringUtil;

public class AllyBase implements Serializable {
	private static final AllyBase DUMMY = new AllyBase(0, 0, 0, new CommanderBase(), new ShipBase(), 0, 0, ""); 
	
	protected long allyId;
	protected long lastLogin;
	protected long lastLogout;
	protected long lastBattleSupport;
	protected CommanderBase commander;
	protected ShipBase flagShip;
	protected int guildSRL;
	protected String guildName;

	public AllyBase(AllyBase base) {
		this(base.getAllyId(), base.getLastLogin(), base.getLastLogout(),
				 base.getCommander(), base.getFlagShip(),
				base.getLastBattleSupport(), base.getGuildSRL(), base.getGuildName() );
	}

	public AllyBase(long allyId, long lastLogin, long lastLogout,
			CommanderBase commander, ShipBase flagShip, long lastBattleSupport, int guildSRL, String guildName) {
		this.allyId = allyId;
		this.lastLogin = lastLogin;
		this.lastLogout = lastLogout;
		this.commander = commander;
		this.flagShip = flagShip;
		this.lastBattleSupport = lastBattleSupport;
		this.guildSRL = guildSRL;
		this.guildName = guildName;
	}
	
	public int getLevel() {
		return this.commander.getLevel();
	}
	
	public void setName(String name) {
		this.commander.setName(name);
	}
	

	public int getGuildSRL() {
		return guildSRL;
	}

	public void setGuildSRL(int guildSRL) {
		this.guildSRL = guildSRL;
	}

	public String getGuildName() {
		return guildName;
	}

	public void setGuildName(String guildName) {
		this.guildName = guildName;
	}

	public long getAllyId() {
		return allyId;
	}

	public void setAllyId(long allyId) {
		this.allyId = allyId;
	}

	public long getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(long lastLogin) {
		this.lastLogin = lastLogin;
	}

	public long getLastLogout() {
		return lastLogout;
	}

	public void setLastLogout(long lastLogout) {
		this.lastLogout = lastLogout;
	}

	public CommanderBase getCommander() {
		return commander;
	}

	public void setCommander(CommanderBase commander) {
		this.commander = commander;
	}

	public ShipBase getFlagShip() {
		return flagShip;
	}

	public void setFlagShip(ShipBase flagShip) {
		this.flagShip = flagShip;
	}

	@Override
	public void serialize(ByteBuffer buffer) {
		buffer.putLong(this.allyId);
		buffer.putLong(this.lastLogin);
		buffer.putLong(this.lastLogout);
		buffer.putLong(this.lastBattleSupport);
		this.commander.serialize(buffer);
		this.flagShip.serialize(buffer);
		buffer.putInt(this.guildSRL);
		if( null == this.guildName ) {
			this.guildName = "";
		}
		StringUtil.putString(buffer, guildName);
	}

	public static AllyBase deserialize(ByteBuffer bb) {
		long allyId = bb.getLong();
		long lastLogin = bb.getLong();
		long lastLogout = bb.getLong();
		long lastBattleSupport = bb.getLong();
		CommanderBase commander = CommanderBase.deserialize(bb);
		ShipBase flagShip = ShipBase.deserialize(bb);
		int guildSRL = bb.getInt();
		String guildName = StringUtil.getString(bb);

		return new AllyBase(allyId, lastLogin, lastLogout, commander, flagShip, lastBattleSupport, guildSRL, guildName);
	}

	public long getLastBattleSupport() {
		return lastBattleSupport;
	}

	public void setLastBattleSupport(long lastBattleSupport) {
		this.lastBattleSupport = lastBattleSupport;
	}

	public int getEmblem() {
		return commander.getCountry();
	}

	public String getName() {
		return this.commander.getName();
	}

	public int getPortrait_id() {
		if (allyId < 1000) {
			return 791001;
		}
		return this.commander.getPortrait_id();
	}
	
	public static AllyBase dummy() {
		return DUMMY;
	}
	
	public int getSkill_b1() {
		return commander.getSkill_b2();
	}
	
	public int getSkill_b2() {
		return commander.getSkill_b2();
	}
}
