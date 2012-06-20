package simplegs.jmx.helper;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

public class ObjectNameHelper {
	public static ObjectName newObjectName(String name) {
		try {
			return new ObjectName(name);
		} catch (MalformedObjectNameException e) {
			// e.printStackTrace();
		}
		return null;
	}
}
