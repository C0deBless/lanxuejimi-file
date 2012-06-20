package com.trnnn.osgi.framework.bundle;

import java.io.File;
import java.util.jar.JarFile;

public class BundleAdaptor {

	private BundleAdaptor() {

	}

	public static BundleAdaptor resolve(JarFile jar) {
		return null;
	}

	public static BundleAdaptor resolve(File file) {
		return null;
	}

	public static BundleAdaptor resolve(String path) {
		return null;
	}
}
