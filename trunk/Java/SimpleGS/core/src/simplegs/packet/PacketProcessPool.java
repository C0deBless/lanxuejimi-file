package simplegs.packet;

import java.util.concurrent.LinkedBlockingQueue;

import simplegs.command.ICommandFactory;
import simplegs.log.Log;
import simplegs.session.ClientSession;
import simplegs.thread.ServerThreadGroup;

public final class PacketProcessPool {
	private PacketProcessPool() {

	}

	private int processors;
	private int maxQueueSize = 100;
	private ProcessQueue[] queues;
	private ICommandFactory commandFactory;
	private ServerThreadGroup threadGroup;

	public static final PacketProcessPool init(ServerThreadGroup threadGroup,
			ICommandFactory commandFactory) {

		Log.trace("PacketProcessPool.init(), init packet process pool");
		if (commandFactory == null) {
			throw new NullPointerException("command factory cannot be null");
		}
		PacketProcessPool pool = new PacketProcessPool();
		pool.threadGroup = threadGroup;
		pool.commandFactory = commandFactory;
		pool.processors = Runtime.getRuntime().availableProcessors();
		pool.queues = new ProcessQueue[pool.processors];
		for (int i = 0; i < pool.processors; i++) {
			LinkedBlockingQueue<Packet> queue = new LinkedBlockingQueue<>(
					pool.maxQueueSize);
			ProcessQueue proccess = new ProcessQueue(queue);
			pool.queues[i] = proccess;
		}
		pool.start();
		return pool;
	}

	public void start() {
		for (ProcessQueue queue : this.queues) {
			PacketProcessor processor = new PacketProcessor(queue,
					commandFactory);
			threadGroup.invokeThread(processor);
		}
	}

	public void dispatchPacket(Packet packet) {
		ClientSession session = packet.getSession();
		int index = session.getSessionId() % this.queues.length;
		this.queues[index].queue(packet);
	}
}
