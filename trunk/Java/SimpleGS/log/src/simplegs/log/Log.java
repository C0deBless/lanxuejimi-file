package simplegs.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

	private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger("");

	public static Logger getLogger(Class<?> clazz) {
		return LoggerFactory.getLogger(clazz);
	}

	public static Logger getLogger() {
		return DEFAULT_LOGGER;
	}

	public static void info(String value, Object... args) {
		DEFAULT_LOGGER.info(value, args);
	}

	public static void trace(String value, Object... args) {
		DEFAULT_LOGGER.trace(value, args);
	}

	public static void warn(String value, Object... args) {
		DEFAULT_LOGGER.warn(value, args);
	}

	public static void debug(String value, Object... args) {
		DEFAULT_LOGGER.debug(value, args);
	}

	public static void error(String value, Object... args) {
		DEFAULT_LOGGER.error(value, args);
	}
}
