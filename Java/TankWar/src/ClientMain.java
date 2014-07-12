import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Block;
import common.Client;
import common.Command;
import common.Explode;
import common.Missile;
import common.Packet;
import common.PacketEventListener;
import common.SocketCloseEventListener;
import common.StringUtil;
import common.Tank;

public class ClientMain {
	private static Logger logger = LoggerFactory.getLogger(ClientMain.class);
	public static final String SERVER_HOST = "127.0.0.1";
	public static final int SERVER_PORT = 8888;

	public static Socket socket = null;
	public static Client client;
	public static TankClient tankClient;

	private static void handlePacket(Packet packet) {
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
			ClientMain.tankClient.move(clientId, id, angle);
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
			new GameStartBox(gameWorldId, clientId);
		}
			break;

		case Command.S_GAME_END: {
			int teamWin = packet.getByteBuffer().getInt();
			if(teamWin == 0){
				ClientMain.tankClient.setTeamWin("RedTeam");
			}else if(teamWin == 1){
				ClientMain.tankClient.setTeamWin("GreenTeam");
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
		}
	}

	public static void showGameBox(int gameWorldId, int clientId) {
		tankClient = new TankClient();
		tankClient.launchFrame();
		tankClient.setMyGameRoomID(gameWorldId);
		tankClient.setMyClientId(clientId);

	}

	public static void connect(String name) {
		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
			logger.debug("Client connect");
			client = new Client(socket);

			Packet packet = new Packet(Command.C_LOGIN);
			StringUtil.putString(packet.getByteBuffer(), name);
			client.pushWritePacket(packet);

			client.setPacketEventListener(new PacketEventListener() {
				@Override
				public void receive(List<Packet> packets) {
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
			});

			client.setCloseEventListener(new SocketCloseEventListener() {
				@Override
				public void onClose() {
					logger.error("socket closed");
				}
			});

			client.start();
		} catch (Exception e) {
			e.printStackTrace();
			if (socket != null) {
				try {
					socket.close();
					socket = null;
				} catch (IOException ex) {
					e.printStackTrace();
				}
			}
		}
	}

}
