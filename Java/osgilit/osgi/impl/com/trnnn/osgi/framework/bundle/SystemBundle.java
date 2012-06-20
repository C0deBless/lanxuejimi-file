package com.trnnn.osgi.framework.bundle;

import java.io.InputStream;

import org.osgi.framework.BundleException;

import com.trnnn.osgi.framework.Framework;

public class SystemBundle extends AbstractBundle {

	public SystemBundle(Framework framework) {
		this.framework = framework;
		this.bundleContext = framework.makeContext();
	}

	@Override
	public void start(int options) throws BundleException {

	}

	@Override
	public void start() throws BundleException {

	}

	@Override
	public void stop(int options) throws BundleException {

	}

	@Override
	public void stop() throws BundleException {

	}

	@Override
	public void update(InputStream input) throws BundleException {

	}

	@Override
	public void update() throws BundleException {

	}

	@Override
	public void uninstall() throws BundleException {

	}

}
