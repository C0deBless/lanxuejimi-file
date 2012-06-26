package com.tech.pojo;

/**
 * FileReference entity. @author MyEclipse Persistence Tools
 */

public class FileReference implements java.io.Serializable {

	// Fields

	private Integer fileId;
	private String fileName;
	private Integer fileReference;

	// Constructors

	/** default constructor */
	public FileReference() {
	}

	/** full constructor */
	public FileReference(Integer fileId, String fileName, Integer fileReference) {
		this.fileId = fileId;
		this.fileName = fileName;
		this.fileReference = fileReference;
	}

	// Property accessors

	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getFileReference() {
		return this.fileReference;
	}

	public void setFileReference(Integer fileReference) {
		this.fileReference = fileReference;
	}

}