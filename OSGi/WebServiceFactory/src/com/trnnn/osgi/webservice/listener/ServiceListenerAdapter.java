package com.trnnn.osgi.webservice.listener;

import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

import com.trnnn.osgi.webservice.WebServiceFactory;

public class ServiceListenerAdapter implements ServiceListener {

	public void serviceChanged(ServiceEvent event) {
		
		String message="ServiceListenerAdapter : ";
		ServiceReference service=event.getServiceReference();//.getProperty(WebServiceFactory.get)
		String serviceClass=WebServiceFactory.getServiceImplementorName(service);
		if(serviceClass!=null&&!serviceClass.equals("")){
			message+=serviceClass ;
			int eventType = event.getType();
			switch (eventType) {
			case ServiceEvent.MODIFIED:
				message+=" modified";
				break;
			case ServiceEvent.REGISTERED:
				message+=" registered";
				break;
			case ServiceEvent.UNREGISTERING:
				message+=" unregistered";
				break;
			default:
				message+=" unknown action";
				break;
			}

			System.out.println(message);
		}
		
	}

}
