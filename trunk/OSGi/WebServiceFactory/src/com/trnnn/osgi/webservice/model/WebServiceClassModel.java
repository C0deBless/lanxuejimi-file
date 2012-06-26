package com.trnnn.osgi.webservice.model;

import org.osgi.framework.Bundle;


public class WebServiceClassModel {

	private Bundle bundle;
	private Class<? extends Object> endpointInterface;
	private Class<? extends Object> implementor;

	public void setEndpointInterface(Class<? extends Object> endpointInterface) {
		this.endpointInterface = endpointInterface;
	}

	public Class<? extends Object> getEndpointInterface() {
		return endpointInterface;
	}

	public void setImplementor(Class<? extends Object> implementor) {
		this.implementor = implementor;
	}

	public Class<? extends Object> getImplementor() {
		return implementor;
	}


	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}

	public Bundle getBundle() {
		return bundle;
	}



}
