package easysocket.utils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class StringUtil {
	static final Charset defaultCharset = Charset.forName("UTF-8");

	public static String getString(ByteBuffer bb) {
		final int length = bb.getInt();
		if (length == 0) {
			return "";
		}
		byte[] str = new byte[length];
		bb.get(str, 0, length);
		return new String(str, defaultCharset);
	}

	public static void putString(ByteBuffer bb, String str) {
		if (str == null || str.equals("")) {
			bb.putInt(0);
			return;
		}
		int size = str.getBytes(defaultCharset).length;
		System.out.println("string size:" + size);
		bb.putInt(size);
		if (size > 0)
			bb.put(str.getBytes(defaultCharset));
	}
}
