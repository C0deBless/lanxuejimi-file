/**
 * Code Sample for
 * Runtime Performance & Availability Monitoring for Java Systems.
 * IBM DevelperWorks.
 * Nicholas Whitehead (whitehead.nicholas@gmail.com)
 */

package org.runtimemonitoring.collectors;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.management.MBeanServer;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import org.runtimemonitoring.collectors.scheduling.Scheduler;
import org.runtimemonitoring.collectors.scheduling.SchedulerMBean;

/**
 * <p>
 * Title: BaseCollector
 * </p>
 * <p>
 * Description: Static Base Collector Utilities and base class for collector
 * implementations.
 * </p>
 * 
 * @author whitehead.nicholas@gmail.com
 * @version $Revision$
 */

public class BaseCollector implements Runnable {
	public static String LOG_DATE_FORMAT = "HH:mm:ss.SS";
	public static String SCHEDULER_OBJECT_NAME = "org.runtimemonitoring:service=Scheduler";
	public static SimpleDateFormat dateFormat = new SimpleDateFormat(
			LOG_DATE_FORMAT);
	protected static SchedulerMBean scheduler = null;
	protected static ObjectInstance schedulerObjectInstance = null;

	protected Integer notification = null;

	public static void log(Object... args) {
		StringBuilder buff = new StringBuilder("[");
		buff.append(dateFormat.format(new Date())).append("] ");
		for (Object o : args) {
			buff.append(o);
		}
		System.out.println(buff.toString());
	}

	/**
	 * The entry point for the scheduler call back.
	 */
	public void run() {
		collect();
	}

	/**
	 * Executes the collector's collection procedure.
	 */
	public void collect() {
		// Base class collect method is a No-Op.
	}

	/** Loads the collection scheduler */
	static {
		try {
			log("Registering Collection Scheduler");
			ObjectName timeON = new ObjectName(SCHEDULER_OBJECT_NAME);
			MBeanServer server = ManagementFactory.getPlatformMBeanServer();
			Scheduler sched = new Scheduler(10,
					"Runtime Monitor Collector Thread Group",
					"Collector Thread", Thread.NORM_PRIORITY);
			server.registerMBean(sched, timeON);
			scheduler = (SchedulerMBean) MBeanServerInvocationHandler
					.newProxyInstance(server, timeON, SchedulerMBean.class,
							false);
			log("Collection Scheduler Registered at:", timeON.toString());

		} catch (Throwable t) {
			log("Failure to Initialize Scheduler", t);
			t.printStackTrace();
		}
	}

}
