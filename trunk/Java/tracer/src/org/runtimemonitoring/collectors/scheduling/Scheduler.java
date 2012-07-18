/**
 * Code Sample for
 * Runtime Performance & Availability Monitoring for Java Systems.
 * IBM DevelperWorks.
 * Nicholas Whitehead (whitehead.nicholas@gmail.com)
 */

package org.runtimemonitoring.collectors.scheduling;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 * Title: Scheduler
 * </p>
 * <p>
 * Description: An implementation of IScheduler that uses a
 * ScheduledExecutorService so that schedule callbacks can be executed in
 * multiple threads.
 * </p>
 * 
 * @author whitehead.nicholas@gmail.com
 * @version $Revision$
 */

public class Scheduler implements SchedulerMBean, ThreadFactory {

	protected ScheduledExecutorService scheduledExecutorService = null;
	protected ThreadGroup threadGroup = null;
	protected String threadNamePrefix = null;
	protected int threadPriority = Thread.NORM_PRIORITY;
	protected AtomicLong threadSerialNumber = new AtomicLong(0);

	/**
	 * Create a new scheduler.
	 * 
	 * @param threadPoolSize
	 *            The core pool historySize.
	 * @param threadGroupName
	 *            The name of the thread group that the threads should run in.
	 * @param threadNamePrefix
	 *            The prefix of the name of the threads that will run in the
	 *            pool.
	 * @param threadPriority
	 *            The priority of the threads in the thread pool.
	 */

	public Scheduler(int threadPoolSize, String threadGroupName,
			String threadNamePrefix, int threadPriority) {
		threadGroup = new ThreadGroup(threadGroupName);
		scheduledExecutorService = Executors.newScheduledThreadPool(
				threadPoolSize, this);
		this.threadNamePrefix = threadNamePrefix;
		this.threadPriority = threadPriority;
	}

	/**
	 * Schedules a fixed delay callback on the passed collector.
	 * 
	 * @param collector
	 *            The collector to call back.
	 * @param frequency
	 *            The fixed delay frequency of the collections.
	 * @param initialDelay
	 *            The time period before the first execution in ms.
	 * @return A Future so the schedule can be cancelled.
	 */

	public Future<?> registerCollectionSchedule(Runnable collector,
			long frequency, long initialDelay) {
		Future<?> f = scheduledExecutorService.scheduleWithFixedDelay(
				collector, initialDelay, frequency, TimeUnit.MILLISECONDS);
		return f;
	}

	/**
	 * Creates new threads for the scheduled thread pool executor.
	 * 
	 * @param r
	 *            A runnable.
	 * @return A new thread.
	 */

	public Thread newThread(Runnable r) {
		long serial = threadSerialNumber.incrementAndGet();
		Thread t = new Thread(threadGroup, r, threadNamePrefix + "-" + serial);
		t.setDaemon(true);
		t.setPriority(threadPriority);
		return t;
	}

}
