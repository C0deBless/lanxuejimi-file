package server.socket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import server.GameStatus;
import server.GameWorld;
import server.ServerMain;
import server.User;
import server.UserSession;

import common.Command;

import easysocket.packet.Packet;
import easysocket.server.SocketServer;
import easysocket.server.SocketServer.ClientConnectedEventHandler;
import easysocket.session.AioTcpSession;
import easysocket.session.event.SessionEventListener;

public class Server {

	private static final int PORT = 8888;
	private static Logger logger = LoggerFactory.getLogger(Server.class);

	private SocketServer server;
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
							if (gameWorld.getStatus() != GameStatus.Idle) {
								gameWorld.update();
							}

						}
						Thread.sleep(5);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		});
		updateThread.start();

		server = new SocketServer(PORT, new ClientConnectedEventHandler() {

			@Override
			public void OnConnect(AioTcpSession session) {
				logger.debug("a client connected....");
				handleNewSocket(session);
			}
		});
		server.start();
	}

	public void removeClient(int clientId) {
		this.clientPool.remove(clientId);
	}

	private void handleNewSocket(final AioTcpSession session) {
		final UserSession userSession = new UserSession(session);

		session.registerEventListener(new SessionEventListener() {

			@Override
			public void onReceivePackets(List<Packet> packets) {
				for (Packet packet : packets) {
					ServerMain.packetQueue.pushPacket(packet);
				}
			}

			@Override
			public void onClose() {
				logger.debug("client closed, id:{}", session.getSessionId());
				User user = userSession.getUser();
				if (user != null) {
					int sessionId = user.getGameWorldIndex();
					if (sessionId > -1) {
						GameWorld game = Server.this.gameWorlds.get(sessionId);
						game.leaveUser(session.getSessionId());
						game.removeTankByClientId(session.getSessionId());
						Packet writePacket = new Packet(Command.S_EXIT, 8);
						writePacket.getByteBuffer().putInt(
								session.getSessionId());
						game.broadcast(writePacket);
					}
				}

				ServerMain.getServer().removeClient(session.getSessionId());

				logger.debug("C_EXIT, clientId:{}", session.getSessionId());
			}
		});

		session.pendingRead();

		clientPool.put(session.getSessionId(), userSession);
	}

	public GameWorld findProperGameWorld(UserSession session) {
		for (int i = 0; i < gameWorlds.size(); i++) {
			GameWorld game = gameWorlds.get(i);
			if (game.canJoin()) {
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
