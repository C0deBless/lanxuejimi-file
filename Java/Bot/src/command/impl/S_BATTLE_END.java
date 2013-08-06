package command.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import packet.Packet;

import command.CommandBinder;

public class S_BATTLE_END implements CommandBinder {

	static Logger logger = LoggerFactory.getLogger(S_BATTLE_END.class);

	@Override
	public void execute(Packet packet) throws Exception {
		String json = new String(packet.getByte());
		logger.info("battle end:{}", json);
	}

}
