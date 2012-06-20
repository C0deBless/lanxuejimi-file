package simplegs.test;

import java.io.IOException;
import java.util.Properties;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.runtimemonitoring.collectors.jmx.JMXCollector;
import org.runtimemonitoring.tracing.TracerFactory;

public class JMXClient3 {

	public static void main(String[] args) throws InterruptedException,
			InstanceNotFoundException, MalformedObjectNameException,
			MBeanException, ReflectionException, IOException {
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
		Object result = connection.invoke(new ObjectName(
				"java.lang:type=CommandMBean"), "user", new Object[] { "all" },
				new String[] {});
		System.out.println(result);
		Thread.currentThread().join();
	}
}
