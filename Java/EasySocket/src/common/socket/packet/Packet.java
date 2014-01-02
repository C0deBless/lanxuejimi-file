package common.socket.packet;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.socket.session.AioTcpession;

public class Packet {
	static final Logger logger = LoggerFactory.getLogger(Packet.class);
	private final short command;
	private byte[] data;
	private AioTcpession session;

	public Packet(short command, byte[] data, AioTcpession session) {
		this.command = command;
		this.data = data;
		this.session = session;
	}

	public short command() {
		return command;
	}

	public byte[] getByte() {
		return this.data;
	}

	public void setByte(byte[] b) {
		this.data = b;
	}

	public AioTcpession getSession() {
		return session;
	}

	public void session(AioTcpession session) {
		this.session = session;
	}

	public ByteBuffer getByteBuffer() {
		return ByteBuffer.wrap(getByte());
	}
}
