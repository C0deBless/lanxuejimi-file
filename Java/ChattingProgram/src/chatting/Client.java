package chatting;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chatting.ui.LoginFrame;
import easysocket.packet.Packet;
import easysocket.session.AioTcpSession;
import easysocket.session.event.SessionReceivedPacketListener;

public class Client {
	public static final int PORT = 8500;
	public static final String HOST_NAME = "127.0.0.1";
	public static AioTcpSession session;
	static Logger logger = LoggerFactory.getLogger(Client.class);

	public static void main(String[] args) throws IOException,
			InterruptedException {

		final AsynchronousSocketChannel channel = AsynchronousSocketChannel
				.open();
		channel.connect(new InetSocketAddress(HOST_NAME, PORT), null,
				new CompletionHandler<Void, Void>() {

					@Override
					public void completed(Void result, Void attachment) {
						session = new AioTcpSession(channel);
						session.pendingRead();
						session.onReceivePacket(new SessionReceivedPacketListener() {
							@Override
							public void onReceivePackets(List<Packet> packets) {
								for (Packet packet : packets) {
									short cmd = packet.getCmd();
									if (cmd == Command.S_LOGIN) {
										int result = packet.getByteBuffer().getInt();
										logger.debug("S_LOGIN, result:{}", result);
									}
								}
							}
						});

					}

					@Override
					public void failed(Throwable exc, Void attachment) {
						exc.printStackTrace();
					}

				});
		LoginFrame loginFrame = new LoginFrame();
		loginFrame.start();
		Thread.currentThread().join();
	}
}
