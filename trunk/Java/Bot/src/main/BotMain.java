package main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import packet.Packet;
import packet.PacketBuilder;
import session.AioSession;
import session.AioSession.SessionState;
import session.AioSession.SessionType;

public class BotMain {

	static Logger logger = LoggerFactory.getLogger("bot");
	private static final long userId = 127580510;
	private static final String sig = "4b681c648e9987dd8072e346499953e9";
	private static final PacketBuilder PACKET_BUILDER = new PacketBuilder() {

		@Override
		public void queue(ByteBuffer buffer, AioSession session) {
			List<Packet> packets = parse(buffer, session);
			session.getContext().getCommandFactory().pushPacket(packets);
		}
	};

	public static final Map<Long, BotContext> pool = new ConcurrentHashMap<>();

	public static void main(String[] args) throws IOException,
			InterruptedException {

		openChannel(userId);
		Thread.currentThread().join();
	}

	private static void openChannel(final long userId) throws IOException {
		final AsynchronousSocketChannel channel = AsynchronousSocketChannel
				.open();
		final String addr = "ec2-107-21-127-76.compute-1.amazonaws.com";// "www.easymode.com";
		final int port = 8030;// 8300;
		channel.connect(new InetSocketAddress(addr, port), null,
				new CompletionHandler<Void, Object>() {

					@Override
					public void completed(Void result, Object attachment) {
						logger.info("server connected, addr:{}, port:{}", addr,
								port);
						final AioSession session = new AioSession(channel,
								SessionType.CLIENT_SESSION) {
							@Override
							protected void queuePacket(ByteBuffer buffer) {
								PACKET_BUILDER.queue(buffer, this);
							}
						};
						session.sessionState.set(SessionState.OPENED);
						startBot(session, userId, sig);
					}

					@Override
					public void failed(Throwable exc, Object attachment) {
						try {
							channel.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
	}

	private static void startBot(AioSession session, long userId, String sig) {
		// do start
		BotContext ctx = new BotContext(session, userId);
		session.pendingRead();
		session.setContext(ctx);
		ctx.login(userId, sig);
		pool.put(userId, ctx);
	}
}
