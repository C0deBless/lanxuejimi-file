package common.struct;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Column;
import common.Constants;
import common.serialize.Serializable;

public class Cash implements Serializable {
	static final Logger logger = LoggerFactory.getLogger(Cash.class);
	
	private int cash;
	private int buy;
	private int free;

	public Cash() {

	}

	public Cash(int cash, int buy, int free) {
		this.cash = cash;
		this.buy = buy;
		this.free = free;
	}

	public Cash(ResultSet rs) throws SQLException {
		if (null != rs) {
			this.cash = rs.getInt(Column.CASH);
			this.buy = rs.getInt(Column.BUY);
			this.free = rs.getInt(Column.FREE);
		}
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	public void addCash(int cash) {
		this.cash += cash;
	}

	public int getBuy() {
		return buy;
	}

	public void setBuy(int buy) {
		this.buy = buy;
	}

	public int getFree() {
		return free;
	}

	public void setFree(int free) {
		this.free = free;
	}

	public void addFree(int free) {
		this.free += free;
	}

	public void serialize(ByteBuffer bb) {
		bb.putInt(cash);
		bb.putInt(buy);
		bb.putInt(free);
	}

	public static Cash deserialize(ByteBuffer bb) {
		int cash = bb.getInt();
		int buy = bb.getInt();
		int free = bb.getInt();

		return new Cash(cash, buy, free);
	}
	
	public Map<String, Object> prepare() {
		Map<String, Object> map = new HashMap<>();
		map.put( Constants.BUY, buy);
		map.put( Constants.FREE, free);
		map.put( Constants.CASH, cash);
		return map;
	}

}
