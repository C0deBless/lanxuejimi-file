package common.serialize;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.tools.PrintStackTrace;


public class SerializeConfig {
	private static final Logger logger = LoggerFactory.getLogger(SerializeConfig.class);
	
	static final Map<Integer, ConfigModel[]> CONFIG_POOL;
	static final String CONFIG_PATH = "/serialize.properties";
	static final Pattern p = Pattern.compile("<([a-zA-Z][a-zA-Z0-9.,]*)>");
	static {
		CONFIG_POOL = new HashMap<Integer, ConfigModel[]>();
		init();
	}

	public static void init() {
		CONFIG_POOL.clear();
		InputStream input = SerializeConfig.class
				.getResourceAsStream(CONFIG_PATH);
		Properties prop = new Properties();
		try {
			prop.load(input);
			Set<Object> keyset = prop.keySet();
			for (Object object : keyset) {
				int key = Integer.parseInt(object.toString());
				String value = prop.getProperty(object.toString());
				if (value != null && !value.equals("")) {
					String[] strs = value.split(",");
					ConfigModel[] list = new ConfigModel[strs.length];
					int index = 0;
					for (String string : strs) {
						String[] _strs = string.split(":");
						String strKey = _strs[0];
						String type = _strs[1];
						ConfigModel model = new ConfigModel(strKey, type);
						wrapGenericType(model);
						list[index] = model;
						index++;
					}
					CONFIG_POOL.put(key, list);
				} else {
					CONFIG_POOL.put(key, new ConfigModel[] {});
				}
			}
		} catch (InvalidPropertiesFormatException e) {
			logger.error("SerializeConfig.init, " + e.getMessage());
			PrintStackTrace.print(logger, e);
		} catch (IOException e) {
			logger.error("SerializeConfig.init, " + e.getMessage());
			PrintStackTrace.print(logger, e);
		}
	}

	static void wrapGenericType(ConfigModel model) {
		String type = model.getType();
		Matcher m = p.matcher(type);
		String genericType = "";
		while (m.find()) {
			type = type.replace(m.group(0), "");
			genericType = m.group(1);
		}
		if (type.startsWith("List")) {
			model.setType(type);
			model.setGenericType1(genericType);
		} else if (type.startsWith("Map")) {
			String[] strs = genericType.split(",");
			String genericType1 = strs[0].trim();
			String genericType2 = strs[1].trim();
			model.setType(type);
			model.setGenericType1(genericType1);
			model.setGenericType2(genericType2);
		}
	}

	public static ConfigModel[] get(int cmd) {
		return CONFIG_POOL.get(cmd);
	}

	public static boolean contains(int cmd) {
		return CONFIG_POOL.containsKey(cmd);
	}
}
