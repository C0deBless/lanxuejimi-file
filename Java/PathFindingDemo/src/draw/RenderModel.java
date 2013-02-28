package draw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

public class RenderModel {

	private int radius = 30;
	private int speed = 100;// pixel per second
	private Vector currentPos = new Vector(100, 100);
	private Vector dest = new Vector(500, 500);
	private long tick = 0;
	private final JComponent canvas;

	public RenderModel(JComponent canvas) {
		this.canvas = canvas;
	}

	public void move(int x, int y) {
		this.dest = new Vector(x, y);
	}

	public void update(long currentTime) {
		if (tick == 0) {
			tick = currentTime;
			return;
		}
		long term = currentTime - tick;
		this.updatePosition(term);
		this.draw();
		this.tick = currentTime;
	}

	private void draw() {
		Graphics2D graphics = (Graphics2D) canvas.getGraphics();
		graphics = (Graphics2D) graphics.create();
		graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setColor(Color.RED);
		graphics.fillArc((int) currentPos.x, (int) currentPos.y, 2 * radius,
				2 * radius, 0, 360);
		graphics.drawLine(0, 0, 100, 100);
		canvas.update(graphics);
	}

	private void updatePosition(long term) {
		if (dest == null) {
			return;
		}
		Vector v = dest.substract(currentPos);
		v.normalize();
		v.scale(speed * term / 1000.0);
		currentPos = currentPos.add(v);

		double r = v.length();
		Vector v1 = this.currentPos.substract(dest);
		if (v1.length() <= r) {
			this.dest = null;
		}
	}
}
