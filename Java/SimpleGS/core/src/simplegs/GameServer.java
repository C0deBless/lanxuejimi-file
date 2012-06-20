package simplegs;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

import simplegs.command.ICommandFactory;
import simplegs.handler.OnRead;
import simplegs.handler.OnWrite;
import simplegs.log.Log;
import simplegs.packet.PacketBuilderFactory;
import simplegs.packet.PacketProcessPool;
import simplegs.session.ClientSession;
import simplegs.thread.ServerThreadGroup;

public class GameServer {

	private int port;
	private final AsynchronousServerSocketChannel listener;
	private final AsynchronousChannelGroup channelGroup;

	private final PacketProcessPool packetProcessPool;

	private final ServerThreadGroup threadGroup;

	public final Map<Integer, ClientSession> CLIENT_SESSION_POOL = new ConcurrentHashMap<Integer, ClientSession>();

	// constructor
	public GameServer(int port, ICommandFactory commandFactory)
			throws IOException {
		Log.info("gameserver start: port {}", port);
		threadGroup = ServerThreadGroup.init("GameServer-" + port);
		packetProcessPool = PacketProcessPool.init(threadGroup, commandFactory);

		channelGroup = AsynchronousChannelGroup.withFixedThreadPool(Runtime
				.getRuntime().availableProcessors(), Executors
				.defaultThreadFactory());
		this.port = port;
		listener = createListener(channelGroup);

	}

	// create listener
	private AsynchronousServerSocketChannel createListener(
			AsynchronousChannelGroup channelGroup) throws IOException {
		final AsynchronousServerSocketChannel ssc = AsynchronousServerSocketChannel
				.open(channelGroup);

		ssc.bind(new InetSocketAddress(port));

		return ssc;
	}

	public void run() {
		listener.accept(null,
				new CompletionHandler<AsynchronousSocketChannel, Void>() {

					@Override
					public void completed(AsynchronousSocketChannel ch,
							Void attachment) {
						listener.accept(null, this);
						handleNewConnection(ch);
					}

					@Override
					public void failed(Throwable arg0, Void arg1) {
						String msg = "[SocketServer.run] CompletionHandler failed()";
						if (null != arg0) {
							arg0.printStackTrace();
							msg = msg + " arg0: " + arg0.getMessage() + ", "
									+ arg0.getLocalizedMessage();
						}
						if (null != arg1) {
							msg = msg + " arg1:" + arg1;
						}
						Log.error(msg);
						// listener.accept(null, this);
					}

				});

	}

	protected void handleNewConnection(AsynchronousSocketChannel channel) {
		if (channel.isOpen()) {
			try {
				channel.setOption(StandardSocketOptions.TCP_NODELAY, true);
			} catch (IOException e) {
				// ignore
			}

			ClientSession session = ClientSession.open(channel, new OnRead(),
					new OnWrite(),
					PacketBuilderFactory.getDefaultPacketBuilder(this));

			CLIENT_SESSION_POOL.put(session.getSessionId(), session);

			session.pendingRead();
		} else {
			Log.info("Server: Connection closed.");
		}
	}

	public ServerThreadGroup getThreadGroup() {
		return threadGroup;
	}

	public PacketProcessPool getPacketProcessPool() {
		return packetProcessPool;
	}
}
