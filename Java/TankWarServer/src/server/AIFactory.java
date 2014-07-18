package server;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.AIStatus;
import common.Command;
import common.Constants;
import common.Missile;
import common.Packet;
import common.Tank;

public class AIFactory {
	static Logger logger = LoggerFactory.getLogger(AIFactory.class);
	private final GameWorld game;
	private long lastUpdateTime;
	private final Random random = new Random();

	public AIFactory(GameWorld game) {
		this.game = game;
	}

	public void update() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastUpdateTime > 1000) {
			lastUpdateTime = currentTime;
			for (Tank tank : game.getNPCTankList()) {
				this.updateAIStatus(tank);
				updateShotingStatus(tank);
			}
		}
	}

	private void updateAIStatus(Tank tank) {
		if (!tank.isLive()) {
			return;
		}

		AIStatus status = tank.getStatus();
		switch (status) {
		case Start:
			onAIStart(tank);
			break;
		case Waiting:
			onAIWaiting(tank);
			break;
		case RandomMove:
			onAIRandomMove(tank);
			break;
		default:
			logger.error("unhandle ai status");
			break;
		}
	}

	private List<Integer> getAvailableAngles(Tank tank) {
		List<Integer> list = new ArrayList<>(4);
		float x = tank.getX();
		float y = tank.getY();

		int gridX = ((int) x) / Constants.A_GRID;
		int gridY = ((int) y) / Constants.A_GRID;

		for (int angle = 0; angle < 4; angle++) {

			if (game.collideWithBlock(tank, angle)) {
				continue;
			}
			Point nextGrid = this.getNextGrid(angle, gridX, gridY);
			int nextX = nextGrid.x;
			int nextY = nextGrid.y;
			if (nextX < 0 || nextY < 0
					|| nextX > Constants.GAME_WIDTH / Constants.A_GRID
					|| nextY > Constants.GAME_HEIGHT / Constants.A_GRID) {
				continue;
			}

			list.add(angle);
		}
		return list;
	}

	private Point getNextGrid(int angle, int gridX, int gridY) {
		Point point = new Point();
		switch (angle) {
		case 0:
			point.x = gridX;
			point.y = gridY - 1;
			break;
		case 1:
			point.x = gridX + 1;
			point.y = gridY;
			break;
		case 2:
			point.x = gridX;
			point.y = gridY + 1;
			break;
		case 3:
			point.x = gridX - 1;
			point.y = gridY;
			break;
		}
		return point;
	}

	private void onAIRandomMove(Tank tank) {

		if (tank.getCurrentSpeed() == 0) {
			List<Integer> angles = this.getAvailableAngles(tank);
			int randomAngle = angles.get(random.nextInt(angles.size()));
			tank.move(randomAngle);
		} else {
			int ran = random.nextInt(100);
			if (ran <= 10) {
				tank.stop();
				tank.setStatus(AIStatus.Waiting);
			} else {
				// do nothing
			}
		}
	}

	private void onAIWaiting(Tank tank) {
		tank.setStatus(AIStatus.RandomMove);

	}

	private void onAIStart(Tank tank) {
		tank.setStatus(AIStatus.Waiting);
	}

	private void updateShotingStatus(Tank tank) {
		Missile m = game.initTankMissile(tank.getId());
		if (m != null) {
			Packet writePacket = new Packet(Command.S_NEW_MISSILE);
			writePacket.getByteBuffer().putInt(tank.getId());
			writePacket.getByteBuffer().putInt(m.getId());
			game.broadcast(writePacket);
		}
	}
}
