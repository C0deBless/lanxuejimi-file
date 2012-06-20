/**
 * Code Sample for
 * Runtime Performance & Availability Monitoring for Java Systems.
 * IBM DevelperWorks.
 * Nicholas Whitehead (whitehead.nicholas@gmail.com)
 */

package org.runtimemonitoring.tracing;


/**
 * <p>Title: Trace</p>
 * <p>Description: Encapsulates a trace reading.</p>
 * @author whitehead.nicholas@gmail.com
 * @version  $Revision$
 * TODO: !! Need to make sure that sorts order lists by timesatmp. !!
 */

public class Trace implements Comparable<Trace> {
	public static final String DELIM = "/";
	protected String[] nameSpace = null;
	protected String type = null;
	protected String value = null;
	protected long timeStamp = 0L;
	protected boolean sticky = false;
	protected boolean numeric = true;
	protected boolean longType = false;
	protected boolean intType = false;
	protected boolean delta = false;
	protected String name = null;
	protected int incidentCount = 1;
	
	
	public Trace(String[] nameSpace, String type, String value) {
		this(nameSpace, type, value, 1);
	}
	
	/**
	 * @param nameSpace
	 * @param type
	 * @param value
	 */
	public Trace(String[] nameSpace, String type, String value, int incidentCount) {
		this.nameSpace = nameSpace;
		this.type = type;
		this.value = value;
		this.incidentCount = 1;
		timeStamp = System.currentTimeMillis();
		name = traceName(nameSpace, type);		
		sticky = (
				type.equalsIgnoreCase(ITracer.METRIC_TYPE_COUNTER_INT) ||
				type.equalsIgnoreCase(ITracer.METRIC_TYPE_COUNTER_LONG) ||
				type.equalsIgnoreCase(ITracer.METRIC_TYPE_DELTA_STICKY_INT) ||
				type.equalsIgnoreCase(ITracer.METRIC_TYPE_DELTA_STICKY_LONG)
		);
		numeric = (!(type.equalsIgnoreCase(ITracer.METRIC_TYPE_STRING)));
		longType = type.contains("LONG");
		intType = type.contains("INT") || type.contains("INCIDENT");	
		delta = type.contains("DELTA") || type.contains("COUNTER"); 
	}
	
	public static String traceName(String[] nameSpace, String type) {
		StringBuilder buff = new StringBuilder("[");
		buff.append(type).append("]");
		for(String s: nameSpace) {
			buff.append(s).append(DELIM);
		}
		buff.deleteCharAt(buff.length()-1);
		return buff.toString();		
	}
	
	/**
	 * Traces the trace.
	 * @param tracer 
	 */
	public void trace(ITracer tracer) {
		tracer.trace(value, type, nameSpace);
	}
	
	/**
	 * Traces the trace with the passed namespace prefix.
	 * @param tracer
	 * @param prefix
	 */
	public void trace(ITracer tracer, String...prefix) {
		String[] fullNameSpace = new String[nameSpace.length + prefix.length];
		System.arraycopy(prefix, 0, fullNameSpace, 0, prefix.length);
		System.arraycopy(nameSpace, 0, fullNameSpace, prefix.length, nameSpace.length);
		tracer.trace(value, type, fullNameSpace);
	}
	
	
	/**
	 * @return the nameSpace
	 */
	public String[] getNameSpace() {
		return nameSpace;
	}
	/**
	 * @param nameSpace the nameSpace to set
	 */
	public void setNameSpace(String[] nameSpace) {
		this.nameSpace = nameSpace;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Returns the typed value.
	 * @return the value
	 * TODO: Should this return java.lang.Number ?
	 */
	public Object getValue() {
		if(numeric) {
			if(intType) {
				if(type.contains("INCIDENT")) {
					return incidentCount;
				} else {
					return Double.valueOf(value).intValue();
				}
			} else {
				return Double.valueOf(value).longValue();
			}
		}
		return value;		
	}

	

	
	public String getFlatNameSpace() {
	    StringBuilder ns = new StringBuilder();
	    for(String s: nameSpace) {
	    	ns.append(s).append("|");	    	
	    }
	    ns.deleteCharAt(ns.length()-1);
	    return ns.toString();
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
	    StringBuilder retValue = new StringBuilder();
	    retValue.append("Trace:[").append(type).append("]").append(getFlatNameSpace()).append(":").append(value);
	    return retValue.toString();
	}

	/**
	 * Returns -1 if this trace has a lower timestamp, 1 if it has a higher timestamp
	 * than the passed in trace.
	 * @param trace
	 * @return
	 */
	public int compareTo(Trace trace) {
		if(trace==null) return -1;
		if(this.timeStamp==trace.getTimeStamp()) return 0;
		return this.timeStamp < trace.getTimeStamp() ? -1 : 1;
	}

	/**
	 * @return the timeStamp
	 */
	public long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @return the sticky
	 */
	public boolean isSticky() {
		return sticky;
	}

	/**
	 * @return the numeric
	 */
	public boolean isNumeric() {
		return numeric;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}



	/**
	 * @return
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (timeStamp ^ (timeStamp >>> 32));
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}



	/**
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Trace other = (Trace) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (timeStamp != other.timeStamp)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
