package com.trnnn.http.config;

public class ServerConfig {

	public ServerConfig() {

	}

	private String name;
	private int port;
	private String protocol;
	private int connectionTimeout;

	public int port() {
		return port;
	}

	public void port(int port) {
		this.port = port;
	}

	public String protocol() {
		return protocol;
	}

	public void protocol(String protocol) {
		this.protocol = protocol;
	}

	public int connectionTimeout() {
		return connectionTimeout;
	}

	public void connectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public String name() {
		return name;
	}

	public void name(String name) {
		this.name = name;
	}
}
