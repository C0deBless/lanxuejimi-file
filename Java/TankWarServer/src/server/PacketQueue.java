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
		switch (cmd) {
		case Command.LOGIN:
			String name = StringUtil.getString(packet.getByteBuffer());
			User user = new User(packet.getClient(), name);
			ServerMain.getGameWorld().getUserPool().put(user.getClientId(), user);
			logger.debug("LOGIN, name:{}", name);
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
