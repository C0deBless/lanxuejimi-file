package com.tech.pojo;

/**
 * EssayReply entity. @author MyEclipse Persistence Tools
 */

public class EssayReply implements java.io.Serializable {

	// Fields

	private Integer essayReplyId;
	private Account accountByEssayReplyToAccountId;
	private Account accountByEssayReplyAuthorId;
	private Essay essay;
	private String essayReplyText;
	private String essayReplyTime;

	// Constructors

	/** default constructor */
	public EssayReply() {
	}

	/** minimal constructor */
	public EssayReply(Integer essayReplyId,
			Account accountByEssayReplyAuthorId, Essay essay,
			String essayReplyText, String essayReplyTime) {
		this.essayReplyId = essayReplyId;
		this.accountByEssayReplyAuthorId = accountByEssayReplyAuthorId;
		this.essay = essay;
		this.essayReplyText = essayReplyText;
		this.essayReplyTime = essayReplyTime;
	}

	/** full constructor */
	public EssayReply(Integer essayReplyId,
			Account accountByEssayReplyToAccountId,
			Account accountByEssayReplyAuthorId, Essay essay,
			String essayReplyText, String essayReplyTime) {
		this.essayReplyId = essayReplyId;
		this.accountByEssayReplyToAccountId = accountByEssayReplyToAccountId;
		this.accountByEssayReplyAuthorId = accountByEssayReplyAuthorId;
		this.essay = essay;
		this.essayReplyText = essayReplyText;
		this.essayReplyTime = essayReplyTime;
	}

	// Property accessors

	public Integer getEssayReplyId() {
		return this.essayReplyId;
	}

	public void setEssayReplyId(Integer essayReplyId) {
		this.essayReplyId = essayReplyId;
	}

	public Account getAccountByEssayReplyToAccountId() {
		return this.accountByEssayReplyToAccountId;
	}

	public void setAccountByEssayReplyToAccountId(
			Account accountByEssayReplyToAccountId) {
		this.accountByEssayReplyToAccountId = accountByEssayReplyToAccountId;
	}

	public Account getAccountByEssayReplyAuthorId() {
		return this.accountByEssayReplyAuthorId;
	}

	public void setAccountByEssayReplyAuthorId(
			Account accountByEssayReplyAuthorId) {
		this.accountByEssayReplyAuthorId = accountByEssayReplyAuthorId;
	}

	public Essay getEssay() {
		return this.essay;
	}

	public void setEssay(Essay essay) {
		this.essay = essay;
	}

	public String getEssayReplyText() {
		return this.essayReplyText;
	}

	public void setEssayReplyText(String essayReplyText) {
		this.essayReplyText = essayReplyText;
	}

	public String getEssayReplyTime() {
		return this.essayReplyTime;
	}

	public void setEssayReplyTime(String essayReplyTime) {
		this.essayReplyTime = essayReplyTime;
	}

	public Account getAccount(){
		return this.accountByEssayReplyAuthorId;
	}
}