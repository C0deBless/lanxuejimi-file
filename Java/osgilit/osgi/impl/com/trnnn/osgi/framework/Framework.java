package com.trnnn.osgi.framework;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;

import com.trnnn.osgi.classloader.FrameworkClassLoader;
import com.trnnn.osgi.console.Console;
import com.trnnn.osgi.framework.bundle.AbstractBundle;
import com.trnnn.osgi.framework.bundle.BundleContextImpl;
import com.trnnn.osgi.framework.bundle.BundleInstaller;
import com.trnnn.osgi.framework.bundle.BundleRepository;
import com.trnnn.osgi.framework.bundle.SystemBundle;

public class Framework implements org.osgi.framework.launch.Framework {

	public static final int FRAMEWORK_STATE_READY = 0x00000001;
	public static final int FRAMEWORK_STATE_LOADING = 0x00000002;
	public static final int FRAMEWORK_STATE_LOADED = 0x00000004;
	public static final int FRAMEWORK_STATE_STARTING = 0x00000008;
	public static final int FRAMEWORK_STATE_STARTED = 0x000000010;
	public static final int FRAMEWORK_STATE_STOPPING = 0x000000020;
	public static final int FRAMEWORK_STATE_STOPPED = 0x00000040;

	private Console console;
	private Map<String, String> config;
	public AbstractBundle systemBundle;
	private AtomicInteger bundleIdCursor = new AtomicInteger(0);
	public static final AtomicInteger frameworkIdCursor = new AtomicInteger(0);
	private BundleInstaller bundleInstaller;
	private BundleRepository bundleRepository;
	private int frameworkState = FRAMEWORK_STATE_READY;
	private FrameworkClassLoader classLoader;

	public FrameworkClassLoader getClassLoader() {
		return classLoader;
	}

	public Framework(Map<String, String> config) {
		this.config = config;
		build();
		try {
			start();
		} catch (BundleException e) {
			e.printStackTrace();
		}
	}

	private void build() {

		this.systemBundle = this.createSystemBundle();
		this.bundleInstaller = new BundleInstaller(this);
		initBundleRepository();

		this.console = new Console(this.systemBundle.getBundleContext());
		try {
			this.console.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.bundleInstaller.installAll();
		this.startLoadedBundle();
		this.initClassLoader();
	}

	private void initClassLoader() {
		this.classLoader = new FrameworkClassLoader(this);
		Thread.currentThread().setContextClassLoader(classLoader);
	}

	private void startLoadedBundle() {
		this.frameworkState = FRAMEWORK_STATE_STARTING;

		this.frameworkState = FRAMEWORK_STATE_STARTED;
	}

	public BundleRepository bundleRepository() {
		return this.bundleRepository;
	}

	public BundleInstaller bundleInstaller() {
		return this.bundleInstaller;
	}

	private void initBundleRepository() {
		String path = config.get(FrameworkConfig.REPOSITORY_PATH);

		File repository = new File(path);
		if (repository.exists() && repository.isDirectory()) {
			File[] files = repository.listFiles();
			URL[] urls = new URL[files.length];
			for (int i = 0; i < files.length; i++) {

				try {
					File sub = files[i];
					URL url = sub.toURI().toURL();
					urls[i] = url;
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}

			this.bundleRepository = new BundleRepository(urls);

		} else {

		}
	}

	public Console console() {
		return this.console;
	}

	private AbstractBundle createSystemBundle() {
		SystemBundle systemBundle = new SystemBundle(this);

		return systemBundle;
	}

	public BundleContext makeContext() {
		BundleContext context = new BundleContextImpl();
		return context;
	}

	public int getState() {
		return 0;
	}

	public Dictionary<String, String> getHeaders() {
		return null;
	}

	public ServiceReference<?>[] getRegisteredServices() {
		return null;
	}

	public ServiceReference<?>[] getServicesInUse() {
		return null;
	}

	public boolean hasPermission(Object permission) {
		return false;
	}

	public URL getResource(String name) {
		return null;
	}

	public Dictionary<String, String> getHeaders(String locale) {
		return null;
	}

	public Class<?> loadClass(String name) throws ClassNotFoundException {
		return null;
	}

	public Enumeration<URL> getResources(String name) throws IOException {
		return null;
	}

	public long getLastModified() {
		return 0;
	}

	public BundleContext getBundleContext() {
		return null;
	}

	public Map<X509Certificate, List<X509Certificate>> getSignerCertificates(
			int signersType) {
		return null;
	}

	public Version getVersion() {
		return null;
	}

	public File getDataFile(String filename) {
		return null;
	}

	public int compareTo(Bundle o) {
		return 0;
	}

	public void init() throws BundleException {
		// TODO: build BundleConext here
	}

	public FrameworkEvent waitForStop(long timeout) throws InterruptedException {
		return null;
	}

	public int generateBundleId() {
		return this.bundleIdCursor.addAndGet(1);
	}

	public void start() throws BundleException {
		try {
			this.init();
			waitForStop(0);
		} catch (InterruptedException | BundleException e) {
			e.printStackTrace();
		}
	}

	public void start(int options) throws BundleException {
		// TODO: handle options here;

		start();
	}

	public void stop() throws BundleException {

	}

	public void stop(int options) throws BundleException {

	}

	public void uninstall() throws BundleException {

	}

	public void update() throws BundleException {

	}

	public void update(InputStream in) throws BundleException {

	}

	public long getBundleId() {
		return 0;
	}

	public String getLocation() {
		return null;
	}

	public String getSymbolicName() {
		return null;
	}

	public Enumeration<String> getEntryPaths(String path) {
		return null;
	}

	public URL getEntry(String path) {
		return null;
	}

	public Enumeration<URL> findEntries(String path, String filePattern,
			boolean recurse) {
		return null;
	}

	public <A> A adapt(Class<A> type) {
		return null;
	}

	public int getFrameworkState() {
		return frameworkState;
	}

	public void setFrameworkState(int frameworkState) {
		this.frameworkState = frameworkState;
	}

}
