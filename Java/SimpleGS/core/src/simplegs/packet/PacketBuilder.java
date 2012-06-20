package simplegs.packet;

import java.nio.ByteBuffer;
import java.util.List;

import simplegs.GameServer;
import simplegs.log.Log;
import simplegs.session.ClientSession;

public abstract class PacketBuilder {

	public PacketBuilder(GameServer gameServer) {
		this.gameServer = gameServer;
	}

	protected GameServer gameServer;
	protected PacketFormat packetFormat;

	protected void initDefaultPacketFormat() {
		Log.warn("PacketBuilder, packetFormat has not been initialized. making a new PacketFormat");
		this.packetFormat = new PacketFormat(2, Short.MAX_VALUE);
	}

	public abstract void queue(Packet packet);

	public abstract List<Packet> parse(ByteBuffer buffer, ClientSession session);

	public void queue(List<Packet> packets) {
		if (packets == null || packets.size() <= 0) {
			return;
		}
		for (Packet packet : packets) {
			this.queue(packet);
		}
	}
}
