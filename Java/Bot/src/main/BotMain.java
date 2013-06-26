package main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import main.AioSession.SessionType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BotMain {

	static Logger logger = LoggerFactory.getLogger("bot");

	public static void main(String[] args) throws IOException,
			InterruptedException {
		final AsynchronousSocketChannel channel = AsynchronousSocketChannel
				.open();
		channel.connect(new InetSocketAddress("www.easymode.com", 8000), null,
				new CompletionHandler<Void, Object>() {

					@Override
					public void completed(Void result, Object attachment) {
						logger.trace("server connected");
						AioSession session = new AioSession(channel,
								SessionType.CLIENT_SESSION) {
							@Override
							protected void queuePacket(ByteBuffer buffer) {

							}
						};
						startBot(session);
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

	private static void startBot(AioSession session) {
		// do start
		BotContext context = new BotContext(session);
	}
}
