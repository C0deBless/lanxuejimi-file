import java.awt.Color;
import java.awt.Graphics;


public class Explode {
	private int x,y;
	
	private boolean live = true;
	
	

	private TankClient tc;
	
	private int step = 0;
	
	private int[] diameter = {7,16,20,30,40,55,30,16,7};
	
	public Explode(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	public void draw(Graphics g){
		if(!live){
			tc.explode.remove(this);
			return;
		}
		if(step == diameter.length){
			live = false;
			step = 0;
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x, y, diameter[step], diameter[step]);
		step++;
		g.setColor(c);
	}
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
}
