package com.trnnn.tools;

import java.io.File;
import java.util.List;

public class FileParser {
	public List<File> getFiles(File inputFile, List<File> files, String ext) {
		File[] tmpFiles = inputFile.listFiles();
		for (File item : tmpFiles) {
			if (item.isDirectory()) {
				getFiles(item, files, ext);
			} else {
				if (ext != null && !ext.equals("") && ext != "*") {
					String fileExt = this.getFileExt(item.getName());
					// System.out.println(fileExt);
					if (fileExt.equalsIgnoreCase(ext)) {
						files.add(item);
					}
				} else {
					files.add(item);
				}
			}
		}
		return files;
	}

	public String getFileExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1,
				fileName.length());
	}

	public static void main(String[] args) {
		// /new FileParser().getFiles(new File("e:\\music"), new
		// ArrayList<File>(), "mp3");
	}
}
