package common.struct;

import java.nio.ByteBuffer;

import common.serialize.Serializable;

public class TickBase implements Serializable {
	int pTime;
	long energyTick;
	long lastCareTime;

	public TickBase(int pTime, long energyTick, long lastCareTime) {
		this.pTime = pTime;
		this.energyTick = energyTick;
		this.lastCareTime = lastCareTime;
	}

	public long getLastCareTime() {
		return lastCareTime;
	}

	public void setLastCareTime(long lastCareTime) {
		this.lastCareTime = lastCareTime;
	}
	
	public long getEnergyTick() {
		return energyTick;
	}

	public void setEnergyTick(long energyTick) {
		this.energyTick = energyTick;
	}

	public int getP_Time() {
		return pTime;
	}

	public void setP_Time(int pTime) {
		this.pTime = pTime;
	}

	public void serialize(ByteBuffer bb) {
		bb.putInt(pTime);
		bb.putLong(energyTick);
		bb.putLong(lastCareTime);		
	}
	
	public static TickBase deserialize(ByteBuffer bb) {
		int pTime = bb.getInt();
		long energyTick = bb.getLong();
		long lastCareTime = bb.getLong();
		return new TickBase(pTime, energyTick, lastCareTime);
	}	
}
