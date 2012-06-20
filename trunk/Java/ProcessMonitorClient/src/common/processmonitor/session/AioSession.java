package common.processmonitor.session;

import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

//import com.easymode.armada.gameserver.packet.WritePacket;

public abstract class AioSession {
	protected AsynchronousSocketChannel channel;
	protected boolean isWriting = false;
	protected final int sessionId;
	protected CompletionHandler<Integer, AioSession> onRead;
	protected CompletionHandler<Integer, AioSession> onWrite;

	// private static final int WRITE_PACKET_QUEUE_SIZE = 1;
	protected final Object channelWriteLock = new Object();

	protected final Map<SessionAttribute, Object> attributes = new ConcurrentHashMap<SessionAttribute, Object>();
	protected static final AtomicInteger sessionIndex = new AtomicInteger();
	public final SessionState sessionState = new SessionState(
			SessionState.UNKNOWN);

	protected AioSession(AsynchronousSocketChannel channel,
			CompletionHandler<Integer, AioSession> onRead,
			CompletionHandler<Integer, AioSession> onWrite) {
		this.sessionId = sessionIndex.getAndIncrement();
		this.onRead = onRead;
		this.onWrite = onWrite;
		this.channel = channel;

		this.sessionState.set(SessionState.OPENED);
	}

	public abstract void pendingRead();

	public abstract void close();

	public final Object getAttribute(SessionAttribute key) {
		return this.attributes.get(key);
	}

	public final void setAttribute(SessionAttribute key, Object value) {
		if (this.attributes.containsKey(key))
			this.attributes.remove(key);
		this.attributes.put(key, value);
	}

	public final boolean containsAttribute(SessionAttribute key) {
		return this.attributes.containsKey(key);
	}

	public final Set<SessionAttribute> attributeKeySet() {
		return this.attributes.keySet();
	}

	public final void removeAttribute(SessionAttribute key) {
		this.attributes.remove(key);
	}

	public final synchronized boolean isWriting() {
		return this.isWriting;
	}

	public final synchronized void isWriting(boolean isWriting) {
		this.isWriting = isWriting;
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

	public final void notifyWriteProcess() {
		synchronized (this.channelWriteLock) {
			this.channelWriteLock.notifyAll();
		}
	}

	public final void waitWriteProcess() {
		synchronized (this.channelWriteLock) {
			try {
				this.channelWriteLock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
