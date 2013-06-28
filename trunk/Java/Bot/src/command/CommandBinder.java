package command;

import packet.Packet;

public interface CommandBinder {
	public void execute(Packet packet) throws Exception;
}
