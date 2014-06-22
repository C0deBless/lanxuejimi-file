import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import common.Command;
import common.Explode;
import common.Missile;
import common.Packet;
import common.Tank;

public class TankClient extends Frame {
	private static final long serialVersionUID = 1L;
	public static final int TANK_SERVER_TCP_PORT = 8888;
	public static final int TANK_SERVER_UDP_PORT = 6666;

	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;

	List<Tank> tanks = new ArrayList<Tank>();
	List<Explode> explode = new ArrayList<Explode>();
	List<Missile> missiles = new ArrayList<Missile>();
	private int myClientId;

	Image offScreenImage = null;

	public void setTankList(List<Tank> tankList) {
		tanks.clear();
		tanks.addAll(tankList);
	}

	private void drawTank(Tank tank, Graphics g) {
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

	public void paint(Graphics g) {

		for (Tank tank : tanks) {
			drawTank(tank, g);
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
		for (Tank tank : tanks) {
			if (tank.getId() == tankId) {
				return tank;
			}
		}
		return null;
	}

	public void move(int clientId, int tankId, int angle) {
		Tank tank = getTank(tankId);
		tank.setAngle(angle);
		tank.setCurrentSpeed(100);
	}

	public void stop(int clientId, int tankId) {
		Tank tank = getTank(tankId);
		tank.setCurrentSpeed(0);
	}

	private class KeyMonitor extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			TankClient.this.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			TankClient.this.keyReleased(e);
		}
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		int angle = 0;
		switch (key) {
		case KeyEvent.VK_LEFT:
			angle = 3;
			break;
		case KeyEvent.VK_UP:
			angle = 0;
			break;
		case KeyEvent.VK_RIGHT:
			angle = 1;
			break;
		case KeyEvent.VK_DOWN:
			angle = 2;
			break;
		}

		Tank myTank = getMyTank();

		Packet packet = new Packet(Command.C_MOVE, 8);
		packet.getByteBuffer().putInt(myTank.getId());
		packet.getByteBuffer().putInt(angle);
		ClientMain.client.pushWritePacket(packet);
	}

	private Tank getMyTank() {
		for (Tank tank : tanks) {
			if (tank.getClientId() == myClientId) {
				return tank;
			}
		}
		return null;
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		// case KeyEvent.VK_O:
		// fire2();
		// break;
		// case KeyEvent.VK_J:
		// fire();
		// break;
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_UP:
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_DOWN: {
			// stop
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

}
