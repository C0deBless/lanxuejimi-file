package draw;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Renderer {

	public static List<RenderModel> pool = new ArrayList<>();
	private static final Timer timer = new Timer();

	private static final long duration = 10;

	public static class RenderTask extends TimerTask {

		@Override
		public void run() {

			long currentTime = System.currentTimeMillis();
			for (RenderModel model : pool) {
				model.update(currentTime);
			}
		}

	}

	static {
		timer.schedule(new RenderTask(), new Date(), duration);
	}

	public static void addModel(RenderModel model) {
		pool.add(model);
	}
}
