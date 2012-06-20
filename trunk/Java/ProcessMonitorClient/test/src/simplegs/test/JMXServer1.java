package simplegs.test;

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
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

public class JMXServer1 {

	public static int connectorServerPort = 1090;
	public static ClassLoadingMXBean classloadingMBean = ManagementFactory
			.getClassLoadingMXBean();
	public static ThreadMXBean threadMBean = ManagementFactory
			.getThreadMXBean();
	public static CompilationMXBean compilationMBean = ManagementFactory
			.getCompilationMXBean();
	public static OperatingSystemMXBean operatingSystemMBean = ManagementFactory
			.getOperatingSystemMXBean();
	public static MemoryMXBean memoryMBean = ManagementFactory
			.getMemoryMXBean();
	public static List<MemoryManagerMXBean> memoryManagerMBean = ManagementFactory
			.getMemoryManagerMXBeans();
	public static List<GarbageCollectorMXBean> gcMBean = ManagementFactory
			.getGarbageCollectorMXBeans();
	public static List<MemoryPoolMXBean> memoryPoolMBean = ManagementFactory
			.getMemoryPoolMXBeans();

	public static void main(String[] args) throws InterruptedException {
		try {
			LocateRegistry.createRegistry(connectorServerPort);
			MBeanServer server = MBeanServerFactory
					.newMBeanServer("jmxconnector");
			JMXServiceURL url = new JMXServiceURL("rmi", null,
					connectorServerPort, "/jndi/rmi://localhost:"
							+ connectorServerPort + "/jmxconnector");
			System.out.println(url.toString());
			// RMIConnectorServer rmiServer = new RMIConnectorServer(url, null);
			JMXConnectorServer jmxConnector = JMXConnectorServerFactory
					.newJMXConnectorServer(url, null, server);
			initMBean(server);
			jmxConnector.start();
			jmxConnector.addNotificationListener(
					new TestNotificationListener(), null, null);
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
		Thread.currentThread().join();
	}

	public static void initMBean(MBeanServer server)
			throws MalformedObjectNameException,
			InstanceAlreadyExistsException, MBeanRegistrationException,
			NotCompliantMBeanException {
		// ClassLoader classloader = JMXServer1.class.getClassLoader();
		server.registerMBean(classloadingMBean,
				newObjectName("java.lang:type=ClassLoading"));
		server.registerMBean(threadMBean,
				newObjectName("java.lang:type=Threading"));
		server.registerMBean(compilationMBean,
				newObjectName("java.lang:type=Compilation"));
		server.registerMBean(operatingSystemMBean,
				newObjectName("java.lang:type=OperatingSystem"));
		server.registerMBean(memoryMBean,
				newObjectName("java.lang:type=Memory"));
		if (memoryManagerMBean != null && memoryManagerMBean.size() > 0) {
			server.registerMBean(memoryManagerMBean.get(0),
					newObjectName("java.lang:type=MemoryManager"));
		}
		if (gcMBean != null && gcMBean.size() > 0) {
			server.registerMBean(gcMBean.get(0),
					newObjectName("java.lang:type=GarbageCollector"));
		}
		if (memoryPoolMBean != null && memoryPoolMBean.size() > 0) {
			server.registerMBean(memoryPoolMBean.get(0),
					newObjectName("java.lang:type=MemoryPool"));
		}
	}

	public static ObjectName newObjectName(String name) {
		try {
			return new ObjectName(name);
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		}
		return null;
	}
}

class TestNotificationListener implements NotificationListener {

	@Override
	public void handleNotification(Notification notification, Object handback) {
		System.out.println(notification.getSource());
		System.out.println(notification.getMessage());
	}

}
