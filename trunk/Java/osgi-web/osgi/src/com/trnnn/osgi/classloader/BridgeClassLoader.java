package com.trnnn.osgi.classloader;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BridgeClassLoader extends ClassLoader {
	Logger logger = LoggerFactory.getLogger(BridgeClassLoader.class);
	protected final Map<Bundle, List<Class<?>>> bundleClassCache = new HashMap<>();

	public BridgeClassLoader(ClassLoader primary) {
		super(primary);
	}

	@Override
	public Class<?> findClass(String name) throws ClassNotFoundException {
		Class<?> clazz = null;
		clazz = super.loadClass(name);
		if (clazz != null) {
			return clazz;
		}
		Set<Bundle> bundles = bundleClassCache.keySet();
		for (Bundle bundle : bundles) {
			clazz = bundle.loadClass(name);
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

		List<Class<?>> classes = null;
		if (this.bundleClassCache.containsKey(bundle)
				&& this.bundleClassCache.get(bundle) != null) {
			classes = this.bundleClassCache.get(bundle);
		} else {
			classes = new ArrayList<>();
			this.bundleClassCache.put(bundle, classes);
		}

		List<String> packages = parsePackages(bundle);
		resolveBundleClassCache(bundle, packages);
	}

	private void resolveBundleClassCache(Bundle bundle, List<String> packages) {

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

		if (strExportPackage != null) {
			String[] tmp = strExportPackage.split(",");
			for (String string : tmp) {
				
			}
		}
		return null;
	}
}
