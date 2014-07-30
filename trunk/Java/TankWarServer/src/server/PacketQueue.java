package server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Command;
import common.Missile;

import easysocket.packet.Packet;
import easysocket.utils.StringUtil;

public class PacketQueue implements Runnable {

	static Logger logger = LoggerFactory.getLogger(PacketQueue.class);
	private BlockingQueue<Packet> queue = new LinkedBlockingQueue<>();

	private Thread thread;
	private boolean isRunning = false;

	public PacketQueue() {
		this.isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

	public void pushPacket(Packet packet) {
		queue.add(packet);
	}

	private void handlePacket(Packet packet) {
		short cmd = packet.getCmd();
		int clientId = packet.getSession().getSessionId();
		UserSession session = ServerMain.getServer().getUserSession(clientId);
		switch (cmd) {
		case Command.C_LOGIN: {
			String name = StringUtil.getString(packet.getByteBuffer());
			User user = new User(name);

			session.setUser(user);

			GameWorld game = ServerMain.getServer()
					.findProperGameWorld(session);
			logger.debug("C_LOGIN,game:" + game + "---session:" + session);
			game.join(session);

			session.getUser().setGameWorldIndex(game.getId());
			game.init();
			game.initUserTank(clientId);

			game.sendServerLoginCommand(packet, clientId);
			logger.debug("C_LOGIN, response packet");
			// if (game.getStatus() == GameStatus.Idle) {
			// game.sendServerLoginCommand(packet, clientId);
			//
			// } else if (game.getStatus() == GameStatus.Waiting) {
			// game.sendServerNewMsg(tank, name);
			// game.sendServerLoginCommand(packet, clientId);
			// }
			// logger.debug("LOGIN, name:{}", name);
			// Tank tank =game.initUserTank(clientId);
			//
			// Packet writePacket2 = new Packet(Command.S_NEW_TANK,
			// Short.MAX_VALUE);
			// tank.serialize(writePacket2.getByteBuffer());
			// StringUtil.putString(writePacket2.getByteBuffer(), name);
			// game.broadcast(writePacket2);
			//
			// Packet writePacket = new Packet(Command.S_LOGIN,
			// Short.MAX_VALUE);
			// writePacket.getByteBuffer().putInt(gameId);
			// writePacket.getByteBuffer().putInt(clientId);
			// game.serializeAllTanks(writePacket.getByteBuffer());
			// game.serializeAllMissiles(writePacket.getByteBuffer());
			// packet.getClient().pushWritePacket(writePacket);

			// Packet writepacket3 = new Packet(Command.S_NEW_PLAYERS_NAME);
			// game.sendAllName(writepacket3.getByteBuffer());
			// packet.getClient().pushWritePacket(writepacket3);
		}
			break;
		case Command.C_MOVE: {
			
			int id = packet.getByteBuffer().getInt();
			int angle = packet.getByteBuffer().getInt();
			logger.debug("C_MOVE, id:{}, angle:{}", id, angle);

			User user = session.getUser();
			GameWorld game = ServerMain.getServer().getGameWorld(
					user.getGameWorldIndex());
			if(game.getTank(id).isMoveToNextBlock()){
				game.move(clientId, id, angle);
			}
		}
			break;
		case Command.C_STOP: {
			int tankId = packet.getByteBuffer().getInt();
			User user = session.getUser();
			GameWorld game = ServerMain.getServer().getGameWorld(
					user.getGameWorldIndex());
			game.stop(clientId, tankId);
			logger.debug("C_STOP, id:{}", tankId);
		}
			break;
		case Command.C_NEW_MISSILE: {
			int tankId = packet.getByteBuffer().getInt();
			logger.debug("C_NEW_MISSILE, tanlId:" + tankId);
			User user = session.getUser();
			GameWorld game = ServerMain.getServer().getGameWorld(
					user.getGameWorldIndex());
			Missile missile = game.initTankMissile(tankId);
			if (missile != null) {
				Packet writePacket = new Packet(Command.S_NEW_MISSILE);
				writePacket.getByteBuffer().putInt(tankId);
				writePacket.getByteBuffer().putInt(missile.getId());

				game.broadcast(writePacket);
			} else {
				// do nothing
			}

		}
			break;
		case Command.C_READY: {
			session.setReady(true);
			User user = session.getUser();
			GameWorld game = ServerMain.getServer().getGameWorld(
					user.getGameWorldIndex());

			Packet writePacket = new Packet(Command.S_READY);
			session.getSession().sendPacket(writePacket);

			// check all user ready
			if (game.isAllUserReady()) {
				game.setStatus(GameStatus.Playing);
				game.broadcastGameStart();
			}
			logger.debug("C_START,GameStatus:{}, GameId:{}", game.getStatus(),
					game.getId());
		}
			break;
		default:
			break;
		}
	}

	@Override
	public void run() {
		logger.info("PacketQueue started....");
		while (isRunning) {
			try {
				Packet packet = queue.take();
				handlePacket(packet);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
	}
}
