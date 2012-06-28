package com.trnnn.osgi.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

import org.osgi.framework.Bundle;

/**
 * @since 2006-11-27
 * @author wushugen
 * 
 *         Modified History:
 * 
 */
public class PackageParser {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		/*
		 * List<Class<? extends Object>> cls = new
		 * PackageParser().getClassInPackage("java.util"); for (Class<? extends
		 * Object> s : cls) { System.out.println(s); }
		 */

	}

	public List<String> getClassInPackage(String pkgName, Bundle bundle) {
		List<String> ret = new ArrayList<String>();
		String rPath = pkgName.replace('.', '/') + "/";
		try {
			List<File> classPaths = getClassPath(bundle);
			for (File classPath : classPaths) {
				// System.out.println("Searching Class Path : "
				// + classPath.getPath());
				if (!classPath.exists())
					continue;
				if (classPath.isDirectory()) {
					File dir = new File(classPath, rPath);
					if (!dir.exists())
						continue;
					for (File file : dir.listFiles()) {
						if (file.isFile()) {
							String clsName = file.getName();
							clsName = pkgName
									+ "."
									+ clsName
											.substring(0, clsName.length() - 6);
							ret.add(clsName);
						}
					}
				} else {
					FileInputStream fis = new FileInputStream(classPath);
					JarInputStream jis = new JarInputStream(fis, false);
					JarEntry e = null;
					while ((e = jis.getNextJarEntry()) != null) {
						String eName = e.getName();

						if (eName.startsWith(rPath) && !eName.endsWith("/")) {
							String clsName = (eName.replace('/', '.')
									.substring(0, eName.length() - 6));
							// bundle.getBundleContext().getServiceReference(clsName).
							ret.add(clsName);
							// System.out.println("Inspect class : " + clsName);
						}
						jis.closeEntry();
					}
					jis.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}

	// private String[] CLASS_PATH_PROP = { "java.class.path",
	// "java.ext.dirs","sun.boot.class.path" };

	// private List<File> CLASS_PATH_ARRAY = getClassPath();

	private List<File> getClassPath(Bundle bundle) {

		Object obj = bundle.getHeaders().get("Bundle-ClassPath");
		String bundleClassPath;
		if (obj == null) {
			bundleClassPath = ".";
		} else {
			bundleClassPath = obj.toString();
		}
		String[] bundleClassPaths = bundleClassPath.split(",");

		List<File> ret = new ArrayList<File>();
		String bundleLocation = bundle.getLocation();
		System.out.println(bundleLocation);
		File rootFile = new File(bundleLocation.replace("file:/", ""));
		if (rootFile.isFile() && bundleLocation.endsWith(".jar")) {
			try {
				JarFile jar = new JarFile(rootFile);
				Enumeration<JarEntry> entrys = jar.entries();
				while (entrys.hasMoreElements()) {
					JarEntry entry = entrys.nextElement();
					String entryName = entry.getName();
					System.out.println(entryName);
					if (entryName.endsWith(".class")) {

					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for (String path : bundleClassPaths) {
			String fileName = bundleLocation.replace("initial@reference:file:",
					"") + path;
			ret.add(new File(fileName));
		}
		// ret.add(new
		// File(bundleLocation.replace("initial@reference:file:","")+"bin/"));
		/*
		 * String delim = ":"; if
		 * (System.getProperty("os.name").indexOf("Windows") != -1) delim = ";";
		 * for (String pro : CLASS_PATH_PROP) { String[] pathes =
		 * System.getProperty(pro).split(delim);
		 * System.out.println("Current ClassPath:"+System.getProperty(pro)); for
		 * (String path : pathes) ret.add(new File(path)); }
		 */
		return ret;
	}
}
