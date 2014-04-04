package stresstest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Practice2 extends JFrame {
	private static final long serialVersionUID = 1L;
	private MyPanel mp;

	public static void main(String[] args) {
		new Practice2();
	}

	public Practice2() {
		this.setTitle("移动的小球");
		this.setLocation(100, 100);
		this.setSize(500, 400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		mp = new MyPanel(10, 10);
		this.add(mp);
		this.addKeyListener(new MyKeyEvent());

		this.setVisible(true);

		Thread thread = new Thread(new Runnable() {
			private long lastInvokeTime = 0;

			@Override
			public void run() {
				while(true){
					long currentTime = System.nanoTime();
					if (lastInvokeTime == 0) {
						lastInvokeTime = currentTime;
					}
					
					if(mp.y > 500){
						//
					}else{
						long deltaTime = currentTime - this.lastInvokeTime;
						double deltaSeconds = deltaTime * 1.0 / 1000 / 1000 / 1000;
						int speed = 100;// 像素/秒
						double deltaY = speed * deltaSeconds;
						mp.y = mp.y + deltaY;	
					}
					
					lastInvokeTime = currentTime;
					mp.repaint();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
	}

	private class MyKeyEvent extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				mp.y--;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				mp.y++;
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				mp.x--;
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				mp.x++;
			}
			mp.repaint();
		}
	}

	private class MyPanel extends JPanel {
		private double x;
		private double y;

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.setColor(Color.GREEN);
			g.fillOval((int) x, (int) y, 30, 30);
		}

		public MyPanel(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
}