package aio;

import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

class AcceptCompletionHandler implements
		CompletionHandler<AsynchronousSocketChannel, Object> {

	@Override
	public void completed(AsynchronousSocketChannel channel, Object attachment) {
		int sessionId = Server.indexGenerator.addAndGet(1);
		System.out.println("有新的客户端连接, " + Thread.currentThread().getName()
				+ ", sessionId:" + sessionId);
		SocketSession session = new SocketSession(sessionId, channel);
		Server.CLIENT_POOL.put(sessionId, session);
		Server.server.accept(null, this);

		channel.read(session.getReadBuffer(), session, new ReadCompletionHandler());
	}

	@Override
	public void failed(Throwable exc, Object attachment) {
		System.err.println("出错了");
		exc.printStackTrace();
	}
}