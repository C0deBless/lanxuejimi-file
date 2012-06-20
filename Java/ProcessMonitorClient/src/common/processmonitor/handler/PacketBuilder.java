package common.processmonitor.handler;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import common.processmonitor.session.AioSession;

public abstract class PacketBuilder {

	public abstract void queue(ByteBuffer buffer, AioSession session);

	public static List<Packet> parse(ByteBuffer buffer, AioSession session) {
		List<Packet> packets = new ArrayList<>();

		boolean loop = true;
		int remaining = 0;

		while (loop) {
			remaining = buffer.remaining();

			if (remaining >= 4) {
				short len = buffer.getShort(buffer.position());

				if (remaining >= len) {
					buffer.getShort(); // to forward position
					short cmd = buffer.getShort();
					byte[] data = new byte[len - 4];
					buffer.get(data);

					packets.add(new Packet(cmd, data));
				} else {
					loop = false;
				}
			} else {
				loop = false;
			}
		}

		buffer.compact();
		buffer.clear();
		buffer.position(remaining);

		return packets;
	}
}
