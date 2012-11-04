package thread;

import java.util.TimerTask;

import model.FolderCache;

public class Worker extends TimerTask {

	final FolderCache cache;

	public Worker(FolderCache cache) {
		if (cache == null)
			throw new NullPointerException();
		this.cache = cache;
	}

	@Override
	public void run() {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
