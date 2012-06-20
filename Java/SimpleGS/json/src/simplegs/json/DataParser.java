package simplegs.json;


import java.util.Map;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataParser {
	private static final Logger logger = LoggerFactory.getLogger(DataParser.class);

	public static Map<String, Object> parse(String strJson) {

		if (strJson != null && !strJson.equals("")) {
			try {
				Object obj = JsonHelper.deserialize(strJson,
						new TypeReference<Map<String, Object>>() {
						});
				if (null != obj) {
					@SuppressWarnings("unchecked")
					Map<String, Object> map = (Map<String, Object>) obj;
					return map;
				} else {
					logger.error("DataParser.parse, NULL is JsonHelper.deserialize Result");
				}
			} catch (Exception e) {
				logger.error("DataParser.parse, " + e.getMessage());
				e.printStackTrace();
			}
		} else {
			logger.error("DataParser.parse, NULL is strJson");
		}
		return null;
	}
	
	public static int parseInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			logger.error(e.getMessage());

		}
		return 0;
	}
	
	public static int parseInt( Map<String, Object> map, String key ) {
		if ( map.containsKey(key) && (null != map.get(key)) ) {
			try {
				return Integer.parseInt(map.get(key).toString());
			} catch (Exception e) {
				logger.error(e.getMessage());
			}

		}
		logger.warn("DataParser.parseInt, no data, key:" + key );
		return 0;
	}
	
	public static long parseLong( Map<String, Object> map, String key ) {
		if ( (null != map) && map.containsKey(key) && (null != map.get(key)) ) {
			try {
				return Long.parseLong(map.get(key).toString());
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		logger.warn("DataParser.parseLong, no data, key:" + key );
		return 0;
	}
	
	public static String parseString( Map<String, Object> map, String key ) {
		if ( (null != map) && map.containsKey(key) && (null != map.get(key)) ) {
			try {
				return map.get(key).toString();
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		logger.warn("DataParser.parseString, no data, key:" + key );
		return null;
	}

}
