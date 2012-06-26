package com.trnnn.osgi.admin.webservice.web;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.ServiceRegistration;

import com.trnnn.osgi.admin.webservice.servlet.model.WebServiceModel;
import com.trnnn.osgi.webservice.WebServiceContext;
import com.trnnn.osgi.webservice.WebServiceFactory;
import com.trnnn.osgi.webservice.model.WebServiceClassModel;

public class WebServiceManage {
	
	public List<WebServiceModel> getWebServiceList(){
		
		List<WebServiceModel> webServiceList=new ArrayList<WebServiceModel>();
		List<WebServiceClassModel> classes=WebServiceContext.getAvailableClasses();
		//System.out.println(classes.size());
		for(WebServiceClassModel clazz:classes){
		
			String endpointInterface=clazz.getEndpointInterface().getName();
			
			WebServiceModel model=new WebServiceModel();
			
			List<ServiceRegistration> services=WebServiceContext.getServiceRegistrations();

			model.setEndpointInterface(endpointInterface);
			model.setImplementor(clazz.getImplementor().getName());
			model.setBundle(clazz.getBundle());
			if(services!=null){
				List<ServiceRegistration> _services=new ArrayList<ServiceRegistration>();
				
				for(ServiceRegistration service:services){
					String implementorName=WebServiceFactory.getServiceImplementorName(service);
					if(implementorName!=null&&implementorName.equals(clazz.getImplementor().getName())){
						_services.add(service);
					}
				}
				model.setPublishedServices(_services);
			}
			webServiceList.add(model);
			
			
		}
		return webServiceList;
	}
	
}
