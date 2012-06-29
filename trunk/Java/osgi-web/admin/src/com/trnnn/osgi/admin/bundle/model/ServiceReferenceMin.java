package com.trnnn.osgi.admin.bundle.model;

import java.util.Map;

public class ServiceReferenceMin {
	private String[] usingBundles;
	private Map<String,Object> properties;
	public void setProperties(Map<String,Object> properties) {
		this.properties = properties;
	}
	public Map<String,Object> getProperties() {
		return properties;
	}
	public void setUsingBundles(String[] usingBundles) {
		this.usingBundles = usingBundles;
	}
	public String[] getUsingBundles() {
		return usingBundles;
	}
}
