import java.util.List;

import common.Tank;

class PaintThread implements Runnable {

	private final TankClient tankClient;

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
	}

	private void updateTanks(long deltaTime) {
		List<Tank> tanks = tankClient.getTanks();
		for (Tank tank : tanks) {
			tank.update(deltaTime);
		}
	}
}