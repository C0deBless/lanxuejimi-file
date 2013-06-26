package common.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeUtil {
	static final Logger logger = LoggerFactory.getLogger(TimeUtil.class);
	static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	static final String defaultExpireTimeStr = "2030-12-31 00:00:00";

	public static Date getDefaultExpireDate() {
		try {
			Date expire = sdf.parse(defaultExpireTimeStr);
			return expire;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}
