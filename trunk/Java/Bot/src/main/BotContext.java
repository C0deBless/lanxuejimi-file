package main;

import command.CommandFactory;

public class BotContext {

	private final AioSession session;

	private final CommandFactory commandFactory;

	public BotContext(AioSession session) {
		this.session = session;
		this.commandFactory = new CommandFactory(this);
	}

	public AioSession getSession() {
		return this.session;
	}

	public void login(long userId, String sig) {
		String json = String.format("{\"user_id\":%d,\"sig\":\"%s\"}", userId,
				sig);

	}
}
