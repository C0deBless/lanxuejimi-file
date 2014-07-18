package server;

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
				updatecollideWithBlockStatus(tank);
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

	private void onAIRandomMove(Tank tank) {

		if (tank.getCurrentSpeed() == 0) {

			int randomAngle;
			List<Integer> angles = new ArrayList<Integer>();
			for (int i = 0; i < 4; i++) {
				if (!game.collideWithBlock(tank, i)) {
					if (tank.getX() - Constants.A_GRID >= 0
							&& tank.getX() + Constants.A_GRID <= Constants.GAME_WIDTH
									- Constants.A_GRID
							&& tank.getY() - Constants.A_GRID >= 0
							&& tank.getY() + Constants.A_GRID <= Constants.GAME_HEIGHT
									- Constants.A_GRID) {
						angles.add(i);
					}

				}
			}
			randomAngle = angles.get(random.nextInt(angles.size()));
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

	private void updatecollideWithBlockStatus(Tank tank) {
		if (game.collideWithBlock(tank, tank.getAngle())) {
			int randomAngle = random.nextInt(4);
			tank.setAngle(randomAngle);
			Packet writePacket = new Packet(Command.S_MOVE, Short.MAX_VALUE);
			writePacket.getByteBuffer().putInt(tank.getClientId());
			writePacket.getByteBuffer().putInt(tank.getId());
			writePacket.getByteBuffer().putInt(randomAngle);
			writePacket.getByteBuffer().put((byte) 1); // 撞墙的情况
			game.broadcast(writePacket);
		} else {
			tank.move(tank.getAngle());
		}
	}
}
