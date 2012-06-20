package simplegs.session;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import simplegs.event.SessionListener;
import simplegs.handler.OnRead;
import simplegs.handler.OnWrite;
import simplegs.packet.Packet;
import simplegs.packet.PacketBuilder;

//import com.easymode.armada.gameserver.packet.WritePacket;

public class ClientSession {
	protected AsynchronousSocketChannel channel;
	protected boolean isWriting = false;
	protected final int sessionId;
	protected CompletionHandler<Integer, ClientSession> onRead;
	protected CompletionHandler<Integer, ClientSession> onWrite;
	protected PacketBuilder packetBuilder;
	protected ByteBuffer readBuffer = ByteBuffer.allocate(1024);
	protected final List<ListenerInfo> listenerList = new ArrayList<>();

	// private static final int WRITE_PACKET_QUEUE_SIZE = 1;
	protected final Object channelWriteLock = new Object();

	protected final Map<SessionAttribute, Object> attributes = new ConcurrentHashMap<SessionAttribute, Object>();
	protected static final AtomicInteger sessionIndex = new AtomicInteger();
	public final SessionState sessionState = new SessionState(
			SessionState.UNKNOWN);

	protected ClientSession(AsynchronousSocketChannel channel,
			CompletionHandler<Integer, ClientSession> onRead,
			CompletionHandler<Integer, ClientSession> onWrite,
			PacketBuilder packetBuilder) {
		this.sessionId = sessionIndex.getAndIncrement();
		this.onRead = onRead;
		this.onWrite = onWrite;
		this.channel = channel;
		this.packetBuilder = packetBuilder;
		this.sessionState.set(SessionState.OPENED);
	}

	private void extendReadBuffer() {
		ByteBuffer tmp = readBuffer;
		int pos = tmp.position();
		tmp.position(0);

		readBuffer = ByteBuffer.allocate(readBuffer.capacity() * 2);
		readBuffer.put(tmp.array(), 0, pos);
	}

	public void beforeRead() {
		readBuffer.flip();
		if (readBuffer.hasRemaining()) {
			List<Packet> packets = packetBuilder.parse(readBuffer, this);
			packetBuilder.queue(packets);

			// Check readBuffer is full
			if (readBuffer.position() > 2
					&& readBuffer.getShort(0) > readBuffer.capacity()) {
				extendReadBuffer();
			}
		} else {
			readBuffer.clear();
		}
	}

	public void pendingRead() {
		beforeRead();
		this.channel.read(this.readBuffer, this, this.onRead);
		this.processEvent(EventType.READ);
	}

	public void close() throws IOException {
		this.channel.close();
		this.processEvent(EventType.CLOSE);
	}

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

	public void addSessionListener(SessionListener listener, Object handback) {
		if (listener == null) {
			throw new NullPointerException(
					"listener is null, cannot put a null value");
		}
		this.listenerList.add(new ListenerInfo(listener, handback));
	}

	public void removeSessionListener(SessionListener listener) {
		if (listener == null) {
			throw new NullPointerException(
					"listener is null, cannot remove a null value");
		}
		this.listenerList.remove(listener);
	}

	public static void main(String[] args) {
		System.out.println(Integer.toBinaryString(EventType.CLOSE_MASK));
		System.out.println(Integer.toBinaryString(EventType.OPEN_MASK));
		System.out.println(Integer.toBinaryString(EventType.READ_MASK));
		System.out.println(Integer.toBinaryString(EventType.WRITE_MASK));
		System.out.println(Integer.toBinaryString(EventType.ERROR_MASK));

		System.out.println(EventType.CLOSE | EventType.CLOSE_MASK);
		System.out.println(0xFFFFFFFF);
	}

	protected void processEvent(int eventType) {
		if (listenerList.size() == 0) {
			return;
		}
		for (ListenerInfo listener : listenerList) {
			SessionListener sessionListener = listener.getListener();
			if ((eventType | EventType.CLOSE_MASK) == -1) {
				sessionListener.onClose(listener.getHandBack());
			}
			if ((eventType | EventType.OPEN_MASK) == -1) {
				sessionListener.onOpen(listener.getHandBack());
			}
			if ((eventType | EventType.READ_MASK) == -1) {
				sessionListener.onRead(listener.getHandBack());
			}
			if ((eventType | EventType.WRITE_MASK) == -1) {
				sessionListener.onWrite(listener.getHandBack());
			}
			if ((eventType | EventType.ERROR_MASK) == -1) {
				sessionListener.onError(listener.getHandBack());
			}
		}
	}

	public static ClientSession open(AsynchronousSocketChannel channel,
			OnRead onRead, OnWrite onWrite, PacketBuilder packetBuilder) {
		ClientSession session = new ClientSession(channel, onRead, onWrite,
				packetBuilder);
		session.processEvent(EventType.OPEN);
		return session;
	}

	static final class EventType {
		public static final int CLOSE = 0x01;
		public static final int OPEN = 0x02;
		public static final int READ = 0x04;
		public static final int WRITE = 0x08;
		public static final int ERROR = 0x10;

		public static final int CLOSE_MASK = 0xFFFFFFFE;
		public static final int OPEN_MASK = 0xFFFFFFFD;
		public static final int READ_MASK = 0xFFFFFFFB;
		public static final int WRITE_MASK = 0xFFFFFFF7;
		public static final int ERROR_MASK = 0xFFFFFFEF;
	}

	final class ListenerInfo {
		private SessionListener listener;
		private Object handBack;

		public SessionListener getListener() {
			return listener;
		}

		public void setListener(SessionListener listener) {
			this.listener = listener;
		}

		public Object getHandBack() {
			return handBack;
		}

		public void setHandBack(Object handBack) {
			this.handBack = handBack;
		}

		public ListenerInfo(SessionListener listener, Object handBack) {
			this.listener = listener;
			this.handBack = handBack;
		}
	}
}
