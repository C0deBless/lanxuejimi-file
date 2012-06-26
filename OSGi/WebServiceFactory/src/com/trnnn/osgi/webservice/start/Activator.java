package com.trnnn.osgi.webservice.start;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleListener;
import org.osgi.framework.ServiceListener;

import com.trnnn.osgi.webservice.WebServiceContext;
import com.trnnn.osgi.webservice.WebServiceSpider;
import com.trnnn.osgi.webservice.listener.BundleListenerAdapter;
import com.trnnn.osgi.webservice.listener.ServiceListenerAdapter;

public class Activator implements BundleActivator {

	private BundleListener bundleListener;
	private ServiceListener serviceListener;
	public static BundleContext bundleContext;

	public void start(BundleContext context) throws Exception {
		bundleContext=context;
		new WebServiceContext(context);
		new WebServiceSpider(context);
		this.bundleListener=new BundleListenerAdapter();
		this.serviceListener=new ServiceListenerAdapter();
		context.addBundleListener(bundleListener);
		
		context.addServiceListener(serviceListener);
		//context.addFrameworkListener(listener)
		System.out.println("WebServiceFactory--Activator: BundleListenerAdapter installed");
		
		/*Thread thread=new Thread(new Runnable(){

			public void run() {
				WebServiceSpider.updateAvailbleClass();
				WebServiceContext.updateEndpoint();
			}
			
		});
		
		thread.start();*/
		/*Dictionary<String,String> props=new Hashtable<String,String>();
		props.put("service.exported.interfaces","*");
		props.put("service.exported.intents","SOAP");
		props.put("service.exported.configs","org.apache.cxf.ws");
		props.put("org.apache.cxf.ws.address","http://localhost:9000/say");
		ServiceRegistration service=context.registerService(HelloWorldService.class.getName(), new HelloWorldServiceImpl(), props);
		String keys[] =service.getReference().getPropertyKeys();
		System.out.println(keys.length);
		for(String key:keys){
			System.out.println("-----"+key+"::::"+service.getReference().getProperty(key));
		}*/
	}


	public void stop(BundleContext context) throws Exception {
		context.removeBundleListener(this.bundleListener);
		System.out.println("WebServiceFactory--Activator: BundleListenerAdapter uninstalled");
	}

}
