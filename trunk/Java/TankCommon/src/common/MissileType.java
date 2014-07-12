package common;

public enum MissileType {
	A((byte) 1), B((byte) 2), C((byte) 3);

	private byte value;

	MissileType(byte value) {
		this.value = value;
	}

	public byte getValue() {
		return value;
	}

	public static MissileType parse(byte value) {
		MissileType[] values = MissileType.values();
		for (MissileType tankType : values) {
			if (tankType.value == value) {
				return tankType;
			}
		}
		return null;
	}
}
