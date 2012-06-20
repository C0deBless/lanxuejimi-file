package simplegs.test;

import java.rmi.RemoteException;
import java.util.Properties;

import javax.management.MBeanServerConnection;

import org.runtimemonitoring.collectors.jmx.JMXCollector;
import org.runtimemonitoring.tracing.TracerFactory;

public class JMXClient2 {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws RemoteException,
			InterruptedException {
		String jmxServerName = "jmxconnector";
		String serviceUrl = "service:jmx:rmi:///jndi/rmi://127.0.0.1:1090/"
				+ jmxServerName;
		// LocateRegistry.createRegistry(1090);
		Properties p = new Properties();
		p.put(TracerFactory.ITRACER_CLASS_NAME_PROPERTY,
				"org.runtimemonitoring.tracing.JMXTracer");
		p.put(TracerFactory.ITRACER_KEY, "JMXTracer");
		System.out.println("JMXServiceURL: " + serviceUrl);
		TracerFactory.getInstance(p);
		JMXCollector collector = new JMXCollector("JMXTracer", serviceUrl);
		MBeanServerConnection connection = collector.getMBeanServerConnection();
		Thread.currentThread().join();
	}
}
