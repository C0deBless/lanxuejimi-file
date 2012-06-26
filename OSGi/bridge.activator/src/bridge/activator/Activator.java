package bridge.activator;

import java.util.Enumeration;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		Bundle[] bundles=context.getBundles();
		for(Bundle bundle : bundles){
			if(bundle.getState()!=Bundle.ACTIVE){
				try{
					Enumeration<String> en=bundle.getHeaders().keys();
					while(en.hasMoreElements()){
						System.out.println("----"+bundle.getHeaders(en.nextElement()));
					}
					bundle.start();
				}
				catch(Exception e){
					
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
