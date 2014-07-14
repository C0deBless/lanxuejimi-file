package common;

public class Command {

	public static final short C_LOGIN = 0;
	public static final short S_LOGIN = 1000;
	
	public static final short C_READY = 1;
	public static final short S_READY = 1001;
	
	public static final short C_MOVE = 2;
	public static final short S_MOVE = 1002;
	
	public static final short C_STOP = 3;
	public static final short S_STOP = 1003;
	
	public static final short S_NEW_TANK = 4;
	
	public static final short S_EXIT = 1005;
	
	public static final short C_NEW_MISSILE = 6;
	public static final short S_NEW_MISSILE = 1006;
	
	public static final short S_HIT_TANK = 7;
	
	public static final short S_TANKS_COLLIDE = 8;
	
	public static final short S_HIT_WALL = 9;
	
	public static final short S_NEW_PLAYERS_NAME = 10;
	
	public static final short S_GAME_START = 1011;
	
	public static final short S_GAME_END = 1012;
	
	public static final short S_HIT_CAMP = 13;
	
	public static final short S_HIT_BLOCK = 14;
	
	public static final short S_DEBUG_TANK_INFO = 15;
}
