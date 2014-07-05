package common;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Client implements Runnable {

	private static int CLIENT_INDEX = 0;

	private final Socket socket;
	private Thread thread;
	private boolean isRunning = false;
	private final int clientId;
	private PacketEventListener packetEventListener;
	private SocketCloseEventListener closeEventListener;

	public Client(Socket socket) {
		this.socket = socket;
		clientId = (++CLIENT_INDEX);
	}

	@Override
	public void run() {
		try {
			InputStream input = socket.getInputStream();
			byte[] buffer = new byte[128];
			while (this.isRunning) {
				int count = input.read(buffer);
				if (count == -1) {
					// 断开
					close();
				} else {
					handleBuffer(buffer, count);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			this.close();
		}

	}

	private void close() {
		try {
			if (!socket.isClosed()) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.isRunning = false;
		if (closeEventListener != null) {
			closeEventListener.onClose();
		}
	}

	private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
	//切包
	private void handleBuffer(byte[] readData, int count) {
		if (readBuffer.remaining() < count) {
			extendReadBuffer(count);
		}
		readBuffer.put(readData, 0, count);
		readBuffer.limit(readBuffer.position());
		readBuffer.position(0);

		List<Packet> packets = new ArrayList<Packet>();

		boolean loop = true;
		int remaining = 0;

		while (loop) {
			remaining = readBuffer.remaining();
			if (remaining >= 6) {
				int len = readBuffer.getInt(readBuffer.position());
				if (len < 6) {
					// TODO Illegal packet
					break;
				}
				if (remaining >= len) {
					readBuffer.getInt(); // to forward position
					short cmd = readBuffer.getShort();
					byte[] data = new byte[len - 6];
					readBuffer.get(data);

					packets.add(new Packet(cmd, data, this));
				} else {
					loop = false;
				}
			} else {
				loop = false;
			}
		}

		readBuffer.compact();
		readBuffer.clear();
		readBuffer.position(remaining);

		if (packetEventListener != null) {
			packetEventListener.receive(packets);
		}
	}
	//read缓冲区不够的时候，扩大read缓冲区
	private final void extendReadBuffer(int count) {
		ByteBuffer tmp = readBuffer;
		int pos = tmp.position();
		tmp.position(0);

		readBuffer = ByteBuffer.allocate(readBuffer.capacity() + count);
		readBuffer.put(tmp.array(), 0, pos);
	}
	
	public void pushWritePacket(Packet packet) {
		ByteBuffer writeBuffer = packet.getByteBuffer();

		int len = writeBuffer.position() + 6;
		ByteBuffer newBuffer = ByteBuffer.allocate(len);
		newBuffer.putInt(len);
		newBuffer.putShort(packet.getCmd());
		newBuffer.put(writeBuffer.array(), 0, writeBuffer.position());

		try {
			socket.getOutputStream().write(newBuffer.array());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public void start() {
		this.isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		this.isRunning = false;
	}

	public int getClientId() {
		return clientId;
	}

	public PacketEventListener getPacketEventListener() {
		return packetEventListener;
	}

	public void setPacketEventListener(PacketEventListener packetEventListener) {
		this.packetEventListener = packetEventListener;
	}

	public SocketCloseEventListener getCloseEventListener() {
		return closeEventListener;
	}

	public void setCloseEventListener(
			SocketCloseEventListener closeEventListener) {
		this.closeEventListener = closeEventListener;
	}

}
