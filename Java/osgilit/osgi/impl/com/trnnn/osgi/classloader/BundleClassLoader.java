package com.trnnn.osgi.classloader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.Manifest;

import org.osgi.framework.Constants;

import sun.misc.Resource;
import sun.misc.URLClassPath;

import com.trnnn.osgi.framework.bundle.AbstractBundle;

public class BundleClassLoader extends ClassLoader {

	private final HashMap<CodeSource, ProtectionDomain> pdcache = new HashMap<>(
			11);
	private AbstractBundle bundle;

	private URLClassPath ucp;
	private final AccessControlContext acc;
	private String[] classPaths;

	private ClassLoader parent;

	public BundleClassLoader(AbstractBundle bundle) {
		this.bundle = bundle;
		this.parent = this.bundle.getFramework().getClassLoader();

		this.acc = AccessController.getContext();

		initClassPath();

		initialized = true;
	}

	private void initClassPath() {
		Dictionary<String, String> headers = bundle.getHeaders();
		String classPath = headers.get(Constants.BUNDLE_CLASSPATH);
		URL[] urls = new URL[1];
		if (classPath != null) {
			classPaths = classPath.split(",");
		}
		String path = bundle.getLocation();
		File file = new File(path);
		try {
			urls[0] = file.toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		this.ucp = new URLClassPath(urls);
	}

	protected Package definePackage(String name, Manifest man, URL url)
			throws IllegalArgumentException {
		String path = name.replace('.', '/').concat("/");
		String specTitle = null, specVersion = null, specVendor = null;
		String implTitle = null, implVersion = null, implVendor = null;
		String sealed = null;
		URL sealBase = null;

		Attributes attr = man.getAttributes(path);
		if (attr != null) {
			specTitle = attr.getValue(Name.SPECIFICATION_TITLE);
			specVersion = attr.getValue(Name.SPECIFICATION_VERSION);
			specVendor = attr.getValue(Name.SPECIFICATION_VENDOR);
			implTitle = attr.getValue(Name.IMPLEMENTATION_TITLE);
			implVersion = attr.getValue(Name.IMPLEMENTATION_VERSION);
			implVendor = attr.getValue(Name.IMPLEMENTATION_VENDOR);
			sealed = attr.getValue(Name.SEALED);
		}
		attr = man.getMainAttributes();
		if (attr != null) {
			if (specTitle == null) {
				specTitle = attr.getValue(Name.SPECIFICATION_TITLE);
			}
			if (specVersion == null) {
				specVersion = attr.getValue(Name.SPECIFICATION_VERSION);
			}
			if (specVendor == null) {
				specVendor = attr.getValue(Name.SPECIFICATION_VENDOR);
			}
			if (implTitle == null) {
				implTitle = attr.getValue(Name.IMPLEMENTATION_TITLE);
			}
			if (implVersion == null) {
				implVersion = attr.getValue(Name.IMPLEMENTATION_VERSION);
			}
			if (implVendor == null) {
				implVendor = attr.getValue(Name.IMPLEMENTATION_VENDOR);
			}
			if (sealed == null) {
				sealed = attr.getValue(Name.SEALED);
			}
		}
		if ("true".equalsIgnoreCase(sealed)) {
			sealBase = url;
		}
		return definePackage(name, specTitle, specVersion, specVendor,
				implTitle, implVersion, implVendor, sealBase);
	}

	private Class<?> defineClass(String name, Resource res) throws IOException {
		long t0 = System.nanoTime();
		int i = name.lastIndexOf('.');
		URL url = res.getCodeSourceURL();
		if (i != -1) {
			String pkgname = name.substring(0, i);
			// Check if package already loaded.
			Manifest man = res.getManifest();
			if (getAndVerifyPackage(pkgname, man, url) == null) {
				try {
					if (man != null) {
						definePackage(pkgname, man, url);
					} else {
						definePackage(pkgname, null, null, null, null, null,
								null, null);
					}
				} catch (IllegalArgumentException iae) {
					// parallel-capable class loaders: re-verify in case of a
					// race condition
					if (getAndVerifyPackage(pkgname, man, url) == null) {
						// Should never happen
						throw new AssertionError("Cannot find package "
								+ pkgname);
					}
				}
			}
		}
		// Now read the class bytes and define the class
		java.nio.ByteBuffer bb = res.getByteBuffer();
		if (bb != null) {
			// Use (direct) ByteBuffer:
			CodeSigner[] signers = res.getCodeSigners();
			CodeSource cs = new CodeSource(url, signers);
			sun.misc.PerfCounter.getReadClassBytesTime().addElapsedTimeFrom(t0);
			return defineClass(name, bb, cs);
		} else {
			byte[] b = res.getBytes();
			// must read certificates AFTER reading bytes.
			CodeSigner[] signers = res.getCodeSigners();
			CodeSource cs = new CodeSource(url, signers);
			sun.misc.PerfCounter.getReadClassBytesTime().addElapsedTimeFrom(t0);
			return defineClass(name, b, 0, b.length, cs);
		}
	}

	protected final Class<?> defineClass(String name, java.nio.ByteBuffer b,
			CodeSource cs) {
		return defineClass(name, b, getProtectionDomain(cs));
	}

	protected final Class<?> defineClass(String name, byte[] b, int off,
			int len, CodeSource cs) {
		return defineClass(name, b, off, len, getProtectionDomain(cs));
	}

	private ProtectionDomain getProtectionDomain(CodeSource cs) {
		if (cs == null)
			return null;

		ProtectionDomain pd = null;
		synchronized (pdcache) {
			pd = pdcache.get(cs);
			if (pd == null) {
				PermissionCollection perms = getPermissions(cs);
				pd = new ProtectionDomain(cs, perms, this, null);
				pdcache.put(cs, pd);
				// if (debug != null) {
				// debug.println(" getPermissions " + pd);
				// debug.println("");
				// }
			}
		}
		return pd;
	}

	protected PermissionCollection getPermissions(CodeSource codesource) {
		check();
		return new Permissions(); // ProtectionDomain defers the binding
	}

	private final boolean initialized;

	private void check() {
		if (!initialized) {
			throw new SecurityException("ClassLoader object not initialized");
		}
	}

	private Package getAndVerifyPackage(String pkgname, Manifest man, URL url) {
		Package pkg = getPackage(pkgname);
		if (pkg != null) {
			// Package found, so check package sealing.
			if (pkg.isSealed()) {
				// Verify that code source URL is the same.
				if (!pkg.isSealed(url)) {
					throw new SecurityException("sealing violation: package "
							+ pkgname + " is sealed");
				}
			} else {
				// Make sure we are not attempting to seal the package
				// at this code source URL.
				if ((man != null) && isSealed(pkgname, man)) {
					throw new SecurityException(
							"sealing violation: can't seal package " + pkgname
									+ ": already loaded");
				}
			}
		}
		return pkg;
	}

	private boolean isSealed(String name, Manifest man) {
		String path = name.replace('.', '/').concat("/");
		Attributes attr = man.getAttributes(path);
		String sealed = null;
		if (attr != null) {
			sealed = attr.getValue(Name.SEALED);
		}
		if (sealed == null) {
			if ((attr = man.getMainAttributes()) != null) {
				sealed = attr.getValue(Name.SEALED);
			}
		}
		return "true".equalsIgnoreCase(sealed);
	}

	protected Class<?> findClass(final String name)
			throws ClassNotFoundException {
		try {
			return AccessController.doPrivileged(
					new PrivilegedExceptionAction<Class<?>>() {
						public Class<?> run() throws ClassNotFoundException {
							String path = name.replace('.', '/').concat(
									".class");
							Resource res = ucp.getResource(path, false);
							if (res != null) {
								try {
									return defineClass(name, res);
								} catch (IOException e) {
									throw new ClassNotFoundException(name, e);
								}
							} else {
								if (classPaths != null) {
									for (String classPath : classPaths) {

										if (classPath.equalsIgnoreCase("."))
											continue;

										String newPath = classPath + path;
										res = ucp.getResource(newPath, false);
										try {
											return defineClass(name, res);
										} catch (IOException e) {
											throw new ClassNotFoundException(
													name, e);
										}
									}
									throw new ClassNotFoundException(name);
								} else
									throw new ClassNotFoundException(name);
							}

						}
					}, acc);
		} catch (java.security.PrivilegedActionException pae) {
			throw (ClassNotFoundException) pae.getException();
		}
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		return loadClass(name, false);
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

	public static void main(String[] args) throws MalformedURLException {
		URL[] urls = new URL[1];
		// urls[0] = new File("D:\\plugins\\plugin1_1.0.0.201203161112.jar")
		// .toURI().toURL();
		urls[0] = new File("D:\\Li Qingfeng\\Java\\plugin1\\").toURI().toURL();
		URLClassPath ucp = new URLClassPath(urls);
		Resource res = ucp.getResource("bin/packet1/Class1.class");
		String str = res.getURL().toString();
		System.out.println(str);
		URLClassLoader ucl = new URLClassLoader(urls);
		try {
			Class<?> clazz = ucl.loadClass("packet1.Class1");
			System.out.println(clazz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

class Test {
	public Test() {

	}
}
