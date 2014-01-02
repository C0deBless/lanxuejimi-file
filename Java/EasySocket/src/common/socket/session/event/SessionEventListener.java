package common.socket.session.event;

import java.util.List;

import common.socket.packet.Packet;

public interface SessionEventListener {
	void onClose();
	void onReceivePackets(List<Packet> packets);
}