/**
 * Code Sample for
 * Runtime Performance & Availability Monitoring for Java Systems.
 * IBM DevelperWorks.
 * Nicholas Whitehead (whitehead.nicholas@gmail.com)
 */

package org.runtimemonitoring.tracing;

import java.util.Date;


import org.runtimemonitoring.tracing.threads.ThreadInfoCapture;

/**
 * <p>Title: NullTracer</p>
 * <p>Description: A default tracer that does nothing.</p>
 * @author whitehead.nicholas@gmail.com
 * @version  $Revision$
 */

public class NullTracer implements ITracer {

	/**
	 * @param name
	 */
	public ThreadInfoCapture endThreadInfoCapture(String... name) {
		StringBuilder buff = new StringBuilder("ThreadInfoCapture:");
		for(String s: name) {
			buff.append(s).append("|");
		}
		buff.deleteCharAt(buff.length()-1);
		System.out.println(buff.toString());
		return ThreadInfoCapture.emptyTic();

	}

	/**
	 * @param rangeName
	 * @param value
	 * @return The located range.
	 */
	public String lookupRange(String rangeName, long value) {
		
		return "";
	}

	/**
	 * @param options
	 */
	public void startThreadInfoCapture(int options) {
		

	}
	
	/**
	 * Starts a ThreadInfo data capture snapshot.
	 * @param options Mask of options. ints are CPU, WAIT and BLOCK. eg. start(CPU+WAIT)
	 * @param nanoTime If true, elapsed time will be captured in nanos. If false, ms.
	 */
	public void startThreadInfoCapture(int options, boolean nanoTime) {
		
	}
	

	/**
	 * @param value
	 * @param name
	 */
	public void trace(int value, String... name) {
		

	}

	/**
	 * @param value
	 * @param name
	 */
	public void trace(long value, String... name) {
		StringBuilder buff = new StringBuilder("Trace Long:");
		for(String s: name) {
			buff.append(s).append("|");
		}
		buff.deleteCharAt(buff.length()-1).append(":").append(value);
		System.out.println(buff.toString());
	}

	/**
	 * @param value
	 * @param name
	 */
	public void trace(String value, String... name) {
		

	}

	/**
	 * @param value
	 * @param name
	 */
	public void trace(Date value, String... name) {
		

	}

	/**
	 * @param value
	 * @param type
	 * @param name
	 */
	public void trace(String value, String type, String... name) {
		

	}

	/**
	 * @param value
	 * @param name
	 */
	public void traceDelta(int value, String... name) {
		

	}

	/**
	 * @param value
	 * @param name
	 */
	public void traceDelta(long value, String... name) {
		

	}

	/**
	 * @param value
	 * @param name
	 */
	public void traceDeltaSticky(int value, String... name) {
		

	}

	/**
	 * @param value
	 * @param name
	 */
	public void traceDeltaSticky(long value, String... name) {
		

	}

	/**
	 * @param name
	 */
	public void traceIncident(String... name) {
		StringBuilder buff = new StringBuilder("Incident:");
		for(String s: name) {
			buff.append(s).append("|");
		}
		buff.deleteCharAt(buff.length()-1);
		System.out.println(buff.toString());
		

	}

	/**
	 * @param value
	 * @param name
	 */
	public void traceIncident(int value, String... name) {
		

	}

	/**
	 * @param value
	 * @param name
	 */
	public void traceSticky(int value, String... name) {
		

	}

	/**
	 * @param value
	 * @param name
	 */
	public void traceSticky(long value, String... name) {
		

	}
	
	public long sizeOf(Object obj) {
		return 0;
	}

}
