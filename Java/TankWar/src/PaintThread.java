import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Explode;
import common.Missile;
import common.Tank;

class PaintThread implements Runnable {

	private final TankClient tankClient;
	static Logger logger = LoggerFactory.getLogger(PaintThread.class);

	PaintThread(TankClient tankClient) {
		this.tankClient = tankClient;
	}

	@Override
	public void run() {
		while (true) {
			this.update();
			this.tankClient.repaint();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private long lastUpdateTime = 0;

	private void update() {
		long currentTime = System.currentTimeMillis();
		if (lastUpdateTime == 0) {
			lastUpdateTime = currentTime;
			return;
		}
		long deltaTime = currentTime - lastUpdateTime;
		lastUpdateTime = currentTime;

		this.updateTanks(deltaTime);
		this.updateMissiles(deltaTime);
		this.updateExplodes();
	}

	private void updateMissiles(long deltaTime) {
		synchronized (tankClient.getMissiles()) {
			List<Missile> missiles = tankClient.getMissiles();
			for (Missile missile : missiles) {
				missile.update(deltaTime);
			}
		}
	}

	private void updateTanks(long deltaTime) {
		List<Tank> tanks = tankClient.getTanks();
		for (Tank tank : tanks) {
			tank.update(deltaTime);
		}
	}

	private void updateExplodes() {
		synchronized (tankClient.getExplodes()) {
			List<Explode> explodes = tankClient.getExplodes();
			Iterator<Explode> it = explodes.iterator();
			while (it.hasNext()) {
				Explode explode = it.next();
				explode.update();
				if (!explode.isLive()) {
					it.remove();
					logger.debug(
							"PaintThread.updateExplodes, remove Explode, id:{}",
							explode.getId());
				}
			}
		}
	}
}