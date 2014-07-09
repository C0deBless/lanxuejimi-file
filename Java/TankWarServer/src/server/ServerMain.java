package server;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import server.socket.Server;

public class ServerMain {

	private static Server SERVER;
	public static PacketQueue packetQueue;
	static Logger logger = LoggerFactory.getLogger(ServerMain.class);

	public static void main(String[] args) throws InterruptedException,
			IOException {
		SERVER = new Server();
		SERVER.start();

		packetQueue = new PacketQueue();
		logger.info("server started...");
		Thread.currentThread().join();
	}

	public static Server getServer() {
		return SERVER;
	}
}
