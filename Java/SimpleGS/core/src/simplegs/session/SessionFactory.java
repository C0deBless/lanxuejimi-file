package simplegs.session;

import simplegs.packet.Packet;

public final class SessionFactory {

	public static final ThreadLocal<ClientSession> SESSION = new ThreadLocal<>();
	// public static final ThreadLocal<User> USER = new ThreadLocal<>();
	public static final ThreadLocal<Packet> PACKET = new ThreadLocal<>();

}
