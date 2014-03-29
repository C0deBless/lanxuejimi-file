package aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;
import java.util.List;

class ReadCompletionHandler implements
		CompletionHandler<Integer, SocketSession> {

	@Override
	public void completed(Integer result, SocketSession session) {
		if (result == 0) {
			System.out.println("客户端断开");
			return;
		}
		System.out.println("读取到数据, " + Thread.currentThread().getName());
		ByteBuffer buffer = session.getReadBuffer();
		System.out.println("buffer pos:" + buffer.position() + ", result:"
				+ result);

		List<Packet> packets = PacketParser.parse(session);
		// TODO packets

		session.getClient().read(session.getReadBuffer(), session, this);

	}

	@Override
	public void failed(Throwable exc, SocketSession session) {
		System.out.println("读数据错误");
		exc.printStackTrace();
		try {
			session.getClient().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}