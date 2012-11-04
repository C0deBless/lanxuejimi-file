package main;

import java.io.FileNotFoundException;

import model.FolderCache;
import thread.Worker;

public class Main {

	public static void main(String[] args) throws FileNotFoundException,
			InterruptedException {
		String root = "E:\\xunlei";
		FolderCache cache = new FolderCache(root);
		Worker worker = new Worker(cache);
		new Monitor(worker).run();
		Thread.currentThread().join();
	}

}
