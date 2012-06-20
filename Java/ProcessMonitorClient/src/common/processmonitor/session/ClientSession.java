package common.processmonitor.session;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import common.processmonitor.handler.MessageHandler;

public class ClientSession extends AioSession {

	private ByteBuffer readBuffer = ByteBuffer.allocate(128);
	private MessageHandler onMessage;

	public ClientSession(AsynchronousSocketChannel channel,
			CompletionHandler<Integer, AioSession> onRead,
			CompletionHandler<Integer, AioSession> onWrite,
			MessageHandler onMessage) {
		super(channel, onRead, onWrite);
		this.onMessage = onMessage;
	}

	private void beforeRead() {
		readBuffer.flip();
		if (readBuffer.hasRemaining()) {
			onMessage.queue(this.readBuffer, this);
			if (readBuffer.position() > 2
					&& readBuffer.getShort(0) > readBuffer.capacity()) {
				extendReadBuffer();
			}
		} else {
			readBuffer.clear();
		}
	}

	private void extendReadBuffer() {
		ByteBuffer tmp = readBuffer;
		int pos = tmp.position();
		tmp.position(0);

		readBuffer = ByteBuffer.allocate(readBuffer.capacity() * 2);
		readBuffer.put(tmp.array(), 0, pos);
	}

	@Override
	public void pendingRead() {
		beforeRead();
		this.channel.read(this.readBuffer, this, this.onRead);
	}

	@Override
	public void close() {

	}

}
