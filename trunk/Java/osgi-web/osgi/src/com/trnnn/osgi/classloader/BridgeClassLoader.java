package com.trnnn.osgi.classloader;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
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
	final ClassLoader primaryClassLoader;

	public BridgeClassLoader(ClassLoader primary) {
		super(primary);
		primaryClassLoader = primary;
	}

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
		List<String> packages = parsePackages(bundle);
		// 是否循环迭代
		boolean recursive = true;
		ClassLoader currentClassLoader = Thread.currentThread()
				.getContextClassLoader();
		for (String packageName : packages) {
			String packageDirName = packageName.replace('.', '/');
			try {
				Enumeration<URL> dirs = currentClassLoader
						.getResources(packageDirName);
				while (dirs.hasMoreElements()) {
					URL url = dirs.nextElement();
					String protocol = url.getProtocol();
					if ("file".equals(protocol)) {
						String filePath = URLDecoder.decode(url.getFile(),
								"UTF-8");
						findAndAddClassesInPackageByFile(bundle, packageName,
								filePath, recursive, classes);
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void findAndAddClassesInPackageByFile(Bundle bundle,
			String packageName, String packagePath, final boolean recursive,
			Set<Class<?>> classes) {
		// 获取此包的目录 建立一个File
		File dir = new File(packagePath);
		// 如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			// log.warn("用户定义包名 " + packageName + " 下没有任何文件");
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			public boolean accept(File file) {
				return (recursive && file.isDirectory())
						|| (file.getName().endsWith(".class"));
			}
		});
		// 循环所有文件
		for (File file : dirfiles) {
			// 如果是目录 则继续扫描
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(bundle, packageName + "."
						+ file.getName(), file.getAbsolutePath(), recursive,
						classes);
			} else {
				// 如果是java类文件 去掉后面的.class 只留下类名
				String className = file.getName().substring(0,
						file.getName().length() - 6);
				try {
					// 添加到集合中去
					// classes.add(Class.forName(packageName + '.' +
					// className));
					// 经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
					Class<?> clazz = bundle.loadClass(packageName + '.'
							+ className);
					URL url = bundle.getResource(file.getName());
					InputStream is = url.openStream();
					byte[] buffer = new byte[is.available()];
					is.read(buffer);
					is.close();
					
					this.defineClass(className, buffer, 0, buffer.length);
					
					classes.add(clazz);
				} catch (ClassNotFoundException e) {
					// log.error("添加用户自定义视图类错误 找不到此类的.class文件");
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
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
