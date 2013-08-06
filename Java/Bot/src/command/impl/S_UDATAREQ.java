package command.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import packet.Packet;
import session.AioSession;

import command.CommandBinder;
import common.struct.UserBase;
import common.tools.JsonHelper;

public class S_UDATAREQ implements CommandBinder {

	static Logger logger = LoggerFactory.getLogger("bot");

	@Override
	public void execute(Packet packet) throws Exception {
		AioSession session = packet.getSession();
		String json = new String(packet.getByte());
		logger.info("receive user data, userId:{}", session.getContext()
				.getUserId());
		UserBase user = JsonHelper.deserialize(json, UserBase.class);
		session.getContext().setUser(user);
		// JsonHelper.
		logger.error("user data:{}", json);
		// prepareDummyData(session);
	}

	// private void prepareDummyData(AioSession session) {
	// BotContext context = session.getContext();
	// context.adminAddLevel();
	// context.adminAddNpc();
	// context.adminAddBase();
	// context.adminAddShip();
	//
	// }
}
