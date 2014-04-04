package stresstest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import easysocket.session.AioTcpSession;

public class ClientPool implements Runnable {

	class ConnectCompletionHandler implements
			CompletionHandler<Void, AsynchronousSocketChannel> {

		@Override
		public void completed(Void arg0, AsynchronousSocketChannel channel) {
			try {
				AioTcpSession session = new AioTcpSession(channel);
				pool.put(session.getSessionId(), session);
				System.out
						.println("client connected:" + session.getSessionId());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void failed(Throwable arg0, AsynchronousSocketChannel channel) {

		}

	}

	private Map<Integer, AioTcpSession> pool = new ConcurrentHashMap<>();
	private final String serverHost;
	private final int serverPort;
	private final int clientCount;
	private ConnectCompletionHandler connectHandler = new ConnectCompletionHandler();

	// private Thread thread;

	public ClientPool(String serverHost, int serverPort, int clientCount) {
		// thread = new Thread(this);
		// thread.start();
		this.serverHost = serverHost;
		this.serverPort = serverPort;
		this.clientCount = clientCount;
	}

	public void start() throws IOException {
		InetSocketAddress addr = new InetSocketAddress(this.serverHost,
				this.serverPort);
		for (int i = 0; i < this.clientCount; i++) {
			AsynchronousSocketChannel channel = AsynchronousSocketChannel
					.open();
			channel.connect(addr, channel, connectHandler);
		}
	}

	@Override
	public void run() {

	}
}
