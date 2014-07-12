package common;

public enum TankType {
	A((byte) 1), B((byte) 2), C((byte) 3);

	private byte value;

	TankType(byte value) {
		this.value = value;
	}

	public byte getValue() {
		return value;
	}

	public static TankType parse(byte value) {
		TankType[] values = TankType.values();
		for (TankType tankType : values) {
			if (tankType.value == value) {
				return tankType;
			}
		}
		return null;
	}
}
