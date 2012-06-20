package com.trnnn.osgi.framework.bundle;

import java.io.InputStream;

import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

import com.trnnn.osgi.framework.Framework;

public class BundleHost extends AbstractBundle {

	@SuppressWarnings("unused")
	private BundleContext bundleContext;
	
	public BundleHost() {

	}

	public BundleHost(BundleData bundledata, Framework framework) {
		// TODO Auto-generated constructor stub
	}

	protected void buildHost() {

	}

	@Override
	public void start(int options) throws BundleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() throws BundleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop(int options) throws BundleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() throws BundleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(InputStream input) throws BundleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() throws BundleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uninstall() throws BundleException {
		// TODO Auto-generated method stub
		
	}
}
