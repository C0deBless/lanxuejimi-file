package simplegs.packet;

import simplegs.GameServer;

public class PacketBuilderFactory {

	public static PacketBuilder getDefaultPacketBuilder(GameServer gameServer) {
		PacketBuilder packetBuilder = new DefaultPacketBuilder(gameServer);
		return packetBuilder;
	}

}
