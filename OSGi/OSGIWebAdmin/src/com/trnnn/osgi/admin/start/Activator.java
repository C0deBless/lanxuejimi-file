package com.trnnn.osgi.admin.start;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.trnnn.osgi.admin.BundleFactory;

public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public static BundleContext bundleContext;
	
	public void start(BundleContext context) throws Exception {
		bundleContext=context;
		BundleFactory.setBundleContext(context);
		System.out.println("Hello Web admin");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		BundleFactory.setBundleContext(null);
		System.out.println("GoodBye Web admin");
	}

}
