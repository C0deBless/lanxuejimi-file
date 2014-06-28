import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Command;
import common.Explode;
import common.Missile;
import common.Packet;
import common.Tank;

public class TankClient extends Frame {

	static Logger logger = LoggerFactory.getLogger(TankClient.class);

	private static final long serialVersionUID = 1L;
	public static final int TANK_SERVER_TCP_PORT = 8888;
	public static final int TANK_SERVER_UDP_PORT = 6666;

	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;

	List<Tank> tanks = new ArrayList<Tank>();
	List<Explode> explodes = new ArrayList<Explode>();
	List<Missile> missiles = new ArrayList<Missile>();
	private int myClientId;

	private boolean judgeKey = true;

	Image offScreenImage = null;

	public void setTankList(List<Tank> tankList) {
		synchronized (tanks) {
			tanks.clear();
			tanks.addAll(tankList);
		}
	}

	private void drawTank(Tank tank, Graphics g) {
		if (!tank.isLive()) {
			return;
		}
		Color c = g.getColor();
		if (tank.getTeam() == 0) {
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.LIGHT_GRAY);
		}
		g.fillRect((int) tank.getX(), (int) tank.getY(), tank.getWidth(),
				tank.getHeight());
		g.drawString("" + tank.getId(), (int) tank.getX(),
				(int) tank.getY() - 10);

		g.setColor(c);

		// FIXME angle
	}

	public void drawMissile(Missile missile, Graphics g) {

		if (!missile.isLive()) {
			return;
		}
		Color c = g.getColor();
		if (missile.getTeam() == 0) {
			g.setColor(Color.PINK);
		} else {
			g.setColor(Color.WHITE);
		}
		g.fillOval((int) missile.getX(), (int) missile.getY(),
				missile.getWidth(), missile.getHeight());
		g.setColor(c);
	}

	public void drawExplode(Explode explode, Graphics g) {
		if (explode.getStep() == explode.getDiameter().length) {
			return;
		}
		if (!explode.isLive()) {
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval((int) explode.getX(), (int) explode.getY(),
				explode.getDiameter()[explode.getStep()],
				explode.getDiameter()[explode.getStep()]);
		g.setColor(c);
	}

	public void drawGuntube(Tank tank, Graphics g) {
		int angle = tank.getAngle();
		int i = 7;
		Color c = g.getColor();
		g.setColor(Color.GREEN);
		switch (angle) {
		case 0:
			g.drawLine((int) tank.getX() + tank.getWidth() / 2,
					(int) tank.getY() + tank.getHeight() / 2, (int) tank.getX()
							+ tank.getWidth() / 2, (int) tank.getY() - i);
			break;
		case 1:
			g.drawLine((int) tank.getX() + tank.getWidth() / 2,
					(int) tank.getY() + tank.getHeight() / 2, (int) tank.getX()
							+ tank.getWidth() + i,
					(int) tank.getY() + tank.getHeight() / 2);
			break;
		case 2:
			g.drawLine((int) tank.getX() + tank.getWidth() / 2,
					(int) tank.getY() + tank.getHeight() / 2, (int) tank.getX()
							+ tank.getWidth() / 2,
					(int) tank.getY() + tank.getHeight() + i);
			break;
		case 3:
			g.drawLine((int) tank.getX() + tank.getWidth() / 2,
					(int) tank.getY() + tank.getHeight() / 2, (int) tank.getX()
							- i, (int) tank.getY() + tank.getHeight() / 2);
			break;

		}
		g.setColor(c);
	}

	public void paint(Graphics g) {

		synchronized (tanks) {
			for (Tank tank : tanks) {
				drawTank(tank, g);
				drawGuntube(tank, g);
			}
		}
		synchronized (missiles) {
			for (Missile missile : missiles) {
				drawMissile(missile, g);
			}
		}
		synchronized (explodes) {
			for (Explode explode : explodes) {
				drawExplode(explode, g);
			}
		}

	}

	public void update(Graphics g) {

		if (offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics offScreen = offScreenImage.getGraphics();
		Color c = offScreen.getColor();
		offScreen.setColor(Color.BLACK);
		offScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		offScreen.setColor(c);
		paint(offScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	public void launchFrame() {

		this.setTitle("TankWar");
		this.setLocation(500, 200);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		this.setResizable(false);
		this.setBackground(Color.BLACK);
		this.addKeyListener(new KeyMonitor());
		this.setVisible(true);
		new Thread(new PaintThread(this)).start();

	}

	private Tank getTank(int tankId) {
		synchronized (tanks) {
			for (Tank tank : tanks) {
				if (tank.getId() == tankId) {
					return tank;
				}
			}
			return null;
		}
	}

	public void removeTankByClientId(int clientId) {
		synchronized (tanks) {
			Iterator<Tank> it = tanks.iterator();
			while (it.hasNext()) {
				Tank tank = it.next();
				if (tank.getClientId() == clientId) {
					it.remove();
				}

			}
		}
	}

	public void move(int clientId, int tankId, int angle) {
		Tank tank = getTank(tankId);
		if (tank == null) {
			logger.error("cannot find tank, clientId:{}, tankId:{}", clientId,
					tankId);
			return;
		}
		tank.setAngle(angle);
		tank.setCurrentSpeed(100);
	}

	public void stop(int clientId, int tankId) {
		Tank tank = getTank(tankId);
		if (tank == null) {
			logger.error("cannot find tank, clientId:{}, tankId:{}", clientId,
					tankId);
			return;
		}
		tank.setCurrentSpeed(0);
	}

	public void missileDead(int missileId) {
		synchronized (missiles) {
			Iterator<Missile> it = missiles.iterator();
			boolean found = false;
			while (it.hasNext()) {
				Missile missile = it.next();
				if (missile.getId() == missileId) {
					missile.setLive(false);
					it.remove();
					Explode explode = new Explode(missile.getX(),
							missile.getY());
					explodes.add(explode);
					logger.debug(
							"TankClient, add Explode to TankClient, id:{}",
							explode.getId());
					found = true;
				}
			}

			if (!found) {
				logger.error("cannot find missile, id:{}", missileId);
			}
		}
	}

	public void tankDead(int tankId) {

		synchronized (tanks) {
			Iterator<Tank> it = tanks.iterator();
			while (it.hasNext()) {
				Tank tank = it.next();
				if (tank.getId() == tankId) {
					tank.setLive(false);
					it.remove();
				}
			}
		}

	}

	public void fire(int tankId) {
		Tank tank = getTank(tankId);
		Missile m = new Missile(tank.getX() + tank.getWidth() / 2 - 3,
				tank.getY() + tank.getHeight() / 2 - 3, tank.getAngle(),
				tank.getTeam());
		m.setMissileSpeed(300);
		synchronized (missiles) {
			missiles.add(m);
			logger.debug(
					"TankClient.fire, add missile, tankId:{}, missileId:{}",
					tankId, m.getId());
		}
	}

	private class KeyMonitor extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (getMyTank() == null) {
				return;
			}
			TankClient.this.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (getMyTank() == null) {
				return;
			}
			TankClient.this.keyReleased(e);

		}
	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		int angle = 0;
		switch (key) {

		case KeyEvent.VK_UP:
			angle = 0;
			break;
		case KeyEvent.VK_RIGHT:
			angle = 1;
			break;
		case KeyEvent.VK_DOWN:
			angle = 2;
			break;
		case KeyEvent.VK_LEFT:
			angle = 3;
			break;
		default:
			return;
		}
		if (judgeKey) {
			Tank myTank = getMyTank();

			Packet packet = new Packet(Command.C_MOVE, 8);
			packet.getByteBuffer().putInt(myTank.getId());
			packet.getByteBuffer().putInt(angle);
			ClientMain.client.pushWritePacket(packet);
			judgeKey = false;
		}

	}

	private Tank getMyTank() {
		synchronized (tanks) {
			for (Tank tank : tanks) {
				if (tank.getClientId() == myClientId) {
					return tank;
				}
			}
			return null;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		// case KeyEvent.VK_O:
		// fire2();
		// break;
		case KeyEvent.VK_J:
			Packet writePacket = new Packet(Command.C_NEW_MISSILE, 4);
			writePacket.getByteBuffer().putInt(getMyTank().getId());
			ClientMain.client.pushWritePacket(writePacket);
			break;
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_UP:
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_DOWN: {
			// stop
			judgeKey = true;
			Packet packet = new Packet(Command.C_STOP, 4);
			packet.getByteBuffer().putInt(getMyTank().getId());
			ClientMain.client.pushWritePacket(packet);
		}
			break;
		}
	}

	public int getMyClientId() {
		return myClientId;
	}

	public void setMyClientId(int myClientId) {
		this.myClientId = myClientId;
	}

	public List<Tank> getTanks() {
		return this.tanks;
	}

	public List<Missile> getMissiles() {
		return this.missiles;
	}

	public void addNewTank(Tank tank) {
		synchronized (tanks) {
			this.tanks.add(tank);
		}
	}

	public List<Explode> getExplodes() {
		return explodes;
	}

}
