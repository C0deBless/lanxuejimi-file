package server;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Command;
import common.Constants;
import common.Explode;
import common.Missile;
import common.Packet;
import common.StringUtil;
import common.Tank;

public class GameWorld implements Runnable {

	public static final int TEAM_NPC = 0;
	public static final int MAX_USER_COUNT = 2;

	static Logger logger = LoggerFactory.getLogger(GameWorld.class);
	private final Map<Integer, UserSession> userPool = new ConcurrentHashMap<>();

	private List<Tank> tankList = new ArrayList<Tank>();
	private List<Missile> missileList = new ArrayList<Missile>();
	private List<Explode> explodeList = new ArrayList<Explode>();

	private Thread thread;
	private boolean isRunning = false;
	private long lastUpdateTime = 0;

	private Random random = new Random();

	private int randomLocationX;
	private int randomLocationY;
	private GameStatus status = GameStatus.Idle;
	private final int id;

	public GameWorld(int id) {
		this.id = id;
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
		randomLocationX = random.nextInt(Constants.GAME_WIDTH);
		randomLocationY = random.nextInt(Constants.GAME_HEIGHT);
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

	public void sendAllName(ByteBuffer buffer, int gameId) {
		int count = userPool.size();
		buffer.putInt(count);
		Collection<UserSession> nameList = userPool.values();
		for (UserSession userSession : nameList) {
			StringUtil.putString(buffer, userSession.getUser().getName());
			userSession.getUser().setGameWorldIndex(gameId);
		}

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

	public void leaveUser(int clientId) {
		userPool.remove(clientId);
		if (userPool.size() == 0) {
			endGame();
		}
	}

	public void endGame() {
		this.status = GameStatus.Idle;

		// FIXME broadcast game end message
		// FIXME handle reward and game result
		// FIXME clear
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

				broadcast(writePacket);

			}
		}
	}

	public void collidesWithTanks(Tank tank) {
		for (Tank t : tankList) {
			if (tank.collidesWithTank(t)) {
				Packet writePacket = new Packet(Command.S_TANKS_COLLIDE);
				writePacket.getByteBuffer().putInt(tank.getId());
				writePacket.getByteBuffer().putInt(t.getId());

				broadcast(writePacket);
			}
		}

	}

	private void colloedWithWall(Missile missile) {
		if (missile.getX() < 10 || missile.getY() < 40
				|| missile.getX() > Constants.GAME_WIDTH - missile.getWidth()
				|| missile.getY() > Constants.GAME_HEIGHT - missile.getHeight()) {

			missile.setLive(false);

			Packet writePacket = new Packet(Command.S_HIT_WALL);
			writePacket.getByteBuffer().putInt(missile.getId());
			Explode explode = new Explode(missile.getX(), missile.getY());
			explodeList.add(explode);
			explode.serialize(writePacket.getByteBuffer());
			broadcast(writePacket);
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

	public void broadcast(Packet packet) {
		Collection<UserSession> sessionList = userPool.values();
		for (UserSession session : sessionList) {
			session.getClient().pushWritePacket(packet);
		}
	}

	public boolean isAllUserReady() {
		Collection<UserSession> users = this.userPool.values();
		for (UserSession user : users) {
			if (!user.isReady()) {
				return false;
			}
		}
		return true;
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

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	public boolean canJoin() {
		if (this.userPool.size() < MAX_USER_COUNT
				&& (this.status == GameStatus.Idle || this.status == GameStatus.Waiting)) {
			logger.debug("canJoin, true");
			return true;
		} else {
			logger.debug("canJoin, false:{}, status:{}", this.userPool.size(),
					this.status);
			return false;
		}
	}

	public int getId() {
		return id;
	}

	public void join(UserSession session) {
		if (status == GameStatus.Idle) {
			status = GameStatus.Waiting;
		}
		this.userPool.put(session.getClient().getClientId(), session);
	}

	public void sendUserReady() {
		Packet writePacket = new Packet(Command.S_READY);
		this.broadcast(writePacket);
	}

	public void sendServerNewMsg(Tank tank, String name) {
		Packet writePacket2 = new Packet(Command.S_NEW_TANK);
		tank.serialize(writePacket2.getByteBuffer());
		StringUtil.putString(writePacket2.getByteBuffer(), name);
		this.broadcast(writePacket2);
	}

	public void sendServerLoginCommand(Packet packet, int clientId) {
		Packet writePacket = new Packet(Command.S_LOGIN);
		writePacket.getByteBuffer().putInt(this.id);
		writePacket.getByteBuffer().putInt(clientId);
		packet.getClient().pushWritePacket(writePacket);
	}

	public void broadcastGameStart() {
		Packet packet = new Packet(Command.S_GAME_START);
		this.serializeAllTanks(packet.getByteBuffer());
		this.serializeAllMissiles(packet.getByteBuffer());
		this.sendAllName(packet.getByteBuffer(), id);
		this.broadcast(packet);
	}

}
