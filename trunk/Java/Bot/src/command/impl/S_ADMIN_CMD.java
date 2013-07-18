package command.impl;

import packet.Packet;
import command.CommandBinder;

public class S_ADMIN_CMD implements CommandBinder {

	@Override
	public void execute(Packet packet) throws Exception {
		String json = new String(packet.getByte());
	}

}
