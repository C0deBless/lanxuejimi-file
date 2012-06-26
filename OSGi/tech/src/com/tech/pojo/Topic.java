package com.tech.pojo;

/**
 * Topic entity. @author MyEclipse Persistence Tools
 */

public class Topic implements java.io.Serializable {

	// Fields

	private Integer topicId;
	private Account account;
	private String topicTitle;
	private String topicDiscription;
	private String topicSponsorTime;
	private String topicIsClosed;

	// Constructors

	/** default constructor */
	public Topic() {
	}

	/** full constructor */
	public Topic(Integer topicId, Account account, String topicTitle,
			String topicDiscription, String topicSponsorTime,
			String topicIsClosed) {
		this.topicId = topicId;
		this.account = account;
		this.topicTitle = topicTitle;
		this.topicDiscription = topicDiscription;
		this.topicSponsorTime = topicSponsorTime;
		this.topicIsClosed = topicIsClosed;
	}

	// Property accessors

	public Integer getTopicId() {
		return this.topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getTopicTitle() {
		return this.topicTitle;
	}

	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}

	public String getTopicDiscription() {
		return this.topicDiscription;
	}

	public void setTopicDiscription(String topicDiscription) {
		this.topicDiscription = topicDiscription;
	}

	public String getTopicSponsorTime() {
		return this.topicSponsorTime;
	}

	public void setTopicSponsorTime(String topicSponsorTime) {
		this.topicSponsorTime = topicSponsorTime;
	}

	public String getTopicIsClosed() {
		return this.topicIsClosed;
	}

	public void setTopicIsClosed(String topicIsClosed) {
		this.topicIsClosed = topicIsClosed;
	}

}