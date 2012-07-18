package org.runtimemonitoring.collectors.jmx;

import java.util.Date;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.ObjectName;

import org.runtimemonitoring.tracing.TracerFactory;

/**
 * <p>Title: DataSourceCollector</p>
 * <p>Description: A data collector that uses JMX to acquire metrics from a JBoss Managed Connection Pool MBean.</p>
 * @author whitehead.nicholas@gmail.com
 * @version  $Revision$
 */
public class DataSourceCollector extends JMXCollector {

	/**
	 * Creates a new DataSourceCollector.
	 * @param tracerKey The cache key where the ITracer instance can be retrieved from.
	 * @param serviceURL The URL to connect to the remote MBeanServer from.
	 */
	public DataSourceCollector(String tracerKey, String serviceURL) {
		try {
			tracer = TracerFactory.getInstance(tracerKey);
			hostName = "AppServer3.myco.org";
			log("Starting DataSourceCollector Example", "\n\tRemote URL", serviceURL);
			jmxServer = getRemoteMBeanServer(serviceURL,null);
			log("Acquired MBeanServerConnection:", jmxServer, "\n\tClass:", jmxServer.getClass().getName());
			objectNameCache.put("DS_OBJ_NAME", new ObjectName("jboss.jca:service=ManagedConnectionPool,name=AA4HDataSource"));
			scheduler.registerCollectionSchedule(this, 10000, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * Executes data collection against the configured MBeanServer.
	 */
	public void collect() {
		try {
			log("Starting DataSource Collection");
			long start = System.currentTimeMillis();
			ObjectName on = objectNameCache.get("DS_OBJ_NAME");
			AttributeList attributes  = jmxServer.getAttributes(on, new String[]{
					"AvailableConnectionCount", 
					"MaxConnectionsInUseCount",
					"InUseConnectionCount",
					"ConnectionCount",
					"ConnectionCreatedCount",
					"ConnectionDestroyedCount"
			});
			for(Object attr: attributes) {
				Attribute attribute = (Attribute) attr;
				if(attribute.getName().equals("ConnectionCreatedCount") || attribute.getName().equals("ConnectionDestroyedCount")) {
					tracer.traceDeltaSticky((Integer)attribute.getValue(), hostName, "DataSource", on.getKeyProperty("name"), attribute.getName());
				} else {
					if(attribute.getValue() instanceof Long) {
						tracer.traceSticky((Long)attribute.getValue(), hostName, "DataSource", on.getKeyProperty("name"), attribute.getName());
					} else {
						tracer.traceSticky((Integer)attribute.getValue(), hostName, "DataSource", on.getKeyProperty("name"), attribute.getName());
					}
				}
			}
			// Done
			long elapsed = System.currentTimeMillis()-start;
			tracer.trace(elapsed, hostName, "DataSource", "DataSource Collector", "Collection", "Last Elapsed Time");
			tracer.trace(new Date(), hostName, "DataSource", "DataSource Collector", "Collection", "Last Collection");			
			log("Completed DataSource Collection in ", elapsed, " ms.");			
		} catch (Exception e) {
			log("Failed:" + e);
			tracer.traceIncident(hostName, "DataSource", "DataSource Collector", "Collection", "Collection Errors");
		}		
	}
	

}
