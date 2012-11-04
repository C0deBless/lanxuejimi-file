package main;

import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import thread.Worker;

public class Monitor {

	static Logger logger = LoggerFactory.getLogger(Monitor.class);
	final Worker worker;
	final Timer timer;
	final long duration = 1000;

	public Monitor(Worker worker) {
		if (worker == null) {
			throw new NullPointerException();
		}
		this.worker = worker;
		timer = new Timer();
		logger.info("Monitor.init, new monitor created.");
	}

	public void run() {
		logger.info("Monitor.init, monitor started.");
		long currentTime = System.currentTimeMillis();

		timer.schedule(this.worker, currentTime, duration);

	}

	public void stop() {
		logger.info("Monitor.init, monitor stopped.");
		timer.cancel();
	}
}
