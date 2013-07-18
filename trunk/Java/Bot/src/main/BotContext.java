package main;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import session.AioSession;
import command.Command;
import command.CommandFactory;
import common.struct.UserBase;
import common.tools.StringUtil;

public class BotContext {

	static Logger logger = LoggerFactory.getLogger("bot");
	private final AioSession session;

	public static final CommandFactory commandFactory = new CommandFactory();
	private final long userId;
	private UserBase user;

	public BotContext(AioSession session, long userId) {
		this.session = session;
		this.userId = userId;
	}

	public AioSession getSession() {
		return this.session;
	}

	public void login(long userId, String sig) {
		logger.info("do login, userId:{}, sig:{}", userId, sig);
		String json = String.format("{\"user_id\":%d,\"sig\":\"%s\"}", userId,
				sig);
		session.responseJson(Command.C_LOGIN, json);
	}

	public CommandFactory getCommandFactory() {
		return commandFactory;
	}

	public void adminAddLevel() {
		this.sendChat("/level 50", session);
	}

	public void adminAddBase() {
		this.sendChat("/addbase", session);
	}

	public void adminAddShip() {
		int id = 200002;
		for (int i = 0; i < 6; i++) {
			this.sendChat("/addfleet " + id, session);
		}
	}

	public void adminAddNpc() {
		int minId = 246001;
		int maxId = 246020;
		for (int id = minId; id <= maxId; id++) {
			this.sendChat("/npc " + id, session);
		}
	}

	public void sendChat(String msg, AioSession session) {
		ByteBuffer bb = ByteBuffer.allocate(1000);
		StringUtil.putString(bb, "l");
		StringUtil.putString(bb, msg);
		StringUtil.putString(bb, "");
		bb.flip();
		session.responseBinary(Command.C_CHAT, bb);
	}

	public void requestUserData() {
		session.responseJson(Command.C_UDATAREQ, "");
	}

	public void requestCreateAvatar() {
		session.responseJson(Command.C_CREATE_AVATAR, "");
	}

	public long getUserId() {
		return userId;
	}

	public UserBase getUser() {
		return user;
	}

	public void setUser(UserBase user) {
		this.user = user;
	}
}
