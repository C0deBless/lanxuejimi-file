package easysocket.test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;

import easysocket.packet.Packet;
import easysocket.server.SocketServer;
import easysocket.server.SocketServer.ClientConnectedEventHandler;
import easysocket.session.AioTcpSession;

public class ServerTest {

	public static void main(String[] args) throws IOException,
			InterruptedException {
		SocketServer server = new SocketServer(8000,
				new ClientConnectedEventHandler() {
					@Override
					public void OnConnect(final AioTcpSession session) {
						Thread thread = new Thread(new Runnable() {
							@Override
							public void run() {
								while (true) {

									String msg = "this is message from server, "
											+ new Date();
									Packet packet = new Packet((short) 1000,
											4 + msg.length(), session);
									ByteBuffer buffer = packet.getByteBuffer();
									buffer.putInt(msg.length());
									buffer.put(msg.getBytes());
									session.sendPacket(packet);
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
						});
						thread.start();
					}
				});
		server.start();
		Thread.currentThread().join();
	}
}
