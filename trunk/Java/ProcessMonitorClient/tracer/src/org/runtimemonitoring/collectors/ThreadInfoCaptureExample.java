/**
 * Code Sample for
 * Runtime Performance & Availability Monitoring for Java Systems.
 * IBM DevelperWorks.
 * Nicholas Whitehead (whitehead.nicholas@gmail.com)
 */

package org.runtimemonitoring.collectors;

import java.util.Properties;


import org.runtimemonitoring.tracing.ITracer;
import org.runtimemonitoring.tracing.TracerFactory;
import static org.runtimemonitoring.tracing.threads.ThreadInfoCapture.*;

/**
 * <p>Title: ThreadInfoCaptureExample</p>
 * <p>Description: Example to demonstrate the use of the ThreadInfoCapture trace.</p>
 * @author whitehead.nicholas@gmail.com
 * @version  $Revision$
 */

public class ThreadInfoCaptureExample {

	/**
	 * Prepares a tracer and the launches a set of tests comparing the collection time required for different ThreadInfo options.
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Starting Prime Number Cycle");
		Properties tracerConfig = new Properties();
		tracerConfig.put(TracerFactory.ITRACER_CLASS_NAME_PROPERTY, System.getProperty("runtime.tracer.class"));		
		tracerConfig.put("org.itracer.thresh.url", System.getProperty("org.itracer.thresh.url"));		
		tracerConfig.put(TracerFactory.ITRACER_KEY, "org.runtimemonitor.tracer");
		ITracer tracer = TracerFactory.getInstance(tracerConfig);
		for(int x = 0; x < 20000; x++) {
			runCycle(tracer, -1, "Time Only");
			runCycle(tracer, CPU, "CPU");
			runCycle(tracer, CPU+WAIT, "CPU+WAIT");
			runCycle(tracer, CPU+BLOCK, "CPU+BLOCK");
			runCycle(tracer, WAIT+BLOCK, "WAIT+BLOCK");
			runCycle(tracer, CPU+BLOCK+WAIT, "CPU+BLOCK+WAIT");			
			System.out.println("Cycle:" + x);
		}
	}
	
	/**
	 * Runs a test cycle, capturing a thread info snapshot, calculating a prime number and then tracing the thread info deltas. 
	 * @param tracer The ITracer.
	 * @param options The thread info options.
	 * @param name The metric name.
	 * @throws InterruptedException
	 */
	public static void runCycle(ITracer tracer, int options, String name) throws InterruptedException {
		tracer.startThreadInfoCapture(options);
		Thread.currentThread().join(100);
		for(int i = 0; i < 1000; i++) {
			tracer.startThreadInfoCapture(options);
			isPrime(i);				
			tracer.endThreadInfoCapture("AppServer3.myco.org", "Prime Number Cycle Options", "isPrime", name);
		}
		tracer.endThreadInfoCapture("AppServer3.myco.org", "Prime Number Cycle Options", name);					
	}
	
	/**
	 * Determines if the passed int is a prime
	 * @param n The int to test.
	 * @return true if it is prime.
	 */
	static boolean isPrime(int n) {
		if (n <= 2) {
			return n == 2;
		}
		if (n % 2 == 0) {
			return false;
		}
		for (int i = 3, end = (int) Math.sqrt(n); i <= end; i += 2) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}	

}
