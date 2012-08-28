package common.tools;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Serializable {

	public Serializable() {
		if (typePool.isEmpty()) {
			init(this);
		}
	}

	protected static Map<String, Class<?>> typePool = new LinkedHashMap<>();
	protected static List<String> columnList = new ArrayList<>();
	protected static Map<String, Field> fieldCache = new LinkedHashMap<>();

	protected static void init(Serializable obj) {
		Class<?> clazz = obj.getClass();
		System.out.println(clazz.getName());
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			Class<?> type = field.getType();
			typePool.put(name, type);
		}
		Set<String> keyset = typePool.keySet();
		List<String> list = new ArrayList<>(keyset.size());
		for (String string : keyset) {
			list.add(string);
		}
		Collections.sort(list);
		columnList.clear();
		columnList.addAll(list);
	}

	public void serialize(ByteBuffer buffer) {
		for (String key : columnList) {
			if (!fieldCache.containsKey(key)) {
				try {
					Field field = this.getClass().getDeclaredField(key);
					field.setAccessible(true);
					fieldCache.put(key, field);
				} catch (NoSuchFieldException | SecurityException e) {
					e.printStackTrace();
				}
			}
			Field field = fieldCache.get(key);
			try {
				Object obj = field.get(this);
				Class<?> type = obj.getClass();
				if (type == int.class) {
					buffer.putInt((int) obj);
				} else if (type == float.class) {
					buffer.putFloat((float) obj);
				} else if (type == double.class) {
					buffer.putDouble((double) obj);
				} else if (type == String.class) {
					StringUtil.putString(buffer, (String) obj);
				} else if (type == List.class) {
					// List<?> list=
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public void json() {
		JsonHelper.serialize(this);
	}

	public Serializable deserialize(ByteBuffer buffer) {
		return null;
	}

	public static void test() {
		ByteBuffer buffer = ByteBuffer.allocate(10000);
		TestCommander tc = new TestCommander();
		tc.setUser_id(1000);
		tc.setName("trnnn");
		tc.setPos("1000,800");
		// tc.setSkills(Arrays.asList(1, 2, 3, 4, 5));
		tc.serialize(buffer);
		tc.serialize(buffer);
		tc.serialize(buffer);
		long d1 = System.nanoTime();
		tc.serialize(buffer);
		long d2 = System.nanoTime();
		tc.serialize(buffer);
		long d3 = System.nanoTime();
		tc.serialize(buffer);
		long d4 = System.nanoTime();
		System.out.println(d2 - d1);
		System.out.println(d3 - d2);
		System.out.println(d4 - d3);
	}

	public static void testJson() {
		TestCommander tc = new TestCommander();
		tc.setUser_id(1000);
		tc.setName("trnnn");
		tc.setPos("1000,800");
		// tc.setSkills(Arrays.asList(1, 2, 3, 4, 5));
		long d1 = System.nanoTime();
		tc.json();
		long d2 = System.nanoTime();
		tc.setUser_id(1001);
		tc.setName("trnnnn");
		tc.setPos("1000,800n");
		tc.json();
		long d3 = System.nanoTime();
		tc.setUser_id(1004);
		tc.setName("trnnnnn");
		tc.setPos("1000,800nn");
		tc.json();
		long d4 = System.nanoTime();
		System.out.println(d2 - d1);
		System.out.println(d3 - d2);
		System.out.println(d4 - d3);
	}

	public static void main(String[] args) throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		test();
		// testJson();
	}

}

class PerformanceTest {
	public int takeAction(String msg) {
		return (msg.length() * (int) (System.currentTimeMillis() % 100000));
	}
}
