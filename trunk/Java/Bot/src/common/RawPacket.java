package common;

import java.nio.ByteBuffer;

public class RawPacket {
	private ByteBuffer buffer;

	public RawPacket(short cmd, long userId, int argc) {
		buffer = ByteBuffer.allocate(32 + (argc * 32));
		buffer.putShort((short)0).putShort(cmd).putLong(userId);
	}
	
	public void put(int value) {
		buffer.putInt(value);
	}
	
	public void put(long value) {
		buffer.putLong(value);
	}
	
	public void put(String value) {
		buffer.putShort((short)value.getBytes().length);
		buffer.put(value.getBytes());
	}
	
	public byte[] made() {
		short len = (short)buffer.position();
		byte[] ret = new byte[len];
		buffer.putShort(0, len).flip();
		buffer.get(ret);
		return ret;
	}
}
