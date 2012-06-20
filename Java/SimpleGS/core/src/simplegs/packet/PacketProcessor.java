package simplegs.packet;

import java.util.Map;

import simplegs.command.ICommandFactory;
import simplegs.json.DataParser;
import simplegs.log.Log;
import simplegs.session.ClientSession;
import simplegs.session.SessionFactory;

public class PacketProcessor implements Runnable {

	private ProcessQueue queue;

	private ICommandFactory commandFactory;

	public boolean isRunning = true;

	public PacketProcessor(ProcessQueue queue, ICommandFactory commandFactory) {
		this.queue = queue;
		this.commandFactory = commandFactory;
	}

	private Packet poll() {
		try {
			return queue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void stop() {
		this.isRunning = false;
	}

	@Override
	public void run() {

		if (queue == null)
			throw new IllegalStateException("packet queue is not initialized.");

		while (isRunning) {

			Log.info("packet processor is running.");
			Packet packet = this.poll();
			try {
				this.process(packet);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Log.info("packet processor stopped.");
	}

	public void process(Packet packet) {
		if (packet.data() != null && packet.data().length() > 2) {
			Map<String, Object> map = DataParser.parse(packet.data());
			packet.putRequestParameter(map);
		}
		setThreadLocal(packet);
		commandFactory.execute(packet);
		packet.clearRequestParameter();
	}

	private void setThreadLocal(Packet packet) {
		ClientSession session = packet.getSession();
		SessionFactory.SESSION.remove();
		SessionFactory.SESSION.set(session);
		SessionFactory.PACKET.remove();
		SessionFactory.PACKET.set(packet);
	}

}
