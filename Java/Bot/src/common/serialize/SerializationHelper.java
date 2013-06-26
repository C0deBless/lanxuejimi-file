package common.serialize;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import common.exception.ListSizeOverException;
import common.tools.StringUtil;

public class SerializationHelper {
	public static void serializeList(List<? extends Serializable> list,
			ByteBuffer bb) {
		if (list == null) {
			bb.putInt(0);
		} else {
			int size = list.size();
			bb.putInt(size);
			for (Serializable base : list) {
				base.serialize(bb);
			}
		}
	}

	public static void serializeIntegerList(List<Integer> list, ByteBuffer bb) {
		if (list == null) {
			bb.putInt(0);
		} else {
			int size = list.size();
			bb.putInt(size);
			for (int base : list) {
				bb.putInt(base);
			}
		}
	}

	public static void serializeLongList(List<Long> list, ByteBuffer bb) {
		if (list == null) {
			bb.putInt(0);
		} else {
			int size = list.size();
			bb.putInt(size);
			for (long base : list) {
				bb.putLong(base);
			}
		}
	}

	public static void serializeStringList(List<String> list, ByteBuffer bb) {
		if (list == null) {
			bb.putInt(0);
		} else {
			int size = list.size();
			bb.putInt(size);
			for (String base : list) {
				StringUtil.putString(bb, base);
			}
		}
	}

	public static List<Integer> deserializeIntegerList(ByteBuffer bb) {
		int size = bb.getInt();
		if (size > Short.MAX_VALUE) {
			throw new ListSizeOverException();
		}
		List<Integer> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			int base = bb.getInt();
			list.add(base);
		}
		return list;
	}

	public static List<String> deserializeStringList(ByteBuffer bb) {
		int size = bb.getInt();
		if (size > Short.MAX_VALUE) {
			throw new ListSizeOverException();
		}
		List<String> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			String str = StringUtil.getString(bb);
			list.add(str);
		}
		return list;
	}

	public static List<Double> deserializeDoubleList(ByteBuffer bb) {
		int size = bb.getInt();
		if (size > Short.MAX_VALUE) {
			throw new ListSizeOverException();
		}
		List<Double> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			list.add(bb.getDouble());
		}
		return list;
	}

	public static void serializeDoubleList(List<Double> list, ByteBuffer bb) {
		if (list == null || list.isEmpty()) {
			bb.putInt(0);
			return;
		}
		int size = list.size();
		bb.putInt(size);
		for (double d : list) {
			bb.putDouble(d);
		}
	}

	public static List<Long> deserializeLongList(ByteBuffer bb) {
		int size = bb.getInt();
		if (size > Short.MAX_VALUE) {
			throw new ListSizeOverException();
		}
		List<Long> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			long l = bb.getLong();
			list.add(l);
		}
		return list;
	}
}
