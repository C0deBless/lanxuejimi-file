package simplegs.packet;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import simplegs.GameServer;
import simplegs.session.ClientSession;

public class DefaultPacketBuilder extends PacketBuilder {

	public DefaultPacketBuilder(GameServer gameServer) {
		super(gameServer);
	}

	@Override
	public void queue(Packet packet) {
		gameServer.getPacketProcessPool().dispatchPacket(packet);
	}

	@Override
	public List<Packet> parse(ByteBuffer buffer, ClientSession session) {

		// if (this.packetFormat == null) {
		// this.initDefaultPacketFormat();
		// }

		// int cmdSize = this.packetFormat.getCmdSize();
		// int maxPacketSize = this.packetFormat.getMaxPacketSize();

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

					packets.add(new Packet(cmd, data, session));
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
