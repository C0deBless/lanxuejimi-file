package com.trnnn.osgi.admin;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;

import com.trnnn.osgi.admin.bundle.model.BundleInfoModel;
import com.trnnn.osgi.admin.bundle.model.ServiceReferenceMin;

public class BundleFactory {
	private static BundleContext bundleContext;

	public static void setBundleContext(BundleContext bundleContext) {
		BundleFactory.bundleContext = bundleContext;
	}

	public static BundleContext getBundleContext() {
		return bundleContext;
	}

	public static Bundle get(long id) {
		return BundleFactory.bundleContext.getBundle(id);
	}

	public static void stop(long id) throws BundleException {
		System.out.println("------------"
				+ BundleFactory.get(id).getClass().getName());
		BundleFactory.get(id).stop();
	}

	public static void start(long id) throws BundleException {
		BundleFactory.get(id).start();
	}

	public static void install(String bundlePath) throws BundleException {
		BundleFactory.bundleContext.installBundle("file:" + bundlePath);
	}

	public static void install(String fileName, InputStream input)
			throws BundleException {
		BundleFactory.bundleContext.installBundle("file:" + fileName, input);
	}

	public static void uninstall(long id) throws BundleException {
		BundleFactory.get(id).uninstall();
	}

	public static void update(long id) throws BundleException {
		BundleFactory.get(id).update();
	}

	public static void update(long id, InputStream input)
			throws BundleException {
		BundleFactory.get(id).update(input);
	}

	public static BundleInfoModel getBundleInfo(long id) {

		BundleInfoModel model = new BundleInfoModel();
		Bundle bundle = BundleFactory.get(id);
		model.setBundleId(id);
		model.setBundleName(bundle.getSymbolicName());
		model.setBundlePath(bundle.getLocation());
		model.setBundleStatus(BundleFactory.getBundleState(id));
		// model.setBundleVersion(bundle..getVersion().getQualifier());
		model.setServiceReferences(convertServiceReferences(bundle
				.getRegisteredServices()));
		model.setServiceInUse(convertServiceReferences(bundle
				.getServicesInUse()));
		Dictionary<?, ?> dic = bundle.getHeaders();
		Enumeration<?> enu = dic.keys();
		while (enu.hasMoreElements()) {
			String str = (String) enu.nextElement();
			// System.out.println("property:::" + str);
			String methodName = "set" + str.replace("-", "");
			try {
				Method method = BundleInfoModel.class.getMethod(methodName,
						String.class);
				method.invoke(model, dic.get(str));

				// System.out.println("ServiceReference:::"+str+"--value:"+dic.get(str));

			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	public static String getBundleState(long id) {
		Bundle bundle = BundleFactory.get(id);
		String strState;
		int iState = bundle.getState();
		switch (iState) {
		case Bundle.ACTIVE:
			strState = "ACTIVE";
			break;
		case Bundle.RESOLVED:
			strState = "RESOLVED";
			break;
		case Bundle.STARTING:
			strState = "STARTING";
			break;
		case Bundle.STOPPING:
			strState = "STOPPING";
			break;
		case Bundle.UNINSTALLED:
			strState = "UNINSTALLED";
			break;
		case Bundle.INSTALLED:
			strState = "INSTALLED";
			break;
		default:
			strState = "N/A";
			break;
		}
		return strState;
	}

	private static ServiceReferenceMin[] convertServiceReferences(
			ServiceReference<?>[] source) {
		if (source != null) {
			ServiceReferenceMin[] target = new ServiceReferenceMin[source.length];
			for (int i = 0; i < source.length; i++) {
				ServiceReferenceMin item = new ServiceReferenceMin();

				// Prepare properties
				Map<String, Object> properties = new HashMap<String, Object>();
				String[] keys = source[i].getPropertyKeys();
				for (String key : keys) {
					Object value = source[i].getProperty(key);
					// System.out.println("ServiceReference:::"+key+"--value:"+value);
					if (value instanceof Version) {
						value = ((Version) value).getQualifier();
					}
					properties.put(key.replace(".", "_").replace("-", "_"),
							value);
				}

				// Prepare bundle informations
				Bundle[] bundles = source[i].getUsingBundles();
				if (bundles != null) {
					String[] strs = new String[bundles.length];
					for (int j = 0; j < bundles.length; j++) {
						strs[j] = "&lt;" + bundles[j].getSymbolicName()
								+ "&gt;;" + " Version:"
								+ bundles[j].getVersion().toString();
					}
					item.setUsingBundles(strs);
				}

				item.setProperties(properties);
				target[i] = item;
			}
			return target;
		} else {
			return null;
		}
	}
}
