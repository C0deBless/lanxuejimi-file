package com.trnnn.osgi.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.eclipse.osgi.framework.internal.core.Constants;
import org.eclipse.osgi.launch.EquinoxFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleListener;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.slf4j.Logger;

import testplugin1.Class1;

import com.trnnn.osgi.classloader.BridgeClassLoader;

public class FrameworkConfigListener implements ServletContextListener {

	private static final String CONTEXT_PARAM_OSGI_PLUGINS_LOCATION = "OSGI_PLUGINS_LOCATION";
	private static final String DEFAULT_OSGI_PLUGINS_LOCATION = "/plugins";
	private static final String CONTEXT_PARAM_OSGI_CONFIG_LOCATION = "OSGI_CONFIG_LOCATION";
	private static final String DEFAULT_OSGI_CONFIG_LOCATION = "/osgi.properties";
	private static final String PROPERTY_FRAMEWORK_STORAGE = "PROPERTY_FRAMEWORK_STORAGE";
	private static final String DEFAULT_OSGI_STORAGE_DIRECTORY = "/framework.properties";
	private static final String WEB_ROOT = "osgi-web";
	static Logger logger = org.slf4j.LoggerFactory
			.getLogger(FrameworkConfigListener.class);
	Framework framework;
	boolean succeed;
	BridgeClassLoader bridgeClassLoader;

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		if (framework != null) {
			logger.info(" Stopping OSGi Framework ");

			boolean succeed = false;
			try {
				if (framework.getState() == Framework.ACTIVE)
					framework.stop();
				framework.waitForStop(0);
				framework = null;

				succeed = true;
			} catch (BundleException e) {
				throw new OSGiStopException(" Stop OSGi Framework error! ", e);
			} catch (InterruptedException e) {
				throw new OSGiStopException(" Stop OSGi Framework error! ", e);
			} finally {
				if (succeed)
					logger.info(" OSGi Framework Stopped! ");
				else
					logger.info(" OSGi Framework not stop! ");
			}
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		this.bridgeClassLoader = new BridgeClassLoader(event
				.getServletContext().getClassLoader());
		Class<EquinoxFactory> frameworkFactoryClass = null;
		try {
			// frameworkFactoryClass =
			// ServiceLoader.load(FrameworkFactory.class);
			frameworkFactoryClass = EquinoxFactory.class;
		} catch (Exception e) {
			throw new IllegalArgumentException(
					" FrameworkFactory service load error. ", e);
		}
		// if (frameworkFactoryClass == null) {
		// throw new IllegalArgumentException(
		// " FrameworkFactory service not found. ");
		// }

		FrameworkFactory frameworkFactory;
		try {
			frameworkFactory = frameworkFactoryClass.newInstance();
		} catch (Exception e) {
			// throw new IllegalArgumentException(
			// " FrameworkFactory instantiation error. ", e);
			e.printStackTrace();
			return;
		}

		Map<String, String> configuration;
		try {
			configuration = loadFrameworkConfig(event.getServletContext());
			for (Object key : configuration.keySet()) {
				logger.info("Load Framework configuration:  \t " + key
						+ "  =  " + configuration.get(key));
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(
					" Load Framework configuration error. ", e);
		}

		try {

			framework = frameworkFactory.newFramework(configuration);
			framework.init();

			logger.info("Initializing new OSGi framework \t  ");
			// 鍒濆鍖朏ramework鐜
			initFramework(framework, event);
			initClassLoader(framework);

			// 鍚姩Framework
			framework.start();

			succeed = true;
		} catch (BundleException e) {
			throw new OSGiStartException(" Start OSGi Framework error! ", e);
		} catch (IOException e) {
			throw new OSGiStartException(" Init OSGi Framework error ", e);
		}

		Class<?> clazz;
		try {
			clazz = this.bridgeClassLoader.loadClass("testplugin1.Class1");
			Class1 c1 = new Class1();
			Object c = clazz.newInstance();
			clazz.getMethod("output").invoke(c);
			// Class1 c = (Class1) clazz.newInstance();
			// c.output();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

	}

	private void initClassLoader(Framework framework) {
		BundleContext bundleContext = framework.getBundleContext();
		bridgeClassLoader.resolveBundleClassSpace(bundleContext);
		bundleContext.addBundleListener(new BundleListener() {
			@Override
			public void bundleChanged(BundleEvent event) {
				Bundle bundle = event.getBundle();
				bridgeClassLoader.resolveBundleClassSpace(bundle);
			}
		});
	}

	@SuppressWarnings("deprecation")
	private static void initFramework(Framework framework,
			ServletContextEvent event) throws IOException {
		BundleContext bundleContext = framework.getBundleContext();
		ServletContext servletContext = event.getServletContext();

		// 灏哠ervletContext娉ㄥ唽涓烘湇鍔�
		registerContext(bundleContext, servletContext);

		File file = bundleContext.getDataFile(".init");
		if (!file.isFile()) { // 绗竴娆″垵濮嬪寲
			logger.info(" Init Framework ");

			String pluginLocation = servletContext
					.getInitParameter(CONTEXT_PARAM_OSGI_PLUGINS_LOCATION);
			if (pluginLocation == null)
				pluginLocation = DEFAULT_OSGI_PLUGINS_LOCATION;
			else if (!pluginLocation.startsWith(" / "))
				pluginLocation = " / ".concat(pluginLocation);

			String bundleRootPath = servletContext.getRealPath(pluginLocation);
			logger.info("Load bundles from path -> " + bundleRootPath);
			// 瀹夎bundle
			File bundleRoot = new File(bundleRootPath);
			if (bundleRoot.isDirectory()) {
				logger.info(" Load Framework bundles from:  " + pluginLocation);

				File bundleFiles[] = bundleRoot.listFiles(new FilenameFilter() {
					public boolean accept(File dir, String name) {
						logger.info("Searching file -> " + name + ",  path -> "
								+ dir.getPath());
						return name.endsWith(".jar");
					}
				});

				if (bundleFiles != null && bundleFiles.length > 0) {
					for (File bundleFile : bundleFiles) {
						try {
							bundleContext.installBundle(bundleFile.toURL()
									.toExternalForm());
							logger.info(" Install bundle success:  "
									+ bundleFile.getName());
						} catch (Throwable e) {
							logger.warn(
									" Install bundle error:  " + bundleFile, e);
						}
					}
				} else {
					logger.warn("Bundle files is empty...");
				}

				for (Bundle bundle : bundleContext.getBundles()) {
					if (bundle.getState() == Bundle.INSTALLED
							|| bundle.getState() == Bundle.RESOLVED) {
						if (bundle.getHeaders().get(Constants.BUNDLE_ACTIVATOR) != null) {
							try {
								bundle.start(Bundle.START_ACTIVATION_POLICY);
								logger.info(" Start bundle:  " + bundle);
							} catch (Throwable e) {
								logger.warn(" Start bundle error:  " + bundle,
										e);
							}
						}
					}
				}
			}

			new FileWriter(file).close();
			logger.info(" Framework inited. ");
		} else {
			logger.info("file.isFile()");
		}
	}

	private static void registerContext(BundleContext bundleContext,
			ServletContext servletContext) {
		Dictionary<String, String> properties = new Hashtable<>();
		properties.put("ServerInfo ", servletContext.getServerInfo());
		properties.put("ServletContextName ",
				servletContext.getServletContextName());
		properties.put("MajorVersion ",
				String.valueOf(servletContext.getMajorVersion()));
		properties.put("MinorVersion ",
				String.valueOf(servletContext.getMinorVersion()));
		bundleContext.registerService(ServletContext.class.getName(),
				servletContext, properties);
	}

	@SuppressWarnings("deprecation")
	private static Map<String, String> loadFrameworkConfig(
			ServletContext context) throws MalformedURLException {
		String configLocation = context
				.getInitParameter(CONTEXT_PARAM_OSGI_CONFIG_LOCATION);
		if (configLocation == null)
			configLocation = DEFAULT_OSGI_CONFIG_LOCATION;
		else if (!configLocation.startsWith(" / "))
			configLocation = " / ".concat(configLocation);

		Properties config = new Properties();
		try {
			// 杞藉叆閰嶇疆椤�
			config.load(context.getResourceAsStream(configLocation));
			logger.info(" Load Framework configuration from:  "
					+ configLocation);
		} catch (IOException e) {
			if (logger.isWarnEnabled())
				logger.warn(" Load Framework configuration error from:  "
						+ configLocation, e);
		}

		String storageDirectory = config.getProperty(
				PROPERTY_FRAMEWORK_STORAGE, DEFAULT_OSGI_STORAGE_DIRECTORY);
		// 妫�煡storageDirectory鍚堟硶鎬�
		if (storageDirectory.startsWith(WEB_ROOT)) {
			// 濡傛灉浠EB_ROOT甯搁噺瀛楃涓插紑澶达紝閭ｄ箞鐩稿浜嶹EB_ROOT鏉ュ畾 浣�
			storageDirectory = storageDirectory.substring(WEB_ROOT.length());
			storageDirectory = context.getRealPath(storageDirectory);
		} else {
			// 濡傛灉鏄浉瀵硅矾寰勶紝閭ｄ箞鐩稿浜嶹EB_ROOT鏉ュ畾浣�
			if (!new File(storageDirectory).isAbsolute()) {
				storageDirectory = context.getRealPath(storageDirectory);
			}
		}
		storageDirectory = new File(storageDirectory).toURL().toExternalForm();
		config.setProperty(PROPERTY_FRAMEWORK_STORAGE, storageDirectory);
		logger.info(" Use Framework Storage:  " + storageDirectory);

		return propertiesToMap(config);
	}

	private static Map<String, String> propertiesToMap(Properties prop) {
		Set<Object> keyset = prop.keySet();
		Map<String, String> values = new HashMap<>();
		for (Object key : keyset) {
			String value = prop.getProperty(key.toString());
			values.put(key.toString(), value);
		}
		return values;
	}

}
