package com.trnnn.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.trnnn.http.config.ServerConfig;
import com.trnnn.http.console.Console;
import com.trnnn.http.session.ClientSession;

public class HttpServer {

	public HttpServer(ServerConfig config) {
		if (config == null) {
			throw new IllegalArgumentException("config can't be null");
		}
		this.config = config;
	}

	private AsynchronousServerSocketChannel listener;
	private AsynchronousChannelGroup group;
	private ServerConfig config;
	private final ConcurrentHashMap<String, ClientSession> sessionPool = new ConcurrentHashMap<>();

	public void run() throws IOException {
		Console.output("HttpServer-" + config.name(),
				"server started. port ->> " + config.port());

		ExecutorService executor = Executors.newCachedThreadPool();
		group = AsynchronousChannelGroup.withCachedThreadPool(executor, 100);
		listener = AsynchronousServerSocketChannel.open(group);
		listener.bind(new InetSocketAddress(config.port()));
		listener.accept(null,
				new CompletionHandler<AsynchronousSocketChannel, Void>() {

					@Override
					public void completed(AsynchronousSocketChannel result,
							Void session) {
						listener.accept(null, this);
						handleNewConnection(result);
					}

					@Override
					public void failed(Throwable exc, Void session) {
						exc.printStackTrace();
					}

				});
	}

	public void handleNewConnection(AsynchronousSocketChannel channel) {
		ClientSession session = ClientSession.open(channel, this);
		this.sessionPool.put(session.id(), session);
	}

	public ClientSession client(String id) {
		return this.sessionPool.get(id);
	}

	public void removeSession(String id){
		this.sessionPool.remove(id);
	}
}
