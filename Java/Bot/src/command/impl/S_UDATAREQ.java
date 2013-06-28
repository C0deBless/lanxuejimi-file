package command.impl;

import main.BotMain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import packet.Packet;

import command.CommandBinder;
import common.struct.UserBase;
import common.tools.JsonHelper;

public class S_UDATAREQ implements CommandBinder {

	static Logger logger = LoggerFactory.getLogger("bot");

	@Override
	public void execute(Packet packet) throws Exception {
		String json = new String(packet.getByte()); 
		logger.info("receive user data");
		UserBase user = JsonHelper.deserialize(json, UserBase.class);
		BotMain.context.setUser(user);
	}

}
