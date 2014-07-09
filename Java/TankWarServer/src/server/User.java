package server;


public class User {

	private final String name;
	private int gameWorldIndex;
	
	
	public User(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public int getGameWorldIndex() {
		return gameWorldIndex;
	}

	public  void setGameWorldIndex(int gameWorldIndex) {
		this.gameWorldIndex = gameWorldIndex;
	}
}
