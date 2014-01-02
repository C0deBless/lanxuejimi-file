package common.socket.session.event;

import java.util.List;

import common.socket.packet.Packet;

public abstract class SessionReceivedPacketEventListener implements
		SessionEventListener {
	@Override
	public void onClose() {
		// do nothing
	}

	@Override
	public abstract void onReceivePackets(List<Packet> packets);

}
