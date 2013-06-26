import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class BotMain {

	public static void main(String[] args) throws IOException,
			InterruptedException {
		final AsynchronousSocketChannel channel = AsynchronousSocketChannel
				.open();
		channel.connect(new InetSocketAddress("www.easymode.com", 8000), null,
				new CompletionHandler<Void, Object>() {

					@Override
					public void completed(Void result, Object attachment) {
						System.out.println("server connected");
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
