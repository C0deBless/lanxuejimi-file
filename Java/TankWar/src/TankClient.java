import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Block;
import common.Camp;
import common.Command;
import common.Constants;
import common.Explode;
import common.Missile;
import common.Packet;
import common.Tank;

public class TankClient extends Frame {

	static Logger logger = LoggerFactory.getLogger(TankClient.class);

	private static final long serialVersionUID = 1L;
	public static final int TANK_SERVER_TCP_PORT = 8888;
	public static final int TANK_SERVER_UDP_PORT = 6666;

	private List<Tank> tanks = new ArrayList<Tank>();
	private List<Explode> explodes = new ArrayList<Explode>();
	private List<Missile> missiles = new ArrayList<Missile>();
	private List<Block> blocks = new ArrayList<Block>();

	private Camp camp;
	private int myClientId;
	private int myGameRoomID;
	private List<String> playersName = new ArrayList<String>();

	private Toolkit tk = Toolkit.getDefaultToolkit();

	private Image[] tankAImages = new Image[4];
	private Image[] tankBImages = new Image[4];

	private Image[] energyWaveImages = new Image[4];
	private Image[] explodeImages = new Image[10];
	private Image campImage;
	private Image gameOverImage;
	private Image wallImage;

	private String teamWin;

	private boolean gameOver = false;
	private boolean winingTeam = false;

	private boolean judgeKey = true;
	private Image offScreenImage = null;
	private boolean explodeinit = false;

	private Panel gamePanel;
	private Graphics currentGraphics;
	private List<Tank> serverTanks;

	public TankClient() {
		camp = new Camp();
		init();
	}

	private void init() {
		tankAImages = new Image[] {
				tk.getImage(TankClient.class.getClassLoader().getResource(
						"images/tank_a_U.png")),
				tk.getImage(TankClient.class.getClassLoader().getResource(
						"images/tank_a_R.png")),
				tk.getImage(TankClient.class.getClassLoader().getResource(
						"images/tank_a_D.png")),
				tk.getImage(TankClient.class.getClassLoader().getResource(
						"images/tank_a_L.png")) };
		tankBImages = new Image[] {
				tk.getImage(TankClient.class.getClassLoader().getResource(
						"images/tank_b_U.png")),
				tk.getImage(TankClient.class.getClassLoader().getResource(
						"images/tank_b_R.png")),
				tk.getImage(TankClient.class.getClassLoader().getResource(
						"images/tank_b_D.png")),
				tk.getImage(TankClient.class.getClassLoader().getResource(
						"images/tank_b_L.png")) };

		energyWaveImages = new Image[] {
				tk.getImage(TankClient.class.getClassLoader().getResource(
						"images/EnergyWaveU.png")),
				tk.getImage(TankClient.class.getClassLoader().getResource(
						"images/EnergyWaveR.png")),
				tk.getImage(TankClient.class.getClassLoader().getResource(
						"images/EnergyWaveD.png")),
				tk.getImage(TankClient.class.getClassLoader().getResource(
						"images/EnergyWaveL.png")) };

		explodeImages = new Image[] {
				tk.getImage(TankClient.class.getClassLoader().getResource(
						"images/1.png")),
				tk.getImage(TankClient.class.getClassLoader().getResource(
						"images/2.png")),
				tk.getImage(TankClient.class.getClassLoader().getResource(
						"images/3.png")),
				tk.getImage(TankClient.class.getClassLoader().getResource(
						"images/4.png")),
				tk.getImage(TankClient.class.getClassLoader().getResource(
						"images/5.png")),
				tk.getImage(TankClient.class.getClassLoader().getResource(
						"images/6.png")),
				tk.getImage(TankClient.class.getClassLoader().getResource(
						"images/7.png")),
				tk.getImage(TankClient.class.getClassLoader().getResource(
						"images/8.png")),
				tk.getImage(TankClient.class.getClassLoader().getResource(
						"images/9.png")),
				tk.getImage(TankClient.class.getClassLoader().getResource(
						"images/10.png")) };
		gameOverImage = tk.getImage(TankClient.class.getClassLoader()
				.getResource("images/GameOver.png"));
		campImage = tk.getImage(TankClient.class.getClassLoader().getResource(
				"images/camp.png"));
		wallImage = tk.getImage(TankClient.class.getClassLoader().getResource(
				"images/Wall.png"));
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

	public void setBlockList(List<Block> blockList) {
		synchronized (blocks) {
			blocks.clear();
			blocks.addAll(blockList);
		}
	}

	public void setPlayersName(List<String> playersNameList) {
		synchronized (playersNameList) {
			playersName.clear();
			playersName.addAll(playersNameList);
		}

	}

	private void drawPlayersName(Graphics g) {
		for (int i = 0; i < playersName.size(); i++) {
			int[] y = { 70, 90, 110, 130, 150 };
			g.drawString(i + ":--" + playersName.get(i), 10, y[i]);
		}
	}

	private void drawTank(Tank tank, Graphics g) {
		if (!tank.isLive()) {
			return;
		}
		Color c = g.getColor();
		int angle = tank.getAngle();

		if (tank.getTeam() == 0) {
			g.setColor(Color.RED);
			g.drawString(tank.getDebugInfo(), (int) tank.getX(),
					(int) tank.getY() - 10);
		} else {
			g.setColor(Color.GREEN);
			g.drawString(tank.getDebugInfo() + tank.getId(), (int) tank.getX(),
					(int) tank.getY() - 10);
		}

		Image image = null;

		switch (tank.getType()) {
		case A:
			image = tankAImages[angle];
			break;
		case B:
			image = tankBImages[angle];
			break;
		default:
			break;
		}

		// AffineTransform at = new AffineTransform();
		// int transX = 100;
		// int transY = 100;
		// at.translate(transX, transY);

		// g.drawImage(image, (int) tank.getX(), (int) tank.getY(), null);
		g.drawImage(image, (int) tank.getX(), (int) tank.getY(),
				(int) tank.getX() + 36, (int) tank.getY() + 36, 0, 0,
				image.getWidth(null), image.getHeight(null), null);

		g.setColor(c);

	}

	public void drawMissile(Missile missile, Graphics g) {

		if (!missile.isLive()) {
			return;
		}
		int angle = missile.getAngle();
		g.drawImage(energyWaveImages[angle], (int) missile.getX(),
				(int) missile.getY(), null);
	}

	public void drawBlock(Block block, Graphics g) {
		if (!block.isLive()) {
			return;
		}
		g.drawImage(wallImage, (int) block.getX(), (int) block.getY(), null);
	}

	public void drawDedugInfo(List<Tank> tanks) {
		for (Tank tank : tanks) {
			Tank clientTank = this.getTank(tank.getId());
			if (clientTank != null) {
				Color color = this.currentGraphics.getColor();
				this.currentGraphics.setColor(Color.YELLOW);
				this.currentGraphics.drawString(tank.getDebugInfo(),
						(int) clientTank.getX(), (int) clientTank.getY() - 20);
				this.currentGraphics.setColor(color);
			}
		}
	}

	public void drawExplode(Explode explode, Graphics g) {
		if (!explodeinit) {
			for (int i = 0; i <= explode.getDiameter(); i++) {
				g.drawImage(explodeImages[i], -100, -100, null);
			}
			explodeinit = true;
		}

		if (explode.getStep() == explode.getDiameter()) {
			return;
		}
		if (!explode.isLive()) {
			return;
		}

		int wdith = explodeImages[explode.getStep()].getWidth(null);
		int height = explodeImages[explode.getStep()].getHeight(null);

		g.drawImage(explodeImages[explode.getStep()], (int) explode.getX()
				- wdith / 2, (int) explode.getY() - height / 2, null);
	}

	private void drawCamp(Graphics g) {
		if (!camp.isLive()) {
			return;
		}
		g.drawImage(campImage, (int) camp.getX(), (int) camp.getY(), null);
	}

	public void paint(Graphics g) {
		this.currentGraphics = g;
		if (gameOver) {
			g.drawImage(gameOverImage, 0, 0, null);
			tanks.clear();
			missiles.clear();
			explodes.clear();
			return;
		}

		synchronized (missiles) {
			for (Missile missile : missiles) {
				drawMissile(missile, g);
			}
		}

		synchronized (blocks) {
			for (Block block : blocks) {
				drawBlock(block, g);
			}
		}

		synchronized (explodes) {
			for (Explode explode : explodes) {
				drawExplode(explode, g);
			}
		}

		synchronized (tanks) {
			for (Tank tank : tanks) {
				drawTank(tank, g);
				drawCamp(g);
			}
		}
		if (this.serverTanks != null) {
			this.drawDedugInfo(serverTanks);
		}
	}

	@Override
	public void update(Graphics g) {
		g.setColor(Color.RED);
		g.drawString("GameRoomNumber:--" + getMyGameRoomID(), 10, 50);
		drawPlayersName(g);
		if (offScreenImage == null) {
			offScreenImage = gamePanel.createImage(Constants.GAME_WIDTH,
					Constants.GAME_HEIGHT);
		}
		Graphics offScreen = offScreenImage.getGraphics();
		Color c = offScreen.getColor();
		offScreen.setColor(Color.BLACK);
		offScreen.fillRect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
		offScreen.setColor(c);
		if (winingTeam) {
			g.drawString(teamWin + "Win", 700, 50);
		}
		paint(offScreen);

		gamePanel.getGraphics().drawImage(offScreenImage, 0, 0, null);
	}

	public void launchFrame() {

		this.setTitle("TankWar");
		this.setLocation(500, 200);
		this.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

		this.setLayout(null);
		gamePanel = new Panel();
		gamePanel.setSize(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
		gamePanel.setLocation(150, 50);
		this.add(gamePanel);
		gamePanel.addKeyListener(new KeyMonitor());

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		this.setResizable(false);
		this.setBackground(Color.GRAY);
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

	public void move(int clientId, int tankId, int angle,
			boolean collideWithBlock) {
		Tank tank = getTank(tankId);
		if (tank == null) {
			logger.error("cannot find tank, clientId:{}, tankId:{}", clientId,
					tankId);
			return;
		}

		tank.setAngle(angle);
		if (collideWithBlock) {
			// do nothing
		} else {
			tank.setCurrentSpeed(50);
		}
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

	public void blockDead(int blockId) {

		synchronized (blocks) {
			Iterator<Block> it = blocks.iterator();
			while (it.hasNext()) {
				Block block = it.next();
				if (block.getId() == blockId) {
					block.setLive(false);
					it.remove();
				}
			}
		}

	}

	public void missileDead(int missileId, Explode explode) {

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
		while (it.hasNext()) {
			Explode explode = it.next();
			if (!explode.isLive()) {
				it.remove();
			}
		}
	}

	public void campDead() {
		camp.setLive(false);
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
		Missile m = tank.makeMissile(missileId);
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
		// if (judgeKey) {
		Tank myTank = getMyTank();

		Packet packet = new Packet(Command.C_MOVE, 8);
		packet.getByteBuffer().putInt(myTank.getId());
		packet.getByteBuffer().putInt(angle);
		ClientMain.client.pushWritePacket(packet);
		judgeKey = false;
		// }

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

	public List<Block> getBlocks() {
		return this.blocks;
	}

	public void addNewTank(Tank tank) {
		synchronized (tanks) {
			this.tanks.add(tank);
		}
	}

	public List<Explode> getExplodes() {
		return explodes;
	}

	public int getMyGameRoomID() {
		return myGameRoomID;
	}

	public void setMyGameRoomID(int myGameRoomID) {
		this.myGameRoomID = myGameRoomID;
	}

	public List<String> getPlayersName() {
		return playersName;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public String getTeamWin() {
		return teamWin;
	}

	public void setTeamWin(String teamWin) {
		this.teamWin = teamWin;
	}

	public boolean isWiningTeam() {
		return winingTeam;
	}

	public void setWiningTeam(boolean winingTeam) {
		this.winingTeam = winingTeam;
	}

	public Camp getCamp() {
		return camp;
	}

	public List<Tank> getServerTanks() {
		return serverTanks;
	}

	public void setServerTanks(List<Tank> serverTanks) {
		this.serverTanks = serverTanks;
	}

	public void correctDeviation(int tankId, float x, float y) {
		Tank tank = this.getTank(tankId);
		if (tank != null) {
			tank.setX(x);
			tank.setY(y);
		} else {
			// FIXME error
		}
	}
}
