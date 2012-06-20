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
 * <p>Title:ITracer</p>
 * <p>Description: Defines the tracing interface for passing collected performance 
 * data measurements to an end point APM.</p>
 * @author whitehead.nicholas@gmail.com
 * @version  $Revision: 39 $
 */

public interface ITracer {
	
	/** The parameter name constant for the metric trace type value of long/Interval Averaged */
	public static final String METRIC_TYPE_LONG = "LONG";
	/** The parameter name constant for the metric trace type value of int/Interval Averaged */
	public static final String METRIC_TYPE_INT = "INT";
	/** The parameter name constant for the metric trace type value of String*/
	public static final String METRIC_TYPE_STRING = "STRING";	
	/** The parameter name constant for the metric trace type value of long/Sticky */
	public static final String METRIC_TYPE_COUNTER_LONG = "SLONG";
	/** The parameter name constant for the metric trace type value of int/Sticky */
	public static final String METRIC_TYPE_COUNTER_INT = "SINT";
	/** The parameter name constant for the metric trace type value of Incident */
	public static final String METRIC_TYPE_INCIDENT = "INCIDENT";
	/** The parameter name constant for the metric trace type value of delta/int/Interval Averaged */
	public static final String METRIC_TYPE_DELTA_INT = "DINT";
	/** The parameter name constant for the metric trace type value of delta/long/Interval Averaged */
	public static final String METRIC_TYPE_DELTA_LONG = "DLONG";
	/** The parameter name constant for the metric trace type value of delta/int/Sticky */
	public static final String METRIC_TYPE_DELTA_STICKY_INT = "SDINT";
	/** The parameter name constant for the metric trace type value of delta/long/Sticky */
	public static final String METRIC_TYPE_DELTA_STICKY_LONG = "SDLONG";
	
	

	/**
	 * Traces an interval averaged int.
	 * @param value The value of the target metric.
	 * @param name The elements of the compund metric name.
	 */
	public void trace(int value, String...name);
	
	/**
	 * Traces an interval averaged long.
	 * @param value The value of the target metric.
	 * @param name The elements of the compund metric name.
	 */
	public void trace(long value, String...name);
	
	/**
	 * Traces a String.
	 * @param value The value of the target metric.
	 * @param name The elements of the compund metric name.
	 */
	public void trace(String value, String...name);
	
	/**
	 * Traces a timestamp.
	 * @param value The value of the target metric.
	 * @param name The elements of the compund metric name.
	 */
	public void trace(Date value, String...name);	
	
	/**
	 * Traces a sticky int.
	 * @param value The value of the target metric.
	 * @param name The elements of the compund metric name.
	 */
	public void traceSticky(int value, String...name);
	
	/**
	 * Traces a sticky long.
	 * @param value The value of the target metric.
	 * @param name The elements of the compund metric name.
	 */
	public void traceSticky(long value, String...name);

	/**
	 * Traces a delta interval averaged int.
	 * @param value The value of the target metric.
	 * @param name The elements of the compund metric name.
	 */
	public void traceDelta(int value, String...name);
	
	/**
	 * Traces a delta interval averaged long.
	 * @param value The value of the target metric.
	 * @param name The elements of the compund metric name.
	 */
	public void traceDelta(long value, String...name);
	
	/**
	 * Traces a delta sticky int.
	 * @param value The value of the target metric.
	 * @param name The elements of the compund metric name.
	 */
	public void traceDeltaSticky(int value, String...name);
	
	/**
	 * Traces a delta sticky long.
	 * @param value The value of the target metric.
	 * @param name The elements of the compund metric name.
	 */
	public void traceDeltaSticky(long value, String...name);
	
	/**
	 * Traces an interval incident.
	 * @param name The elements of the compund metric name. 
	 */
	public void traceIncident(String...name);
	
	/**
	 * Traces a a defined number of interval incidents. 
	 * @param value The number of incidents to trace.
	 * @param name The elements of the compund metric name.
	 */
	public void traceIncident(int value, String...name);
			
	/**
	 * Traces a type defined metric. 
	 * @param value The value of the target metric. The value will be converted 
	 * to the appropriate data type according to the trace type defined.
	 * @param type The type of the metric as defined in the constants.
	 * @param name The elements of the compund metric name.
	 */
	public void trace(String value, String type, String...name);	
	
	/**
	 * Starts a ThreadInfo data capture snapshot. 
	 * @param options Mask of options. ints are CPU, WAIT and BLOCK.
	 * eg. start(CPU+WAIT)  
	 */
	public void startThreadInfoCapture(int options);
	
	/**
	 * Starts a ThreadInfo data capture snapshot.
	 * @param options Mask of options. ints are CPU, WAIT and BLOCK. eg. start(CPU+WAIT)
	 * @param nanoTime If true, elapsed time will be captured in nanos. If false, ms.
	 */
	public void startThreadInfoCapture(int options, boolean nanoTime);
	
	/**
	 * a ThreadInfo data capture, calculates deltas and traces to the passed name segment.
	 * The ThreadInfo metric names are automatically supplied.
	 * @param name A compound name prefix.
	 */
	public ThreadInfoCapture endThreadInfoCapture(String...name);
	
	/**
	 * Decodes a value into a range description.
	 * @param rangeName The name of the range.
	 * @param value The value to decode.
	 * @return A range name.
	 */
	public String lookupRange(String rangeName, long value);
	
	/**
	 * Uses the loaded instrumentation instance to get the deep historySize of an object.
	 * @param obj The object to historySize.
	 * @return The deep historySize of the object.
	 */
	public long sizeOf(Object obj);
	
	
	
}
