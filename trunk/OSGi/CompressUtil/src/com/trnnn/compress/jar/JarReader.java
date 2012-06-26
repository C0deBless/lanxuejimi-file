package com.trnnn.compress.jar;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

public class JarReader {
	public void read(String inputPath) throws Exception {

		JarInputStream in = new JarInputStream(new FileInputStream(inputPath));
		Manifest manifest = in.getManifest();
		Attributes atts = manifest.getMainAttributes();
		// 输入所有的manifest信息
		Iterator ite = atts.keySet().iterator();
		while (ite.hasNext()) {
			Object key = ite.next();
			System.out.println(key + ":" + atts.getValue(key.toString()));
		}
		ZipEntry entry = null;
		while ((entry = in.getNextEntry()) != null) {
			// 输入每个文件的名称
			System.out.println(entry.getName());
			byte[] buffer = new byte[1024];
			int read;
			while ((read = in.read(buffer)) != -1) {// 输出文件内容
				System.out.println(new String(buffer));
			}
			in.closeEntry();
		}
		in.close();
	}
}
