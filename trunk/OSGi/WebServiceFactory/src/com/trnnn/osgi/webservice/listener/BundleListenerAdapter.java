package com.trnnn.osgi.webservice.listener;

import java.util.Arrays;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

import com.trnnn.osgi.webservice.WebServiceContext;
import com.trnnn.osgi.webservice.WebServiceSpider;

public class BundleListenerAdapter implements BundleListener {

	private List<String> systemBundles = Arrays.asList(
			"com.springsource.org.aopalliance", "commons-fileupload",
			"Compiler", "CompressUtil", "CXFLib", "IOUtil", "javax.servlet",
			"javax.servlet.jsp", "json-plugin", "org.apache.commons.el",
			"org.apache.commons.logging", "org.apache.jasper",
			"org.apache.log4j", "org.eclipse.osgi", "org.eclipse.osgi.services",
			"org.springframework.aop",
			"org.springframework.beans", "org.springframework.context",
			"org.springframework.core","org.osgi.compendium",
			"org.springframework.osgi.catalina.osgi",
			"org.springframework.osgi.catalina.start.osgi",
			"org.springframework.osgi.core",
			"org.springframework.osgi.extender", "org.springframework.osgi.io",
			"org.springframework.osgi.web",
			"org.springframework.osgi.web.extender", "org.springframework.web",
			"org.springframework.web.servlet", "OSGIWebAdmin","cxf-dosgi-ri-singlebundle-distribution",
			"WebServiceFactory");

	@Override
	public void bundleChanged(BundleEvent event) {

		int eventType = event.getType();
		Bundle bundle = event.getBundle();
		String message = "WebServiceFactory--BundleListenerAdapter:Detected ";
		String bundleInfo = "<BundleId:" + bundle.getBundleId()
				+ "; BundleName:" + bundle.getSymbolicName() + "; Version:"
				+ bundle.getVersion().toString() + ">";
		String finalMessage;
		switch (eventType) {
		case BundleEvent.INSTALLED:
			finalMessage = message + bundleInfo + " is Installed";

			break;
		case BundleEvent.RESOLVED:
			finalMessage = message + bundleInfo + " is Resolved";

			break;
		case BundleEvent.STARTED:
			finalMessage = message + bundleInfo + " is Started";

			break;
		case BundleEvent.STOPPED:
			finalMessage = message + bundleInfo + " is Stopped";

			break;
		case BundleEvent.UNINSTALLED:
			finalMessage = message + bundleInfo + " is Uninstalled";

			break;
		case BundleEvent.UPDATED:
			finalMessage = message + bundleInfo + " is Updated";

			break;
		default:
			finalMessage = message + bundleInfo + " unknown action";

			break;
		}

		System.out.println(finalMessage);
		/*String bundleName = bundle.getSymbolicName();
		if (!this.systemBundles.contains(bundleName)) {
			System.out.println("Bundle Name :"+bundleName+" is not a system bundle; Start WebServiceSpider");
			WebServiceSpider.updateAvailbleClass();
		}
	*/
	}
}
