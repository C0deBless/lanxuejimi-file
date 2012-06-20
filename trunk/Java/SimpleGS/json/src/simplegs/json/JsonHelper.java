package simplegs.json;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.type.TypeReference;

public class JsonHelper {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,
				false);
	}

	public static ObjectMapper mapper() {
		return objectMapper;
	}

	public static String serialize(Object model) {
		try {
			return objectMapper.writeValueAsString(model);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T deserialize(String strJson, TypeReference<T> type) {
		try {
			return objectMapper.readValue(strJson, type);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T deserialize(JsonNode node, TypeReference<T> type) {
		try {
			return objectMapper.readValue(node, type);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T deserialize(String strJson, Class<T> type) {
		try {
			return objectMapper.readValue(strJson, type);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T deserialize(JsonNode node, Class<T> type) {
		try {
			return objectMapper.readValue(node, type);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static JsonNode getValue(String strJson, String field) {
		try {
			JsonNode node = objectMapper.readTree(strJson);
			JsonNode valueNode = node.get(field);
			return valueNode;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <T> String putValue(String strJson, String field, T value) {
		try {
			ObjectNode node = (ObjectNode) objectMapper.readTree(strJson);

			if (value instanceof String)
				node.put(field, (String) value);

			else if (value instanceof Integer)
				node.put(field, (Integer) value);

			else if (value instanceof Boolean)
				node.put(field, (Boolean) value);

			else if (value instanceof Long)
				node.put(field, (Long) value);

			else if (value instanceof Double)
				node.put(field, (Double) value);

			else if (value instanceof Float)
				node.put(field, (Float) value);

			else if (value instanceof BigDecimal)
				node.put(field, (BigDecimal) value);

			else {
				node.put(field, objectMapper.readTree(serialize(value)));
				// node.putPOJO(field, value);
			}
			return node.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// public static void main(String[] args) {
	// String str = "{\"a\":\"a\"}";
	// long d1 = System.nanoTime();
	// String str1 = putValue(str, "b", new User(21, "trnnn", 34));
	// long d2 = System.nanoTime();
	// String str2 = putValue(str, "b", new User(21, "trnnn", 34));
	// long d3 = System.nanoTime();
	// System.out.println((d2 - d1) + str1);
	// System.out.println((d3 - d2) + str2);
	// }

	public static String putValue(String strJson, String field, int value) {
		ObjectNode node;
		try {
			node = (ObjectNode) objectMapper.readTree(strJson);
			node.put(field, value);
			return node.toString();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String putValue(String strJson, String field, byte[] value) {
		ObjectNode node;
		try {
			node = (ObjectNode) objectMapper.readTree(strJson);
			node.put(field, value);
			return node.toString();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String putValue(String strJson, String field, boolean value) {
		ObjectNode node;
		try {
			node = (ObjectNode) objectMapper.readTree(strJson);
			node.put(field, value);
			return node.toString();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String putValue(String strJson, String field,
			JsonNode jsonNode) {
		try {
			ObjectNode node = (ObjectNode) objectMapper.readTree(strJson);// objectMapper.
			node.put(field, jsonNode);
			return node.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String removeValue(String strJson, String field) {
		if (strJson.indexOf(field) >= 0)
			try {
				ObjectNode node = (ObjectNode) objectMapper.readTree(strJson);
				node.remove(field);
				String result = node.toString();
				return result;
			} catch (IOException e) {
				e.printStackTrace();
			}
		return null;
	}

	public static String removeValue(String strJson, String field,
			boolean isDeepClean) {
		return removeValue(strJson, Arrays.asList(field), isDeepClean);
	}

	public static String removeValue(String strJson, String nodeName,
			String field, boolean isDeepClean) {
		return removeValue(strJson, nodeName, Arrays.asList(field), isDeepClean);
	}

	public static String removeValue(String strJson, String nodeName,
			List<String> fields, boolean isDeepClean) {
		JsonNode jsonNode;
		try {
			jsonNode = objectMapper.readTree(strJson);
			if (jsonNode.isObject()) {
				ObjectNode node = (ObjectNode) jsonNode;
				if (isDeepClean)
					removeValue(node, nodeName, fields);
				return node.toString();
			} else {
				return strJson;
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String removeValue(String strJson, List<String> fields,
			boolean isDeepClean) {
		try {
			JsonNode jsonNode = objectMapper.readTree(strJson);
			if (jsonNode.isObject()) {
				ObjectNode node = (ObjectNode) jsonNode;
				if (isDeepClean)
					removeValue(node, fields);
				else
					node.remove(fields);
				return node.toString();
			} else {
				return strJson;
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void removeValue(JsonNode node, String targetNode,
			List<String> fields) {
		JsonNode target = node.get(targetNode);
		if (target != null) {
			removeValue(target, fields);
		} else {
			Iterator<String> iStr = node.getFieldNames();
			while (iStr.hasNext()) {
				if (iStr.next().equals(targetNode)) {
					JsonNode objectNode = node.get(iStr.next());
					if (objectNode.isArray()) {
						// System.out.println("iStr : " + iStr +
						// " -->> isArray.");
						int size = objectNode.size();
						for (int i = 0; i < size; i++) {
							removeValue(objectNode.get(i), fields);
						}
					} else if (objectNode.isObject()) {
						// System.out.println("iStr : " + iStr +
						// " -->> isObject.");
						((ObjectNode) objectNode).remove(fields);
						removeValue(objectNode, fields);
					} else {
						// System.out.println("iStr : " + iStr +
						// " -->> is other.");
					}
				}

			}
		}
	}

	public static void removeValue(JsonNode node, List<String> fields) {

		if (node.isObject()) {
			((ObjectNode) node).remove(fields);
			// return node;
			// System.out.println("---->>isObject : " + node.toString());
		}
		if (node.isArray()) {
			int size = node.size();
			for (int i = 0; i < size; i++) {
				JsonNode jn = node.get(i);
				if (jn.isObject())
					((ObjectNode) jn).remove(fields);
			}
		}

		Iterator<String> iStr = node.getFieldNames();
		while (iStr.hasNext()) {
			JsonNode objectNode = node.get(iStr.next());
			if (objectNode.isArray()) {
				// System.out.println("iStr : " + iStr + " -->> isArray.");
				int size = objectNode.size();
				for (int i = 0; i < size; i++) {
					removeValue(objectNode.get(i), fields);
				}
			} else if (objectNode.isObject()) {
				// System.out.println("iStr : " + iStr + " -->> isObject.");
				((ObjectNode) objectNode).remove(fields);
				removeValue(objectNode, fields);
			} else {
				// System.out.println("iStr : " + iStr + " -->> is other.");
			}
		}
	}

	// public static void main(String[] args) {
	// String json =
	// "{\"energy\":0,\"res\":1,\"port\":{\"item\":[{\"port_id\":0,\"id\":130003,\"type\":null,\"price\":450,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130004,\"type\":null,\"price\":390,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130005,\"type\":null,\"price\":850,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130006,\"type\":null,\"price\":1200,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130007,\"type\":null,\"price\":2000,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130008,\"type\":null,\"price\":2600,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130009,\"type\":null,\"price\":300,\"supply\":318,\"demand\":0},{\"port_id\":0,\"id\":130010,\"type\":null,\"price\":340,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130011,\"type\":null,\"price\":580,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130012,\"type\":null,\"price\":460,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130013,\"type\":null,\"price\":130,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130014,\"type\":null,\"price\":460,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130015,\"type\":null,\"price\":9500,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130016,\"type\":null,\"price\":190,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130017,\"type\":null,\"price\":1400,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130018,\"type\":null,\"price\":520,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130019,\"type\":null,\"price\":480,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130020,\"type\":null,\"price\":800,\"supply\":125,\"demand\":0},{\"port_id\":0,\"id\":130021,\"type\":null,\"price\":300,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130022,\"type\":null,\"price\":570,\"supply\":0,\"demand\":0}],\"building\":[{\"port_id\":0,\"id\":140001,\"type\":null,\"price\":0,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":140002,\"type\":null,\"price\":0,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":140003,\"type\":null,\"price\":0,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":140004,\"type\":null,\"price\":0,\"supply\":0,\"demand\":0}],\"ship\":[{\"port_id\":0,\"id\":200001,\"type\":null,\"price\":50000,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":200002,\"type\":null,\"price\":40000,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":200003,\"type\":null,\"price\":20000,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":200004,\"type\":null,\"price\":5000,\"supply\":0,\"demand\":0}],\"gun\":[{\"port_id\":0,\"id\":210001,\"type\":null,\"price\":500,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":210002,\"type\":null,\"price\":1000,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":210003,\"type\":null,\"price\":3000,\"supply\":0,\"demand\":0}]}}";
	// long d1 = System.nanoTime();
	// String str = removeValue(json,
	// Arrays.asList("port_id", "type", "demand"), true);
	// long d2 = System.nanoTime();
	// String json1 =
	// "{\"energy\":0,\"res\":1,\"port\":{\"item\":[{\"port_id\":0,\"id\":130003,\"type\":null,\"price\":450,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130004,\"type\":null,\"price\":390,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130005,\"type\":null,\"price\":850,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130006,\"type\":null,\"price\":1200,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130007,\"type\":null,\"price\":2000,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130008,\"type\":null,\"price\":2600,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130009,\"type\":null,\"price\":300,\"supply\":318,\"demand\":0},{\"port_id\":0,\"id\":130010,\"type\":null,\"price\":340,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130011,\"type\":null,\"price\":580,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130012,\"type\":null,\"price\":460,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130013,\"type\":null,\"price\":130,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130014,\"type\":null,\"price\":460,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130015,\"type\":null,\"price\":9500,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130016,\"type\":null,\"price\":190,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130017,\"type\":null,\"price\":1400,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130018,\"type\":null,\"price\":520,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130019,\"type\":null,\"price\":480,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130020,\"type\":null,\"price\":800,\"supply\":125,\"demand\":0},{\"port_id\":0,\"id\":130021,\"type\":null,\"price\":300,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130022,\"type\":null,\"price\":570,\"supply\":0,\"demand\":0}],\"building\":[{\"port_id\":0,\"id\":140001,\"type\":null,\"price\":0,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":140002,\"type\":null,\"price\":0,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":140003,\"type\":null,\"price\":0,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":140004,\"type\":null,\"price\":0,\"supply\":0,\"demand\":0}],\"ship\":[{\"port_id\":0,\"id\":200001,\"type\":null,\"price\":50000,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":200002,\"type\":null,\"price\":40000,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":200003,\"type\":null,\"price\":20000,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":200004,\"type\":null,\"price\":5000,\"supply\":0,\"demand\":0}],\"gun\":[{\"port_id\":0,\"id\":210001,\"type\":null,\"price\":500,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":210002,\"type\":null,\"price\":1000,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":210003,\"type\":null,\"price\":3000,\"supply\":0,\"demand\":0}]}}";
	// long d3 = System.nanoTime();
	// String str1 = removeValue(json1,
	// Arrays.asList("port_id", "type", "demand"), true);
	// long d4 = System.nanoTime();
	// String json2 =
	// "{\"energy\":0,\"res\":1,\"port\":{\"item\":[{\"port_id\":0,\"id\":130003,\"type\":null,\"price\":450,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130004,\"type\":null,\"price\":390,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130005,\"type\":null,\"price\":850,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130006,\"type\":null,\"price\":1200,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130007,\"type\":null,\"price\":2000,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130008,\"type\":null,\"price\":2600,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130009,\"type\":null,\"price\":300,\"supply\":318,\"demand\":0},{\"port_id\":0,\"id\":130010,\"type\":null,\"price\":340,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130011,\"type\":null,\"price\":580,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130012,\"type\":null,\"price\":460,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130013,\"type\":null,\"price\":130,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130014,\"type\":null,\"price\":460,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130015,\"type\":null,\"price\":9500,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130016,\"type\":null,\"price\":190,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130017,\"type\":null,\"price\":1400,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130018,\"type\":null,\"price\":520,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130019,\"type\":null,\"price\":480,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130020,\"type\":null,\"price\":800,\"supply\":125,\"demand\":0},{\"port_id\":0,\"id\":130021,\"type\":null,\"price\":300,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130022,\"type\":null,\"price\":570,\"supply\":0,\"demand\":0}],\"building\":[{\"port_id\":0,\"id\":140001,\"type\":null,\"price\":0,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":140002,\"type\":null,\"price\":0,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":140003,\"type\":null,\"price\":0,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":140004,\"type\":null,\"price\":0,\"supply\":0,\"demand\":0}],\"ship\":[{\"port_id\":0,\"id\":200001,\"type\":null,\"price\":50000,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":200002,\"type\":null,\"price\":40000,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":200003,\"type\":null,\"price\":20000,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":200004,\"type\":null,\"price\":5000,\"supply\":0,\"demand\":0}],\"gun\":[{\"port_id\":0,\"id\":210001,\"type\":null,\"price\":500,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":210002,\"type\":null,\"price\":1000,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":210003,\"type\":null,\"price\":3000,\"supply\":0,\"demand\":0}]}}";
	// long d5 = System.nanoTime();
	// String str2 = removeValue(json2,
	// Arrays.asList("port_id", "type", "demand"), true);
	// long d6 = System.nanoTime();
	// String json3 =
	// "{\"energy\":0,\"res\":1,\"port\":{\"item\":[{\"port_id\":0,\"id\":130003,\"type\":null,\"price\":450,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130004,\"type\":null,\"price\":390,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130005,\"type\":null,\"price\":850,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130006,\"type\":null,\"price\":1200,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130007,\"type\":null,\"price\":2000,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130008,\"type\":null,\"price\":2600,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130009,\"type\":null,\"price\":300,\"supply\":318,\"demand\":0},{\"port_id\":0,\"id\":130010,\"type\":null,\"price\":340,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130011,\"type\":null,\"price\":580,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130012,\"type\":null,\"price\":460,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130013,\"type\":null,\"price\":130,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130014,\"type\":null,\"price\":460,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130015,\"type\":null,\"price\":9500,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130016,\"type\":null,\"price\":190,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130017,\"type\":null,\"price\":1400,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130018,\"type\":null,\"price\":520,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130019,\"type\":null,\"price\":480,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130020,\"type\":null,\"price\":800,\"supply\":125,\"demand\":0},{\"port_id\":0,\"id\":130021,\"type\":null,\"price\":300,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":130022,\"type\":null,\"price\":570,\"supply\":0,\"demand\":0}],\"building\":[{\"port_id\":0,\"id\":140001,\"type\":null,\"price\":0,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":140002,\"type\":null,\"price\":0,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":140003,\"type\":null,\"price\":0,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":140004,\"type\":null,\"price\":0,\"supply\":0,\"demand\":0}],\"ship\":[{\"port_id\":0,\"id\":200001,\"type\":null,\"price\":50000,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":200002,\"type\":null,\"price\":40000,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":200003,\"type\":null,\"price\":20000,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":200004,\"type\":null,\"price\":5000,\"supply\":0,\"demand\":0}],\"gun\":[{\"port_id\":0,\"id\":210001,\"type\":null,\"price\":500,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":210002,\"type\":null,\"price\":1000,\"supply\":0,\"demand\":0},{\"port_id\":0,\"id\":210003,\"type\":null,\"price\":3000,\"supply\":0,\"demand\":0}]}}";
	// long d7 = System.nanoTime();
	// String str3 = removeValue(json3,
	// Arrays.asList("port_id", "type", "demand"), true);
	// long d8 = System.nanoTime();
	// System.out.println((d2 - d1) + " -> " + str);
	// System.out.println((d4 - d3) + " -> " + str1);
	// System.out.println((d6 - d5) + " -> " + str2);
	// System.out.println((d8 - d7) + " -> " + str3);
	//
	// }
	// public static void main(String[] args) {
	// String json =
	// "{\"fleet\":{\"whr\":\"prt\",\"port_id\":120004,\"ns\":\"N46\",\"ew\":\"E005\",\"pos\":\"1495,750\"},\"res\":1,\"session_id\":\"1-113077403606230\",\"commander\":{\"user_id\":100003075441353,\"energy\":50,\"max_energy\":50,\"gold\":806400,\"country\":110004,\"name\":\"Li\",\"level\":1,\"combat\":20,\"sailing\":20,\"luck\":20,\"xp\":92,\"cash\":120,\"whr\":\"prt\",\"port_id\":120004,\"ns\":\"N46\",\"ew\":\"E005\",\"pos\":\"1495,750\",\"m_time\":1325568889000},\"ship\":[{\"ship_id\":3,\"id\":200004,\"name\":\"Prospero\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":100,\"order\":0,\"flag\":\"y\",\"captain\":\"100003075441353\",\"bosun\":\"\",\"part\":[{\"id\":130004,\"count\":1,\"buy_price\":0},{\"id\":130009,\"count\":10,\"buy_price\":0},{\"id\":210001,\"count\":1,\"buy_price\":0},{\"id\":220001,\"count\":1,\"buy_price\":0}]},{\"ship_id\":38,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":130009,\"count\":1,\"buy_price\":300},{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":39,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":130009,\"count\":1,\"buy_price\":300},{\"id\":130020,\"count\":1,\"buy_price\":800},{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":40,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":41,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":42,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":45,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":46,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":47,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":51,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":52,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":56,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":57,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":58,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":59,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":60,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":61,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":62,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":63,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":64,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":65,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":66,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":67,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":68,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":69,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":70,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":71,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":73,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":74,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":78,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":79,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":80,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":81,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":82,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":83,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":84,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]},{\"ship_id\":85,\"id\":200004,\"name\":\"???\",\"mast\":2,\"oar\":0,\"capacity\":11,\"speed\":15,\"cargo_deck\":8,\"crew_deck\":2,\"gun_deck\":1,\"hp\":300,\"max_hp\":300,\"morale\":200,\"order\":0,\"flag\":\"n\",\"captain\":null,\"bosun\":\"\",\"part\":[{\"id\":210002,\"count\":1,\"buy_price\":0},{\"id\":220002,\"count\":2,\"buy_price\":0}]}],\"missions\":[{\"expired\":1924873200000,\"complete\":\"n\",\"issued\":1325568849000,\"user_id\":100003075441353,\"tasks\":[{\"t_id\":560001,\"count\":1,\"pattern\":\"W\",\"p1\":0,\"cash\":0,\"user_id\":100003075441353,\"m_id\":700001}],\"m_id\":700001},{\"expired\":1325579689000,\"complete\":\"n\",\"issued\":1325568889000,\"user_id\":100003075441353,\"tasks\":[{\"t_id\":120002,\"count\":1,\"pattern\":\"I\",\"p1\":0,\"cash\":5,\"user_id\":100003075441353,\"m_id\":710001},{\"t_id\":120005,\"count\":1,\"pattern\":\"I\",\"p1\":0,\"cash\":5,\"user_id\":100003075441353,\"m_id\":710001},{\"t_id\":120008,\"count\":1,\"pattern\":\"I\",\"p1\":0,\"cash\":5,\"user_id\":100003075441353,\"m_id\":710001}],\"m_id\":710001},{\"expired\":1325579689000,\"complete\":\"n\",\"issued\":1325568889000,\"user_id\":100003075441353,\"tasks\":[{\"t_id\":0,\"count\":5,\"pattern\":\"O\",\"p1\":0,\"cash\":2,\"user_id\":100003075441353,\"m_id\":710002}],\"m_id\":710002}],\"port\":{\"item\":[{\"id\":130003,\"price\":450,\"supply\":0,\"demand\":165,\"special\":\"n\"},{\"id\":130004,\"price\":390,\"supply\":0,\"demand\":165,\"special\":\"n\"},{\"id\":130005,\"price\":850,\"supply\":0,\"demand\":100,\"special\":\"n\"},{\"id\":130006,\"price\":1200,\"supply\":0,\"demand\":50,\"special\":\"n\"},{\"id\":130007,\"price\":2000,\"supply\":0,\"demand\":60,\"special\":\"n\"},{\"id\":130008,\"price\":2600,\"supply\":0,\"demand\":25,\"special\":\"n\"},{\"id\":130009,\"price\":300,\"supply\":330,\"demand\":165,\"special\":\"y\"},{\"id\":130010,\"price\":340,\"supply\":0,\"demand\":250,\"special\":\"n\"},{\"id\":130011,\"price\":580,\"supply\":0,\"demand\":250,\"special\":\"n\"},{\"id\":130012,\"price\":460,\"supply\":0,\"demand\":250,\"special\":\"n\"},{\"id\":130013,\"price\":130,\"supply\":0,\"demand\":500,\"special\":\"n\"},{\"id\":130014,\"price\":460,\"supply\":0,\"demand\":250,\"special\":\"n\"},{\"id\":130015,\"price\":9500,\"supply\":0,\"demand\":10,\"special\":\"n\"},{\"id\":130016,\"price\":190,\"supply\":0,\"demand\":500,\"special\":\"n\"},{\"id\":130017,\"price\":1400,\"supply\":0,\"demand\":100,\"special\":\"n\"},{\"id\":130018,\"price\":520,\"supply\":0,\"demand\":250,\"special\":\"n\"},{\"id\":130019,\"price\":480,\"supply\":0,\"demand\":165,\"special\":\"n\"},{\"id\":130020,\"price\":800,\"supply\":125,\"demand\":60,\"special\":\"y\"},{\"id\":130021,\"price\":300,\"supply\":0,\"demand\":250,\"special\":\"n\"},{\"id\":130022,\"price\":570,\"supply\":0,\"demand\":165,\"special\":\"n\"}],\"building\":[{\"id\":140001,\"price\":0,\"supply\":0,\"demand\":0,\"special\":\"n\"},{\"id\":140002,\"price\":0,\"supply\":0,\"demand\":0,\"special\":\"n\"},{\"id\":140003,\"price\":0,\"supply\":0,\"demand\":0,\"special\":\"n\"},{\"id\":140004,\"price\":0,\"supply\":0,\"demand\":0,\"special\":\"n\"}],\"ship\":[{\"id\":200001,\"price\":50000,\"supply\":0,\"demand\":0,\"special\":\"n\"},{\"id\":200002,\"price\":40000,\"supply\":0,\"demand\":0,\"special\":\"n\"},{\"id\":200003,\"price\":20000,\"supply\":0,\"demand\":0,\"special\":\"n\"},{\"id\":200004,\"price\":5000,\"supply\":0,\"demand\":0,\"special\":\"n\"}],\"gun\":[{\"id\":210001,\"price\":500,\"supply\":0,\"demand\":0,\"special\":\"n\"},{\"id\":210002,\"price\":1000,\"supply\":0,\"demand\":0,\"special\":\"n\"},{\"id\":210003,\"price\":3000,\"supply\":0,\"demand\":0,\"special\":\"n\"}],\"crew\":[{\"id\":220001,\"price\":100,\"supply\":0,\"demand\":0,\"special\":\"n\"},{\"id\":220002,\"price\":500,\"supply\":0,\"demand\":0,\"special\":\"n\"},{\"id\":220003,\"price\":1000,\"supply\":0,\"demand\":0,\"special\":\"n\"},{\"id\":220004,\"price\":1500,\"supply\":0,\"demand\":0,\"special\":\"n\"},{\"id\":220005,\"price\":300,\"supply\":0,\"demand\":0,\"special\":\"n\"}]}}";
	// long d1=System.nanoTime();
	// String result = removeValue(json, "missions",Arrays.asList("user_id"),
	// true);
	// long d2=System.nanoTime();
	// String result1 = removeValue(json, "missions",Arrays.asList("user_id"),
	// true);
	// long d3=System.nanoTime();
	// String result2 = removeValue(json, "missions",Arrays.asList("user_id"),
	// true);
	// long d4=System.nanoTime();
	// String result3 = removeValue(json, "missions",Arrays.asList("user_id"),
	// true);
	// long d5=System.nanoTime();
	// System.out.println(result+"\n"+(d2-d1));
	// System.out.println(result1+"\n"+(d3-d2));
	// System.out.println(result2+"\n"+(d4-d3));
	// System.out.println(result3+"\n"+(d5-d4));
	// String result4 = removeValue(result3,
	// "tasks",Arrays.asList("user_id","m_id"), true);
	// //result4=removeValue(result4,"tasks","m_id",true);
	// System.out.println(result4);
	// }
}
