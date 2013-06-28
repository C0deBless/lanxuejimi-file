package main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import session.AioSession;

import command.Command;
import command.CommandFactory;
import common.struct.UserBase;

public class BotContext {

	static Logger logger = LoggerFactory.getLogger("bot");
	private final AioSession session;

	private final CommandFactory commandFactory;
	private final long userId;
	private UserBase user;

	public BotContext(AioSession session, long userId) {
		this.session = session;
		this.commandFactory = new CommandFactory(this);
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

	public void requestUserData() {
		logger.info("request user data");
		session.responseJson(Command.C_UDATAREQ, "");
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
