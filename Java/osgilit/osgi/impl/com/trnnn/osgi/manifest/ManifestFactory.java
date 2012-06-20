package com.trnnn.osgi.manifest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class ManifestFactory {

	public Manifest getManifest(JarFile jar) throws IOException {
		return jar.getManifest();
	}

	public Manifest getManifest(String pluginRoot) {
		File file = new File(pluginRoot);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files == null) {
				return null;
			}
			for (File f : files) {
				String str = f.getName();
				if (str.equals("META-INF")) {
					File[] fs = f.listFiles();
					if (fs == null) {
						return null;
					}
					for (File ff : fs) {
						if (ff.getName().equals("MANIFEST.MF")) {
							try {
								return new Manifest(new FileInputStream(ff));
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
							return null;
						}
					}
				}
			}
		} else if (file.isFile()) {
			try {
				JarFile jar = new JarFile(file);
				return jar.getManifest();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	public Dictionary<String, String> parseHeaders(Manifest manifest) {
		Map<String, String> map = ManifestFactory.parseManifest(manifest);
		Hashtable<String, String> table = new Hashtable<String, String>();
		table.putAll(map);
		return table;
	}

	public static Map<String, String> parseManifest(Manifest manifest) {
		Attributes attrs = manifest.getMainAttributes();
		Set<Object> keys = attrs.keySet();
		Map<String, String> map = Collections
				.synchronizedMap(new HashMap<String, String>());
		for (Object key : keys) {
			// System.out.println(key + "->> " + attrs.get(key));
			map.put(key.toString(), attrs.get(key).toString());
		}
		return map;
	}

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		ManifestFactory factory = new ManifestFactory();
		Manifest manifest = factory
				.getManifest("D:\\plugins\\plugin1_1.0.0.201203161136.jar");
		if (manifest != null) {
			ManifestFactory.parseManifest(manifest);
		}
	}
}
