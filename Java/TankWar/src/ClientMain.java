import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Block;
import common.Command;
import common.Explode;
import common.Missile;
import common.Tank;

import easysocket.packet.Packet;
import easysocket.session.AioTcpSession;
import easysocket.session.event.SessionEventListener;
import easysocket.utils.StringUtil;

public class ClientMain {
	private static Logger logger = LoggerFactory.getLogger(ClientMain.class);
	public static final String SERVER_HOST = "127.0.0.1";
	public static final int SERVER_PORT = 8888;

	public static AioTcpSession session;
	public static TankClient tankClient;

	private static void handlePacket(Packet packet) {
		logger.debug("ClientMain.handlePacket, " + packet.getCmd());
		short cmd = packet.getCmd();
		switch (cmd) {
		case Command.S_READY: {
			logger.debug("S_READY:");

			// do nothing
		}
			break;
		case Command.S_GAME_START: {

			logger.debug("S_GAME_START");
			float x = packet.getByteBuffer().getFloat();
			float y = packet.getByteBuffer().getFloat();
			ClientMain.tankClient.getCamp().setX(x);
			ClientMain.tankClient.getCamp().setY(y);

			int tankCount = packet.getByteBuffer().getInt();
			// logger.debug("S_LOGIN:,clientId:{}", clientId);
			List<Tank> tankList = new ArrayList<Tank>(tankCount);
			for (int i = 0; i < tankCount; i++) {
				Tank tank = Tank.deserialize(packet.getByteBuffer());
				tankList.add(tank);
			}

			int missileCount = packet.getByteBuffer().getInt();
			List<Missile> missileList = new ArrayList<Missile>(missileCount);
			for (int i = 0; i < missileCount; i++) {
				Missile missile = Missile.deserialize(packet.getByteBuffer());
				missileList.add(missile);
			}

			int blockCount = packet.getByteBuffer().getInt();
			List<Block> BlockList = new ArrayList<Block>(blockCount);
			for (int i = 0; i < blockCount; i++) {
				Block block = Block.deserialize(packet.getByteBuffer());
				BlockList.add(block);
			}

			int playersCount = packet.getByteBuffer().getInt();
			List<String> playersNameList = new ArrayList<String>(playersCount);
			for (int i = 0; i < playersCount; i++) {
				String playerName = StringUtil
						.getString(packet.getByteBuffer());
				playersNameList.add(playerName);
			}

			tankClient.setTankList(tankList);
			tankClient.setMissileList(missileList);
			tankClient.setBlockList(BlockList);
			tankClient.setPlayersName(playersNameList);
		}
			break;
		case Command.S_MOVE: {
			int clientId = packet.getByteBuffer().getInt();
			int id = packet.getByteBuffer().getInt();
			int angle = packet.getByteBuffer().getInt();
			boolean collideWithBlock = packet.getByteBuffer().get() == 1 ? true
					: false;
			ClientMain.tankClient.move(clientId, id, angle, collideWithBlock);
		}
			break;
		case Command.S_STOP: {
			int clientId = packet.getByteBuffer().getInt();
			int tankId = packet.getByteBuffer().getInt();
			ClientMain.tankClient.stop(clientId, tankId);
		}
			break;
		case Command.S_EXIT: {
			int clientId = packet.getByteBuffer().getInt();
			ClientMain.tankClient.removeTankByClientId(clientId);
		}
			break;
		case Command.S_NEW_MISSILE: {
			int tankId = packet.getByteBuffer().getInt();
			int missileId = packet.getByteBuffer().getInt();
			ClientMain.tankClient.fire(tankId, missileId);
		}
			break;
		case Command.S_HIT_TANK: {
			int missileId = packet.getByteBuffer().getInt();
			int tankId = packet.getByteBuffer().getInt();

			Explode explode = Explode.deserialize(packet.getByteBuffer());
			logger.debug("S_HIT_TANK,missileId:{}, tankId:{}", missileId,
					tankId);
			ClientMain.tankClient.tankDead(tankId);
			ClientMain.tankClient.missileDead(missileId, explode);
			ClientMain.tankClient.explodeDead(explode.getId());

		}
			break;
		case Command.S_TANKS_COLLIDE: {
			int tank1Id = packet.getByteBuffer().getInt();
			int tank2Id = packet.getByteBuffer().getInt();

			ClientMain.tankClient.tanksCollide(tank1Id, tank2Id);
		}
			break;
		case Command.S_HIT_WALL: {
			int missileId = packet.getByteBuffer().getInt();
			Explode explode = Explode.deserialize(packet.getByteBuffer());

			ClientMain.tankClient.missileDead(missileId, explode);
			ClientMain.tankClient.explodeDead(explode.getId());

		}
			break;
		case Command.S_LOGIN: {
			int gameWorldId = packet.getByteBuffer().getInt();
			int clientId = packet.getByteBuffer().getInt();
			logger.debug("S_LOGIN, clientId:{}", clientId);
			new GameStartBox(gameWorldId, clientId);
		}
			break;

		case Command.S_GAME_END: {
			int teamWin = packet.getByteBuffer().getInt();
			if (teamWin == 0) {
				ClientMain.tankClient.setTeamWin("UserTeam");
			} else if (teamWin == 1) {
				ClientMain.tankClient.setTeamWin("NPCTeam");
			}
			ClientMain.tankClient.setWiningTeam(true);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ClientMain.tankClient.setGameOver(true);
		}
			break;
		case Command.S_HIT_CAMP: {
			int missileId = packet.getByteBuffer().getInt();

			Explode explode = Explode.deserialize(packet.getByteBuffer());
			logger.debug("S_HIT_CAMP,missileId:{}", missileId);
			ClientMain.tankClient.missileDead(missileId, explode);
			ClientMain.tankClient.explodeDead(explode.getId());
			ClientMain.tankClient.campDead();

		}
			break;
		case Command.S_HIT_BLOCK: {
			int missileId = packet.getByteBuffer().getInt();
			int blockId = packet.getByteBuffer().getInt();

			Explode explode = Explode.deserialize(packet.getByteBuffer());
			logger.debug("S_HIT_CAMP,missileId:{}, blockId:{}", missileId,
					blockId);
			ClientMain.tankClient.missileDead(missileId, explode);
			ClientMain.tankClient.explodeDead(explode.getId());
			ClientMain.tankClient.blockDead(blockId);

		}
			break;
		case Command.S_DEBUG_TANK_INFO: {
			int count = packet.getByteBuffer().getInt();
			List<Tank> tanks = new ArrayList<Tank>(count);
			for (int i = 0; i < count; i++) {
				Tank tank = Tank.deserialize(packet.getByteBuffer());
				tanks.add(tank);
			}
			if (ClientMain.tankClient != null)
				ClientMain.tankClient.setServerTanks(tanks);
		}
			break;
		case Command.S_TANK_POS_UPDATE: {
			int tankId = packet.getByteBuffer().getInt();
			float x = packet.getByteBuffer().getFloat();
			float y = packet.getByteBuffer().getFloat();
			if (ClientMain.tankClient != null)
				ClientMain.tankClient.correctDeviation(tankId, x, y);
		}
			break;
		}
	}

	public static void showGameBox(int gameWorldId, int clientId) {
		tankClient = new TankClient();
		tankClient.launchFrame();
		tankClient.setMyGameRoomID(gameWorldId);
		tankClient.setMyClientId(clientId);

	}

	public static void connect(String name) {

		AioTcpSession session = null;
		try {
			AsynchronousSocketChannel channel = AsynchronousSocketChannel
					.open();
			channel.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
			logger.debug("Client connect");

			session = new AioTcpSession(channel);
			session.registerEventListener(new SessionEventListener() {

				@Override
				public void onReceivePackets(List<Packet> packets) {
					for (Packet packet : packets) {
						try {
							handlePacket(packet);
						} catch (Exception e) {
							logger.error("packet error, type:{}, msg:{}", e
									.getClass().getName(), e.getMessage());
							e.printStackTrace();
						}
					}
				}

				@Override
				public void onClose() {
					logger.error("socket closed");
				}
			});

			ClientMain.session = session;
			session.pendingRead();
			Packet packet = new Packet(Command.C_LOGIN);
			StringUtil.putString(packet.getByteBuffer(), name);
			session.sendPacket(packet);

		} catch (Exception e) {
			e.printStackTrace();

			if (session != null) {
				try {
					session.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
