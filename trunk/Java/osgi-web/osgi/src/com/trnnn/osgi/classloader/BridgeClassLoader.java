package com.trnnn.osgi.classloader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BridgeClassLoader extends ClassLoader {
	Logger logger = LoggerFactory.getLogger(BridgeClassLoader.class);
	protected final Map<Bundle, Set<Class<?>>> bundleClassCache = new HashMap<>();
	PackageParser packageParser = new PackageParser();
	final ClassLoader primaryClassLoader;
	Method resolveMethod;
	Method defineMethod;

	public BridgeClassLoader(ClassLoader primary) {
		super(primary);
		primaryClassLoader = primary;
		initMethods();
	}

	private void initMethods() {
//		Class<?> clazz = this.primaryClassLoader.getClass();
		try {
			resolveMethod = ClassLoader.class.getDeclaredMethod("resolveClass",
					Class.class);
			resolveMethod.setAccessible(true);
			defineMethod = ClassLoader.class.getDeclaredMethod("defineClass",
					String.class, byte[].class, int.class, int.class);
			defineMethod.setAccessible(true);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	// public static void main(String[] args){
	// Method[] ms=ClassLoader.class.getMethods();
	// for(Method method : ms){
	// System.out.println(method);
	// }
	// }

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		Class<?> clazz = null;
		try {
			clazz = primaryClassLoader.loadClass(name);
		} catch (ClassNotFoundException e) {
			// do nothing
		}
		if (clazz != null) {
			return clazz;
		}

		Set<Bundle> bundles = bundleClassCache.keySet();
		for (Bundle bundle : bundles) {
			try {
				clazz = bundle.loadClass(name);
			} catch (ClassNotFoundException e) {
				// do nothing
			}
			if (clazz != null) {
				return clazz;
			}
		}
		return clazz;
	}

	public void resolveBundleClassSpace(BundleContext bundleContext) {
		bundleClassCache.clear();
		Bundle[] bundles = bundleContext.getBundles();
		if (bundles != null && bundles.length > 0) {
			for (Bundle bundle : bundles) {
				resolveBundleClassSpace(bundle);
			}
		}
	}

	public void resolveBundleClassSpace(Bundle bundle) {

		logger.info(
				"[BridgeClassLoader.resolveBundleClassSpace] name : {}, location: {}",
				bundle.getSymbolicName(), bundle.getLocation());

		Set<Class<?>> classes = null;
		if (this.bundleClassCache.containsKey(bundle)
				&& this.bundleClassCache.get(bundle) != null) {
			classes = this.bundleClassCache.get(bundle);
		} else {
			classes = new HashSet<>();
			this.bundleClassCache.put(bundle, classes);
		}

		classes.clear();
		resolveBundleClassCache(bundle, classes);
	}

	private void resolveBundleClassCache(Bundle bundle, Set<Class<?>> classes) {
		List<String> packages = this.parsePackages(bundle);
		List<String> list = new ArrayList<>();
		for (String string : packages) {
			if (!checkPackageName(string)) {
				continue;
			}
			list.addAll(packageParser.getClassInPackage(string, bundle));
		}
		for (String className : list) {
			logger.info("loading class -> {}", className);
			URL url = bundle.getResource(className);

			try {
				InputStream is = url.openStream();
				byte[] buffer = new byte[is.available()];
				is.read(buffer);
				is.close();
				// Class<?> c = this.defineClass(className, buffer, 0,
				// buffer.length);
				Class<?> c = (Class<?>) this.defineMethod.invoke(
						this.primaryClassLoader, className, buffer, 0,
						buffer.length);
//				this.resolveClass(c);
				this.resolveMethod.invoke(this.primaryClassLoader, c);
				this.primaryClassLoader.loadClass(className);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
	}

	List<String> excludePackages = Arrays.asList("org.apache",
			"org.springframework", "org.jdom", "info.dmtree", "antlr", "Legal",
			"groovy", "org.osgi", "org.eclipse", "org.omg", "org.ietf",
			"org.eclipse", "org.w3c", " com.opensymphony", "com.microsoft",
			"com.sdicons", "org.xml", "com.springsource", "java", "javax",
			"ognl", "org.dom4j", "sun", "com.caucho", "com.sun",
			"org.aopalliance", "org.jruby", "com.lowagie", "jxl", "freemarker",
			"net.sf", "org.aspectj", "com.ibm", "org.codehaus", "META-INF.cxf");

	private boolean checkPackageName(String pak) {
		for (String string : excludePackages) {
			if (pak.trim().startsWith(string)) {
				return false;
			}
		}
		return true;
	}

	private List<String> parsePackages(Bundle bundle) {
		// bundle.getResources(arg0);
		Dictionary<String, String> dic = bundle.getHeaders();
		Enumeration<String> it = dic.keys();
		String strExportPackage = null;
		while (it.hasMoreElements()) {
			String key = it.nextElement();
			if (!key.equals("Export-Package")) {
				continue;
			}
			strExportPackage = dic.get(key);
		}

		logger.info("packages -> {}", strExportPackage);
		List<String> packages = new ArrayList<>();
		if (strExportPackage != null) {
			String[] tmp = strExportPackage.split(",");
			for (String string : tmp) {
				String[] tmp1 = string.split(";");
				if (tmp1.length >= 1) {
					packages.add(tmp1[0]);
				}
			}
		}
		return packages;
	}
}
