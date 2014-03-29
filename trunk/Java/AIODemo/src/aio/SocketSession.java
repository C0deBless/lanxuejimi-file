package aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

public class SocketSession {

	private final int sessionId;
	private final  AsynchronousSocketChannel client;
	private final ByteBuffer readBuffer = ByteBuffer.allocate(30);
	private ByteBuffer remainingBuffer; 
	
	public SocketSession(int sessionId, AsynchronousSocketChannel channel){
		this.sessionId = sessionId;
		this.client = channel;
	}

	public int getSessionId() {
		return sessionId;
	}

	public AsynchronousSocketChannel getClient() {
		return client;
	}

	public ByteBuffer getReadBuffer() {
		return readBuffer;
	}

	public ByteBuffer getRemainingBuffer() {
		return remainingBuffer;
	}

	public void setRemainingBuffer(ByteBuffer remainingBuffer) {
		this.remainingBuffer = remainingBuffer;
	}
}
