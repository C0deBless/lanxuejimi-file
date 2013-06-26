package common.tools;

import java.nio.ByteBuffer;

public class BooleanUtil {
	public static boolean getBoolean(ByteBuffer bb) {
		final byte value = bb.get();
		if (value == 0) {
			return false;
		} else {
			return true;
		}
	}

	public static void putBoolean(ByteBuffer bb, boolean value) {
		if (value) {
			bb.put((byte) 1);
		} else {
			bb.put((byte) 0);
		}
	}
}
