package com.trnnn.osgi.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
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
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.slf4j.Logger;

import com.trnnn.osgi.lancher.ServiceLoader;

public class FrameworkConfigListener implements ServletContextListener {

	private static final String CONTEXT_PARAM_OSGI_PLUGINS_LOCATION = null;
	private static final String DEFAULT_OSGI_PLUGINS_LOCATION = null;
	private static final String CONTEXT_PARAM_OSGI_CONFIG_LOCATION = null;
	private static final String DEFAULT_OSGI_CONFIG_LOCATION = null;
	private static final String PROPERTY_FRAMEWORK_STORAGE = null;
	private static final String DEFAULT_OSGI_STORAGE_DIRECTORY = null;
	private static final String WEB_ROOT = null;
	static Logger logger = org.slf4j.LoggerFactory
			.getLogger(FrameworkConfigListener.class);
	Framework framework;
	boolean succeed;

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		if (framework != null) {
			if (logger.isInfoEnabled())
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
				if (logger.isInfoEnabled()) {
					if (succeed)
						logger.info(" OSGi Framework Stopped! ");
					else
						logger.info(" OSGi Framework not stop! ");
				}
			}
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {

		Class<FrameworkFactory> frameworkFactoryClass = null;
		try {
			frameworkFactoryClass = ServiceLoader.load(FrameworkFactory.class);
		} catch (Exception e) {
			throw new IllegalArgumentException(
					" FrameworkFactory service load error. ", e);
		}
		if (frameworkFactoryClass == null) {
			throw new IllegalArgumentException(
					" FrameworkFactory service not found. ");
		}

		FrameworkFactory frameworkFactory;
		try {
			frameworkFactory = frameworkFactoryClass.newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException(
					" FrameworkFactory instantiation error. ", e);
		}

		Map<String, String> configuration;
		try {
			// 载入Framework启动配置
			configuration = loadFrameworkConfig(event.getServletContext());
			if (logger.isInfoEnabled()) {
				logger.info(" Load Framework configuration: [ ");
				for (Object key : configuration.keySet()) {
					logger.info(" /t " + key + "  =  " + configuration.get(key));
				}
				logger.info(" ] ");
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(
					" Load Framework configuration error. ", e);
		}

		try {
			framework = frameworkFactory.newFramework(configuration);
			framework.init();

			// 初始化Framework环境
			initFramework(framework, event);

			// 启动Framework
			framework.start();

			succeed = true;
		} catch (BundleException e) {
			throw new OSGiStartException(" Start OSGi Framework error! ", e);
		} catch (IOException e) {
			throw new OSGiStartException(" Init OSGi Framework error ", e);
		}

	}

	private static void initFramework(Framework framework,
			ServletContextEvent event) throws IOException {
		BundleContext bundleContext = framework.getBundleContext();
		ServletContext servletContext = event.getServletContext();

		// 将ServletContext注册为服务
		registerContext(bundleContext, servletContext);

		File file = bundleContext.getDataFile(" .init ");
		if (!file.isFile()) { // 第一次初始化
			if (logger.isInfoEnabled())
				logger.info(" Init Framework ");

			String pluginLocation = servletContext
					.getInitParameter(CONTEXT_PARAM_OSGI_PLUGINS_LOCATION);
			if (pluginLocation == null)
				pluginLocation = DEFAULT_OSGI_PLUGINS_LOCATION;
			else if (!pluginLocation.startsWith(" / "))
				pluginLocation = " / ".concat(pluginLocation);

			// 安装bundle
			File bundleRoot = new File(
					servletContext.getRealPath(pluginLocation));
			if (bundleRoot.isDirectory()) {
				if (logger.isInfoEnabled())
					logger.info(" Load Framework bundles from:  "
							+ pluginLocation);

				File bundleFiles[] = bundleRoot.listFiles(new FilenameFilter() {
					public boolean accept(File dir, String name) {
						return name.endsWith(" .jar ");
					}
				});

				if (bundleFiles != null && bundleFiles.length > 0) {
					for (File bundleFile : bundleFiles) {
						try {
							bundleContext.installBundle(bundleFile.toURL()
									.toExternalForm());
							if (logger.isInfoEnabled())
								logger.info(" Install bundle success:  "
										+ bundleFile.getName());
						} catch (Throwable e) {
							if (logger.isWarnEnabled())
								logger.warn(" Install bundle error:  "
										+ bundleFile, e);
						}
					}
				}

				for (Bundle bundle : bundleContext.getBundles()) {
					if (bundle.getState() == Bundle.INSTALLED
							|| bundle.getState() == Bundle.RESOLVED) {
						if (bundle.getHeaders().get(Constants.BUNDLE_ACTIVATOR) != null) {
							try {
								bundle.start(Bundle.START_ACTIVATION_POLICY);
								if (logger.isInfoEnabled())
									logger.info(" Start bundle:  " + bundle);
							} catch (Throwable e) {
								if (logger.isWarnEnabled())
									logger.warn(" Start bundle error:  "
											+ bundle, e);
							}
						}
					}
				}
			}

			new FileWriter(file).close();
			if (logger.isInfoEnabled())
				logger.info(" Framework inited. ");
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
			if (logger.isInfoEnabled())
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
		if (logger.isInfoEnabled())
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
