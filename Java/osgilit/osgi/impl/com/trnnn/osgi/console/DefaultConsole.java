package com.trnnn.osgi.console;

import org.osgi.framework.BundleContext;

public class DefaultConsole extends Console {

	public DefaultConsole(BundleContext bundleContext) {
		super(bundleContext);
		this.in = System.in;
		this.out = System.out;
	}

}
