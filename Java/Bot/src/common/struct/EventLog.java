package common.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import common.serialize.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventLog implements Serializable {

	private int obj_id;
	private long time;

	public EventLog() {
	}

	public EventLog(int obj_id, long time) {
		super();
		this.obj_id = obj_id;
		this.time = time;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getObj_id() {
		return obj_id;
	}

	public void setObj_id(int obj_id) {
		this.obj_id = obj_id;
	}

	public void serialize(ByteBuffer bb) {
		bb.putInt(obj_id);
		bb.putLong(time);
	}

	public static EventLog deserialize(ByteBuffer bb) {
		int obj_id = bb.getInt();
		long time = bb.getLong();

		return new EventLog(obj_id, time);
	}

	public static List<EventLog> deserializeList(ByteBuffer bb) {
		int size = bb.getInt();
		List<EventLog> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			EventLog b = EventLog.deserialize(bb);
			list.add(b);
		}
		return list;
	}
}
