/**
 * Code Sample for
 * Runtime Performance & Availability Monitoring for Java Systems.
 * IBM DevelperWorks.
 * Nicholas Whitehead (whitehead.nicholas@gmail.com)
 */

package org.runtimemonitoring.tracing;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Title:TracerFactory</p> <p>Description: Factory class for instances of <code>ITracer</code>.</p>
 * @author whitehead.nicholas@gmail.com
 * @version $Revision$
 * @uml.dependency   supplier="org.runtimemonitoring.tracing.ITracer"
 */

public class TracerFactory {
	
	/** Mandatory property indicating the ITracer class name */
	public static final String ITRACER_CLASS_NAME_PROPERTY = "itracer.class.name";
	/** Optional property. If defined, will be used as the ITracer instance cache key. */
	public static final String ITRACER_KEY = "itracer.key";
	/** The ITracer cache */
	protected static Map<String, ITracer> tracers = new ConcurrentHashMap<String, ITracer>();
	
	/**
	 * Acquires the default ITracer implementation.
	 * @return An ITracer.
	 */
	public static ITracer getInstance() {
		try {
			String className = System.getProperty(ITRACER_CLASS_NAME_PROPERTY);
			if(className==null) className = NullTracer.class.getName();
			String cacheKey = "DEFAULT";
			if(cacheKey!=null) {
				// Check the cache and see if the ITracer has alreayd been created.
				if(tracers.containsKey(cacheKey)) return tracers.get(cacheKey);
			}
			// If the ITracer has not been created yet, create it.
			Class<?> itracerClass = Class.forName(className, true, ITracer.class.getClassLoader());
			Constructor<?> ctor = itracerClass.getConstructor();
			ITracer tracer = (ITracer)ctor.newInstance();
			if(cacheKey!=null) {
				// if a cache key has been provided, cache the itracer.
				tracers.put(cacheKey, tracer);
			}
			return tracer;
		} catch (Exception e) {
			e.printStackTrace();
			throw new InstantiationError("Failed to create ITracer:" + e);
		}
	}
	
	/**
	 * Constructs a new ITracer.
	 * @param p The configuration for the ITracer class.
	 * @return An ITracer instance.
	 * @throws InstantiationError
	 */
	public static ITracer getInstance(Properties p) throws InstantiationError {
		try {
			String className = p.getProperty(ITRACER_CLASS_NAME_PROPERTY);
			String cacheKey = p.getProperty(ITRACER_KEY);
			if(cacheKey!=null) {
				// Check the cache and see if the ITracer has alreayd been created.
				if(tracers.containsKey(cacheKey)) return tracers.get(cacheKey);
			}
			// If the ITracer has not been created yet, create it.
			Class<?> itracerClass = Class.forName(className, true, ITracer.class.getClassLoader());
			Constructor<?> ctor = itracerClass.getConstructor(Properties.class);
			ITracer tracer = (ITracer)ctor.newInstance(p);
			if(cacheKey!=null) {
				// if a cache key has been provided, cache the itracer.
				tracers.put(cacheKey, tracer);
			}
			return tracer;
		} catch (Exception e) {
			e.printStackTrace();
			throw new InstantiationError("Failed to create ITracer:" + e);
		}
	}
	
	/**
	 * Retrieves a cached ITracer by cache key. The Itracer must have been already cached.
	 * @param cacheKey The cache key of the ITracer.
	 * @return The cached ITracer.
	 */
	public static ITracer getInstance(String cacheKey) {
		if(tracers.containsKey(cacheKey)) return tracers.get(cacheKey);
		else throw new RuntimeException("ITracer not found in cache under cache key [" + cacheKey + "]");
	}
}
