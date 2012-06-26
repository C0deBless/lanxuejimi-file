package com.trnnn.osgi.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import com.trnnn.osgi.webservice.model.WebServiceClassModel;

public class WebServiceContext {

	//public static List<ServiceReference> serviceReferences;
	public WebServiceContext(BundleContext context){
		setBundleContext(context);
	}
	
	private static BundleContext bundleContext;
	
	private  static List<ServiceRegistration> serviceRegistrations=new ArrayList<ServiceRegistration>();
	
	public static ServiceRegistration getServiceRegistration(String serviceId){
		for(ServiceRegistration service:serviceRegistrations){
			if(WebServiceFactory.getServiceId(service).equals(serviceId)){
				return service;
			}
		}
		return null;
	}
	
	public static List<ServiceRegistration> getServiceRegistrations() {
		return serviceRegistrations;
	}

	private static List<WebServiceClassModel> availableClasses = new ArrayList<WebServiceClassModel>();

	public static List<WebServiceClassModel> getAvailableClasses() {
		return availableClasses;
	}

	public static void addAvailableClass(WebServiceClassModel clazz) {
		availableClasses.add(clazz);
	}

	public static void removeAvailableClass(WebServiceClassModel clazz) {
		availableClasses.remove(clazz);
	}

	public static void clearAvailbleClass() {
		availableClasses.clear();
	}
	
	public static void addServiceRegistration(ServiceRegistration service){
		serviceRegistrations.add(service);
	}
	
	public static void unregisterService(ServiceRegistration service){
		service.unregister();
		serviceRegistrations.remove(service);
	}
	
	public static void updateServiceRegistrations(){
		for (ServiceRegistration registration : serviceRegistrations) {
			//TODO:adfasfasfdadfasfd
			//String implementorName = registration.getReference().getProperty(key)

			
			
			/*for (WebServiceClassModel clazz : availableClasses) {
				if (implementorName.equals(clazz.getImplementor().getName())) {
					isAvailable = true;
					break;
				}
			}
			if (!isAvailable || !endpoint.isPublished()) {
				String address = ((EndpointImpl) endpoint).getAddress();
				System.out
						.println("WebServiceContext:<updateEndpoint()> Stopped endpoint <implementorName="
								+ implementorName + ",URL=" + address + ">");
				endpoint.stop();
				endPoints.remove(endpoint);
			}*/
		}
	}

	public static void setBundleContext(BundleContext bundleContext) {
		WebServiceContext.bundleContext = bundleContext;
	}

	public static BundleContext getBundleContext() {
		return bundleContext;
	}
}