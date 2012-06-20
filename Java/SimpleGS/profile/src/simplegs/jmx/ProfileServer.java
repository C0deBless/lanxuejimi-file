package simplegs.jmx;

import java.io.IOException;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryManagerMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import simplegs.jmx.helper.ObjectNameHelper;
import simplegs.log.Log;

public class ProfileServer {

	public int connectorServerPort = 1090;
	public ClassLoadingMXBean classloadingMBean = ManagementFactory
			.getClassLoadingMXBean();
	public ThreadMXBean threadMBean = ManagementFactory.getThreadMXBean();
	public CompilationMXBean compilationMBean = ManagementFactory
			.getCompilationMXBean();
	public OperatingSystemMXBean operatingSystemMBean = ManagementFactory
			.getOperatingSystemMXBean();
	public MemoryMXBean memoryMBean = ManagementFactory.getMemoryMXBean();
	public List<MemoryManagerMXBean> memoryManagerMBean = ManagementFactory
			.getMemoryManagerMXBeans();
	public List<GarbageCollectorMXBean> gcMBean = ManagementFactory
			.getGarbageCollectorMXBeans();
	public List<MemoryPoolMXBean> memoryPoolMBean = ManagementFactory
			.getMemoryPoolMXBeans();

	public void run() {
		try {
			LocateRegistry.createRegistry(connectorServerPort);
			MBeanServer server = MBeanServerFactory
					.newMBeanServer("jmxconnector");
			JMXServiceURL url = new JMXServiceURL("rmi", null,
					connectorServerPort, "/jndi/rmi://localhost:"
							+ connectorServerPort + "/jmxconnector");
			Log.info("jmx server start bind url -> {}", url.toString());
			// RMIConnectorServer rmiServer = new RMIConnectorServer(url, null);
			JMXConnectorServer jmxConnector = JMXConnectorServerFactory
					.newJMXConnectorServer(url, null, server);
			initMBean(server);
			jmxConnector.start();
			jmxConnector.addNotificationListener(
					new TestNotificationListener(), null, null);
			Log.info("server started port -> {}", connectorServerPort);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		} catch (InstanceAlreadyExistsException e) {
			e.printStackTrace();
		} catch (MBeanRegistrationException e) {
			e.printStackTrace();
		} catch (NotCompliantMBeanException e) {
			e.printStackTrace();
		}
	}

	public void initMBean(MBeanServer server)
			throws MalformedObjectNameException,
			InstanceAlreadyExistsException, MBeanRegistrationException,
			NotCompliantMBeanException {
		// ClassLoader classloader = JMXServer1.class.getClassLoader();
		server.registerMBean(classloadingMBean,
				ObjectNameHelper.newObjectName("java.lang:type=ClassLoading"));
		server.registerMBean(threadMBean,
				ObjectNameHelper.newObjectName("java.lang:type=Threading"));
		server.registerMBean(compilationMBean,
				ObjectNameHelper.newObjectName("java.lang:type=Compilation"));
		server.registerMBean(operatingSystemMBean, ObjectNameHelper
				.newObjectName("java.lang:type=OperatingSystem"));
		server.registerMBean(memoryMBean,
				ObjectNameHelper.newObjectName("java.lang:type=Memory"));
		if (memoryManagerMBean != null && memoryManagerMBean.size() > 0) {
			server.registerMBean(memoryManagerMBean.get(0), ObjectNameHelper
					.newObjectName("java.lang:type=MemoryManager"));
		}
		if (gcMBean != null && gcMBean.size() > 0) {
			server.registerMBean(gcMBean.get(0), ObjectNameHelper
					.newObjectName("java.lang:type=GarbageCollector"));
		}
		if (memoryPoolMBean != null && memoryPoolMBean.size() > 0) {
			server.registerMBean(memoryPoolMBean.get(0),
					ObjectNameHelper.newObjectName("java.lang:type=MemoryPool"));
		}
	}

}

class TestNotificationListener implements NotificationListener {

	@Override
	public void handleNotification(Notification notification, Object handback) {
		System.out.println(notification.getSource());
		System.out.println(notification.getMessage());
	}

}