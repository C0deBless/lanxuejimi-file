package simplegs.test;

import simplegs.jmx.ProfileServer;

public class TestServer1 {
	public static void main(String[] args) throws InterruptedException {
		ProfileServer server = new ProfileServer();
		server.run();
		Thread.currentThread().join();
	}
}
