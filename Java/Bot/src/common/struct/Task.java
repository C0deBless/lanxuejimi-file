package common.struct;

import java.nio.ByteBuffer;

import common.serialize.Serializable;
import common.tools.StringUtil;

public class Task implements Serializable {
	private int m_id;
	private int p1;
	private int count;
	private int cash;
	private String pattern;
	private String t_id;

	public Task() {
	}

	public Task(int m_id, int p1, int count, int cash, String pattern,
			String t_id) {
		super();
		this.m_id = m_id;
		this.p1 = p1;
		this.count = count;
		this.cash = cash;
		this.pattern = pattern;
		this.t_id = t_id;
	}

	public int getM_id() {
		return m_id;
	}

	public void setM_id(int m_id) {
		this.m_id = m_id;
	}

	public int getP1() {
		return p1;
	}

	public void setP1(int p1) {
		this.p1 = p1;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void increaseP1(int increase) {
		this.p1 += increase;
		if (this.p1 > count)
			this.p1 = count;
	}

	public void increaseP1() {
		this.p1++;
	}

	public String getT_id() {
		return t_id;
	}

	public void setT_id(String t_id) {
		this.t_id = t_id;
	}

	public void serialize(ByteBuffer bb) {
		bb.putInt(m_id);
		bb.putInt(p1);
		bb.putInt(count);
		bb.putInt(cash);
		StringUtil.putString(bb, pattern);
		StringUtil.putString(bb, t_id);
	}

	public static Task deserialize(ByteBuffer bb) {
		int m_id = bb.getInt();
		int p1 = bb.getInt();
		int count = bb.getInt();
		int cash = bb.getInt();
		String pattern = StringUtil.getString(bb);
		String t_id = StringUtil.getString(bb);

		return new Task(m_id, p1, count, cash, pattern, t_id);
	}

}
