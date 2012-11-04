package model;

import java.io.File;

public class FileModel {
	File file;
	String hash;

	private FileModel(File file) {
		if (file == null)
			throw new NullPointerException();
		this.file = file;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
