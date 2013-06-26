package common.struct;


import java.nio.ByteBuffer;

import common.serialize.Serializable;

public class BattleUpdateShip  implements Serializable {
	int shipId;
	int kills;
	int dealtDam;
	
	public BattleUpdateShip( ShipBase ship ) {
		this.shipId = ship.getShip_id();
		this.kills = ship.getKills();
		this.dealtDam = ship.getDealt_dam();
	}

	public BattleUpdateShip(int shipId, int kills, int dealtDam) {
		super();
		this.shipId = shipId;
		this.kills = kills;
		this.dealtDam = dealtDam;
	}

	public int getShipId() {
		return shipId;
	}

	public void setShipId(int shipId) {
		this.shipId = shipId;
	}
	
	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getDealtDam() {
		return dealtDam;
	}

	public void setDealtDam(int dealtDam) {
		this.dealtDam = dealtDam;
	}
	
	public void serialize(ByteBuffer buffer) {		
		buffer.putInt(shipId);
		buffer.putInt(kills);
		buffer.putInt(dealtDam);
	}
	
	public static BattleUpdateShip deserialize(ByteBuffer bb) {
		int shipId = bb.getInt();
		int kills = bb.getInt();
		int dealtDam = bb.getInt();
		
		return new BattleUpdateShip(shipId, kills, dealtDam );
	}
	
}
