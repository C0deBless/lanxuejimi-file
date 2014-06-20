
class PaintThread implements Runnable{
	
	private final TankClient tankClient;

	PaintThread(TankClient tankClient) {
		this.tankClient = tankClient;
	}

	@Override
	public void run() {
		while(true){
			this.tankClient.repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}