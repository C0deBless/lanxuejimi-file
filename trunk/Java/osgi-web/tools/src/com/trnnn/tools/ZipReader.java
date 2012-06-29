package com.trnnn.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipReader {

	private String sourcePath;
	private String targetPath;

	/*
	 * public ZipReader() {
	 * 
	 * }
	 */

	public ZipReader(String sourcePath, String targetPath) {
		this.sourcePath = sourcePath;
		this.targetPath = targetPath;
	}

	public void read() throws Exception {
		this.read(this.sourcePath);
	}

	public void read(String sourcePath) throws Exception {
		File sourceFile = new File(sourcePath);
		ZipInputStream in = new ZipInputStream(new FileInputStream(sourceFile));
		String targetFullPath = this.targetPath;
		File targetFullPathFile = new File(targetFullPath);
		if (targetFullPathFile.isDirectory() && !targetFullPathFile.exists()) {
			targetFullPathFile.mkdirs();
		}

		ZipEntry entry = null;
		while ((entry = in.getNextEntry()) != null) {
			// 输入每个文件的名称
			String entryFullPath = (targetFullPath + entry.getName()).replace(
					"/", "\\");

			if (!entry.isDirectory()) {
				System.out.println("Entry:::::" + entry.getName());

				String rootPath = entryFullPath.substring(0,
						entryFullPath.lastIndexOf("\\"));
				File rootPathFile = new File(rootPath);
				if (!rootPathFile.exists()) {
					rootPathFile.mkdirs();
				}

				File outputPathFile = new File(entryFullPath);

				if (!outputPathFile.exists()) {
					outputPathFile.createNewFile();
				}
				FileOutputStream fileOutput = new FileOutputStream(
						outputPathFile);
				byte[] buffer = new byte[1024];
				int read;
				while ((read = in.read(buffer)) != -1) {// 输出文件内容
					// System.out.println(new String(buffer));
					fileOutput.write(buffer, 0, read);
					fileOutput.flush();
				}
				fileOutput.close();
			} else {
				File entryFullPathFile = new File(entryFullPath);
				if (!entryFullPathFile.exists()) {
					entryFullPathFile.mkdirs();
				}
			}
			in.closeEntry();
		}
		in.close();
	}

	public static void main(String[] args) throws Exception {
		ZipReader reader = new ZipReader("d:\\webtest.zip", "d:\\");
		reader.read();
	}
}
