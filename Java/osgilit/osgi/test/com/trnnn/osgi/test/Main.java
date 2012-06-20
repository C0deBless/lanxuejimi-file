package com.trnnn.osgi.test;

import java.util.HashMap;
import java.util.Map;

import com.trnnn.osgi.framework.FrameworkConfig;
import com.trnnn.osgi.launcher.Launcher;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		Launcher launcher = new Launcher();
		Map<String, String> config = new HashMap<String, String>();
		config.put(FrameworkConfig.REPOSITORY_PATH, "D:\\plugins");
		launcher.runFramework(config);
		Thread.currentThread().join();
	}
}
