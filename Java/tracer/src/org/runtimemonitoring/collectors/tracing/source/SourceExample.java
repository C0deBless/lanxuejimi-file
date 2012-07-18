/**
 * Code Sample for
 * Runtime Performance & Availability Monitoring for Java Systems.
 * IBM DevelperWorks.
 * Nicholas Whitehead (whitehead.nicholas@gmail.com)
 */

package org.runtimemonitoring.collectors.tracing.source;

import static org.runtimemonitoring.tracing.threads.ThreadInfoCapture.BLOCK;
import static org.runtimemonitoring.tracing.threads.ThreadInfoCapture.CPU;
import static org.runtimemonitoring.tracing.threads.ThreadInfoCapture.WAIT;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.runtimemonitoring.tracing.ITracer;
import org.runtimemonitoring.tracing.TracerFactory;

/**
 * <p>
 * Title: SourceExample
 * </p>
 * <p>
 * Description: A source based instrumentation example.
 * </p>
 * 
 * @author whitehead.nicholas@gmail.com
 * @version $Revision$
 */

public class SourceExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ITracer tracer = TracerFactory.getInstance();
		AtomicInteger concurrency = new AtomicInteger(0);
		for (int i = 0; i < 5; i++) {
			Worker worker = new Worker(1000000, 100000, concurrency, tracer);
			worker.start();
			Worker2 worker2 = new Worker2(1000000, 100000, concurrency, tracer);
			worker2.start();
		}
	}
}

/**
 * <p>
 * Title: Worker
 * </p>
 * <p>
 * Description: Contrived Worker Thread
 * </p>
 * 
 * @author whitehead.nicholas@gmail.com
 * @version $Revision$
 */
class Worker extends Thread {
	protected int factor = 0;
	protected int loops = 0;
	protected ITracer tracer = null;
	protected AtomicInteger concurrency = null;
	protected Random random = new Random(System.nanoTime());

	/**
	 * @param factor
	 * @param tracer
	 */
	public Worker(int factor, int loops, AtomicInteger concurrency,
			ITracer tracer) {
		super();
		this.factor = factor;
		this.loops = loops;
		this.tracer = tracer;
		this.concurrency = concurrency;
	}

	public void run() {
		System.out.println("Starting Loop With Factor:" + factor);
		for (int x = 0; x < loops; x++) {
			tracer.startThreadInfoCapture(CPU + BLOCK + WAIT);
			int c = concurrency.incrementAndGet();
			tracer.trace(c, "Source Instrumentation",
					"heavillyInstrumentedMethod", "Concurrent Invocations");
			try {
				// ===================================
				// Here is the method
				// ===================================
				heavillyInstrumentedMethod(factor);
				// ===================================
				tracer.traceIncident("Source Instrumentation",
						"heavillyInstrumentedMethod", "Responses");
			} catch (Exception e) {
				tracer.traceIncident("Source Instrumentation",
						"heavillyInstrumentedMethod", "Exceptions");
			} finally {
				tracer.endThreadInfoCapture("Source Instrumentation",
						"heavillyInstrumentedMethod");
				c = concurrency.decrementAndGet();
				tracer.trace(c, "Source Instrumentation",
						"heavillyInstrumentedMethod", "Concurrent Invocations");
				tracer.traceIncident("Source Instrumentation",
						"heavillyInstrumentedMethod", "Invocations");
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
		}
	}

	public long heavillyInstrumentedMethod(int cfactor) {
		long fact = 1;
		for (int i = 1; i < cfactor; i++) {
			if (i % 2 == 0)
				fact = fact + i;
			else
				fact = fact - i;
		}
		try {
			Thread.currentThread().join(100);
		} catch (InterruptedException e) {
		}
		if (random.nextInt(10) < 4) {
			throw new RuntimeException();
		} else {
			return fact;
		}
	}

}

/**
 * <p>
 * Title: Worker2
 * </p>
 * <p>
 * Description: Contrived worker thread
 * </p>
 * 
 * @author whitehead.nicholas@gmail.com
 * @version $Revision$
 */
class Worker2 extends Thread {
	protected int factor = 0;
	protected int loops = 0;
	protected ITracer tracer = null;
	protected AtomicInteger concurrency = null;
	protected Random random = new Random(System.nanoTime());

	/**
	 * @param factor
	 * @param tracer
	 */
	public Worker2(int factor, int loops, AtomicInteger concurrency,
			ITracer tracer) {
		super();
		this.factor = factor;
		this.loops = loops;
		this.tracer = tracer;
		this.concurrency = concurrency;
	}

	public void run() {
		System.out.println("Starting Loop With Factor:" + factor);
		for (int x = 0; x < loops; x++) {
			long start = System.currentTimeMillis();
			try {
				// ===================================
				// Here is the method
				// ===================================
				lightlyInstrumentedMethod(factor);
				long elapsed = System.currentTimeMillis() - start;
				tracer.trace(elapsed, "Source Instrumentation",
						"lightlyInstrumentedMethod", "Elapsed Time (ms)");
				// ===================================
			} catch (Exception e) {
			}
			// try { Thread.sleep(200); } catch (InterruptedException e) { }
		}
	}

	public long lightlyInstrumentedMethod(int cfactor) {
		long fact = 1;
		for (int i = 1; i < cfactor; i++) {
			if (i % 2 == 0)
				fact = fact + i;
			else
				fact = fact - i;
		}
		try {
			Thread.currentThread().join(100);
		} catch (InterruptedException e) {
		}
		if (random.nextInt(10) < 4) {
			throw new RuntimeException();
		} else {
			return fact;
		}
	}

}
