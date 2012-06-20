package com.trnnn.http;

import java.io.IOException;
import java.util.List;

import com.trnnn.http.config.ServerConfig;
import com.trnnn.http.config.ServerConfigFactory;

public class Main {

	public static void main(String[] args) throws InterruptedException,
			IOException {
		ServerConfigFactory.init();
		List<ServerConfig> configs = ServerConfigFactory.factory().config();
		for (ServerConfig serverConfig : configs) {
			HttpServer server = new HttpServer(serverConfig);
			server.run();
		}
		Thread.currentThread().join();
	}
}
