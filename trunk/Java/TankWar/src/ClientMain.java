import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Client;
import common.Command;
import common.Packet;
import common.PacketEventListener;
import common.StringUtil;

public class ClientMain {
	private static Logger logger = LoggerFactory.getLogger(ClientMain.class);
	public static final String SERVER_HOST = "127.0.0.1";
	public static final int SERVER_PORT = 8888;

	public static Socket socket = null;
	public static Client client;
	public static TankClient tank;

	public static void main(String[] args) {
		connect();
		tank = new TankClient();
		tank.launchFrame();
	}

	private static void connect() {
		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
			logger.debug("Client connect");
			client = new Client(socket);

			Packet packet = new Packet(Command.LOGIN);
			StringUtil.putString(packet.getByteBuffer(), "ClientXXX");
			client.pushWritePacket(packet);

			client.setPacketEventListener(new PacketEventListener() {
				@Override
				public void receive(List<Packet> packet) {
					// FIXME handle packets
				}
			});
			client.start();
		} catch (Exception e) {
			e.printStackTrace();
			if (socket != null) {
				try {
					socket.close();
					socket = null;
				} catch (IOException ex) {
					e.printStackTrace();
				}
			}
		}
	}

}
