package com.trnnn.osgi.classloader;

import java.util.List;

import org.osgi.framework.Bundle;

public class BridgeClassLoader extends ClassLoader {
	private final ClassLoader secondary;

	public BridgeClassLoader(ClassLoader primary, ClassLoader secondary) {
		super(primary);
		this.secondary = secondary;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		return secondary.loadClass(name);
	}

	public List<Class<?>> loadBundleExportedClass(Bundle bundle) {
		return null;
	}
}
