package server;

import common.Client;

public class User {

	private final Client client;
	private final String name;
	private static int gameWorldIndex;
	
	
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
	
	public static int getGameWorldIndex(Client client) {
		if(client.equals(client)){
			return gameWorldIndex;
		}
		return -1;
	}

	public int getGameWorldIndex() {
		return gameWorldIndex;
	}

	public void setGameWorldIndex(int gameWorldIndex) {
		this.gameWorldIndex = gameWorldIndex;
	}
	
	public User getUser(int clientId){
		if(this.getClientId() != clientId){
			return null;
		}
		return this;
	}
}
