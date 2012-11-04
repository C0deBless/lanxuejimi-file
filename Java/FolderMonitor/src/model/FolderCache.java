package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class FolderCache {

	final List<FileModel> fileList = new ArrayList<>();
	final String folderRoot;

	public FolderCache(String folderRoot) throws FileNotFoundException {
		if (folderRoot == null)
			throw new NullPointerException();
		File file = new File(folderRoot);
		if (!file.exists()) {
			throw new FileNotFoundException(folderRoot);
		}
		this.folderRoot = folderRoot;
	}
}
