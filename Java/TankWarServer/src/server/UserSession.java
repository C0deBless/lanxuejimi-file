package server;

import easysocket.session.AioTcpSession;

public class UserSession {

	private User user;
	private final AioTcpSession session;
	private boolean ready = false;

	public UserSession(AioTcpSession session) {
		this.session = session;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public AioTcpSession getSession() {
		return session;
	}

}
