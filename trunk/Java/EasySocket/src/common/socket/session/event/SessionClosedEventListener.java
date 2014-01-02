package common.socket.session.event;

import java.util.List;

import common.socket.packet.Packet;

public abstract class SessionClosedEventListener implements SessionEventListener {
	@Override
	public abstract void onClose();

	@Override
	public void onReceivePackets(List<Packet> packets) {
		// do nothing
	}
}
