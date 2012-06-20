package com.trnnn.http.session;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.UUID;

import com.trnnn.http.HttpServer;
import com.trnnn.http.console.Console;
import com.trnnn.http.request.RequestBuilder;

public class ClientSession {

	private final String sessionId;
	private final AsynchronousSocketChannel channel;
	private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
	private RequestBuilder builder;
	private HttpServer server;

	private ClientSession(AsynchronousSocketChannel channel) {
		UUID uuid = UUID.randomUUID();
		this.sessionId = uuid.toString().replace("-", "");
		this.channel = channel;
		this.builder = new RequestBuilder(this.readBuffer, this);
	}

	public static ClientSession open(AsynchronousSocketChannel channel,
			HttpServer httpServer) {

		ClientSession clientSession = new ClientSession(channel);
		clientSession.pendingRead();
		clientSession.server = httpServer;
		Console.output("ClientSession::open", "session opened. sessionId ->> "
				+ clientSession.sessionId);
		return clientSession;
	}

	public void pendingRead() {
		beforeRead();
		this.channel.read(readBuffer, this, new ReadCompletionHandler());
	}

	public void pendingWrite(byte[] data) {
		this.channel.write(ByteBuffer.wrap(data), this,
				new WriteCompletionHandler());
	}

	private void beforeRead() {
		builder.queue();
	}

	public String id() {
		return this.sessionId;
	}

	public void close() {
		try {
			Console.output("[ClientSession::close] session closed. sessionId ->> "
					+ sessionId);
			this.channel.close();
			this.server.removeSession(this.sessionId);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	class ReadCompletionHandler implements
			CompletionHandler<Integer, ClientSession> {

		@Override
		public void completed(Integer result, ClientSession session) {
			if (result > 0) {
				session.pendingRead();
			} else {
				session.close();
			}
		}

		@Override
		public void failed(Throwable exc, ClientSession session) {
			exc.printStackTrace();
		}
	}

	class WriteCompletionHandler implements
			CompletionHandler<Integer, ClientSession> {

		@Override
		public void completed(Integer result, ClientSession session) {

		}

		@Override
		public void failed(Throwable exc, ClientSession session) {
			exc.printStackTrace();
		}

	}

}
