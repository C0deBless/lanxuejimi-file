package common;

import java.nio.ByteBuffer;

public class StringUtil {

	public static void putString(ByteBuffer buffer, String str) {
		if (str == null || str.equals("")) {
			buffer.putInt(0);
		} else {
			byte[] data = str.getBytes();
			int length = data.length;
			buffer.putInt(length);
			buffer.put(data);
		}
	}

	public static String getString(ByteBuffer buffer) {
		int length = buffer.getInt();
		byte[] dst = new byte[length];
		buffer.get(dst, 0, length);
		return new String(dst);
	}
}
