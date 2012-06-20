/**
 * Code Sample for
 * Runtime Performance & Availability Monitoring for Java Systems.
 * IBM DevelperWorks.
 * Nicholas Whitehead (whitehead.nicholas@gmail.com)
 */

package org.runtimemonitoring.collectors.jmx;

import static java.lang.management.ManagementFactory.CLASS_LOADING_MXBEAN_NAME;
import static java.lang.management.ManagementFactory.COMPILATION_MXBEAN_NAME;
import static java.lang.management.ManagementFactory.GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE;
import static java.lang.management.ManagementFactory.MEMORY_MXBEAN_NAME;
import static java.lang.management.ManagementFactory.MEMORY_POOL_MXBEAN_DOMAIN_TYPE;
import static java.lang.management.ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME;
import static java.lang.management.ManagementFactory.THREAD_MXBEAN_NAME;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerFactory;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.runtimemonitoring.collectors.BaseCollector;
import org.runtimemonitoring.tracing.ITracer;
import org.runtimemonitoring.tracing.TracerFactory;

/**
 * <p>
 * Title:JMXCollector
 * </p>
 * <p>
 * Description: Sample implementation of a JMX polling collector that collects
 * data from the target VM's MXBeans.
 * </p>
 * 
 * @author whitehead.nicholas@gmail.com
 * @version $Revision$
 */

public class JMXCollector extends BaseCollector {

	/** A reference to the configured ITracer */
	protected ITracer tracer = null;
	/** A connection to the target MBeanServer */
	protected MBeanServerConnection jmxServer = null;
	/** A cache of pre-created ObjectNames */
	protected Map<String, ObjectName> objectNameCache = new HashMap<String, ObjectName>();
	/** A cache of discovered ObjectNames */
	protected Map<String, Set<ObjectName>> objectNameQueryCache = new HashMap<String, Set<ObjectName>>();
	/** The host name that is being monitored */
	protected String hostName = null;

	/**
	 * Unused default constructor.
	 */
	public JMXCollector() {

	}

	/**
	 * Creates a new JMXCollector.
	 * 
	 * @param tracerKey
	 *            The cache key where the ITracer instance can be retrieved
	 *            from.
	 * @param serviceURL
	 *            The URL to connect to the remote MBeanServer from.
	 */
	public JMXCollector(String tracerKey, String serviceURL) {
		try {
			tracer = TracerFactory.getInstance(tracerKey);
			// hostName = "AppServer3.myco.org";

			hostName = "127.0.0.1";
			log("Starting JMXCollector Example", "\n\tRemote URL", serviceURL);
			jmxServer = getRemoteMBeanServer(serviceURL);
			log("Acquired MBeanServerConnection:", jmxServer, "\n\tClass:",
					jmxServer.getClass().getName());
			// Create and cache JMX Object Names
			objectNameCache.put(THREAD_MXBEAN_NAME, new ObjectName(
					THREAD_MXBEAN_NAME));
			objectNameCache.put(CLASS_LOADING_MXBEAN_NAME, new ObjectName(
					CLASS_LOADING_MXBEAN_NAME));
			objectNameCache.put(COMPILATION_MXBEAN_NAME, new ObjectName(
					COMPILATION_MXBEAN_NAME));
			objectNameCache.put(OPERATING_SYSTEM_MXBEAN_NAME, new ObjectName(
					OPERATING_SYSTEM_MXBEAN_NAME));
			objectNameCache.put(MEMORY_MXBEAN_NAME, new ObjectName(
					MEMORY_MXBEAN_NAME));
			objectNameCache.put("GC_MBEAN_QUERY", new ObjectName(
					GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE + ",*"));
			objectNameCache.put("MP_MBEAN_QUERY", new ObjectName(
					MEMORY_POOL_MXBEAN_DOMAIN_TYPE + ",*"));

			// Schedule Collection
			scheduler.registerCollectionSchedule(this, 10000, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Executes a single collection of data from the configured MBeanServer.
	 */
	public void collect() {
		CompositeData compositeData = null;
		String type = null;
		if (jmxServer == null) {
			return;
		}
		try {
			log("Starting JMX Collection");
			long start = System.currentTimeMillis();
			ObjectName on = null;
			// Class Loading Monitoring
			on = objectNameCache.get(CLASS_LOADING_MXBEAN_NAME);
			tracer.traceDeltaSticky(
					(Long) jmxServer.getAttribute(on, "TotalLoadedClassCount"),
					hostName, "JMX", on.getKeyProperty("type"),
					"TotalLoadedClassCount");
			tracer.traceDeltaSticky(
					(Long) jmxServer.getAttribute(on, "UnloadedClassCount"),
					hostName, "JMX", on.getKeyProperty("type"),
					"UnloadedClassCount");
			tracer.traceSticky(
					(Integer) jmxServer.getAttribute(on, "LoadedClassCount"),
					hostName, "JMX", on.getKeyProperty("type"),
					"LoadedClassCount");
			// Compilation Monitoring
			on = objectNameCache.get(COMPILATION_MXBEAN_NAME);
			tracer.traceDeltaSticky(
					(Long) jmxServer.getAttribute(on, "TotalCompilationTime"),
					hostName, "JMX", on.getKeyProperty("type"),
					"CompilationTime");
			// Thread Monitoring
			on = objectNameCache.get(THREAD_MXBEAN_NAME);
			tracer.traceDeltaSticky((Long) jmxServer.getAttribute(on,
					"TotalStartedThreadCount"), hostName, "JMX", on
					.getKeyProperty("type"), "StartedThreadRate");
			tracer.traceSticky(
					(Integer) jmxServer.getAttribute(on, "ThreadCount"),
					hostName, "JMX", on.getKeyProperty("type"),
					"CurrentThreadCount");
			// Memory Usage
			on = objectNameCache.get(MEMORY_MXBEAN_NAME);
			compositeData = (CompositeData) jmxServer.getAttribute(on,
					"HeapMemoryUsage");
			for (Object key : compositeData.getCompositeType().keySet()) {
				tracer.traceSticky((Long) compositeData.get(key.toString()),
						hostName, "JMX", "Memory", "HeapMemory", key.toString());
			}
			compositeData = (CompositeData) jmxServer.getAttribute(on,
					"NonHeapMemoryUsage");
			for (Object key : compositeData.getCompositeType().keySet()) {
				tracer.traceSticky((Long) compositeData.get(key.toString()),
						hostName, "JMX", "Memory", "NonHeapMemory",
						key.toString());
			}
			// Memory Pool Monitoring
			if (!objectNameQueryCache.containsKey("MP_MBEAN_QUERY")) {
				objectNameQueryCache.put(
						"MP_MBEAN_QUERY",
						(Set<ObjectName>) jmxServer.queryNames(
								objectNameCache.get("MP_MBEAN_QUERY"), null));
			}
			for (ObjectName mpOn : objectNameQueryCache.get("MP_MBEAN_QUERY")) {
				compositeData = (CompositeData) jmxServer.getAttribute(mpOn,
						"Usage");
				type = (String) jmxServer.getAttribute(mpOn, "Type");
				for (Object key : compositeData.getCompositeType().keySet()) {
					tracer.traceSticky(
							(Long) compositeData.get(key.toString()), hostName,
							"JMX", "MemoryPools", type,
							mpOn.getKeyProperty("name"), key.toString());
				}
			}
			// Garbage Collection Monitoring.
			if (!objectNameQueryCache.containsKey("GC_MBEAN_QUERY")) {
				objectNameQueryCache.put(
						"GC_MBEAN_QUERY",
						(Set<ObjectName>) jmxServer.queryNames(
								objectNameCache.get("GC_MBEAN_QUERY"), null));
			}
			for (ObjectName gcOn : objectNameQueryCache.get("GC_MBEAN_QUERY")) {
				tracer.traceDeltaSticky(
						(Long) jmxServer.getAttribute(gcOn, "CollectionCount"),
						hostName, "JMX", gcOn.getKeyProperty("type"),
						gcOn.getKeyProperty("name"), "CollectionRate");
				tracer.traceDeltaSticky(
						(Long) jmxServer.getAttribute(gcOn, "CollectionTime"),
						hostName, "JMX", gcOn.getKeyProperty("type"),
						gcOn.getKeyProperty("name"), "CollectionTimeRate");
			}
			// Done
			long elapsed = System.currentTimeMillis() - start;
			tracer.trace(elapsed, hostName, "JMX", "JMX Collector",
					"Collection", "Last Elapsed Time");
			tracer.trace(new Date(), hostName, "JMX", "JMX Collector",
					"Collection", "Last Collection");
			log("Completed JMX Collection in ", elapsed, " ms.");
		} catch (Exception e) {
			log("Failed:" + e);
			e.printStackTrace();
			tracer.traceIncident(hostName, "JMX", "JMX Collector",
					"Collection", "Collection Errors");
		}
	}

	/**
	 * Acquires a connection to the in-vm MBeanServer with the passed default
	 * domain name.
	 * 
	 * @param defaultDomainName
	 *            The default domain name of the requested MBeanServer.
	 * @return An MBeanServerConnection.
	 */
	public MBeanServerConnection getLocalMBeanServer(String defaultDomainName) {
		// MBeanServerConnection conn = null;
		for (MBeanServer server : (List<MBeanServer>) MBeanServerFactory
				.findMBeanServer(null)) {
			if (server.getDefaultDomain().equals(defaultDomainName))
				return server;
		}
		throw new RuntimeException("Failed to locate MBeanServer for "
				+ defaultDomainName);
	}

	/**
	 * Returns the local platform MBeanServer.
	 * 
	 * @return An MBeanServerConnection.
	 */
	public MBeanServerConnection getPlatformMBeanServer() {
		return getPlatformMBeanServer();
	}

	/**
	 * Acquires a connection to a remote MBeanServer. Example URL: eg:
	 * <code>service:jmx:rmi://127.0.0.1/jndi/rmi://127.0.0.1:1090/jmxconnector</code>
	 * 
	 * @param serviceURL
	 *            The JMX Service URL to connect to.
	 * @return An MBeanServerConnection.
	 */
	protected MBeanServerConnection getRemoteMBeanServer(String serviceURL) {
		try {
			JMXServiceURL jMXServiceURL = new JMXServiceURL(serviceURL);
			JMXConnector connector = JMXConnectorFactory.connect(jMXServiceURL);
			return connector.getMBeanServerConnection();
		} catch (Exception e) {
			throw new RuntimeException(
					"Failed to acquire remote connection with URL ["
							+ serviceURL + "]", e);
		}
	}

	public MBeanServerConnection getMBeanServerConnection() {
		return this.jmxServer;
	}

	// public void startLocalMBeanServer(String serviceURL) {
	// try {
	// String jmxServerName = "jmxconnector";
	// MBeanServer mbs = MBeanServerFactory
	// .createMBeanServer(jmxServerName);
	// JMXServiceURL url = new JMXServiceURL(
	// "service:jmx:rmi:///jndi/rmi://127.0.0.1:1090/"
	// + jmxServerName);
	// System.out.println("JMXServiceURL: " + url.toString());
	// JMXConnectorServer jmxConnServer = JMXConnectorServerFactory
	// .newJMXConnectorServer(url, null, mbs);
	// // jmxConnServer.start();
	// jmxConnServer.start();
	// jmxConnServer.addNotificationListener(new ClientListener(), null,
	// null);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// }
	//
	// public void startLocalMBeanServer1(String serviceURL) {
	// try {
	// Dynamic mbean = new Dynamic();
	// MBeanServer server = MBeanServerFactory.newMBeanServer();
	//
	// JMXServiceURL url = new JMXServiceURL(serviceURL);
	// System.out.println("JMXServiceURL: " + url.toString());
	// JMXConnectorServer jmxConnServer = JMXConnectorServerFactory
	// .newJMXConnectorServer(url, null, server);
	// jmxServer = jmxConnServer.getMBeanServer();
	//
	// Set<String> keyset = objectNameCache.keySet();
	// for (String string : keyset) {
	// ObjectName bean = objectNameCache.get(string);
	// server.registerMBean(mbean, bean);
	// }
	// // server.registerMBean(null,
	// // ObjectName.getInstance("config:service=loader"));
	// } catch (NullPointerException e) {
	// e.printStackTrace();
	// } catch (MalformedURLException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// } catch (InstanceAlreadyExistsException e) {
	// e.printStackTrace();
	// } catch (MBeanRegistrationException e) {
	// e.printStackTrace();
	// } catch (NotCompliantMBeanException e) {
	// e.printStackTrace();
	// }
	//
	// }

	// public void startJMXServer() throws IOException {
	// String jmxServerName = "jmxconnector";
	// MBeanServer mbs = MBeanServerFactory.createMBeanServer(jmxServerName);
	// JMXServiceURL url = new JMXServiceURL(
	// "service:jmx:rmi:///jndi/rmi://127.0.0.1:1090/" + jmxServerName);
	// System.out.println("JMXServiceURL: " + url.toString());
	// JMXConnectorServer jmxConnServer = JMXConnectorServerFactory
	// .newJMXConnectorServer(url, null, mbs);
	// jmxConnServer.start();
	// }

	public static class ClientListener implements NotificationListener {

		public void handleNotification(Notification notification,
				Object handback) {
			echo("\nReceived notification:");
			echo("\tClassName: " + notification.getClass().getName());
			echo("\tSource: " + notification.getSource());
			echo("\tType: " + notification.getType());
			echo("\tMessage: " + notification.getMessage());
			if (notification instanceof AttributeChangeNotification) {
				AttributeChangeNotification acn = (AttributeChangeNotification) notification;
				echo("\tAttributeName: " + acn.getAttributeName());
				echo("\tAttributeType: " + acn.getAttributeType());
				echo("\tNewValue: " + acn.getNewValue());
				echo("\tOldValue: " + acn.getOldValue());
			}
		}

		private void echo(String string) {
			System.out.println(string);
		}
	}
}
