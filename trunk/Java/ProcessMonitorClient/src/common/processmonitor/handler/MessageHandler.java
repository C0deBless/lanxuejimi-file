package common.processmonitor.handler;

import java.nio.ByteBuffer;
import java.util.List;

import common.processmonitor.analysis.SnapshotAnalyzer;
import common.processmonitor.session.AioSession;

public class MessageHandler {

	PacketBuilder packetBuilder;

	public MessageHandler() {
		this.packetBuilder = new PacketBuilder() {
			@Override
			public void queue(ByteBuffer buffer, AioSession session) {
				List<Packet> packets = parse(buffer, session);
				if (packets == null || packets.size() <= 0)
					return;
				for (Packet packet : packets) {
					handle(packet);
				}
			}
		};
	}

	public void queue(ByteBuffer buffer, AioSession session) {
		this.packetBuilder.queue(buffer, session);
	}

	public void handle(Packet packet) {
		System.out.println(new String(packet.getData()));
		if (packet.getCmd() == Packet.SNAPSHOT) {
			SnapshotAnalyzer.analyze(packet);
		}
	}
}
