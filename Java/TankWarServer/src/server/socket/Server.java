package server.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import server.GameWorld;
import server.ServerMain;
import server.User;
import server.UserSession;
import common.Client;
import common.Command;
import common.Packet;
import common.PacketEventListener;
import common.SocketCloseEventListener;

public class Server {

	private static final int PORT = 8888;
	private static Logger logger = LoggerFactory.getLogger(Server.class);

	private ServerSocket serverSocket;
	private Thread acceptThread;
	private Thread updateThread;
	private boolean isRunning = false;
	private Map<Integer, UserSession> clientPool = new ConcurrentHashMap<>();
	private List<GameWorld> gameWorlds = new ArrayList<GameWorld>();

	public void start() throws IOException {
		isRunning = true;
		
		for (int i = 0; i < 1000; i++) {
			GameWorld gameWorld = new GameWorld(i);
			gameWorlds.add(gameWorld);
			
		}
		updateThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (isRunning) {
					try {
						for (GameWorld gameWorld : gameWorlds) {
							gameWorld.update();
		
						}
						Thread.sleep(20);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
		});
		updateThread.start();
		
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

	private void handleNewSocket(Socket socket) {
		final Client client = new Client(socket);
		final UserSession session = new UserSession(client);
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
				User user = session.getUser();
				if(user != null){
					int sessionId = user.getGameWorldIndex();
					if(sessionId > -1){
						GameWorld game = Server.this.gameWorlds.get(sessionId);
						game.leaveUser(client.getClientId());
						game.removeTankByClientId(client.getClientId());
						Packet writePacket = new Packet(Command.S_EXIT, 8);
						writePacket.getByteBuffer().putInt(client.getClientId());
						game.broadcast(writePacket);
					}
				}
				
				ServerMain.getServer().removeClient(client.getClientId());

				logger.debug("C_EXIT, clientId:{}", client.getClientId());
				
				
			}
		});
		int clientId = client.getClientId();
		clientPool.put(clientId, session);
		client.start();
	}

	public GameWorld findProperGameWorld(UserSession session){
		for(int i = 0; i < gameWorlds.size(); i++){
			GameWorld game = gameWorlds.get(i);
			if(game.canJoin()){
				return game;
			}
		}
		return null;
	}
	
	public void stop() {
		isRunning = false;
	}

	public UserSession getUserSession(int clientId) {
		return this.clientPool.get(clientId);
	}

	public GameWorld getGameWorld(int gameWorldIndex) {
		return this.gameWorlds.get(gameWorldIndex);
	}
}
