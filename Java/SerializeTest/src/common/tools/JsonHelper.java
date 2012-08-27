package common.tools;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.type.TypeReference;

public class JsonHelper {

	private static final ObjectMapper objectMapper = new ObjectMapper();

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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

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
			// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}

	public static String removeValue(String strJson, String field,
			boolean isDeepClean) {
		return removeValue(strJson, Arrays.asList(field), isDeepClean);
	}

	public static String removeValue(String strJson, List<String> fields,
			boolean isDeepClean) {
		try {
			JsonNode jsonNode=objectMapper.readTree(strJson);
			if(jsonNode.isObject()){
				ObjectNode node = (ObjectNode)jsonNode ;
				if (isDeepClean)
					removeValue(node, fields);
				else
					node.remove(fields);
				return node.toString();
			}
			else{
				return strJson;
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void removeValue(JsonNode node, List<String> fields) {

		if (node.isObject()) {
			((ObjectNode) node).remove(fields);
			// return node;
			//Debug.output("---->>isObject : " + node.toString());
		}

		Iterator<String> iStr = node.getFieldNames();
		while (iStr.hasNext()) {
			JsonNode objectNode = node.get(iStr.next());
			if (objectNode.isArray()) {
				//Debug.output("iStr : " + iStr + " -->> isArray.");
				int size = objectNode.size();
				for (int i = 0; i < size; i++) {
					removeValue(objectNode.get(i), fields);
				}
			} else if (objectNode.isObject()) {
				//Debug.output("iStr : " + iStr + " -->> isObject.");
				((ObjectNode) objectNode).remove(fields);
				removeValue(objectNode, fields);
			} else {
				//Debug.output("iStr : " + iStr + " -->> is other.");
			}
		}
	}

}
