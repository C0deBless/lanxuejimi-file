package com.trnnn.osgi.console;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

public class ConsoleCommand {
	enum CommandName {
		ss, start, stop, install, uninstall, restart, update;
		public static CommandName get(String name) {
			return valueOf(name);
		}
	}

	private BundleContext bundleContext;

	public ConsoleCommand(BundleContext bundleContext) {
		if (bundleContext == null)
			throw new NullPointerException("bundleContext cannot be null value");
		this.bundleContext = bundleContext;
	}

	public void parseAndExecute(String input) {
		String[] args = parse(input);
		execute(args);
	}

	private String[] parse(String input) {
		String[] strs = new String[2];
		int index = input.indexOf(" ");
		if (index > 0 && index < input.length()) {
			String com = input.substring(0, index);
			strs[0] = com;
			String param = input.substring(index).trim();
			strs[1] = param;
		}

		return strs;
	}

	private void execute(String[] args) {
		String com = null;
		String param = null;
		if (args.length >= 1)
			com = args[0];
		if (args.length >= 2)
			param = args[1];
		System.out.println(com+", "+param);
		execute(com, param);
	}

	private void execute(String name, String param) {

		CommandName com = CommandName.get(name);
		switch (com) {
		case ss:
			executeSS(param);
			break;
		case install:
			executeInstall(param);
			break;
		case restart:
			executeRestart(param);
			break;
		case start:
			executeStart(param);
			break;
		case stop:
			executeStop(param);
			break;
		case uninstall:
			executeUninstall(param);
			break;
		case update:
			executeUpdate(param);
			break;
		}
	}

	private void executeSS(String param) {

	}

	private void executeInstall(String param) {
		try {
			this.bundleContext.installBundle(param);
		} catch (BundleException e) {
			e.printStackTrace();
		}
	}

	private void executeRestart(String param) {
		try {
			long bundleId = Long.parseLong(param);
			Bundle bundle = this.bundleContext.getBundle(bundleId);
			bundle.stop();
			bundle.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void executeStart(String param) {
		try {
			long bundleId = Long.parseLong(param);
			Bundle bundle = this.bundleContext.getBundle(bundleId);
			bundle.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void executeStop(String param) {
		try {
			long bundleId = Long.parseLong(param);
			Bundle bundle = this.bundleContext.getBundle(bundleId);
			bundle.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void executeUninstall(String param) {
		try {
			long bundleId = Long.parseLong(param);
			Bundle bundle = this.bundleContext.getBundle(bundleId);
			bundle.uninstall();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void executeUpdate(String param) {
		try {
			long bundleId = Long.parseLong(param);
			Bundle bundle = this.bundleContext.getBundle(bundleId);
			bundle.update();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
