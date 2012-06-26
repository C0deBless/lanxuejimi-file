package com.tech.pojo;

/**
 * EssayType entity. @author MyEclipse Persistence Tools
 */

public class EssayType implements java.io.Serializable {

	// Fields

	private Integer essayTypeId;
	private String essayTypeName;

	// Constructors

	/** default constructor */
	public EssayType() {
	}

	/** full constructor */
	public EssayType(Integer essayTypeId, String essayTypeName) {
		this.essayTypeId = essayTypeId;
		this.essayTypeName = essayTypeName;
	}

	// Property accessors

	public Integer getEssayTypeId() {
		return this.essayTypeId;
	}

	public void setEssayTypeId(Integer essayTypeId) {
		this.essayTypeId = essayTypeId;
	}

	public String getEssayTypeName() {
		return this.essayTypeName;
	}

	public void setEssayTypeName(String essayTypeName) {
		this.essayTypeName = essayTypeName;
	}

}