package simplegs.packet;

import java.util.concurrent.LinkedBlockingQueue;

import simplegs.log.Log;

public class ProcessQueue {

	private final LinkedBlockingQueue<Packet> queue;

	public ProcessQueue(LinkedBlockingQueue<Packet> queue) {
		Log.trace("make process queue");
		if (queue == null) {
			throw new NullPointerException("queue cannot be null");
		}
		this.queue = queue;
	}

	public void queue(Packet packet) {

		if (packet == null)
			throw new IllegalArgumentException("packet is null");

		queue.add(packet);
	}

	public Packet take() throws InterruptedException {
		return this.queue.take();
	}

}
