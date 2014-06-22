package server.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import server.ServerMain;

import common.Client;
import common.Packet;
import common.PacketEventListener;
import common.SocketCloseEventListener;

public class Server {

	private static final int PORT = 8888;
	private static Logger logger = LoggerFactory.getLogger(Server.class);

	private ServerSocket serverSocket;
	private Thread acceptThread;
	private boolean isRunning = false;
	private Map<Integer, Client> clientPool = new ConcurrentHashMap<>();

	public void start() throws IOException {
		isRunning = true;
		serverSocket = new ServerSocket();
		serverSocket.bind(new InetSocketAddress(PORT));

		acceptThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (isRunning) {
					try {
						Socket socket = serverSocket.accept();
						logger.debug("a client connected....");
						handleNewSocket(socket);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		acceptThread.start();
	}

	public void removeClient(int clientId) {
		this.clientPool.remove(clientId);
	}

	public void BroadcastPacket(Packet packet) {
		Collection<Client> clients = this.clientPool.values();
		for (Client client : clients) {
			client.pushWritePacket(packet);
		}
	}

	private void handleNewSocket(Socket socket) {
		final Client client = new Client(socket);
		client.setPacketEventListener(new PacketEventListener() {
			@Override
			public void receive(List<Packet> packets) {
				for (Packet packet : packets) {
					ServerMain.packetQueue.pushPacket(packet);
				}
			}
		});

		client.setCloseEventListener(new SocketCloseEventListener() {
			@Override
			public void onClose() {
				logger.debug("client closed, id:{}", client.getClientId());
				ServerMain.getGameWorld().removeUser(client.getClientId());
				ServerMain.getServer().removeClient(client.getClientId());
			}
		});
		int clientId = client.getClientId();
		clientPool.put(clientId, client);
		client.start();
	}

	public void stop() {
		isRunning = false;
	}
}
