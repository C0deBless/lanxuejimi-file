package common;

import java.nio.ByteBuffer;

public class Packet {
	private Client client;
	private final short cmd;
	private final ByteBuffer buffer;

	public Packet(short cmd) {
		this(cmd, Short.MAX_VALUE);
	}

	// 接收数据用的
	public Packet(short cmd, byte[] data, Client client) {
		this.cmd = cmd;
		buffer = ByteBuffer.wrap(data);
		this.client = client;
	}

	// 发送数据用的
	public Packet(short cmd, int initialBufferSize) {
		this.cmd = cmd;
		buffer = ByteBuffer.allocate(initialBufferSize);
	}

	public ByteBuffer getByteBuffer() {
		return this.buffer;
	}

	public short getCmd() {
		return cmd;
	}

	public Client getClient() {
		return client;
	}
}
