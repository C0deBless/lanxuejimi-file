package server;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Command;
import common.Explode;
import common.Missile;
import common.Packet;
import common.Tank;

public class GameWorld implements Runnable {
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	public static final int TEAM_NPC = 0;

	static Logger logger = LoggerFactory.getLogger(GameWorld.class);
	private final Map<Integer, User> userPool = new ConcurrentHashMap<>();

	private List<Tank> tankList = new ArrayList<Tank>();
	private List<Missile> missileList = new ArrayList<Missile>();
	private List<Explode> explodeList = new ArrayList<Explode>();

	private Thread thread;
	private boolean isRunning = false;
	private long lastUpdateTime = 0;
	
	private Random random = new Random();
	
	private int randomLocationX;
	private int randomLocationY;

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
		Tank tank = null;
		randomLocationX = random.nextInt(760);
		randomLocationY = random.nextInt(660);
		if (clientId % 2 == 0) {
			tank = new Tank(randomLocationX, randomLocationY, 1);
		} else {
			tank = new Tank(randomLocationX, randomLocationY, 0);
		}
		tank.setClientId(clientId);
		tankList.add(tank);
		return tank;
	}
	
	public Missile initTankMissile(int tankId) {
		Tank tank = getTank(tankId);
		Missile missile = new Missile(tank.getX() + tank.getWidth() / 2 + 3,
				tank.getY() + tank.getHeight() / 2 + 3, tank.getAngle(),
				tank.getTeam());
		missile.setMissileSpeed(300);
		missileList.add(missile);
		
		return missile;
	}

	public void serializeAllTanks(ByteBuffer buffer) {
		int count = this.tankList.size();
		buffer.putInt(count);
		for (int i = 0; i < count; i++) {
			Tank tank = this.tankList.get(i);
			tank.serialize(buffer);
		}
	}
	
	public void serializeAllMissiles(ByteBuffer buffer) {
		int count = this.missileList.size();
		buffer.putInt(count);
		for (int i = 0; i < count; i++) {
			Missile missile = this.missileList.get(i);
			missile.serialize(buffer);
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

	public void hitTank(Missile missile) {
		for (Tank tank : tankList) {
			if (missile.hitTank(tank)) {
				Packet writePacket = new Packet(Command.S_HIT_TANK);
				writePacket.getByteBuffer().putInt(missile.getId());
				writePacket.getByteBuffer().putInt(tank.getId());

				Explode explode = new Explode(missile.getX(), missile.getY());
				explodeList.add(explode);
				
				explode.serialize(writePacket.getByteBuffer());

				ServerMain.getServer().broadcastPacket(writePacket);

			}
		}
	}
	
	public void collidesWithTanks(Tank tank) {
		for (Tank t : tankList) {
			if (tank.collidesWithTank(t)) {
				Packet writePacket = new Packet(Command.S_TANKS_COLLIDE);
				writePacket.getByteBuffer().putInt(tank.getId());
				writePacket.getByteBuffer().putInt(t.getId());
				
				ServerMain.getServer().broadcastPacket(writePacket);
			}
		}
		
	}
	
	private void colloedWithWall(Missile missile) {
		if (missile.getX() < 10 || missile.getY() < 40 || missile.getX() > GAME_WIDTH - missile.getWidth()
						|| missile.getY() > GAME_HEIGHT - missile.getHeight()) {
			
			missile.setLive(false);
			
			Packet writePacket = new Packet(Command.S_HIT_WALL);
			writePacket.getByteBuffer().putInt(missile.getId());
			Explode explode = new Explode(missile.getX(), missile.getY());
			explodeList.add(explode);
			explode.serialize(writePacket.getByteBuffer());
			ServerMain.getServer().broadcastPacket(writePacket);
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
		synchronized (tankList) {
			Iterator<Tank> itt = tankList.iterator();
			while (itt.hasNext()) {
				Tank tank = itt.next();

				if (!tank.isLive()) {
					tankList.remove(tank);
					return;
				}
				collidesWithTanks(tank);
				tank.update(deltaTime);
			}
		}
		synchronized (missileList) {
			Iterator<Missile> itm = missileList.iterator();
			while (itm.hasNext()) {

				Missile missile = itm.next();
				
				if (!missile.isLive()) {
					itm.remove();
					return;
				}
				
				colloedWithWall(missile);
				missile.update(deltaTime);
				hitTank(missile);
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
