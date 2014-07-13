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

import common.Block;
import common.Camp;
import common.Command;
import common.Constants;
import common.Explode;
import common.Missile;
import common.Packet;
import common.StringUtil;
import common.Tank;
import common.TankType;

public class GameWorld {

	public static final int TEAM_NPC = 0;
	public static final int MAX_USER_COUNT = 2;

	private int missileIndex = 0;

	static Logger logger = LoggerFactory.getLogger(GameWorld.class);
	private final Map<Integer, UserSession> userPool = new ConcurrentHashMap<>();

	private List<Tank> tankList = new ArrayList<Tank>();
	private List<Missile> missileList = new ArrayList<Missile>();
	private List<Explode> explodeList = new ArrayList<Explode>();
	private List<Block> blockList = new ArrayList<Block>();
	private Camp camp;
	private int[][] blocksCoordinate;

	private long lastUpdateTime = 0;

	private Random random = new Random();

	private int randomLocationX;
	private int randomLocationY;
	private GameStatus status = GameStatus.Idle;
	private final int id;

	public GameWorld(int id) {
		this.id = id;
		camp = new Camp(Constants.CAMP_X, Constants.CAMP_Y);
		initblocksCoordinate();
		initBlocks();
	}

	public void initBlocks() {
		for (int i = 0; i < blocksCoordinate.length; i++) {
			Block block = new Block(blocksCoordinate[i][0],
					blocksCoordinate[i][1]);
			blockList.add(block);
		}
	}

	public void initblocksCoordinate() {
		blocksCoordinate = new int[][] {
				{ Constants.CAMP_X + (Constants.A_GRID * 0),
						Constants.CAMP_Y - (Constants.A_GRID * 1) },
				{ Constants.CAMP_X + (Constants.A_GRID * 0),
						Constants.CAMP_Y - (Constants.A_GRID * 3) },
				{ Constants.CAMP_X + (Constants.A_GRID * 0),
						Constants.CAMP_Y - (Constants.A_GRID * 4) },
				{ Constants.CAMP_X + (Constants.A_GRID * 0),
						Constants.CAMP_Y - (Constants.A_GRID * 8) },
				{ Constants.CAMP_X + (Constants.A_GRID * 0),
						Constants.CAMP_Y - (Constants.A_GRID * 10) },
				{ Constants.CAMP_X - (Constants.A_GRID * 1),
						Constants.CAMP_Y - (Constants.A_GRID * 0) },
				{ Constants.CAMP_X - (Constants.A_GRID * 1),
						Constants.CAMP_Y - (Constants.A_GRID * 1) },
				{ Constants.CAMP_X - (Constants.A_GRID * 1),
						Constants.CAMP_Y - (Constants.A_GRID * 3) },
				{ Constants.CAMP_X - (Constants.A_GRID * 1),
						Constants.CAMP_Y - (Constants.A_GRID * 4) },
				{ Constants.CAMP_X - (Constants.A_GRID * 1),
						Constants.CAMP_Y - (Constants.A_GRID * 5) },
				{ Constants.CAMP_X - (Constants.A_GRID * 1),
						Constants.CAMP_Y - (Constants.A_GRID * 6) },
				{ Constants.CAMP_X - (Constants.A_GRID * 1),
						Constants.CAMP_Y - (Constants.A_GRID * 8) },
				{ Constants.CAMP_X - (Constants.A_GRID * 3),
						Constants.CAMP_Y - (Constants.A_GRID * 0) },
				{ Constants.CAMP_X - (Constants.A_GRID * 3),
						Constants.CAMP_Y - (Constants.A_GRID * 2) },
				{ Constants.CAMP_X - (Constants.A_GRID * 3),
						Constants.CAMP_Y - (Constants.A_GRID * 3) },
				{ Constants.CAMP_X - (Constants.A_GRID * 3),
						Constants.CAMP_Y - (Constants.A_GRID * 4) },
				{ Constants.CAMP_X - (Constants.A_GRID * 3),
						Constants.CAMP_Y - (Constants.A_GRID * 5) },
				{ Constants.CAMP_X - (Constants.A_GRID * 3),
						Constants.CAMP_Y - (Constants.A_GRID * 6) },
				{ Constants.CAMP_X - (Constants.A_GRID * 3),
						Constants.CAMP_Y - (Constants.A_GRID * 8) },
				{ Constants.CAMP_X - (Constants.A_GRID * 3),
						Constants.CAMP_Y - (Constants.A_GRID * 9) },
				{ Constants.CAMP_X - (Constants.A_GRID * 3),
						Constants.CAMP_Y - (Constants.A_GRID * 11) },
				{ Constants.CAMP_X - (Constants.A_GRID * 3),
						Constants.CAMP_Y - (Constants.A_GRID * 12) },
				{ Constants.CAMP_X - (Constants.A_GRID * 4),
						Constants.CAMP_Y - (Constants.A_GRID * 6) },
				{ Constants.CAMP_X - (Constants.A_GRID * 5),
						Constants.CAMP_Y - (Constants.A_GRID * 0) },
				{ Constants.CAMP_X - (Constants.A_GRID * 5),
						Constants.CAMP_Y - (Constants.A_GRID * 1) },
				{ Constants.CAMP_X - (Constants.A_GRID * 5),
						Constants.CAMP_Y - (Constants.A_GRID * 2) },
				{ Constants.CAMP_X - (Constants.A_GRID * 5),
						Constants.CAMP_Y - (Constants.A_GRID * 3) },
				{ Constants.CAMP_X - (Constants.A_GRID * 5),
						Constants.CAMP_Y - (Constants.A_GRID * 4) },
				{ Constants.CAMP_X - (Constants.A_GRID * 5),
						Constants.CAMP_Y - (Constants.A_GRID * 6) },
				{ Constants.CAMP_X - (Constants.A_GRID * 5),
						Constants.CAMP_Y - (Constants.A_GRID * 10) },
				{ Constants.CAMP_X - (Constants.A_GRID * 5),
						Constants.CAMP_Y - (Constants.A_GRID * 11) },
				{ Constants.CAMP_X - (Constants.A_GRID * 6),
						Constants.CAMP_Y - (Constants.A_GRID * 4) },
				{ Constants.CAMP_X + (Constants.A_GRID * 1),
						Constants.CAMP_Y - (Constants.A_GRID * 0) },
				{ Constants.CAMP_X + (Constants.A_GRID * 1),
						Constants.CAMP_Y - (Constants.A_GRID * 1) },
				{ Constants.CAMP_X + (Constants.A_GRID * 1),
						Constants.CAMP_Y - (Constants.A_GRID * 3) },
				{ Constants.CAMP_X + (Constants.A_GRID * 1),
						Constants.CAMP_Y - (Constants.A_GRID * 4) },
				{ Constants.CAMP_X + (Constants.A_GRID * 1),
						Constants.CAMP_Y - (Constants.A_GRID * 5) },
				{ Constants.CAMP_X + (Constants.A_GRID * 1),
						Constants.CAMP_Y - (Constants.A_GRID * 6) },
				{ Constants.CAMP_X + (Constants.A_GRID * 1),
						Constants.CAMP_Y - (Constants.A_GRID * 7) },
				{ Constants.CAMP_X + (Constants.A_GRID * 1),
						Constants.CAMP_Y - (Constants.A_GRID * 11) },
				{ Constants.CAMP_X + (Constants.A_GRID * 1),
						Constants.CAMP_Y - (Constants.A_GRID * 12) },
				{ Constants.CAMP_X + (Constants.A_GRID * 2),
						Constants.CAMP_Y - (Constants.A_GRID * 7) },
				{ Constants.CAMP_X + (Constants.A_GRID * 3),
						Constants.CAMP_Y - (Constants.A_GRID * 0) },
				{ Constants.CAMP_X + (Constants.A_GRID * 3),
						Constants.CAMP_Y - (Constants.A_GRID * 1) },
				{ Constants.CAMP_X + (Constants.A_GRID * 3),
						Constants.CAMP_Y - (Constants.A_GRID * 3) },
				{ Constants.CAMP_X + (Constants.A_GRID * 3),
						Constants.CAMP_Y - (Constants.A_GRID * 5) },
				{ Constants.CAMP_X + (Constants.A_GRID * 3),
						Constants.CAMP_Y - (Constants.A_GRID * 8) },
				{ Constants.CAMP_X + (Constants.A_GRID * 3),
						Constants.CAMP_Y - (Constants.A_GRID * 9) },
				{ Constants.CAMP_X + (Constants.A_GRID * 3),
						Constants.CAMP_Y - (Constants.A_GRID * 10) },
				{ Constants.CAMP_X + (Constants.A_GRID * 3),
						Constants.CAMP_Y - (Constants.A_GRID * 11) },
				{ Constants.CAMP_X + (Constants.A_GRID * 4),
						Constants.CAMP_Y - (Constants.A_GRID * 0) },
				{ Constants.CAMP_X + (Constants.A_GRID * 4),
						Constants.CAMP_Y - (Constants.A_GRID * 3) },
				{ Constants.CAMP_X + (Constants.A_GRID * 4),
						Constants.CAMP_Y - (Constants.A_GRID * 10) },
				{ Constants.CAMP_X + (Constants.A_GRID * 5),
						Constants.CAMP_Y - (Constants.A_GRID * 0) },
				{ Constants.CAMP_X + (Constants.A_GRID * 5),
						Constants.CAMP_Y - (Constants.A_GRID * 1) },
				{ Constants.CAMP_X + (Constants.A_GRID * 5),
						Constants.CAMP_Y - (Constants.A_GRID * 3) },
				{ Constants.CAMP_X + (Constants.A_GRID * 5),
						Constants.CAMP_Y - (Constants.A_GRID * 4) },
				{ Constants.CAMP_X + (Constants.A_GRID * 5),
						Constants.CAMP_Y - (Constants.A_GRID * 5) },
				{ Constants.CAMP_X + (Constants.A_GRID * 5),
						Constants.CAMP_Y - (Constants.A_GRID * 6) },
				{ Constants.CAMP_X + (Constants.A_GRID * 5),
						Constants.CAMP_Y - (Constants.A_GRID * 8) },
				{ Constants.CAMP_X + (Constants.A_GRID * 5),
						Constants.CAMP_Y - (Constants.A_GRID * 10) },
				{ Constants.CAMP_X + (Constants.A_GRID * 5),
						Constants.CAMP_Y - (Constants.A_GRID * 11) },
				{ Constants.CAMP_X + (Constants.A_GRID * 6),
						Constants.CAMP_Y - (Constants.A_GRID * 8) },

		};
	}

	public void init() {
		Tank tank = new Tank(100, 100, TEAM_NPC, TankType.A);
		tankList.add(tank);
	}

	public Tank initUserTank(int clientId) {
		Tank tank = null;
		randomLocationX = random.nextInt(Constants.GAME_WIDTH);
		randomLocationY = random.nextInt(Constants.GAME_HEIGHT);
		if (clientId % 2 == 0) {
			tank = new Tank(randomLocationX, randomLocationY, 1, TankType.B);
		} else {
			tank = new Tank(randomLocationX, randomLocationY, 0, TankType.B);
		}
		tank.setClientId(clientId);
		tankList.add(tank);
		return tank;
	}

	public Missile initTankMissile(int tankId) {
		int missileId = (++missileIndex);
		Tank tank = getTank(tankId);
		Missile missile = tank.makeMissile(missileId);
		missileList.add(missile);
		return missile;
	}

	public void sendAllName(ByteBuffer buffer, int gameId) {
		int count = userPool.size();
		buffer.putInt(count);
		Collection<UserSession> nameList = userPool.values();
		for (UserSession userSession : nameList) {
			StringUtil.putString(buffer, userSession.getUser().getName());
		}

	}

	public void serializeAllBlock(ByteBuffer buffer) {
		int count = this.blockList.size();
		buffer.putInt(count);
		for (int i = 0; i < count; i++) {
			Block block = this.blockList.get(i);
			block.serialize(buffer);
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

	public boolean checkGreenTeamIsLive() {
		for (Tank tank : tankList) {
			if (tank.getTeam() == 1) {
				if (tank.isLive()) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean checkRedTeamIsLive() {
		for (Tank tank : tankList) {
			if (tank.getTeam() == 0) {
				if (tank.isLive()) {
					return false;
				}
			}
		}
		return true;
	}

	public void endGame() {
		this.status = GameStatus.Idle;
		int team = -1;
		if (!checkRedTeamIsLive()) {
			team = 0;
		} else if (!checkGreenTeamIsLive()) {
			team = 1;
		}
		Packet packet = new Packet(Command.S_GAME_END);
		packet.getByteBuffer().putInt(team);
		this.broadcast(packet);
		tankList.clear();
		missileList.clear();
		explodeList.clear();
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

	private void hitCamp(Missile missile) {
		if (missile.hitCamp(camp)) {
			Packet writePacket = new Packet(Command.S_HIT_CAMP);
			writePacket.getByteBuffer().putInt(missile.getId());

			Explode explode = new Explode(missile.getX(), missile.getY());
			explodeList.add(explode);

			explode.serialize(writePacket.getByteBuffer());

			broadcast(writePacket);
		}
	}

	private void hitBlock(Missile missile) {
		for (Block block : blockList) {
			if (missile.hitBlock(block)) {
				Packet writePacket = new Packet(Command.S_HIT_BLOCK);
				writePacket.getByteBuffer().putInt(missile.getId());
				writePacket.getByteBuffer().putInt(block.getId());
				
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
		if (missile.getX() < 10
				|| missile.getY() < 10
				|| missile.getX() > Constants.GAME_WIDTH - missile.getWidth()
				|| missile.getY() > Constants.GAME_HEIGHT - missile.getHeight()
						/ 2) {

			missile.setLive(false);

			Packet writePacket = new Packet(Command.S_HIT_WALL);
			writePacket.getByteBuffer().putInt(missile.getId());
			Explode explode = new Explode(missile.getX(), missile.getY());
			explodeList.add(explode);
			explode.serialize(writePacket.getByteBuffer());
			broadcast(writePacket);
		}
	}

	public void update() {
		if (!camp.isLive()) {
			endGame();
			return;
		}
		camp.update();
		long currentTime = System.currentTimeMillis();
		if (lastUpdateTime == 0) {
			lastUpdateTime = currentTime;
			return;
		}
		long deltaTime = currentTime - lastUpdateTime;
		lastUpdateTime = currentTime;

		Iterator<Tank> itt = tankList.iterator();
		while (itt.hasNext()) {
			Tank tank = itt.next();

			if (!tank.isLive()) {
				tankList.remove(tank);
				endGame();
				return;
			}
			collidesWithTanks(tank);
			tank.update(deltaTime);
		}

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
			hitCamp(missile);
			hitBlock(missile);
		}

		Iterator<Block> itb = blockList.iterator();
		while (itb.hasNext()) {

			Block block = itb.next();

			block.update();
		}

	}

	public void broadcast(Packet packet) {
		Collection<UserSession> sessionList = userPool.values();
		for (UserSession session : sessionList) {
			session.getClient().pushWritePacket(packet);
		}
	}

	public boolean isAllUserReady() {
		// if (this.userPool.size() < MAX_USER_COUNT) {
		// return false;
		// }
		Collection<UserSession> users = this.userPool.values();
		for (UserSession user : users) {
			if (!user.isReady()) {
				return false;
			}
		}
		return true;
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
			logger.debug("canJoin, true,  gameWorldId:{}, status:{}", this.id,
					this.status);
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
		packet.getByteBuffer().putFloat(camp.getX());
		packet.getByteBuffer().putFloat(camp.getY());
		this.serializeAllTanks(packet.getByteBuffer());
		this.serializeAllMissiles(packet.getByteBuffer());
		this.serializeAllBlock(packet.getByteBuffer());
		this.sendAllName(packet.getByteBuffer(), id);
		this.broadcast(packet);
	}

}
