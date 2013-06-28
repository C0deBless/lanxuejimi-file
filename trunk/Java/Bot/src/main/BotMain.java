package main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import packet.Packet;
import packet.PacketBuilder;
import session.AioSession;
import session.AioSession.SessionState;
import session.AioSession.SessionType;

public class BotMain {

	static Logger logger = LoggerFactory.getLogger("bot");
	public static BotContext context;
	private static final long userId = 100003075441353l;
	private static final String sig = "e6a29e1d5cd835eb2e558cd30c025d08";
	private static final PacketBuilder PACKET_BUILDER = new PacketBuilder() {

		@Override
		public void queue(ByteBuffer buffer, AioSession session) {
			List<Packet> packets = parse(buffer, session);
			context.getCommandFactory().pushPacket(packets);
		}
	};

	public static void main(String[] args) throws IOException,
			InterruptedException {
		final AsynchronousSocketChannel channel = AsynchronousSocketChannel
				.open();
		final String addr = "www.easymode.com";
		final int port = 8000;
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
		Thread.currentThread().join();
	}

	private static void startBot(AioSession session, long userId, String sig) {
		// do start
		BotContext ctx = new BotContext(session, userId);
		session.pendingRead();
		context = ctx;
		context.login(userId, sig);
	}
}
