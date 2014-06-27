package server;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Command;
import common.Missile;
import common.Packet;
import common.Tank;

public class GameWorld implements Runnable {

	public static final int TEAM_NPC = 0;

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

	public void init() {
		Tank tank = new Tank(100, 100, TEAM_NPC);
		tankList.add(tank);
	}

	public Tank initUserTank(int clientId) {
		Tank tank = new Tank(200, 200, 1);
		tank.setClientId(clientId);
		tankList.add(tank);
		return tank;
	}

	public void serializeAllTanks(ByteBuffer buffer) {
		int count = this.tankList.size();
		buffer.putInt(count);
		for (int i = 0; i < count; i++) {
			Tank tank = this.tankList.get(i);
			tank.serialize(buffer);
		}
	}

	public Map<Integer, User> getUserPool() {
		return userPool;
	}

	public void removeUser(int clientId) {
		userPool.remove(clientId);
	}

	public void removeTankByClientId(int clientId) {
		Iterator<Tank> it = tankList.iterator();
		while (it.hasNext()) {
			Tank tank = it.next();
			if (tank.getClientId() == clientId) {
				it.remove();
			}
		}

	}

	public void move(int clientId, int tankId, int angle) {
		Tank tank = getTank(tankId);
		if (tank == null || tank.getClientId() != clientId) {
			logger.error("illegal move");
			return;
		}
		tank.setAngle(angle);
		tank.setCurrentSpeed(100);
	}

	public void fire(int tankId) {
		Tank tank = getTank(tankId);
		Missile m = new Missile(tank.getX() + tank.getWidth() / 2 + 3,
				tank.getY() + tank.getHeight() / 2 + 3, tank.getAngle(),
				tank.getTeam());
		m.setMissileSpeed(300);

		missileList.add(m);
	}

	public void stop(int clientId, int tankId) {
		Tank tank = getTank(tankId);
		if (tank == null || tank.getClientId() != clientId) {
			logger.error("illegal move");
			return;
		}
		tank.setCurrentSpeed(0);
	}

	public Tank getTank(int tankId) {
		for (Tank tank : tankList) {
			if (tank.getId() == tankId) {
				logger.debug("getTank, id:" + tank.getId());
				return tank;
			}
		}
		return null;
	}
	
	public void tankAndMissileDead(int tankId, int missileId) {

		Iterator<Tank> itt = tankList.iterator();
		Iterator<Missile> itm = missileList.iterator();
		if (itt.hasNext()) {
			Tank tank = itt.next();
			if (tank.getId() == tankId) {
				itt.remove();
			}
		}
		if(itm.hasNext()){
			Missile missile = itm.next();
			if(missile.getId() == missileId){
				itm.remove();
			}
		}

	}


	private void update() {
		long currentTime = System.currentTimeMillis();
		if (lastUpdateTime == 0) {
			lastUpdateTime = currentTime;
			return;
		}
		long deltaTime = currentTime - lastUpdateTime;
		lastUpdateTime = currentTime;

		for (Tank tank : tankList) {
			tank.update(deltaTime);
		}
		for (Missile missile : missileList) {
			missile.update(deltaTime);
			for (Tank tank : tankList) {
				if (missile.hitTank(tank)) {
					Packet packet = new Packet(Command.S_HIT_TANK);
					packet.getByteBuffer().putInt(missile.getId());
					packet.getByteBuffer().putInt(tank.getId());
					ServerMain.getServer().broadcastPacket(packet);
					
					tankAndMissileDead(tank.getId(), missile.getId());
				}
			}
		}
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
