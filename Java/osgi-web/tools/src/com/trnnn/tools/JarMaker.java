package com.trnnn.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class JarMaker {

	public void make(String outputPath, String inputPath)
			throws FileNotFoundException, IOException {
		Manifest manifest = new Manifest();
		/*
		 * manifest.getMainAttributes().putValue("Manifest-Version", "1.0");
		 * manifest.getMainAttributes().putValue("author", "pony");
		 * manifest.getMainAttributes().putValue("blog",
		 * "http://pony.cnblogs.com");
		 */
		// JarOutputStream和JarInputStream是jar包生成时特有封装stream
		File outfile = new File(outputPath);
		JarOutputStream out = new JarOutputStream(
				new FileOutputStream(outfile), manifest);
		File f = new File(inputPath);

		List<File> sourceFiles = new ArrayList<File>();
		sourceFiles = new FileParser().getFiles(f, sourceFiles, "*");
		/************************ 将输入文件读取写入jar outputstream中 **********************************/
		for (File sourceFile : sourceFiles) {
			// JarEntry 是jar包目录类
			String entryPath = sourceFile.getPath();
			if (entryPath.indexOf(inputPath) == 0) {
				entryPath = entryPath.replace(inputPath, "");
				if (entryPath.indexOf(File.separator) == 0) {
					entryPath = entryPath.replace(File.separator, "");
				}
			}
			JarEntry entry = new JarEntry(new String(entryPath.getBytes(),
					"utf-8"));
			// 将目录加入到out中
			out.putNextEntry(entry);
			FileInputStream in = new FileInputStream(sourceFile);
			byte[] buffer = new byte[1024];
			int n = in.read(buffer);
			while (n != -1) {
				out.write(buffer, 0, n);
				n = in.read(buffer);
			}
			in.close();
			out.closeEntry();// 关闭目录
		}
		// 注意关闭输出文件流
		out.close();
	}

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		JarMaker jar = new JarMaker();
		jar.make("D:\\test.jar", "D:\\classses\\");
		/*
		 * List<File> files = jar.getFiles(new File("E:\\music"),new
		 * ArrayList<File>()); for (File file : files) {
		 * System.out.println(file.getPath()); }
		 */
	}

}
