package com.trnnn.osgi.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Dictionary;
import java.util.Enumeration;
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
			// 载入Framework启动配置
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
			// 初始化Framework环境
			initFramework(framework, event);
			initClassLoader(framework);

			// 启动Framework
			framework.start();

			succeed = true;
		} catch (BundleException e) {
			throw new OSGiStartException(" Start OSGi Framework error! ", e);
		} catch (IOException e) {
			throw new OSGiStartException(" Init OSGi Framework error ", e);
		}

		// Class1 clazz = new Class1();
		// clazz.output();
	}

	private void initClassLoader(Framework framework) {
		BundleContext bundleContext = framework.getBundleContext();
		bundleContext.addBundleListener(new BundleListener() {
			@Override
			public void bundleChanged(BundleEvent event) {
				Bundle bundle = event.getBundle();
				Dictionary<String, String> dic = bundle.getHeaders();
				Enumeration<String> it = dic.keys();
				logger.info("[Bundle changed] headers : {}, location: {}",
						bundle.getSymbolicName(), bundle.getLocation());
				while (it.hasMoreElements()) {
					String key = it.nextElement();
					// if (!key.equals("Export-Package")) {
					// continue;
					// }
					String attr = dic.get(key);
					logger.info("{}: {}", key, attr);
				}
			}
		});
	}

	@SuppressWarnings("deprecation")
	private static void initFramework(Framework framework,
			ServletContextEvent event) throws IOException {
		BundleContext bundleContext = framework.getBundleContext();
		ServletContext servletContext = event.getServletContext();

		// 将ServletContext注册为服务
		registerContext(bundleContext, servletContext);

		File file = bundleContext.getDataFile(".init");
		if (!file.isFile()) { // 第一次初始化
			logger.info(" Init Framework ");

			String pluginLocation = servletContext
					.getInitParameter(CONTEXT_PARAM_OSGI_PLUGINS_LOCATION);
			if (pluginLocation == null)
				pluginLocation = DEFAULT_OSGI_PLUGINS_LOCATION;
			else if (!pluginLocation.startsWith(" / "))
				pluginLocation = " / ".concat(pluginLocation);

			String bundleRootPath = servletContext.getRealPath(pluginLocation);
			logger.info("Load bundles from path -> " + bundleRootPath);
			// 安装bundle
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
			// 载入配置项
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
		// 检查storageDirectory合法性
		if (storageDirectory.startsWith(WEB_ROOT)) {
			// 如果以WEB_ROOT常量字符串开头，那么相对于WEB_ROOT来定 位
			storageDirectory = storageDirectory.substring(WEB_ROOT.length());
			storageDirectory = context.getRealPath(storageDirectory);
		} else {
			// 如果是相对路径，那么相对于WEB_ROOT来定位
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
