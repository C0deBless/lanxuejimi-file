package server;

import common.Client;

public class User {

	private final Client client;
	private final String name;
	
	public User(Client client, String name){
		this.client = client;
		this.name = name;
	}

	public Client getClient() {
		return client;
	}

	public String getName() {
		return name;
	}
	
	public int getClientId(){
		return this.client.getClientId();
	}
}
