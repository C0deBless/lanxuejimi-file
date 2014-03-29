package socket;

import socket.Server.ServerThread;

public class Packet {
	private String data;
	private ServerThread client;

	public Packet(String data, ServerThread client) {
		this.setData(data);
		this.setClient(client);
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public ServerThread getClient() {
		return client;
	}

	public void setClient(ServerThread client) {
		this.client = client;
	}
}
