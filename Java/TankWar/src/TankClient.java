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

public class TankClient extends Frame {
	public static final int TANK_SERVER_TCP_PORT = 8888;
	public static final int TANK_SERVER_UDP_PORT = 6666;

	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;

	Tank myTank = new Tank(50, 50, true, this);

	List<Tank> tanks = new ArrayList<Tank>();
	List<Explode> explode = new ArrayList<Explode>();
	List<Missile> missiles = new ArrayList<Missile>();

	NetClient nc = new NetClient(this);

	Image offScreenImage = null;

	ConnectDialog dialog = new ConnectDialog(this);

	public static void main(String[] args) {
		new TankClient().launchFrame();
	}

	public void paint(Graphics g) {
		myTank.draw(g);
		for (int i = 0; i < missiles.size(); i++) {
			Missile m = missiles.get(i);
			// m.hitTanks(tanks);
			if (m.hitTank(myTank)) {
				TankDeadMsg msg = new TankDeadMsg(myTank.getId());
				nc.send(msg);
				MissileDeadMsg mdmMsg = new MissileDeadMsg(m.getTankId(),
						m.getId());
				nc.send(mdmMsg);
			}

			m.darw(g);
		}
		for (int i = 0; i < explode.size(); i++) {
			Explode e = explode.get(i);
			e.draw(g);
		}
		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			t.draw(g);
		}

		Color c = g.getColor();
		g.setColor(Color.GREEN);
		g.drawString("Missiles count:" + missiles.size(), 10, 50);
		g.drawString("   Tanks count:" + tanks.size(), 10, 70);
		g.setColor(c);

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

		// for(int i = 0; i < 15; i++){
		// Tank t = new Tank(50*(i+1), 100+i , false,this);
		// tanks.add(t);
		// }
		//

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

		// nc.connect("127.0.0.1", TankServer.TCP_PORT);
	}

	private class KeyMonitor extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_F1) {
				dialog.setVisible(true);
			}
			myTank.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}
	}
}
