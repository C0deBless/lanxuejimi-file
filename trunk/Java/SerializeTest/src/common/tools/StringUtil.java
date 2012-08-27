package common.tools;

import java.nio.ByteBuffer;

public class StringUtil {
	public static String getString(ByteBuffer bb) {
		final short length = bb.getShort();
		byte[] str = new byte[length];
		bb.get(str, 0, length);
		return new String(str);
	}
	
	public static void putString(ByteBuffer bb, String str) {
		bb.putShort((short)str.getBytes().length);
		bb.put(str.getBytes());
	}

}
