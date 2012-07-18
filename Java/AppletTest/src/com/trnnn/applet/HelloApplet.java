package com.trnnn.applet;

import java.applet.Applet;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;

import org.runtimemonitoring.collectors.jmx.JMXCollector;
import org.runtimemonitoring.tracing.TracerFactory;

public class HelloApplet extends Applet {

	private static final long serialVersionUID = 1224360515438731409L;
	int connectorServerPort = 1090;

	public void paint(Graphics g) {
		g.drawRect(0, 0, 499, 149);
		g.drawString("Hello World", 5, 70);
	}

	public int add(int a, int b) {
		System.out.println(a + b);
		return a + b;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		super.stop();
		System.exit(0);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		System.exit(0);
	}

	@SuppressWarnings("unused")
	public void startJMX(String pwd) {
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
		Map<String, Object> env = new HashMap<>();
		env.put(JMXConnector.CREDENTIALS, pwd);
		JMXCollector collector = new JMXCollector("JMXTracer", serviceUrl, env);
		MBeanServerConnection connection = collector.getMBeanServerConnection();
	}
}
