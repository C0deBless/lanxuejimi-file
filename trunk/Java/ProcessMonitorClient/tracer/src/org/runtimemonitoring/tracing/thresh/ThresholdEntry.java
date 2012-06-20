/**
 * Code Sample for
 * Runtime Performance & Availability Monitoring for Java Systems.
 * IBM DevelperWorks.
 * Nicholas Whitehead (whitehead.nicholas@gmail.com)
 */

package org.runtimemonitoring.tracing.thresh;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * <p>Title: ThresholdEntry</p>
 * <p>Description: A container class for a Threshold declaration.</p>
 * @author whitehead.nicholas@gmail.com
 * @version  $Revision$
 */

public class ThresholdEntry {
	protected long okValue = 0L;
	protected long warnValue = 0L;
	protected String metricIdentRegexStr = null;
	protected Pattern metricIdentRegex = null;
	
	public static final int OK = 0;
	public static final int WARN = 1;
	public static final int CRITICAL = 2;
	
	protected static Map<Integer, String> decode = new HashMap<Integer, String>(3);
	
	static {
		decode.put(0, "Ok");
		decode.put(1, "Warn");
		decode.put(2, "Critical");		
	}
	
	public static String decode(int code) {
		if(!decode.containsKey(code)) {
			return "Unknown";
		} else {
			return decode.get(code);
		}
	}
	
	/**
	 * Create a new ThresholdEntry
	 * @param metricIdentRegexStr The regular expression to identify a Threshold tagged metric name.
	 * @param okValue Measurements equal or less than this value are considered Ok.
	 * @param warnValue Measurements greater than ok but equal or less than this value are considered Warn.
	 */
	public ThresholdEntry(String metricIdentRegexStr, long okValue, long warnValue) {
		super();
		this.okValue = okValue;
		this.warnValue = warnValue;
		this.metricIdentRegexStr = metricIdentRegexStr;
		this.metricIdentRegex = Pattern.compile(this.metricIdentRegexStr);
	}
	
	/**
	 * Categorizes the passed value into one of three ranges, OK, WARN or CRITICAL.
	 * @param value The value to categorize.
	 * @return A constant representing OK, WARN or CRITICAL.
	 */
	public int categorize(long value) {
		if(value <= okValue) return OK;
		if(value <= warnValue) return WARN;
		return CRITICAL;
	}

	/**
	 * @return the okValue
	 */
	public long getOkValue() {
		return okValue;
	}
	/**
	 * @return the warnValue
	 */
	public long getWarnValue() {
		return warnValue;
	}
	/**
	 * @return the metricIdentRegexStr
	 */
	public String getMetricIdentRegexStr() {
		return metricIdentRegexStr;
	}
	/**
	 * @return the metricIdentRegex
	 */
	public Pattern getMetricIdentRegex() {
		return metricIdentRegex;
	}
	/**
	 * @return The hashCode of the object.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((metricIdentRegexStr == null) ? 0 : metricIdentRegexStr
						.hashCode());
		return result;
	}
	/**
	 * @param obj
	 * @return true if equals, false if not equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ThresholdEntry other = (ThresholdEntry) obj;
		if (metricIdentRegexStr == null) {
			if (other.metricIdentRegexStr != null)
				return false;
		} else if (!metricIdentRegexStr.equals(other.metricIdentRegexStr))
			return false;
		return true;
	}
	/**
	 * Constructs a <code>String</code> with all attributes
	 * in name = value format.
	 *
	 * @return a <code>String</code> representation 
	 * of this object.
	 */
	public String toString()
	{
	    final String TAB = "    ";
	
	    StringBuilder retValue = new StringBuilder();
	    
	    retValue.append("ThresholdEntry ( ")
	        .append(super.toString()).append(TAB)
	        .append("okValue = ").append(this.okValue).append(TAB)
	        .append("warnValue = ").append(this.warnValue).append(TAB)
	        .append("metricIdentRegexStr = ").append(this.metricIdentRegexStr).append(TAB)
	        .append(" )");
	    
	    return retValue.toString();
	}
	
}
