package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import server.socket.Server;

public class ServerMain {

	private static Server SERVER;
	public static PacketQueue packetQueue;
	private static List<GameWorld> gameWorlds = new ArrayList<GameWorld>();
	static Logger logger = LoggerFactory.getLogger(ServerMain.class);

	public static void main(String[] args) throws InterruptedException,
			IOException {
		SERVER = new Server();
		SERVER.start();

		for (int i = 0; i < 10; i++) {
			GameWorld gameWorld = new GameWorld();
			gameWorlds.add(gameWorld);
		}
		packetQueue = new PacketQueue();
		logger.info("server started...");
		Thread.currentThread().join();
	}

	public static Server getServer() {
		return SERVER;
	}
	
	public static  List<GameWorld> getGameWorlds(){
		return gameWorlds;
	}
}
