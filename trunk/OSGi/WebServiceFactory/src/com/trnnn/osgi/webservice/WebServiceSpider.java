package com.trnnn.osgi.webservice;

import java.util.List;

import javax.jws.WebService;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import com.trnnn.osgi.webservice.io.PackageParser;
import com.trnnn.osgi.webservice.model.WebServiceClassModel;

public class WebServiceSpider {
	private static BundleContext context;

	public WebServiceSpider() {
		System.out.println("WebServiceSpider initiallized!!!");
	}

	public WebServiceSpider(BundleContext context) {
		WebServiceSpider.context = context;
		System.out.println("WebServiceSpider initiallized!!!");
		System.out
				.println("New bundleContext injected into WebServiceSpider!!!");
	}

	public void setBundleContext(BundleContext context) {
		WebServiceSpider.context = context;
		System.out
				.println("New bundleContext injected into WebServiceSpider!!!");
	}

	public static BundleContext getBundleContext() {
		return context;
	}

	@SuppressWarnings("unchecked")
	public static void updateAvailbleClass() {

		String[] excludePackages = { "org.apache", "org.springframework","org.jdom","info.dmtree","antlr","Legal","groovy",
				"org.osgi", "org.eclipse", "org.omg", "org.ietf", "org.w3c"," com.opensymphony","com.microsoft",
				"com.sdicons", "org.xml", "com.springsource", "java", "javax","ognl","org.dom4j",
				"sun", "com.caucho", "com.sun", "org.aopalliance", "org.jruby",
				"com.lowagie", "jxl", "freemarker", "net.sf", "org.aspectj",
				"com.ibm", "org.codehaus","META-INF.cxf" };

		// List<Endpoint> endPoints = WebServiceContext.getEndPoints();
		WebServiceContext.clearAvailbleClass();
		List<WebServiceClassModel> availableClass = WebServiceContext
				.getAvailableClasses();

		// endPoints.clear();

		BundleContext bundleContext = WebServiceSpider.context;
		Bundle[] bundles = bundleContext.getBundles();

		for (Bundle bundle : bundles) {

			// ServiceReference[] serviceReferences =
			// bundle.getRegisteredServices();

			if (bundle.getHeaders().get("Export-Package") != null
					&& bundle.getHeaders().get("Bundle-ClassPath") != null) {
				String exportPackage = bundle.getHeaders()
						.get("Export-Package").toString();
				String[] exportPackages_raw = exportPackage.split(",");

				for (String rawPackage : exportPackages_raw) {
					String[] tmp = rawPackage.split(";");
					String packageName = tmp[0];
					boolean isAvailable = true;
					for (String excludePackage : excludePackages) {
						int index = rawPackage.trim().indexOf(
								excludePackage.trim());
						if (index == 0) {
							isAvailable = false;
							break;
						}
					}
					if (isAvailable) {
						System.out.println("WebServiceSpider Searching Package: "
										+ packageName);
						List<String> classes = new PackageParser()
								.getClassInPackage(packageName, bundle);
						for (String clazzName : classes) {
							try {

								Class<? extends Object> clazz= bundle.loadClass(clazzName);
								
								WebService webServiceAnnotation = clazz.getAnnotation(WebService.class);
								System.out.println("WebServiceSpider Package: "
										+ packageName
										+ "---found class:"
										+ clazz.getName());
								if (webServiceAnnotation != null) {

									// Get endpointInterface from available Class
									String endpointInterface = webServiceAnnotation
											.endpointInterface();

									if (endpointInterface.equals(null)
											|| endpointInterface.equals("")) {

									} else {
										// add to available web service classes
										System.out.println("WebServiceSpider: detected available WebService Class Implementor : "
														+ clazz.getName());
										
										WebServiceClassModel classModel=new WebServiceClassModel();
										classModel.setEndpointInterface(bundle.loadClass(endpointInterface));
										classModel.setImplementor(clazz);
										classModel.setBundle(bundle);
										availableClass.add(classModel);
										
									}
								}
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							}
							catch(Exception e){
								e.printStackTrace();
							}
						}
					}
				}

			}
		}
	}
	
}
