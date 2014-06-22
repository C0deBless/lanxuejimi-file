package server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Missile;
import common.Tank;

public class GameWorld implements Runnable {

	static Logger logger = LoggerFactory.getLogger(GameWorld.class);
	private final Map<Integer, User> userPool = new ConcurrentHashMap<>();

	private List<Tank> tankList = new ArrayList<>();
	private List<Missile> missileList = new ArrayList<>();

	private Thread thread;
	private boolean isRunning = false;
	private long lastUpdateTime = 0;

	public GameWorld() {
		thread = new Thread(this);
		thread.start();
		isRunning = true;
	}

	public Map<Integer, User> getUserPool() {
		return userPool;
	}

	public void removeUser(int clientId) {
		userPool.remove(clientId);
	}

	private void update() {
		long currentTime = System.currentTimeMillis();
		if (lastUpdateTime == 0) {
			lastUpdateTime = currentTime;
			return;
		}
		long deltaTime = currentTime - lastUpdateTime;
		lastUpdateTime = currentTime;
	}

	@Override
	public void run() {
		while (isRunning) {

			update();

			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				logger.error("Thread sleep error");
			}
		}
	}
}
