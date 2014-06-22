package server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Command;
import common.Packet;
import common.StringUtil;

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
		int clientId = packet.getClient().getClientId();

		switch (cmd) {
		case Command.C_LOGIN: {
			String name = StringUtil.getString(packet.getByteBuffer());
			User user = new User(packet.getClient(), name);
			ServerMain.getGameWorld().getUserPool()
					.put(user.getClientId(), user);
			logger.debug("LOGIN, name:{}", name);

			ServerMain.getGameWorld().initUserTank(clientId);

			Packet writePacket = new Packet(Command.S_LOGIN, Short.MAX_VALUE);
			writePacket.getByteBuffer().putInt(clientId);
			ServerMain.getGameWorld().serializeAllTanks(
					writePacket.getByteBuffer());
			packet.getClient().pushWritePacket(writePacket);

		}
			break;
		case Command.C_MOVE: {
			int id = packet.getByteBuffer().getInt();
			int angle = packet.getByteBuffer().getInt();
			logger.debug("C_MOVE, id:{}, angle:{}", id, angle);
			ServerMain.getGameWorld().move(clientId, id, angle);

			Packet writePacket = new Packet(Command.S_MOVE, Short.MAX_VALUE);
			writePacket.getByteBuffer().putInt(clientId);
			writePacket.getByteBuffer().putInt(id);
			writePacket.getByteBuffer().putInt(angle);
			ServerMain.getServer().BroadcastPacket(writePacket);

		}
			break;
		case Command.C_STOP: {
			int tankId = packet.getByteBuffer().getInt();
			ServerMain.getGameWorld().stop(clientId, tankId);
			logger.debug("C_STOP, id:{}, angle:{}", tankId);
			Packet writePacket = new Packet(Command.S_STOP, 8);
			writePacket.getByteBuffer().putInt(clientId);
			writePacket.getByteBuffer().putInt(tankId);
			ServerMain.getServer().BroadcastPacket(writePacket);
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
