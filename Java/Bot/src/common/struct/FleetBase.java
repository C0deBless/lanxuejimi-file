package common.struct;

import java.awt.Point;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import common.Constants;
import common.serialize.Serializable;

public class FleetBase implements Serializable {
	protected static final int CURRENT_POS = 0;
	protected static final int LAST_POS = 1;

	protected int sPvpTotalWin; // 그림자 pvp 총 승리
	protected int sPvpTotalLose; // 그림자 pvp 총 패배
	protected int sPvpVictory; // 그림자 pvp 현재 연승
	protected int sPvpMaxVictory; // 그림자 pvp 최대 연승
	protected int basepvp_win;
	protected int basepvp_lose;	
	protected Coordinate[] coordinates = new Coordinate[2];
	protected int marketSellCount;
	protected int marketBuyCount;
	protected long lastBasePVPTime;

	public FleetBase() {
		this.coordinates = new Coordinate[2];
		coordinates[0] = new Coordinate("unknown", 0, "unknown", "unknown", "-1,-1");
		coordinates[1] = coordinates[0];
	}

	public FleetBase(FleetBase base) {
		this(base.coordinates, base.getsPvpTotalWin(), base.getsPvpTotalLose(), 
				base.getsPvpVictory(), base.getsPvpMaxVictory(),
				base.getMarketSellCount(), base.getMarketBuyCount(), base.getLastBasePVPTime());
		this.basepvp_win = base.getBasepvp_win();
		this.basepvp_lose = base.getBasepvp_lose();
	}

	public FleetBase(Coordinate[] coordinates, int s_pvp_total_win, int s_pvp_total_lose,
			int s_pvp_victory, int s_pvp_max_victory, int marketSellCount, int marketBuyCount,long lastBasePVPTime) {
		this.coordinates = coordinates;
		this.sPvpTotalWin = s_pvp_total_win;
		this.sPvpTotalLose = s_pvp_total_lose;
		this.sPvpVictory = s_pvp_victory;
		this.sPvpMaxVictory = s_pvp_max_victory;
		this.marketSellCount = marketSellCount;
		this.marketBuyCount = marketBuyCount;
		this.lastBasePVPTime = lastBasePVPTime;
	}

	public int getBasepvp_win() {
		return basepvp_win;
	}

	public void setBasepvp_win(int basepvp_win) {
		this.basepvp_win = basepvp_win;
	}

	public int getBasepvp_lose() {
		return basepvp_lose;
	}

	public void setBasepvp_lose(int basepvp_lose) {
		this.basepvp_lose = basepvp_lose;
	}

	public Coordinate getCurrentPosCoordinate() {
		return coordinates[CURRENT_POS];
	}

	public void setCurrentPosCoordinate(Coordinate currentPos) {
		this.coordinates[CURRENT_POS] = currentPos;
	}

	public Coordinate getLastPosCoordinate() {
		return coordinates[LAST_POS];
	}

	public void setLastPos(Coordinate lastPos) {
		this.coordinates[LAST_POS] = lastPos;
	}

	public String getCurWHR() {
		return coordinates[CURRENT_POS].getWhr();
	}

	public void setCurWHR(String whr) {
		this.coordinates[CURRENT_POS].setWhr(whr);
	}

	public int getCurPortId() {
		return coordinates[CURRENT_POS].getPortId();
	}

	public void setCurPortId(int port_id) {
		this.coordinates[CURRENT_POS].setPortId(port_id);
	}

	public String getCurNS() {
		return this.coordinates[CURRENT_POS].getNs();
	}

	public void setCurNS(String ns) {
		this.coordinates[CURRENT_POS].setNs(ns);
	}

	public String getCurEW() {
		return this.coordinates[CURRENT_POS].getEw();
	}

	public void setCurEW(String ew) {
		this.coordinates[CURRENT_POS].setEw(ew);
	}

	public String getCurPos() {
		return coordinates[CURRENT_POS].getPos();
	}

	public void setCurPos(String pos) {
		this.coordinates[CURRENT_POS].setPos(pos);
	}
	
	public int getLastPortId() {
		return coordinates[LAST_POS].getPortId();
	}

	public void setLastPortId(int port_id) {
		this.coordinates[LAST_POS].setPortId(port_id);
	}

	public String getLastNS() {
		return this.coordinates[LAST_POS].getNs();
	}

	public void setLastNS(String ns) {
		this.coordinates[LAST_POS].setNs(ns);
	}

	public String getLastEW() {
		return this.coordinates[LAST_POS].getEw();
	}

	public void setLastEW(String ew) {
		this.coordinates[LAST_POS].setEw(ew);
	}

	public String getLastPos() {
		return coordinates[LAST_POS].getPos();
	}

	public void setLastPos(String pos) {
		this.coordinates[LAST_POS].setPos(pos);
	}


	public int getsPvpTotalWin() {
		return sPvpTotalWin;
	}

	public void setsPvpTotalWin(int sPvpTotalWin) {
		this.sPvpTotalWin = sPvpTotalWin;
	}

	public int getsPvpTotalLose() {
		return sPvpTotalLose;
	}

	public void setsPvpTotalLose(int sPvpTotalLose) {
		this.sPvpTotalLose = sPvpTotalLose;
	}

	public int getsPvpVictory() {
		return sPvpVictory;
	}

	public void setsPvpVictory(int sPvpVictory) {
		this.sPvpVictory = sPvpVictory;
		if (sPvpMaxVictory < sPvpVictory) {
			sPvpMaxVictory = sPvpVictory;
		}
	}

	public int getsPvpMaxVictory() {
		return sPvpMaxVictory;
	}

	public void setsPvpMaxVictory(int sPvpMaxVictory) {
		this.sPvpMaxVictory = sPvpMaxVictory;
	}

	public void resetFleetPosInfo() {
		this.coordinates[CURRENT_POS].setData(this.coordinates[LAST_POS]);
		this.coordinates[CURRENT_POS].setWhr(Constants.PRT);
	}

	public void storeLastPortPosInfo() {
		this.coordinates[LAST_POS].setData(this.coordinates[CURRENT_POS]);
		this.coordinates[LAST_POS].setWhr(Constants.PRT);
	}

	public Point posInt() {
		String[] arr = this.coordinates[CURRENT_POS].pos.split(",");
		Point p = new Point(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
		return p;
	}

	public Map<String, Object> lastPosInfo() {
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("port_id", this.coordinates[LAST_POS].getPortId());
		info.put("ns", this.coordinates[LAST_POS].getNs());
		info.put("ew", this.coordinates[LAST_POS].getEw());
		info.put("pos", this.coordinates[LAST_POS].getPos());
		return info;
	}
	
	public int getMarketSellCount() {
		return marketSellCount;
	}

	public void setMarketSellCount(int marketSellCount) {
		this.marketSellCount = marketSellCount;
	}

	public int getMarketBuyCount() {
		return marketBuyCount;
	}

	public void setMarketBuyCount(int marketBuyCount) {
		this.marketBuyCount = marketBuyCount;
	}

	public void serialize(ByteBuffer bb) {
		bb.putInt(sPvpTotalWin);
		bb.putInt(sPvpTotalLose);
		bb.putInt(basepvp_lose);
		bb.putInt(basepvp_win);
		bb.putInt(sPvpVictory);
		bb.putInt(sPvpMaxVictory);
		bb.putInt(marketSellCount);
		bb.putInt(marketBuyCount);
		bb.putLong(lastBasePVPTime);
		
		int length = coordinates.length;
		bb.putShort((short) length); // 길이
		for (int i = 0; i < length; i++) {
			coordinates[i].serialize(bb);
		}
	}

	public static FleetBase deserialize(ByteBuffer bb) {
		int total_win = bb.getInt();
		int total_lose = bb.getInt();
		int basepvp_lose = bb.getInt();
		int basepvp_win = bb.getInt();
		int victory = bb.getInt();
		int max_victory = bb.getInt();
		int marketSellCount  = bb.getInt();
		int marketBuyCount  = bb.getInt();
		long lastBasePVPTime = bb.getLong();

		final short length = bb.getShort();
		Coordinate[] coordinates = new Coordinate[length];
		for (int i = 0; i < length; ++i) {
			coordinates[i] = Coordinate.deserialize(bb);
		}
		
		FleetBase fleet = new FleetBase(coordinates, total_win, total_lose,
										victory, max_victory, 
										marketSellCount, marketBuyCount, lastBasePVPTime);
		
		fleet.setBasepvp_lose(basepvp_lose);
		fleet.setBasepvp_win(basepvp_win);
		return fleet;
	}
	
	public boolean isSea() {
		return this.coordinates[CURRENT_POS].isSea();
	}

	public long getLastBasePVPTime() {
		return lastBasePVPTime;
	}

	public void setLastBasePVPTime(long lastBasePVPTime) {
		this.lastBasePVPTime = lastBasePVPTime;
	}
	
	
}