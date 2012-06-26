package com.trnnn.osgi.admin.webservice.servlet.model;

import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceRegistration;


public class WebServiceModel {

	public WebServiceModel() {

	}

	private Bundle bundle;
	private String endpointInterface;
	private String implementor;
	private List<ServiceRegistration> publishedServices;

	public void setImplementor(String implementor) {
		this.implementor = implementor;
	}

	public String getImplementor() {
		return implementor;
	}

	public void setEndpointInterface(String endpointInterface) {
		this.endpointInterface = endpointInterface;
	}

	public String getEndpointInterface() {
		return endpointInterface;
	}

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}

	public Bundle getBundle() {
		return bundle;
	}

	public void setPublishedServices(List<ServiceRegistration> publishedServices) {
		this.publishedServices = publishedServices;
	}

	public List<ServiceRegistration> getPublishedServices() {
		return publishedServices;
	}

}
