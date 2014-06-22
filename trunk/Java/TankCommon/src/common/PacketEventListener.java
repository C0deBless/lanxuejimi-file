package common;

import java.util.List;

public interface PacketEventListener {
	public void receive(List<Packet> packet);
}
