package server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Command;
import common.Missile;
import common.Packet;
import common.StringUtil;
import common.Tank;

public class PacketQueue implements Runnable {

	static Logger logger = LoggerFactory.getLogger(PacketQueue.class);
	private BlockingQueue<Packet> queue = new LinkedBlockingQueue<>();

	private List<User> users = new ArrayList<User>();
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
		int clientId = packet.getClient().getClientId();

		switch (cmd) {
		case Command.C_LOGIN: {
			String name = StringUtil.getString(packet.getByteBuffer());
			User user = new User(packet.getClient(), name);
			users.add(user);
			
			for (int i = 0; i < ServerMain.getGameWorlds().size(); i++) {
				if (ServerMain.getGameWorlds().get(i).getUserPool().size() < 3) {
					GameWorld gameWorld = ServerMain.getGameWorlds().get(i);
					user.setGameWorldIndex(i);
				}
			}
			ServerMain.getGameWorlds().get(user.getGameWorldIndex()).getUserPool().put(user.getClientId(), user);
			logger.debug("LOGIN, name:{}", name);

			Tank tank = ServerMain.getGameWorlds().get(user.getGameWorldIndex()).initUserTank(clientId);

			Packet writePacket2 = new Packet(Command.S_NEW_TANK,
					Short.MAX_VALUE);
			tank.serialize(writePacket2.getByteBuffer());
			ServerMain.getServer().broadcastPacket(writePacket2);

			Packet writePacket = new Packet(Command.S_LOGIN, Short.MAX_VALUE);
			writePacket.getByteBuffer().putInt(clientId);
			ServerMain.getGameWorlds().get(user.getGameWorldIndex()).serializeAllTanks(writePacket.getByteBuffer());
			ServerMain.getGameWorlds().get(user.getGameWorldIndex()).serializeAllMissiles(writePacket.getByteBuffer());
			packet.getClient().pushWritePacket(writePacket);

		}
			break;
		case Command.C_MOVE: {
			int id = packet.getByteBuffer().getInt();
			int angle = packet.getByteBuffer().getInt();
			logger.debug("C_MOVE, id:{}, angle:{}", id, angle);
			
			User user = this.getUser(clientId);
			ServerMain.getGameWorlds().get(user.getGameWorldIndex()).move(clientId, id, angle);

			Packet writePacket = new Packet(Command.S_MOVE, Short.MAX_VALUE);
			writePacket.getByteBuffer().putInt(clientId);
			writePacket.getByteBuffer().putInt(id);
			writePacket.getByteBuffer().putInt(angle);
			ServerMain.getServer().broadcastPacket(writePacket);

		}
			break;
		case Command.C_STOP: {
			int tankId = packet.getByteBuffer().getInt();
			User user = this.getUser(clientId);
			ServerMain.getGameWorlds().get(user.getGameWorldIndex()).stop(clientId, tankId);
			logger.debug("C_STOP, id:{}", tankId);

			Packet writePacket = new Packet(Command.S_STOP, 8);
			writePacket.getByteBuffer().putInt(clientId);
			writePacket.getByteBuffer().putInt(tankId);
			ServerMain.getServer().broadcastPacket(writePacket);
		}
			break;
		case Command.C_NEW_MISSILE: {
			int tankId = packet.getByteBuffer().getInt();
			logger.debug("C_NEW+MISSILE, tanlId:" + tankId);
			User user = this.getUser(clientId);
			Missile missile = ServerMain.getGameWorlds().get(user.getGameWorldIndex()).initTankMissile(tankId);

			Packet writePacket = new Packet(Command.S_NEW_MISSILE);
			writePacket.getByteBuffer().putInt(tankId);
			writePacket.getByteBuffer().putInt(missile.getId());

			ServerMain.getServer().broadcastPacket(writePacket);

		}
			break;
		default:
			break;
		}
	}
	
	public User getUser(int clientId){
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i).getUser(clientId);
			return user;
		}
		return null;
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
