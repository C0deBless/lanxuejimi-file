package com.trnnn.orm;

public class Restrictions {

	public static final int EQUAL = 0x1;
	public static final int OR = 0x10;
	public static final int AND = 0x100;
	public static final int LARGER = 0x1000;
	public static final int SMALLER = 0x10000;
	public static final int LARGER_OR_EQUAL = 0x100000;
	public static final int SMALLER_OR_EQUAL = 0x1000000;

	private String key;
	private Object value;
	private int LR;

	private Restrictions(String key, Object value, int LR) {
		this.key = key;
		this.value = value;
		this.setLR(LR);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public int getLR() {
		return LR;
	}

	public void setLR(int lR) {
		LR = lR;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(key);
		switch (this.LR) {
		case Restrictions.AND:
			buffer.append(" and ");
			break;
		case Restrictions.EQUAL:
			buffer.append('=');
			break;
		case Restrictions.LARGER:
			buffer.append('>');
			break;
		case Restrictions.OR:
			buffer.append(" or ");
			break;
		case Restrictions.SMALLER:
			buffer.append('<');
			break;
		default:
			// handle error
			throw new IllegalStateException(
					"unknown  logic relationship value " + this.LR);
		}
		buffer.append(value.toString());
		return buffer.toString();
	}

	/**
	 * @param key
	 * @param value
	 * @param LR
	 *            logic relationship {@link Restrictions}
	 * @return a new Resctrictions object
	 */
	public static Restrictions create(String key, Object value, int LR) {
		Restrictions res = new Restrictions(key, value, LR);
		return res;
	}
}