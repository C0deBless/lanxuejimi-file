package server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.AIStatus;
import common.Block;
import common.Camp;
import common.Command;
import common.Constants;
import common.Explode;
import common.Missile;
import common.Tank;
import common.TankType;
import common.event.TankEventListener;
import easysocket.packet.Packet;
import easysocket.utils.StringUtil;

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

	private GameStatus status = GameStatus.Idle;
	private final int id;

	private final AIFactory aiFactory;

	public GameWorld(int id) {
		this.id = id;
		camp = new Camp(Constants.CAMP_X, Constants.CAMP_Y);
		initblocksCoordinate();
		initBlocks();
		aiFactory = new AIFactory(this);
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
		for (int i = 0; i < 20; i++) {
			final Tank tank = new Tank(0 + (Constants.A_GRID * i), 0, TEAM_NPC,
					TankType.B);
			tank.setAngle(2);
			tank.registerEventListener(new TankEventListener() {

				@Override
				public void onStop() {
					int clientId = tank.getClientId();
					int tankId = tank.getId();
					Packet writePacket = new Packet(Command.S_STOP, 8);
					writePacket.getByteBuffer().putInt(clientId);
					writePacket.getByteBuffer().putInt(tankId);
					GameWorld.this.broadcast(writePacket);
					GameWorld.this.correctDeviation(tank);
				}

				@Override
				public void onMove() {
					int clientId = tank.getClientId();
					int tankId = tank.getId();

					Packet writePacket = new Packet(Command.S_MOVE,
							Short.MAX_VALUE);
					writePacket.getByteBuffer().putInt(clientId);
					writePacket.getByteBuffer().putInt(tankId);
					writePacket.getByteBuffer().putInt(tank.getAngle());
					writePacket.getByteBuffer().put((byte) 0); // 非撞墙停止
					GameWorld.this.broadcast(writePacket);
				}
			});
			tankList.add(tank);
		}

	}

	public Tank initUserTank(final int clientId) {
		final Tank tank = new Tank(Constants.CAMP_X
				+ (clientId % 2 == 0 ? 1 : -1) * (Constants.A_GRID * 2),
				Constants.CAMP_Y, 1, TankType.A);
		tank.setClientId(clientId);
		tank.setStatus(AIStatus.Start);
		tankList.add(tank);
		final int tankId = tank.getId();
		tank.registerEventListener(new TankEventListener() {

			@Override
			public void onStop() {
				Packet writePacket = new Packet(Command.S_STOP, 8);
				writePacket.getByteBuffer().putInt(clientId);
				writePacket.getByteBuffer().putInt(tankId);
				GameWorld.this.broadcast(writePacket);
				GameWorld.this.correctDeviation(tank);
			}

			@Override
			public void onMove() {

				int clientId = tank.getClientId();
				int tankId = tank.getId();

				Packet writePacket = new Packet(Command.S_MOVE, Short.MAX_VALUE);
				writePacket.getByteBuffer().putInt(clientId);
				writePacket.getByteBuffer().putInt(tankId);
				writePacket.getByteBuffer().putInt(tank.getAngle());
				writePacket.getByteBuffer().put((byte) 0); // 非撞墙停止
				GameWorld.this.broadcast(writePacket);

			}
		});
		return tank;
	}

	public Missile initTankMissile(int tankId) {
		int missileId = (++missileIndex);
		Tank tank = getTank(tankId);
		logger.debug("Time:" + tank.getTime());
		if (tank.isValidShotTime()) {
			Missile missile = tank.makeMissile(missileId);
			missileList.add(missile);
			tank.setLastShotTime(System.currentTimeMillis());
			return missile;
		} else {
			return null;
		}
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

	public boolean userIsDead() {
		for (Tank tank : tankList) {
			if (tank.getType() == TankType.A) {
				if (tank.isLive()) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean npcIsDead() {
		for (Tank tank : tankList) {
			if (tank.getType() == TankType.B) {
				if (tank.isLive()) {
					return false;
				}
			}
		}
		return true;
	}

	public void endGame() {
		this.status = GameStatus.Idle;
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

		this.syncTankPos(tank);

		if (this.collideWithBlock(tank, angle)) {
			tank.setAngle(angle);
			Packet writePacket = new Packet(Command.S_MOVE, Short.MAX_VALUE);
			writePacket.getByteBuffer().putInt(clientId);
			writePacket.getByteBuffer().putInt(tankId);
			writePacket.getByteBuffer().putInt(angle);
			writePacket.getByteBuffer().put((byte) 1); // 撞墙的情况
			this.broadcast(writePacket);
		} else {
			tank.move(angle);
		}
	}

	private boolean isAnyBlockAt(int blockX, int blockY) {
		for (Block block : this.blockList) {
			int x = ((int) block.getX()) / Constants.A_GRID;
			int y = ((int) block.getY()) / Constants.A_GRID;
			if (x == blockX && y == blockY) {
				return true;
			}
		}
		return false;
	}

	public void stop(int clientId, int tankId) {
		Tank tank = getTank(tankId);
		if (tank == null || tank.getClientId() != clientId) {
			logger.error("illegal move");
			return;
		}

		if (tank.isValidGrid()) {
			this.syncTankPos(tank);
			tank.stop();
			tank.setStatus(AIStatus.Waiting);
		} else {
			tank.moveToNextBlockAndStop();
		}

	}

	public void correctDeviation(Tank tank) {
		int blockX = ((int) tank.getX()) / Constants.A_GRID;
		int blockY = ((int) tank.getY()) / Constants.A_GRID;
		tank.setX(blockX * Constants.A_GRID);
		tank.setY(blockY * Constants.A_GRID);

		Packet packet = new Packet(Command.S_TANK_POS_UPDATE, 12);
		packet.getByteBuffer().putInt(tank.getId());
		packet.getByteBuffer().putFloat(tank.getX());
		packet.getByteBuffer().putFloat(tank.getY());
		this.broadcast(packet);
	}

	public void syncTankPos(Tank tank) {
		Packet packet = new Packet(Command.S_TANK_POS_UPDATE, 12);
		packet.getByteBuffer().putInt(tank.getId());
		packet.getByteBuffer().putFloat(tank.getX());
		packet.getByteBuffer().putFloat(tank.getY());
		this.broadcast(packet);
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

	public boolean collideWithBlock(Tank tank, int angle) {

		if (tank.isValidGrid()) {
			int blockX = ((int) tank.getX()) / Constants.A_GRID;
			int blockY = ((int) tank.getY()) / Constants.A_GRID;

			int targetX = -1;
			int targetY = -1;
			switch (angle) {
			case 0:
				targetX = blockX;
				targetY = blockY - 1;
				break;
			case 1:
				targetX = blockX + 1;
				targetY = blockY;
				break;
			case 2:
				targetX = blockX;
				targetY = blockY + 1;
				break;
			case 3:
				targetX = blockX - 1;
				targetY = blockY;
				break;
			}

			if (this.isAnyBlockAt(targetX, targetY)) {
				return true;
			}
			// do nothing
		} else {

		}
		return false;
	}

	public void update() {

		if (this.status == GameStatus.Waiting) {
			return;
		}

		if (!camp.isLive()) {
			Packet packet = new Packet(Command.S_GAME_END);
			packet.getByteBuffer().putInt(1);
			this.broadcast(packet);
			endGame();
			return;
		}
		Iterator<Block> itb = blockList.iterator();
		while (itb.hasNext()) {
			Block block = itb.next();
			if (!block.isLive()) {
				itb.remove();

			}
		}

		camp.update();
		aiFactory.update();

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
				return;
			}
			if (npcIsDead()) {
				Packet packet = new Packet(Command.S_GAME_END);
				packet.getByteBuffer().putInt(0);
				this.broadcast(packet);
				endGame();
			} else if (userIsDead()) {
				Packet packet = new Packet(Command.S_GAME_END);
				packet.getByteBuffer().putInt(1);
				this.broadcast(packet);
				endGame();
			}
			tank.update(deltaTime);
			// 判断一下有没有和墙相撞
			if (this.collideWithBlock(tank, tank.getAngle())) {
				tank.stop();
				tank.setStatus(AIStatus.Waiting);
			}
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

		updateDebugInfo();
	}

	private long lastDebugUpdateTime = 0;

	private void updateDebugInfo() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastDebugUpdateTime >= 100) {
			this.lastDebugUpdateTime = currentTime;
			// send debug packet
			Packet packet = new Packet(Command.S_DEBUG_TANK_INFO);
			packet.getByteBuffer().putInt(this.tankList.size());
			for (Tank tank : this.tankList) {
				tank.serialize(packet.getByteBuffer());
			}
			this.broadcast(packet);
		}
	}

	public void broadcast(Packet packet) {
		Collection<UserSession> sessionList = userPool.values();
		for (UserSession session : sessionList) {
			session.getSession().sendPacket(packet);
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
		this.userPool.put(session.getSession().getSessionId(), session);
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
		packet.getSession().sendPacket(writePacket);
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

	public List<Tank> getNPCTankList() {
		List<Tank> tankList = new ArrayList<>(this.tankList.size());
		for (Tank tank : this.tankList) {
			if (tank.getTeam() == TEAM_NPC) {
				tankList.add(tank);
			}
		}
		return tankList;
	}

	public List<Block> getBlockList() {
		return this.blockList;
	}
}
