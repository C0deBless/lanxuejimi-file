import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AioSession {

	static final Logger logger = LoggerFactory.getLogger(AioSession.class);

	public static enum SessionType {
		CLIENT_SESSION, DATABASE_SESSION,
	}

	public static class SessionState {

		public static final int UNKNOWN = 0;
		public static final int OPENED = 1;
		public static final int CLOSED = 2;
		public static final int PACKETPROCESSING = 3;
		public static final int PACKETBLOCKED = 4;

		private int state0;

		public SessionState(int state) {
			this.state0 = state;
		}

		public void set(int state) {
			this.state0 = state;
		}

		public int getState() {
			return this.state0;
		}
	}

	public static interface SessionEventListener {
		void onClose();
	}

	private class WriteCompletionHandler implements
			CompletionHandler<Integer, AioSession> {
		@Override
		public void completed(Integer result, AioSession session) {
			if (result < 0 || !session.getChannel().isOpen()) {
				try {
					close();
				} catch (IOException e) {
					logger.error("WriteCompletionHandler.completed, msg:"
							+ e.getMessage());
				}
				return;
			}
			if (writeBuffer != null) {
				writeBuffer.position(result);
			}
			isWriting(false);
			write();
		}

		@Override
		public void failed(Throwable exc, AioSession session) {
			try {
				close();
			} catch (IOException e) {
				logger.error("WriteCompletionHandler.failed, msg:"
						+ e.getMessage());
			}
		}
	}

	private class ReadComletionHandler implements
			CompletionHandler<Integer, AioSession> {
		@Override
		public void completed(Integer result, AioSession session) {
			try {
				if (result > 0) {
					session.pendingRead();
				} else {
					session.close();
				}
			} catch (IOException e) {
				logger.error("ReadComletionHandler.completed. msg:"
						+ e.getMessage());
				try {
					session.close();
				} catch (IOException e1) {
					logger.error("ReadComletionHandler.completed. msg:"
							+ e1.getMessage());
				}
			} catch (Exception e) {
				logger.error("ReadComletionHandler.completed. msg:"
						+ e.getMessage());
				try {
					session.close();
				} catch (IOException e1) {
					logger.error("ReadComletionHandler.completed. msg:"
							+ e.getMessage());
				}
			}
		}

		@Override
		public void failed(Throwable exc, AioSession session) {
			try {
				session.close();
			} catch (IOException e) {
				logger.error("ReadComletionHandler.failed. msg:"
						+ e.getMessage());
			} catch (Exception e) {
				logger.error("ReadComletionHandler.failed. msg:"
						+ e.getMessage());
				try {
					session.close();
				} catch (IOException e1) {
					logger.error("ReadComletionHandler.failed. msg:"
							+ e.getMessage());
				}
			}
		}
	}

	private ByteBuffer readBuffer = ByteBuffer.allocate(256);
	protected AsynchronousSocketChannel channel;
	protected AtomicBoolean isWriting = new AtomicBoolean(false);
	protected final int sessionId;
	protected CompletionHandler<Integer, AioSession> readCompletionHandler;
	protected CompletionHandler<Integer, AioSession> writeCompletionHandler;
	private final ConcurrentLinkedQueue<ByteBuffer> outs = new ConcurrentLinkedQueue<>();
	private ByteBuffer writeBuffer;
	protected static final AtomicInteger sessionIndex = new AtomicInteger(1);
	public final SessionState sessionState = new SessionState(
			SessionState.UNKNOWN);
	private SessionEventListener listener = null;
	private final SessionType type;

	public void setEventListener(SessionEventListener listener) {
		this.listener = listener;
	}

	protected AioSession(AsynchronousSocketChannel channel, SessionType type) {
		this.sessionId = sessionIndex.addAndGet(1);
		this.readCompletionHandler = new ReadComletionHandler();
		this.writeCompletionHandler = new WriteCompletionHandler();
		this.channel = channel;
		this.sessionState.set(SessionState.OPENED);
		this.type = type;
	}

	protected abstract void queuePacket(ByteBuffer buffer);

	private void beforeRead(ByteBuffer readBuffer) {
		readBuffer.flip();
		if (readBuffer.hasRemaining()) {
			this.queuePacket(readBuffer);
			// Check readBuffer is full
			if (readBuffer.position() > 4
					&& readBuffer.getInt(0) > readBuffer.capacity()) {
				extendReadBuffer();
			}
		} else {
			readBuffer.clear();
		}
	}

	public final void pendingRead() {
		beforeRead(this.readBuffer);
		// if (this.channel.isOpen())
		this.channel.read(this.readBuffer, this, this.readCompletionHandler);
	}

	private final void extendReadBuffer() {
		ByteBuffer tmp = readBuffer;
		int pos = tmp.position();
		tmp.position(0);

		readBuffer = ByteBuffer.allocate(readBuffer.capacity() * 2);
		readBuffer.put(tmp.array(), 0, pos);
	}

	public void close() throws IOException {
		if (this.listener != null) {
			this.listener.onClose();
		}
		if (this.channel.isOpen())
			this.channel.close();
	}

	private ByteBuffer format(int command, byte[] data) {
		int size = 4 + 2 + data.length;
		ByteBuffer buffer = ByteBuffer.allocate(size);
		buffer.putInt(size);
		buffer.putShort((short) command);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	public void responseBinary(int command, byte[] data) {
		ByteBuffer buffer = this.format(command, data);
		this.pushWriteData(buffer);
	}

	public void responseJson(int command, String json) {
		byte[] data = json.getBytes();
		this.responseBinary(command, data);
	}

	public void responseBinary(int command, ByteBuffer bb) {
		byte[] data = bb.array();
		this.responseBinary(command, data);
	}

	protected final void pushWriteData(ByteBuffer buffer) {
		outs.add(buffer);
		write();
	}

	private final void write() {
		if (isWriting.compareAndSet(false, true)) {
			if (this.writeBuffer != null) {
				int pos = this.writeBuffer.position();
				int limit = this.writeBuffer.limit();
				if (pos >= limit) {
					this.writeBuffer = null;
				} else {
					this.writeBuffer.compact();
					this.writeBuffer.flip();
					this.channel.write(this.writeBuffer, this,
							this.writeCompletionHandler);
					return;
				}
			}
			ByteBuffer buffer = outs.poll();
			if (buffer != null) {
				this.writeBuffer = buffer;
				int cmd = this.writeBuffer
						.getShort(this.writeBuffer.position() + 4);
				if (cmd == 0) {
					logger.error(
							"AioSession.write, send packet cmd 0, data->{}",
							new String(this.writeBuffer.array()));
				}
				this.channel.write(buffer, this, this.writeCompletionHandler);
			} else {
				isWriting.set(false);
			}
		}
	}

	public final boolean isWriting() {
		return this.isWriting.get();
	}

	public final void isWriting(boolean isWriting) {
		this.isWriting.set(isWriting);
	}

	public final boolean isChannelOpen() {
		return channel.isOpen();
	}

	public final AsynchronousSocketChannel getChannel() {
		return channel;
	}

	public final void setChannel(AsynchronousSocketChannel channel) {
		this.channel = channel;
	}

	public final int getSessionId() {
		return sessionId;
	}

	public final String remoteAddress() {
		try {
			InetSocketAddress sa = (InetSocketAddress) this.channel
					.getRemoteAddress();
			String addr = sa.getAddress().getHostAddress();
			return addr;
		} catch (IOException e) {
			logger.error("AioSession.remoteAddress, " + e.getMessage());
		}
		return "";
	}

	public SessionType getType() {
		return type;
	}

}
