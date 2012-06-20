package com.trnnn.osgi.classloader;

import com.trnnn.osgi.framework.Framework;

public class FrameworkClassLoader extends ClassLoader {

	private ClassLoader parent;
	@SuppressWarnings("unused")
	private Framework framework;

	public FrameworkClassLoader(Framework framework) {
		this.framework = framework;
		this.parent = this.getClass().getClassLoader();
	}

	@Override
	protected Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		Class<?> clazz = findLoadedClass(name);
		if (clazz != null)
			return clazz;

		// boolean childFirst = childFirst(name);
		ClassNotFoundException cnfe = null;

		// if (childFirst)
		try {
			clazz = findClass(name);
		} catch (ClassNotFoundException e) {
			// continue
			cnfe = e;
		}

		if (clazz == null)
			try {
				clazz = parent.loadClass(name);
			} catch (ClassNotFoundException e) {
				// continue
			}

		if (clazz == null && cnfe != null)
			throw cnfe;
		// if (clazz == null && !childFirst)
		// clazz = findClass(name);

		if (resolve)
			resolveClass(clazz);
		return clazz;
	}

}
