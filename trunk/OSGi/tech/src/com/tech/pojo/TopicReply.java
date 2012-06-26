package com.tech.pojo;

/**
 * TopicReply entity. @author MyEclipse Persistence Tools
 */

public class TopicReply implements java.io.Serializable {

	// Fields

	private Integer topicReplyId;
	private Account account;
	private String topicReplyText;
	private Integer topicReplyTopicId;
	private String topicReplyToAccountId;
	private String topicReplyTime;

	// Constructors

	/** default constructor */
	public TopicReply() {
	}

	/** minimal constructor */
	public TopicReply(Integer topicReplyId, Account account,
			String topicReplyText, Integer topicReplyTopicId,
			String topicReplyTime) {
		this.topicReplyId = topicReplyId;
		this.account = account;
		this.topicReplyText = topicReplyText;
		this.topicReplyTopicId = topicReplyTopicId;
		this.topicReplyTime = topicReplyTime;
	}

	/** full constructor */
	public TopicReply(Integer topicReplyId, Account account,
			String topicReplyText, Integer topicReplyTopicId,
			String topicReplyToAccountId, String topicReplyTime) {
		this.topicReplyId = topicReplyId;
		this.account = account;
		this.topicReplyText = topicReplyText;
		this.topicReplyTopicId = topicReplyTopicId;
		this.topicReplyToAccountId = topicReplyToAccountId;
		this.topicReplyTime = topicReplyTime;
	}

	// Property accessors

	public Integer getTopicReplyId() {
		return this.topicReplyId;
	}

	public void setTopicReplyId(Integer topicReplyId) {
		this.topicReplyId = topicReplyId;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getTopicReplyText() {
		return this.topicReplyText;
	}

	public void setTopicReplyText(String topicReplyText) {
		this.topicReplyText = topicReplyText;
	}

	public Integer getTopicReplyTopicId() {
		return this.topicReplyTopicId;
	}

	public void setTopicReplyTopicId(Integer topicReplyTopicId) {
		this.topicReplyTopicId = topicReplyTopicId;
	}

	public String getTopicReplyToAccountId() {
		return this.topicReplyToAccountId;
	}

	public void setTopicReplyToAccountId(String topicReplyToAccountId) {
		this.topicReplyToAccountId = topicReplyToAccountId;
	}

	public String getTopicReplyTime() {
		return this.topicReplyTime;
	}

	public void setTopicReplyTime(String topicReplyTime) {
		this.topicReplyTime = topicReplyTime;
	}

}