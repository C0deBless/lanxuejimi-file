package common;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Tank {
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	int x, y;

	private Missile m;

	private int id;

	private boolean good;

	private boolean live = true;

	// private int step = r.nextInt(15)+4;
	public static Random r = new Random();

	Direction dir = Direction.STOP;
	private Direction[] dirs = Direction.values();
	private Direction gunTube = Direction.D;

	private boolean bL = false, bU = false, bR = false, bD = false;

	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.good = good;
	}

	public void draw(Graphics g) {
		if (!live) {
			if (!good) {
				tc.tanks.remove(this);
			}
			return;
		}
		Color c = g.getColor();
		if (good) {
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.LIGHT_GRAY);
		}
		g.fillRect(x, y, WIDTH, HEIGHT);
		g.drawString("" + id, x, y - 10);
		g.setColor(c);
		drawGunTube(g);
		move();
	}

	private void drawGunTube(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.GREEN);
		switch (gunTube) {
		case L:
			g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x - XSPEED, y + HEIGHT
					/ 2);
			break;
		case LU:
			g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x - XSPEED, y - XSPEED);
			break;
		case U:
			g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x + WIDTH / 2, y - XSPEED);
			break;
		case RU:
			g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x + WIDTH + XSPEED, y
					- XSPEED);
			break;
		case R:
			g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x + WIDTH + XSPEED, y
					+ HEIGHT / 2);
			break;
		case RD:
			g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x + WIDTH + XSPEED, y
					+ HEIGHT + XSPEED);
			break;
		case D:
			g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x + WIDTH / 2, y + HEIGHT
					+ XSPEED);
			break;
		case LD:
			g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x - XSPEED, y + HEIGHT
					+ XSPEED);
			break;
		}
		if (dir != Direction.STOP) {
			gunTube = dir;
		}
		g.setColor(c);
	}

	public void move() {

		switch (dir) {
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case STOP:
			break;

		}
		if (x < 0)
			x = 0;
		if (y < 30)
			y = 30;
		if (x + Tank.WIDTH > TankClient.GAME_WIDTH)
			x = TankClient.GAME_WIDTH - Tank.WIDTH;
		if (y + Tank.HEIGHT > TankClient.GAME_HEIGHT)
			y = TankClient.GAME_HEIGHT - Tank.HEIGHT;
		/*
		 * if(!good){
		 * 
		 * if(step == 0){ int rn = r.nextInt(dirs.length); step =
		 * r.nextInt(15)+3; dir = dirs[rn]; } step--; if(r.nextInt(40) > 38){
		 * fire(); }
		 * 
		 * }
		 */

	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		direction();
	}

	private Missile fire() {
		if (!live) {
			return null;
		}
		Missile m = new Missile(id, x + WIDTH / 2, y + HEIGHT / 2, good,
				gunTube, this.tc);
		tc.missiles.add(m);
		MissileNewMsg msg = new MissileNewMsg(m);
//		tc.nc.send(msg);
		return m;
	}

	private Missile fire2() {
		if (!live) {
			return null;
		}
		for (int i = 0; i < dirs.length; i++) {
			m = new Missile(id, x + WIDTH / 2, y + HEIGHT / 2, good, dirs[i],
					this.tc);
			tc.missiles.add(m);
			if (i == 7)
				return m;
		}
		return m;
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_O:
			fire2();
			break;
		case KeyEvent.VK_J:
			fire();
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		}
		direction();
	}

	public void direction() {
		Direction oldDirection = dir;

		if (bL && !bU && !bR && !bD) {
			dir = Direction.L;
		} else if (bL && bU && !bR && !bD) {
			dir = Direction.LU;
		} else if (!bL && bU && !bR && !bD) {
			dir = Direction.U;
		} else if (!bL && bU && bR && !bD) {
			dir = Direction.RU;
		} else if (!bL && !bU && bR && !bD) {
			dir = Direction.R;
		} else if (!bL && !bU && bR && bD) {
			dir = Direction.RD;
		} else if (!bL && !bU && !bR && bD) {
			dir = Direction.D;
		} else if (bL && !bU && !bR && bD) {
			dir = Direction.LD;
		} else if (!bL && !bU && !bR && !bD) {
			dir = Direction.STOP;
		}

		if (dir != oldDirection) {
//			TankMoveMsg msg = new TankMoveMsg(this);
//			tc.nc.send(msg);
			Packet packet =new Packet(Command.MOVE);
			packet.getByteBuffer().putInt(this.getId());
			packet.getByteBuffer().putInt(this.x);
			packet.getByteBuffer().putInt(this.y);
			packet.getByteBuffer().putInt(this.getGunTube().ordinal());
			packet.getByteBuffer().putInt(this.dir.ordinal());
			ClientMain.client.pushWritePacket(packet);
		}

	}

	public Rectangle getRectangle() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isGood() {
		return good;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Direction getGunTube() {
		return gunTube;
	}

	public void setGunTube(Direction gunTube) {
		this.gunTube = gunTube;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

}
