package chatting;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chatting.database.Database;
import easysocket.packet.Packet;
import easysocket.session.AioTcpSession;
import easysocket.session.event.SessionReceivedPacketListener;
import easysocket.utils.StringUtil;

public class Server {
	static Logger logger = LoggerFactory.getLogger(Server.class);

	static final int PORT = 8500;
	static Database database;

	public static void main(String[] args) throws IOException,
			InterruptedException, ClassNotFoundException, SQLException {

		AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel
				.open();
		server.bind(new InetSocketAddress(PORT));
		server.accept(null, new AcceptCompletionHandler());
		database = new Database();
		logger.info("服务器启动完毕.....");
		Thread.currentThread().join();
	}

	public static class AcceptCompletionHandler implements
			CompletionHandler<AsynchronousSocketChannel, Void> {
		@Override
		public void completed(final AsynchronousSocketChannel channel,
				Void attachment) {
			try {
				System.out.println(channel.getRemoteAddress().toString());
				final AioTcpSession session = new AioTcpSession(channel);
				session.onReceivePacket(new SessionReceivedPacketListener() {
					@Override
					public void onReceivePackets(List<Packet> packets) {
						for (Packet packet : packets) {
							short cmd = packet.getCmd();
							if (cmd == Command.C_LOGIN) {
								String userName = StringUtil.getString(packet
										.getByteBuffer());
								String password = StringUtil.getString(packet
										.getByteBuffer());
								logger.debug(
										"Login::: userName:{}, password:{}",
										userName, password);

								int result = 1;
								try {
									result = Server.database.login(userName,
											password);
								} catch (SQLException e) {
									e.printStackTrace();
									result = 3;
								} // 1 -> 成功， 2->需要注册, 3-> 服务器异常
								
								Packet writePacket = new Packet(
										Command.S_LOGIN, session);
								writePacket.getByteBuffer().putInt(result);
								session.sendPacket(writePacket);
							}
						}
					}
				});
				session.pendingRead();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void failed(Throwable exc, Void attachment) {
			exc.printStackTrace();
		}
	}
}
