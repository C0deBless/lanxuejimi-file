package packet;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import session.AioSession;

public class Packet {
	private static final Logger logger = LoggerFactory.getLogger(Packet.class);

	private final int command;
	private byte[] data;
	private AioSession session;
	private int count;
	private int packetId;
	private int index;
	private int sessionId;

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public int getPacketId() {
		return packetId;
	}

	public void setPacketId(int sessionId) {
		this.packetId = sessionId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Packet(int command, byte[] data, AioSession session) {
		this.command = command;
		this.data = data;
		this.session = session;
	}

	public int command() {
		return command;
	}

	public String data() {
		try {
			return new String(data, "utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("Packet.data, " + e.getMessage());
			return null;
		}
	}

	public byte[] getByte() {
		return this.data;
	}

	public void setByte(byte[] b) {
		this.data = b;
	}

	public AioSession getSession() {
		return session;
	}

	public void session(AioSession session) {
		this.session = session;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public ByteBuffer getByteBuffer() {
		return ByteBuffer.wrap(getByte());
	}
}
