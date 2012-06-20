package simplegs.test;

import java.io.IOException;

import javax.management.AttributeChangeNotification;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JMXClient1 {

	public static int connectorServerPort = 1090;

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException,
			InterruptedException, InstanceNotFoundException, MBeanException,
			ReflectionException, MalformedObjectNameException,
			NullPointerException, IntrospectionException {
		// LocateRegistry.createRegistry(connectorServerPort);

		JMXServiceURL url = new JMXServiceURL("rmi", null, connectorServerPort,
				"/jndi/rmi://localhost:" + connectorServerPort + "/TestServer1");

		System.out.println(url.toString());

		JMXConnector connector = JMXConnectorFactory.connect(url);

		MBeanServerConnection connection = connector.getMBeanServerConnection();
		Thread.currentThread().join();
	}

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
