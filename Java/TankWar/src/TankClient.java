import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] tankimages = null;
	private static Map<String, Image> greenImages  = new HashMap<String, Image>();
	private static Map<String, Image> redImages  = new HashMap<String, Image>();
	private static Map<String, Image> missileImages  = new HashMap<String, Image>();
	private static Map<Integer, Image> explodeImages  = new HashMap<Integer, Image>();
	
	private boolean judgeKey = true;

	Image offScreenImage = null;
	
	private boolean explodeinit = false;
	
	static{
		tankimages = new Image[]{
			tk.getImage(TankClient.class.getClassLoader().getResource("images/greenU.png")),
			tk.getImage(TankClient.class.getClassLoader().getResource("images/greenR.png")),
			tk.getImage(TankClient.class.getClassLoader().getResource("images/greenD.png")),
			tk.getImage(TankClient.class.getClassLoader().getResource("images/greenL.png")),
			tk.getImage(TankClient.class.getClassLoader().getResource("images/redU.png")),
			tk.getImage(TankClient.class.getClassLoader().getResource("images/redR.png")),
			tk.getImage(TankClient.class.getClassLoader().getResource("images/redD.png")),
			tk.getImage(TankClient.class.getClassLoader().getResource("images/redL.png")),
			tk.getImage(TankClient.class.getClassLoader().getResource("images/missileU.png")),
			tk.getImage(TankClient.class.getClassLoader().getResource("images/missileR.png")),
			tk.getImage(TankClient.class.getClassLoader().getResource("images/missileD.png")),
			tk.getImage(TankClient.class.getClassLoader().getResource("images/missileL.png")),
			tk.getImage(TankClient.class.getClassLoader().getResource("images/1.png")),
			tk.getImage(TankClient.class.getClassLoader().getResource("images/2.png")),
			tk.getImage(TankClient.class.getClassLoader().getResource("images/3.png")),
			tk.getImage(TankClient.class.getClassLoader().getResource("images/4.png")),
			tk.getImage(TankClient.class.getClassLoader().getResource("images/5.png")),
			tk.getImage(TankClient.class.getClassLoader().getResource("images/6.png")),
			tk.getImage(TankClient.class.getClassLoader().getResource("images/7.png")),
			tk.getImage(TankClient.class.getClassLoader().getResource("images/8.png")),
			tk.getImage(TankClient.class.getClassLoader().getResource("images/9.png")),
			tk.getImage(TankClient.class.getClassLoader().getResource("images/10.png")),
			
		};
		greenImages.put("0", tankimages[0]);
		greenImages.put("1", tankimages[1]);
		greenImages.put("2", tankimages[2]);
		greenImages.put("3", tankimages[3]);
		redImages.put("0", tankimages[4]);
		redImages.put("1", tankimages[5]);
		redImages.put("2", tankimages[6]);
		redImages.put("3", tankimages[7]);
		missileImages.put("0", tankimages[8]);
		missileImages.put("1", tankimages[9]);
		missileImages.put("2", tankimages[10]);
		missileImages.put("3", tankimages[11]);
		explodeImages.put(0, tankimages[12]);
		explodeImages.put(1, tankimages[13]);
		explodeImages.put(2, tankimages[14]);
		explodeImages.put(3, tankimages[15]);
		explodeImages.put(4, tankimages[16]);
		explodeImages.put(5, tankimages[17]);
		explodeImages.put(6, tankimages[18]);
		explodeImages.put(7, tankimages[19]);
		explodeImages.put(8, tankimages[20]);
		explodeImages.put(9, tankimages[21]);
	}
	
	
	public void setTankList(List<Tank> tankList) {
		synchronized (tanks) {
			tanks.clear();
			tanks.addAll(tankList);
		}
	}
	
	public void setMissileList(List<Missile> missileList) {
		synchronized (missiles) {
			missiles.clear();
			missiles.addAll(missileList);
		}
		
	}


	private void drawTank(Tank tank, Graphics g) {
		if (!tank.isLive()) {
			return;
		}
		Color c = g.getColor();
		if (tank.getTeam() == 0) {
			g.setColor(Color.RED);
			g.drawString("" + tank.getId(), (int) tank.getX(),
					(int) tank.getY() - 10);
			redTank(tank, g);
		} else {
			g.setColor(Color.GREEN);
			g.drawString("" + tank.getId(), (int) tank.getX(),
					(int) tank.getY() - 10);
			greenTank(tank, g);
		}
		
		g.setColor(c);

	}

	public void drawMissile(Missile missile, Graphics g) {

		if (!missile.isLive()) {
			return;
		}
		int angle = missile.getAngle();
		switch (angle) {
		case 0:
			g.drawImage(missileImages.get("0"), (int)missile.getX(), (int)missile.getY(), null);
			break;
		case 1:
			g.drawImage(missileImages.get("1"), (int)missile.getX(), (int)missile.getY(), null);
			break;
		case 2:
			g.drawImage(missileImages.get("2"), (int)missile.getX(), (int)missile.getY(), null);
			break;
		case 3:
			g.drawImage(missileImages.get("3"), (int)missile.getX(), (int)missile.getY(), null);
			break;

		}
		
	}

	public void drawExplode(Explode explode, Graphics g) {
		if(!explodeinit){
			for (int i = 0; i <= explode.getDiameter(); i++) {
				g.drawImage(explodeImages.get(i), -100, -100, null);
			}
			explodeinit = true;
		}
		
		if (explode.getStep() == explode.getDiameter()) {
			return;
		}
		if (!explode.isLive()) {
			return;
		}
		
		int wdith = explodeImages.get(explode.getStep()).getWidth(null);
		int height = explodeImages.get(explode.getStep()).getHeight(null);
		
		g.drawImage(explodeImages.get(explode.getStep()), (int)explode.getX() - wdith/2, (int)explode.getY() - height/2, null);	
	}

	public void greenTank(Tank tank, Graphics g) {
		int angle = tank.getAngle();
		switch (angle) {
		case 0:
			g.drawImage(greenImages.get("0"), (int)tank.getX(), (int)tank.getY(), null);
			break;
		case 1:
			g.drawImage(greenImages.get("1"), (int)tank.getX(), (int)tank.getY(), null);
			break;
		case 2:
			g.drawImage(greenImages.get("2"), (int)tank.getX(), (int)tank.getY(), null);
			break;
		case 3:
			g.drawImage(greenImages.get("3"), (int)tank.getX(), (int)tank.getY(), null);
			break;

		}
	}
	
	public void redTank(Tank tank, Graphics g) {
		int angle = tank.getAngle();
		switch (angle) {
		case 0:
			g.drawImage(redImages.get("0"), (int)tank.getX(), (int)tank.getY(), null);
			break;
		case 1:
			g.drawImage(redImages.get("1"), (int)tank.getX(), (int)tank.getY(), null);
			break;
		case 2:
			g.drawImage(redImages.get("2"), (int)tank.getX(), (int)tank.getY(), null);
			break;
		case 3:
			g.drawImage(redImages.get("3"), (int)tank.getX(), (int)tank.getY(), null);
			break;

		}
	}

	public void paint(Graphics g) {

		synchronized (tanks) {
			for (Tank tank : tanks) {
				drawTank(tank, g);
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

	
	public void missileDead(int missileId, Explode explode){

		synchronized (missiles) {
			Iterator<Missile> it = missiles.iterator();
			while (it.hasNext()) {
				Missile missile = it.next();
				if (missile.getId() == missileId) {
					missile.setLive(false);

					Explode e = explode;
					explodes.add(e);

				}
			}

		}
	}
	
	public void explodeDead(int id) {
		Iterator<Explode> it = explodes.iterator();
		while(it.hasNext()){
			Explode explode = it.next();
			if(!explode.isLive()){
				it.remove();
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

	public void fire(int tankId, int missileId) {
		Tank tank = getTank(tankId);
		Missile m = new Missile(tank.getX() + tank.getWidth() / 2 - 3,
				tank.getY() + tank.getHeight() / 2 -3 , tank.getAngle(),
				tank.getTeam(), missileId);
		m.setMissileSpeed(300);
		synchronized (missiles) {
			missiles.add(m);
			logger.debug(
					"TankClient.fire, add missile, tankId:{}, missileId:{}",
					tankId, m.getId());
		}
	}
	
	public void tanksCollide(int tank1Id, int tank2Id) {
		getTank(tank1Id).collidesWithTank(getTank(tank2Id));
		
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
