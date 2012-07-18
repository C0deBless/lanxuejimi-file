/**
 * Code Sample for
 * Runtime Performance & Availability Monitoring for Java Systems.
 * IBM DevelperWorks.
 * Nicholas Whitehead (whitehead.nicholas@gmail.com)
 */

package org.runtimemonitoring.tracing.thresh;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.Map.Entry;




/**
 * <p>Title: RangeEntry</p>
 * <p>Description: Allocates a number of buckets, each one associated with a numerical range from 0 to Long.MAX_VALUE.
 * High Range (<= this) maps to --> A Range Name</p>
 * @author whitehead.nicholas@gmail.com
 * @version  $Revision$
 */

public class RangeEntry {
	protected TreeMap<Long, String> range = new TreeMap<Long, String>();
	protected String topEnd = null;
	protected long lowKey = 0L;
	protected long highKey = 0L;
	
	
	
	/**
	 * Creates a new RangeEntry.
	 * @param config The configuration for the entry in the format of <value>,<value>
	 */
	public RangeEntry(String config) {
		String[] confItems = config.split(",");
		String[] entry = null;
		topEnd = confItems[0];
		for(int i = 1; i < confItems.length; i++) {
			entry = confItems[i].split(":");
			range.put(Long.parseLong(entry[0]), entry[1]);
		}
		lowKey = range.firstKey();
		highKey = range.lastKey();
	}
	
	/**
	 * Locate the matching range for the passed value.
	 * @param value The value to locate the range for.
	 * @return The name of the range the name was matchhed to.
	 * @throws Exception Thrown if the range is not found, or an unexpected error occurs.
	 */
	public String lookupRangeName(long value)throws Exception {
		try {
			if(value < lowKey) return range.get(lowKey);
			if(value > highKey) return topEnd;
			
			Iterator<Long> sub = range.subMap(range.firstKey(), value+1).keySet().iterator();
			long locatedKey = -1;
			while(sub.hasNext()) {
				long key = sub.next();
				if(key <= value) locatedKey=key;
			}
			if(locatedKey==-1) throw new Exception("Range Not Found");
			else return range.get(locatedKey);
		} catch (Exception e) {
			log("Error Finding Range for Value:" + value);
			throw e;
		}
		
	}
	
	/**
	 * A command line test.
	 * @param args
	 */
	public static void main(String[] args) {
		log("Range Example");
		RangeEntry re = new RangeEntry("Above 100,10:0 to 10,20:11 to 30,50:31 to 50,80:51 to 80,100:81 to 100");
		Random random = new Random(System.nanoTime());
		long totalTime = 0L;
		int calls = 0;
		Map<String, Integer> results = new HashMap<String, Integer>(10);
		for(String s: re.range.values()) {
			results.put(s, 0);
		}
		results.put(re.topEnd, 0);
		try {
			for(int i = 0; i < 100000; i++ ) {
				try {
					int low = random.nextInt(150);
					int high = random.nextInt(150);
					String highName = null;
					String lowName = null;
					long start = System.nanoTime();
					highName = re.lookupRangeName(high);
					lowName = re.lookupRangeName(low);
					long elapsed = System.nanoTime()-start;
					totalTime+=elapsed;
					calls+=2;
					results.put(highName, new Integer(results.get(highName)+1));
					results.put(lowName, new Integer(results.get(lowName)+1));
//					log("High [" + high + "]:" + highName);
//					log("Low [" + low + "]:" + lowName);
					random.setSeed(System.nanoTime());
				} catch (Exception e2) {}
			}
				
			float avg = totalTime/calls;
			log("Average Elapsed Time:" + avg + " ns. for " + calls + " invocations.");
			log("Results:");
			for(Entry<String, Integer> e: results.entrySet()) {
				log("\t" + e.getKey() + ":\t" + e.getValue());
			}
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	/**
	 * Low maintenance logger.
	 * @param message
	 */
	public static void log(Object message) {
		System.out.println(message);
	}
}
