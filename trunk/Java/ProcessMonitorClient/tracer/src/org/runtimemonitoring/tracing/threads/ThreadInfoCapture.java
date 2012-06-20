/**
 * Code Sample for
 * Runtime Performance & Availability Monitoring for Java Systems.
 * IBM DevelperWorks.
 * Nicholas Whitehead (whitehead.nicholas@gmail.com)
 */

package org.runtimemonitoring.tracing.threads;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Stack;

import org.runtimemonitoring.tracing.ITracer;
import org.runtimemonitoring.tracing.TracingHelper;

/**
 * <p>Title: ThreadInfoCapture</p>
 * <p>Description: Utility class and value container for capturing the delta
 * in ThreadInfo stats between two periods.</p>
 * @author whitehead.nicholas@gmail.com
 * @version  $Revision$
 */

public class ThreadInfoCapture {
	
	public static final int CPU      = 1 << 0;  
	public static final int WAIT      = 1 << 1; 
	public static final int BLOCK    = 1 << 2;  

	
	protected static boolean threadContentionMonitoringEnabled = false;
	protected static boolean threadCPUTimeEnabled = false;
	protected static ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
	
	protected int metricOption = CPU+WAIT+BLOCK;
	protected long elapsedTime = 0;
	protected long totalCpuTime = -1;
	protected long blockedCount = -1;
	protected long blockedTime = -1;
	protected long waitCount = -1;
	protected long waitTime = -1;
	protected boolean nanoTime = false;
	
	protected static ThreadLocal<Stack<ThreadInfoCapture>> threadInfoLocal = new ThreadLocal<Stack<ThreadInfoCapture>>() {
		protected synchronized Stack<ThreadInfoCapture> initialValue() {
            return new Stack<ThreadInfoCapture>();
        }
	};
	
	protected ThreadInfoCapture() {}
	
	static {
		if(threadMXBean.isCurrentThreadCpuTimeSupported()) {
			threadMXBean.setThreadCpuTimeEnabled(true);
			threadCPUTimeEnabled = true;
		}
		if(threadMXBean.isThreadContentionMonitoringSupported()) {
			threadMXBean.setThreadContentionMonitoringEnabled(true);
			threadContentionMonitoringEnabled = true;
		}
	}
	
	/**
	 * Starts a ThreadInfo snapshot.
	 * By default captures all enabled metrics and ms. elapsed time.
	 */
	public static void start() {		
		start(false);
	}
	
	/**
	 * Starts a ThreadInfo snapshot.
	 * By default captures all enabled metrics.
	 * @param nanoTime If true, elapsed time will be captured in nanos. If false, ms.
	 */
	public static void start(boolean nanoTime) {		
		threadInfoLocal.get().push(new ThreadInfoCapture(CPU+WAIT+BLOCK));
	}
	
	public static ThreadInfoCapture emptyTic() {
		return new ThreadInfoCapture();
	}
	
	
	/**
	 * Starts a ThreadInfo snapshot.
	 * @param options Mask of options. ints are CPU, WAIT and BLOCK.
	 * eg. start(CPU+WAIT) 
	 */
	public static void start(int options) {
		start(options, false);
	}
	
	/**
	 * Starts a ThreadInfo snapshot.
	 * @param options Mask of options. ints are CPU, WAIT and BLOCK. eg. start(CPU+WAIT) 
	 * @param nanoTime If true, elapsed time will be captured in nanos. If false, ms.
	 */
	public static void start(int options, boolean nanoTime) {
		threadInfoLocal.get().push(new ThreadInfoCapture(options, nanoTime));
	}
	
	
	
	/**
	 * Ends a ThreadInfo snapshot and returns a ThreadInfoCapture containing the delta values.
	 * @return A ThreadInfoCapture or null if the starting snapshot was not found.
	 */
	public static ThreadInfoCapture end() {
		if(threadInfoLocal.get().isEmpty()) return null;
		ThreadInfoCapture snapshot = threadInfoLocal.get().pop();
		int metricOption = snapshot.metricOption;
		ThreadInfoCapture tic = new ThreadInfoCapture(metricOption);				
		tic.elapsedTime = (snapshot.nanoTime ? System.nanoTime() : System.currentTimeMillis()) - snapshot.elapsedTime;
		ThreadInfo threadInfo = threadMXBean.getThreadInfo(Thread.currentThread().getId());		
		if(threadCPUTimeEnabled && ((metricOption & CPU) == CPU)) {
			tic.totalCpuTime = threadMXBean.getCurrentThreadCpuTime()-snapshot.totalCpuTime;
		}
		if(threadContentionMonitoringEnabled) {
			if(((metricOption & BLOCK) == BLOCK)) {
				tic.blockedCount = threadInfo.getBlockedCount()-snapshot.blockedCount;
				tic.blockedTime = threadInfo.getBlockedTime()-snapshot.blockedTime;
			}
			if(((metricOption & WAIT) == WAIT)) {
				tic.waitCount = threadInfo.getWaitedCount()-snapshot.waitCount;
				tic.waitTime = threadInfo.getWaitedTime()-snapshot.waitTime;
			}
		}				
		return tic;
	}
	
	/**
	 * Traces the current values.
	 * Used when a ThreadInfoCapture needs to be traced to two name spaces. 
	 * @param name
	 * @param tracer
	 */
	public void trace(String[] name, ITracer tracer) {
		if(tracer==null) return;
		if(metricOption<0) return;
		if(((metricOption & CPU) == CPU)) {
			tracer.trace(totalCpuTime, TracingHelper.flatten(name, "Total CPU Time (ns)"));
		}
		if(((metricOption & BLOCK) == BLOCK)) {
			tracer.trace(blockedCount, TracingHelper.flatten(name, "Total Blocks"));
			tracer.trace(blockedTime, TracingHelper.flatten(name, "Total Block Time (ms)"));
		}
		if(((metricOption & WAIT) == WAIT)) {
			tracer.trace(waitCount, TracingHelper.flatten(name, "Total Waits"));
			tracer.trace(waitTime, TracingHelper.flatten(name, "Total Wait Time (ms)"));
		}			
	}
	
	/**
	 * Traces the current values but uses an incident trace for waits and blocks.
	 * Used when a ThreadInfoCapture needs to be traced to two name spaces 
	 * and waits and blocks should be aggregated for the interval. 
	 * @param name
	 * @param tracer
	 */
	public void traceIncident(String[] name, ITracer tracer) {
		if(tracer==null) return;
		if(metricOption<0) return;
		if(((metricOption & CPU) == CPU)) {
			tracer.trace(totalCpuTime, TracingHelper.flatten(name, "Total CPU Time (ns)"));
		}
		if(((metricOption & BLOCK) == BLOCK)) {
			tracer.traceIncident((int)blockedCount, TracingHelper.flatten(name, "Total Blocks"));
			tracer.trace(blockedTime, TracingHelper.flatten(name, "Total Block Time (ms)"));
		}
		if(((metricOption & WAIT) == WAIT)) {
			tracer.traceIncident((int)waitCount, TracingHelper.flatten(name, "Total Waits"));
			tracer.trace(waitTime, TracingHelper.flatten(name, "Total Wait Time (ms)"));
		}			
	}
	

	
	
	/**
	 * Calls ThreadInfoCapture end and traces the captured measurements.
	 * @param name The compound name fragments.
	 * @param tracer The tracer to trace to.
	 */
	public static ThreadInfoCapture traceEnd(String[] name, ITracer tracer) {
		if(tracer==null) return null;
		ThreadInfoCapture tic = end();
		int metricOption = tic.metricOption;
		if(tic!=null) {
			traceEnd(name, "Elapsed Time (ms)", tic.elapsedTime, tracer);
			if(metricOption<0) return tic;
			if(((metricOption & CPU) == CPU)) {
				traceEnd(name, "Total CPU Time (ns)", tic.totalCpuTime, tracer);
			}
			if(((metricOption & BLOCK) == BLOCK)) {
				traceEnd(name, "Total Blocks", tic.blockedCount, tracer);
				traceEnd(name, "Total Block Time (ms)", tic.blockedTime, tracer);
			}
			if(((metricOption & WAIT) == WAIT)) {
				traceEnd(name, "Total Waits", tic.waitCount, tracer);			
				traceEnd(name, "Total Wait Time (ms)", tic.waitTime, tracer);							
			}			
		}
		return tic;
	}
	
	/**
	 * Executes tracing on the passed measurement.
	 * @param name
	 * @param metricName
	 * @param value
	 * @param tracer the tracer to use.
	 */
	protected static void traceEnd(String[] name, String metricName, long value, ITracer tracer) {
		if(tracer==null || value==-1) return;
		String[] cname = new String[name.length +1];
		System.arraycopy(name, 0, cname, 0, name.length);
		cname[cname.length-1] = metricName;
		tracer.trace(value, cname);
	}
	
	/**
	 * Creates a new ThreadInfoCapture snapshot and populates it.
	 * @param options Mask for collection options.
	 */
	protected ThreadInfoCapture(int options) {
		this(options, false);
	}
	
	/**
	 * Creates a new ThreadInfoCapture snapshot and populates it.
	 * @param options Mask for collection options.
	 * @param nanoTime if true, elapsed time will be in nanos. If false, it will be in ms.
	 */
	protected ThreadInfoCapture(int options, boolean nanoTime) {
		this.nanoTime = nanoTime;
		ThreadInfo threadInfo = threadMXBean.getThreadInfo(Thread.currentThread().getId());		
		metricOption = options;
		
		elapsedTime = nanoTime ? System.nanoTime() : System.currentTimeMillis();
		if(metricOption<0) return;
		if(threadCPUTimeEnabled && ((metricOption & CPU) == CPU)) {
			totalCpuTime = threadMXBean.getCurrentThreadCpuTime();
		}
		if(threadContentionMonitoringEnabled) {
			if(((metricOption & BLOCK) == BLOCK)) {
				blockedCount = threadInfo.getBlockedCount();
				blockedTime = threadInfo.getBlockedTime();
			}
			if(((metricOption & WAIT) == WAIT)) {
				waitCount = threadInfo.getWaitedCount();
				waitTime = threadInfo.getWaitedTime();
			}
		}		
	}
	
	
}
