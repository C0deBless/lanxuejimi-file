package com.trnnn.osgi.framework.bundle;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.trnnn.osgi.classloader.BundleClassLoader;
import com.trnnn.osgi.framework.Framework;

/**
 * This object is given out to bundles and wraps the internal Bundle object. It
 * is destroyed when a bundle is uninstalled and reused if a bundle is updated.
 * This class is abstract and is extended by BundleHost and BundleFragment.
 */
public abstract class AbstractBundle implements Bundle, Comparable<Bundle> {

	protected BundleContext bundleContext;
	protected BundleData bundleData;
	protected Framework framework;

	public Framework getFramework() {
		return framework;
	}

	protected ClassLoader classLoader;// = new BundleClassLoader(this);

	// public void initClassLoader(){
	//
	// this.classLoader=new BundleClassLoader(this);
	// }

	protected static AbstractBundle createBundle(BundleData bundledata,
			Framework framework, boolean setBundle) {
		AbstractBundle result;
		if ((bundledata.getType() & BundleData.TYPE_FRAGMENT) > 0)
			result = new BundleFragment(bundledata, framework);
		else if ((bundledata.getType() & BundleData.TYPE_COMPOSITEBUNDLE) > 0)
			result = new CompositeImpl(bundledata, framework);
		else if ((bundledata.getType() & BundleData.TYPE_SURROGATEBUNDLE) > 0)
			result = new SurrogateImpl(bundledata, framework);
		else
			result = new BundleHost(bundledata, framework);
		if (setBundle)
			bundledata.setBundle(result);
		result.framework = framework;
		result.bundleData = bundledata;
		result.classLoader = new BundleClassLoader(result);
		return result;
	}

	@Override
	public int compareTo(Bundle o) {
		return 0;
	}

	@Override
	public int getState() {
		return this.bundleData.getState();
	}

	@Override
	public Dictionary<String, String> getHeaders() {
		return this.bundleData.getHeaders();
	}

	@Override
	public long getBundleId() {
		return this.bundleData.getId();
	}

	@Override
	public String getLocation() {
		return this.bundleData.getLocation().getFile();
	}

	@Override
	public ServiceReference<?>[] getRegisteredServices() {
		return null;
	}

	@Override
	public ServiceReference<?>[] getServicesInUse() {
		return null;
	}

	@Override
	public boolean hasPermission(Object permission) {
		return false;
	}

	@Override
	public URL getResource(String name) {
		return null;
	}

	@Override
	public Dictionary<String, String> getHeaders(String locale) {
		return null;
	}

	@Override
	public String getSymbolicName() {
		return null;
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		return this.classLoader.loadClass(name);
	}

	@Override
	public Enumeration<URL> getResources(String name) throws IOException {
		return null;
	}

	@Override
	public Enumeration<String> getEntryPaths(String path) {
		return null;
	}

	@Override
	public URL getEntry(String path) {
		return null;
	}

	@Override
	public long getLastModified() {
		return 0;
	}

	@Override
	public Enumeration<URL> findEntries(String path, String filePattern,
			boolean recurse) {
		return null;
	}

	@Override
	public BundleContext getBundleContext() {
		return this.bundleContext;
	}

	@Override
	public Map<X509Certificate, List<X509Certificate>> getSignerCertificates(
			int signersType) {
		return null;
	}

	@Override
	public Version getVersion() {
		return null;
	}

	@Override
	public <A> A adapt(Class<A> type) {
		return null;
	}

	@Override
	public File getDataFile(String filename) {
		throw new NotImplementedException();
		// return null;
	}

	@Override
	public abstract void start(int options) throws BundleException;

	@Override
	public abstract void start() throws BundleException;

	@Override
	public abstract void stop(int options) throws BundleException;

	@Override
	public abstract void stop() throws BundleException;

	@Override
	public abstract void update(InputStream input) throws BundleException;

	@Override
	public abstract void update() throws BundleException;

	@Override
	public abstract void uninstall() throws BundleException;

}
