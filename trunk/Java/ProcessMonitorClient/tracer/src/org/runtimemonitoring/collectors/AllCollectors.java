/**
 * Code Sample for
 * Runtime Performance & Availability Monitoring for Java Systems.
 * IBM DevelperWorks.
 * Nicholas Whitehead (whitehead.nicholas@gmail.com)
 */

package org.runtimemonitoring.collectors;


/**
 * <p>Title: AllCollectors</p>
 * <p>Description: Loads, schedules and runs a group of collectors.</p>
 * <p>Expects a system property <code>runtime.tracer.class</code> where the value is the concrete implementation
 * of the <code>ITracer</code> interface.</p>
 * <p>To enable threshold support, requires a system property <code>org.itracer.thresh.url</code> where the value
 * is a URL reference to an XML file containing the thresholds.</p>
 * @author whitehead.nicholas@gmail.com
 * @version  $Revision$
 */


public class AllCollectors {

	/**
	 * @param args
	 */
	// public static void main(String[] args) {
	// String serviceURL =
	// "service:jmx:rmi://127.0.0.1/jndi/rmi://127.0.0.1:1090/jmxconnector";
	// Properties tracerConfig = new Properties();
	// tracerConfig.put(TracerFactory.ITRACER_CLASS_NAME_PROPERTY,
	// System.getProperty("runtime.tracer.class"));
	// tracerConfig.put("org.itracer.thresh.url",
	// System.getProperty("org.itracer.thresh.url"));
	// tracerConfig.put(TracerFactory.ITRACER_KEY, "org.runtimemonitor.tracer");
	// TracerFactory.getInstance(tracerConfig);
	// DataSourceCollector dscollector = new
	// DataSourceCollector("org.runtimemonitor.tracer", serviceURL);
	// JMXCollector jmxcollector = new JMXCollector("org.runtimemonitor.tracer",
	// serviceURL);
	// try {
	// Thread.currentThread().join();
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// }

}
