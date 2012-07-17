package com.trnnn.osgi.classloader;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import testplugin1.CalculateService;

import com.trnnn.osgi.listener.FrameworkConfigListener;

public class FrameworkServiceDelegate {

	Map<ServiceCacheKey, ServiceDelegate> classCache = Collections
			.synchronizedMap(new HashMap<ServiceCacheKey, ServiceDelegate>());

	public Object invoke(Class<?> clazz, String methodName, Object... objects) {

		return null;
	}

	public Object invoke(Object obj, String methodName, Object... objects) {

		return null;
	}
	
	public Object invokeService(Class<?> clazz){
		BundleContext context=FrameworkConfigListener.equinox.getBundleContext();
		ServiceReference<?> sr=context.getServiceReference(clazz);
		if(sr!=null)
		{
			CalculateService cs=(CalculateService)context.getService(sr);
			int c=cs.add(2, 3);
			System.out.println("----------"+c);
		}
		return null;
	}
}

class ServiceCacheKey {
	public ServiceCacheKey(String methodName, Class<?> clazz, Object obj) {
		super();
		this.methodName = methodName;
		this.clazz = clazz;
		this.obj = obj;
	}

	private String methodName;
	private Class<?> clazz;
	private Object obj;

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ServiceCacheKey)) {
			return false;
		}

		ServiceCacheKey key = (ServiceCacheKey) object;
		if (key.getObj() != this.obj)
			return false;
		if (key.getClazz() != this.clazz)
			return false;
		if (!key.getMethodName().equals(key.getMethodName()))
			return false;
		return true;
	}

	public String getMethodName() {
		return methodName;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public Object getObj() {
		return obj;
	}
}

class ServiceDelegate {

}