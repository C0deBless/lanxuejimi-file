package common;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

public class Missile {
	public static final int XSPEED = 10;
	public static final int YSPEED = 10;
	public static final int WIDTH = 7;
	public static final int HEIGHT = 7;

	public static int ID = 1;

	private int x, y;
	private Direction dir;
	private boolean live = true;
	private boolean good;

	private int tankId;
	private int id;

	public Missile(int tankId, int x, int y, boolean good, Direction dir) {
		this(tankId, x, y, dir);
		this.good = good;
		this.id = ID++;
	}

	public Missile(int tankId, int x, int y, Direction dir) {
		this.tankId = tankId;
		this.x = x;
		this.y = y;
		this.dir = dir;

	}

//	public void darw(Graphics g) {
//		if (!live) {
//			tc.missiles.remove(this);
//			return;
//		}
//		Color c = g.getColor();
//		if (good) {
//			g.setColor(Color.PINK);
//		} else {
//			g.setColor(Color.GREEN);
//		}
//		g.fillOval(x, y, 7, 7);
//		g.setColor(c);
//
//	}
//
//	public boolean hitTank(Tank t) {
//		if (getRectangle().intersects(t.getRectangle()) && t.isLive()
//				&& t.isGood() != good) {
//			live = false;
//			t.setLive(false);
//			Explode e = new Explode(x, y, tc);
//			tc.explode.add(e);
//			return true;
//		}
//		return false;
//	}

//	public boolean hitTanks(List<Tank> tanks) {
//		for (int i = 0; i < tanks.size(); i++) {
//			Tank t = tanks.get(i);
//			hitTank(t);
//		}
//
//		return false;
//	}

	public Rectangle getRectangle() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

	public boolean isLive() {
		return live;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Direction getDir() {
		return dir;
	}

	public void setDir(Direction dir) {
		this.dir = dir;
	}

	public int getTankId() {
		return tankId;
	}

	public void setTankId(int tankId) {
		this.tankId = tankId;
	}

	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
