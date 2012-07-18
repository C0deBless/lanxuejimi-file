/**
 * Code Sample for
 * Runtime Performance & Availability Monitoring for Java Systems.
 * IBM DevelperWorks.
 * Nicholas Whitehead (whitehead.nicholas@gmail.com)
 */

package org.runtimemonitoring.collectors.scheduling;

import java.util.concurrent.Future;

/**
 * <p>
 * Title: IScheduler
 * </p>
 * <p>
 * Description: Defines a polling collector scheduler.
 * </p>
 * 
 * @author whitehead.nicholas@gmail.com
 * @version $Revision$
 */

public interface SchedulerMBean {
	/**
	 * Schedules a collection callback on a collector.
	 * 
	 * @param collector
	 *            The instance of the collector to schedule for.
	 * @param frequency
	 *            The frequency of collections in ms.
	 * @param initialDelay
	 *            The time period before the first execution in ms.
	 * @return A Future that can be used to cancel the schedule.
	 */
	public Future<?> registerCollectionSchedule(Runnable collector,
			long frequency, long initialDelay);
}
