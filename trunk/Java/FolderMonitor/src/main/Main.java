package main;

import model.FolderCache;
import thread.Worker;

public class Main {

	public static void main(String[] args) {
		FolderCache cache = new FolderCache();
		Worker worker = new Worker(cache);
		new Monitor(worker).run();
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
