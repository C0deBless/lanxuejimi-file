package aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {

	private static final int PORT = 8000;
	public static AsynchronousServerSocketChannel server;
	public static AtomicInteger indexGenerator = new AtomicInteger(100);
	public static Map<Integer, SocketSession> CLIENT_POOL = new ConcurrentHashMap<>();

	public static void main(String[] args) throws IOException,
			InterruptedException {
		server = AsynchronousServerSocketChannel.open();
		server.bind(new InetSocketAddress(PORT));
		server.accept(null, new AcceptCompletionHandler());
		System.out.println("server start accept."
				+ Thread.currentThread().getName());
		Thread.currentThread().join();
	}
}
