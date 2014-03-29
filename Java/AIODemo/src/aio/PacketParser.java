package aio;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class PacketParser {

	public static List<Packet> parse(SocketSession session) {
		ByteBuffer readBuffer = session.getReadBuffer();
		readBuffer.position(0);

		List<Packet> resultList = new ArrayList<>();
		ByteBuffer remainingBuffer = session.getRemainingBuffer();
		if (remainingBuffer == null) {
			remainingBuffer = ByteBuffer.allocate(128);
			session.setRemainingBuffer(remainingBuffer);
		}

		// TODO 如果容量不够 需要扩容
		if (remainingBuffer.limit() - remainingBuffer.position() < readBuffer
				.limit()) {
			ByteBuffer tmpBuffer = ByteBuffer.allocate(remainingBuffer.position() + readBuffer.limit());
		}

		remainingBuffer.put(readBuffer);

		for (;;) {
			int remaingLength = remainingBuffer.limit();
			int packetLength = remainingBuffer.getInt(0);

			if (packetLength <= remaingLength) {
				//
				remainingBuffer.getInt();
				byte[] data = new byte[packetLength - 4];
				remainingBuffer.get(data, 0, data.length);
				Packet packet = new Packet();
				packet.setData(data);
				resultList.add(packet);
				remainingBuffer.compact();
			} else if (packetLength > remaingLength) {
				break;
			}
		}

		readBuffer.clear();
		return resultList;
	}
}
