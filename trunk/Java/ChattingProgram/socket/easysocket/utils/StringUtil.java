package easysocket.utils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {
	static final Logger logger = LoggerFactory
			.getLogger(StringUtil.class);
	static final Charset defaultCharset = Charset.forName("UTF-8");

	public static String getString(ByteBuffer bb) {
		final short length = bb.getShort();
		if (length == 0) {
			return "";
		}
		byte[] str = new byte[length];
		bb.get(str, 0, length);
		return new String(str, defaultCharset);
	}

	public static void putString(ByteBuffer bb, String str) {
		if (str == null || str.equals("")) {
			bb.putShort((short) 0);
			return;
		}
		byte[] bytes = str.getBytes(defaultCharset);
		short size = (short) bytes.length;
		bb.putShort(size);
		if (size > 0)
			bb.put(bytes);
	}
}
