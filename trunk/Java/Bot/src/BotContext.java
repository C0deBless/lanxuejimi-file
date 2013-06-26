public class BotContext {

	private final AioSession session;

	public BotContext(AioSession session) {
		this.session = session;
	}

	public AioSession getSession() {
		return this.session;
	}

	public void login(long userId, String sig) {
		String json = String.format("{\"user_id\":%d,\"sig\":\"%s\"}", userId,
				sig);

	}
}
