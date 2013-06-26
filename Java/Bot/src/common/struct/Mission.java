package common.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import common.serialize.Serializable;
import common.tools.BooleanUtil;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Mission implements Serializable {
	private char complete;
	private boolean isFailed;
	private int m_id;
	private Date issued;
	private Date expired;
	private Date time;
	private List<Task> tasks = new ArrayList<>();
	private boolean isUseCash = false;
	private boolean isNew;

	public Mission() {
	}

	public Mission(int m_id, char complete, Date expired) {
		this.m_id = m_id;
		this.complete = complete;
		this.expired = expired;
	}

	public Mission(char complete, boolean isFailed, int m_id, Date issued,
			Date expired, Date time) {
		super();
		this.complete = complete;
		this.isFailed = isFailed;
		this.m_id = m_id;
		this.issued = issued;
		this.expired = expired;
		this.time = time;
	}

	public Mission(char complete, boolean isFailed, int m_id, Date issued,
			Date expired, Date time, List<Task> tasks) {
		this(complete, isFailed, m_id, issued, expired, time);
		this.tasks = tasks;
	}

	public int getM_id() {
		return m_id;
	}

	public void setM_id(int m_id) {
		this.m_id = m_id;
	}

	public char getComplete() {
		return complete;
	}

	public void setComplete(char complete) {
		this.complete = complete;
	}

	public Date getIssued() {
		return issued;
	}

	public void setIssued(Date issued) {
		this.issued = issued;
	}

	public Date getExpired() {
		return expired;
	}

	public void setExpired(Date expired) {
		this.expired = expired;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public boolean isFailed() {
		return isFailed;
	}

	public void setIsFailed(boolean isFailed) {
		this.isFailed = isFailed;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public Mission clone() {
		return new Mission(complete, isFailed, m_id, issued, expired, time,
				tasks);
	}

	public void addTask(Task task) {
		this.tasks.add(task);
	}

	public void serialize(ByteBuffer bb) {
		bb.putChar(complete);
		if (isFailed) {
			bb.putChar('t');
		} else {
			bb.putChar('f');
		}
		bb.putInt(m_id);

		if (null != issued) {
			bb.putLong(issued.getTime());
		} else {
			bb.putLong(0);
		}

		if (null != expired) {
			bb.putLong(expired.getTime());
		} else {
			bb.putLong(0);
		}

		if (null != time) {
			bb.putLong(time.getTime());
		} else {
			bb.putLong(0);
		}

		bb.putShort((short) tasks.size()); // 길이
		for (int i = 0; i < tasks.size(); i++) {
			Task t = tasks.get(i);
			t.serialize(bb);
		}

		BooleanUtil.putBoolean(bb, isNew);
	}

	public static Mission deserialize(ByteBuffer bb) {
		char complete = bb.getChar();
		char failed = bb.getChar();
		boolean isFailed = true;
		if ('t' == failed) {
			isFailed = true;
		} else {
			isFailed = false;
		}
		int m_id = bb.getInt();

		long issued = bb.getLong();
		long expired = bb.getLong();
		long time = bb.getLong();

		final short length = bb.getShort();
		List<Task> list = new ArrayList<>();
		for (int i = 0; i < length; ++i) {
			Task t = Task.deserialize(bb);
			list.add(t);
		}

		boolean isNew = BooleanUtil.getBoolean(bb);

		Mission mission = new Mission(complete, isFailed, m_id,
				new Date(issued), new Date(expired), new Date(time), list);
		mission.setNew(isNew);
		return mission;
	}

	public static List<Mission> deserializeList(ByteBuffer bb) {
		int size = bb.getInt();
		List<Mission> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			Mission b = Mission.deserialize(bb);
			list.add(b);
		}
		return list;
	}

	public boolean isUseCash() {
		return isUseCash;
	}

	public void setUseCash(boolean isUseCash) {
		this.isUseCash = isUseCash;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
}
