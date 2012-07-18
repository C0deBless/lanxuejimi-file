/**
 * Code Sample for
 * Runtime Performance & Availability Monitoring for Java Systems.
 * IBM DevelperWorks.
 * Nicholas Whitehead (whitehead.nicholas@gmail.com)
 */

package org.runtimemonitoring.tracing;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Title: TracingHelper</p>
 * <p>Description: Some static helper methods for Tracing</p>
 * @author whitehead.nicholas@gmail.com
 * @version  $Revision$
 */

public class TracingHelper {
	
	public static ThreadLocal<StringBuilder> fastBuffer = new ThreadLocal<StringBuilder>();
	protected static Map<Integer, SoftReference<String[]>> traceEntryCache = new ConcurrentHashMap<Integer, SoftReference<String[]>>();
	protected static ITracer tracer = null;
	
	static {
		tracer = TracerFactory.getInstance();
	}

	/**
	 * Converts an array of <code>String<code>s and <code>String[]</code>s to a single array.
	 * @param segments
	 * @return
	 */
	public static String[] flatten(String[] prefix, String...segments) {
		long startTime = System.currentTimeMillis();
		int key = flattenHashCode(prefix, segments);
		SoftReference<String[]> sr = traceEntryCache.get(key);
		String[] entry = null;
		if(sr != null && (entry=sr.get()) != null) {
			tracer.traceIncident("ITracing", "Trace Entry Cache", "Hit", "Count");
			tracer.trace(System.currentTimeMillis()-startTime, "JDBC", "Trace Entry Cache", "Hit", "Elapsed Time");
			return entry;
		} else {
			tracer.traceIncident("ITracing", "Trace Entry Cache", "Miss", "Count");
			List<String> elements = new ArrayList<String>(prefix.length + segments.length);
			for(String seg: prefix) {
				elements.add(seg);
			}
			for(String seg: segments) {
				elements.add(seg);
			}
			String[] result = elements.toArray(new String[elements.size()]);
			traceEntryCache.put(key, new SoftReference<String[]>(result));
			tracer.trace(System.currentTimeMillis()-startTime, "ITracing", "Trace Entry Cache", "Miss", "Elapsed Time");
			return result;
		}		
	}
	
	/**
	 * Generates a hash code from a submited metric name set.
	 * @param prefix
	 * @param segments
	 * @return
	 */
	protected static int flattenHashCode(String[] prefix, String...segments) {
		StringBuilder buff = fastBuffer.get();
		if(buff==null) {
			buff = new StringBuilder();
			fastBuffer.set(buff);
		}
		buff.setLength(0);
		for(String s: prefix) {
			buff.append(s.hashCode());
		}
		for(String s: segments) {
			buff.append(s.hashCode());
		}
		return buff.toString().hashCode();
		
	}

}
