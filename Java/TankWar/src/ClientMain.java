import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Client;
import common.Command;
import common.Packet;
import common.PacketEventListener;
import common.SocketCloseEventListener;
import common.StringUtil;
import common.Tank;

public class ClientMain {
	private static Logger logger = LoggerFactory.getLogger(ClientMain.class);
	public static final String SERVER_HOST = "127.0.0.1";
	public static final int SERVER_PORT = 8888;

	public static Socket socket = null;
	public static Client client;
	public static TankClient tankClient;

	public static void main(String[] args) {
		tankClient = new TankClient();
		tankClient.launchFrame();
		connect();
	}

	private static void handlePacket(Packet packet) {
		short cmd = packet.getCmd();
		switch (cmd) {
		case Command.S_LOGIN: {
			logger.debug("S_LOGIN:");
			int clientId = packet.getByteBuffer().getInt();
			int count = packet.getByteBuffer().getInt();

			List<Tank> tankList = new ArrayList<>(count);
			for (int i = 0; i < count; i++) {
				Tank tank = Tank.deserialize(packet.getByteBuffer());
				tankList.add(tank);
			}
			tankClient.setTankList(tankList);
			tankClient.setMyClientId(clientId);
		}
			break;
		case Command.S_MOVE: {
			int clientId = packet.getByteBuffer().getInt();
			int id = packet.getByteBuffer().getInt();
			int angle = packet.getByteBuffer().getInt();
			ClientMain.tankClient.move(clientId, id, angle);
		}
			break;
		case Command.S_STOP: {
			int clientId = packet.getByteBuffer().getInt();
			int tankId = packet.getByteBuffer().getInt();
			ClientMain.tankClient.stop(clientId, tankId);
		}
			break;
		case Command.S_NEW_TANK: {
			Tank tank = Tank.deserialize(packet.getByteBuffer());
			ClientMain.tankClient.addNewTank(tank);
		}
			break;
		case Command.S_EXIT:{
			int clientId = packet.getByteBuffer().getInt();
			ClientMain.tankClient.removeTankByClientId(clientId);
		}
			break;
		}
	}

	private static void connect() {
		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
			logger.debug("Client connect");
			client = new Client(socket);

			Packet packet = new Packet(Command.C_LOGIN);
			StringUtil.putString(packet.getByteBuffer(), "ClientXXX");
			client.pushWritePacket(packet);

			client.setPacketEventListener(new PacketEventListener() {
				@Override
				public void receive(List<Packet> packets) {
					for (Packet packet : packets) {
						try {
							handlePacket(packet);
						} catch (Exception e) {
							logger.error("packet error, type:{}, msg:{}", e
									.getClass().getName(), e.getMessage());
							e.printStackTrace();
						}
					}
				}
			});

			client.setCloseEventListener(new SocketCloseEventListener() {
				@Override
				public void onClose() {
					logger.error("socket closed");
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
