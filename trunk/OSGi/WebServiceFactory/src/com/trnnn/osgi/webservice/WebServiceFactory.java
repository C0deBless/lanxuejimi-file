package com.trnnn.osgi.webservice;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

public class WebServiceFactory {
	
	public static final String SERVICE_EXPORTED_INTERFACES="service.exported.interfaces";
	
	public static final String SERVICE_EXPORTED_INTENTS="service.exported.intents";
	
	public static final String SERVICE_EXPORTED_CONFIGS="service.exported.configs";
	
	public static final String ORG_APACHE_CXF_WS_ADDRESS="org.apache.cxf.ws.address";
	
	public static final String SERVICE_EXPORTED_CLASSNAME="service.exported.classname";
	
	public static final String SOAP="SOAP";
	
	public static final String ORG_APACHE_CXF_WS="org.apache.cxf.ws";
	
	public static final String SERVICE_ID="service.id";

	public static void publishService(String address,Class<? extends Object> interfac,Class<? extends Object> implementor) {

		String interfacName=interfac.getName();
		String implementorName=implementor.getName();
		
		Dictionary<String,String> props=new Hashtable<String,String>();
		props.put(SERVICE_EXPORTED_INTERFACES,"*");
		props.put(SERVICE_EXPORTED_INTENTS,SOAP);
		props.put(SERVICE_EXPORTED_CONFIGS,ORG_APACHE_CXF_WS);
		props.put(ORG_APACHE_CXF_WS_ADDRESS,address);
		props.put(SERVICE_EXPORTED_CLASSNAME, implementorName);
		
		try {
			ServiceRegistration service=WebServiceContext.getBundleContext().registerService(implementorName, implementor.newInstance(), props);
			WebServiceContext.getServiceRegistrations().add(service);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		System.out.println("WebServiceFactory:Published web service<Address=" + address + ",Implementor="+implementorName+">");
	}

	public static void unpublishService(ServiceRegistration service) {

		service.unregister();
		
		WebServiceContext.getServiceRegistrations().remove(service);

		System.out.println("WebServiceFactory:Unpublished web service<Address=" + "xxx" + ",Implementor="+"xxxxx"+">");
	}
	
	public static String getServiceId(ServiceRegistration service){
		return service.getReference().getProperty(SERVICE_ID)==null?null:service.getReference().getProperty(SERVICE_ID).toString();
	}
	
	public static String getServiceImplementorName(ServiceRegistration service){
		return service.getReference().getProperty(SERVICE_EXPORTED_CLASSNAME)==null?null:service.getReference().getProperty(SERVICE_EXPORTED_CLASSNAME).toString();
	}

	public static String getServiceInterfacesName(ServiceRegistration service){
		return service.getReference().getProperty(SERVICE_EXPORTED_INTERFACES)==null?null:service.getReference().getProperty(SERVICE_EXPORTED_INTERFACES).toString();
	}
	
	public static String getServiceAddress(ServiceRegistration service){
		return service.getReference().getProperty(ORG_APACHE_CXF_WS_ADDRESS)==null?null:service.getReference().getProperty(ORG_APACHE_CXF_WS_ADDRESS).toString();
	}
	public static String getServiceId(ServiceReference service){
		return service.getProperty(SERVICE_ID)==null?null:service.getProperty(SERVICE_ID).toString();
	}
	
	public static String getServiceImplementorName(ServiceReference service){
		return service.getProperty(SERVICE_EXPORTED_CLASSNAME)==null?null:service.getProperty(SERVICE_EXPORTED_CLASSNAME).toString();
	}

	public static String getServiceInterfacesName(ServiceReference service){
		return service.getProperty(SERVICE_EXPORTED_INTERFACES)==null?null:service.getProperty(SERVICE_EXPORTED_INTERFACES).toString();
	}
	
	public static String getServiceAddress(ServiceReference service){
		return service.getProperty(ORG_APACHE_CXF_WS_ADDRESS)==null?null:service.getProperty(ORG_APACHE_CXF_WS_ADDRESS).toString();
	}
}
