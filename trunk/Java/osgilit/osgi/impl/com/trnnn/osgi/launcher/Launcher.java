package com.trnnn.osgi.launcher;

import java.util.Map;

import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

public class Launcher {
	private static final String frameworkImplementClass = "com.trnnn.osgi.framework.FrameworkFactoryImpl";
	private FrameworkFactory frameworkFactory;

	public void runFramework(Map<String, String> config) {
		try {
			this.frameworkFactory = (FrameworkFactory) Class.forName(
					frameworkImplementClass).newInstance();
			Framework framework = this.frameworkFactory.newFramework(config);
			framework.start();

		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
		} catch (BundleException e) {
			e.printStackTrace();
		}
	}

}
