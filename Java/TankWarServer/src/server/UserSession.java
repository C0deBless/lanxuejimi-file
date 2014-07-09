package server;

import common.Client;

public class UserSession {

	private User user;
	private final Client client;

	public UserSession(Client client) {
		this.client = client;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Client getClient() {
		return client;
	}
}
