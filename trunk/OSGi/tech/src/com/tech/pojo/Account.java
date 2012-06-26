package com.tech.pojo;

import java.util.Set;
import java.util.TreeSet;

/**
 * Account entity. @author MyEclipse Persistence Tools
 */

public class Account implements java.io.Serializable {

	// Fields

	private String accountId;
	private String accountName;
	private String accountPassword;
	private String accountNickName;
	private String accountGender;
	private String accountTechType;
	private String accountAvatarPath;
	private String accountFriendList;
	private String accountLastLoginTime;
	private String accountIsActive;
	private String accountDisable;
	private Integer accountVisits;
	private String accountLoginName;
	private String accountCompetence;
	private Set topics = new TreeSet();
	private Set topicReplies = new TreeSet();
	private Set essaies = new TreeSet();
	private Set essayRepliesForEssayReplyToAccountId = new TreeSet();
	private Set questionReplies = new TreeSet();
	private Set essayRepliesForEssayReplyAuthorId = new TreeSet();
	private Set questions = new TreeSet();

	// Constructors

	/** default constructor */
	public Account() {
	}

	/** minimal constructor */
	public Account(String accountId, String accountName,
			String accountPassword, String accountNickName,
			String accountGender, String accountTechType,
			String accountAvatarPath, String accountFriendList,
			String accountLastLoginTime, String accountIsActive,
			String accountDisable, Integer accountVisits,
			String accountLoginName, String accountCompetence) {
		this.accountId = accountId;
		this.accountName = accountName;
		this.accountPassword = accountPassword;
		this.accountNickName = accountNickName;
		this.accountGender = accountGender;
		this.accountTechType = accountTechType;
		this.accountAvatarPath = accountAvatarPath;
		this.accountFriendList = accountFriendList;
		this.accountLastLoginTime = accountLastLoginTime;
		this.accountIsActive = accountIsActive;
		this.accountDisable = accountDisable;
		this.accountVisits = accountVisits;
		this.accountLoginName = accountLoginName;
		this.accountCompetence = accountCompetence;
	}

	/** full constructor */
	public Account(String accountId, String accountName,
			String accountPassword, String accountNickName,
			String accountGender, String accountTechType,
			String accountAvatarPath, String accountFriendList,
			String accountLastLoginTime, String accountIsActive,
			String accountDisable, Integer accountVisits,
			String accountLoginName, String accountCompetence, Set topics,
			Set topicReplies, Set essaies,
			Set essayRepliesForEssayReplyToAccountId, Set questionReplies,
			Set essayRepliesForEssayReplyAuthorId, Set questions) {
		this.accountId = accountId;
		this.accountName = accountName;
		this.accountPassword = accountPassword;
		this.accountNickName = accountNickName;
		this.accountGender = accountGender;
		this.accountTechType = accountTechType;
		this.accountAvatarPath = accountAvatarPath;
		this.accountFriendList = accountFriendList;
		this.accountLastLoginTime = accountLastLoginTime;
		this.accountIsActive = accountIsActive;
		this.accountDisable = accountDisable;
		this.accountVisits = accountVisits;
		this.accountLoginName = accountLoginName;
		this.accountCompetence = accountCompetence;
		this.topics = topics;
		this.topicReplies = topicReplies;
		this.essaies = essaies;
		this.essayRepliesForEssayReplyToAccountId = essayRepliesForEssayReplyToAccountId;
		this.questionReplies = questionReplies;
		this.essayRepliesForEssayReplyAuthorId = essayRepliesForEssayReplyAuthorId;
		this.questions = questions;
	}

	// Property accessors

	public String getAccountId() {
		return this.accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountPassword() {
		return this.accountPassword;
	}

	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}

	public String getAccountNickName() {
		return this.accountNickName;
	}

	public void setAccountNickName(String accountNickName) {
		this.accountNickName = accountNickName;
	}

	public String getAccountGender() {
		return this.accountGender;
	}

	public void setAccountGender(String accountGender) {
		this.accountGender = accountGender;
	}

	public String getAccountTechType() {
		return this.accountTechType;
	}

	public void setAccountTechType(String accountTechType) {
		this.accountTechType = accountTechType;
	}

	public String getAccountAvatarPath() {
		return this.accountAvatarPath;
	}

	public void setAccountAvatarPath(String accountAvatarPath) {
		this.accountAvatarPath = accountAvatarPath;
	}

	public String getAccountFriendList() {
		return this.accountFriendList;
	}

	public void setAccountFriendList(String accountFriendList) {
		this.accountFriendList = accountFriendList;
	}

	public String getAccountLastLoginTime() {
		return this.accountLastLoginTime;
	}

	public void setAccountLastLoginTime(String accountLastLoginTime) {
		this.accountLastLoginTime = accountLastLoginTime;
	}

	public String getAccountIsActive() {
		return this.accountIsActive;
	}

	public void setAccountIsActive(String accountIsActive) {
		this.accountIsActive = accountIsActive;
	}

	public String getAccountDisable() {
		return this.accountDisable;
	}

	public void setAccountDisable(String accountDisable) {
		this.accountDisable = accountDisable;
	}

	public Integer getAccountVisits() {
		return this.accountVisits;
	}

	public void setAccountVisits(Integer accountVisits) {
		this.accountVisits = accountVisits;
	}

	public String getAccountLoginName() {
		return this.accountLoginName;
	}

	public void setAccountLoginName(String accountLoginName) {
		this.accountLoginName = accountLoginName;
	}

	public String getAccountCompetence() {
		return this.accountCompetence;
	}

	public void setAccountCompetence(String accountCompetence) {
		this.accountCompetence = accountCompetence;
	}

	public Set getTopics() {
		return this.topics;
	}

	public void setTopics(Set topics) {
		this.topics = topics;
	}

	public Set getTopicReplies() {
		return this.topicReplies;
	}

	public void setTopicReplies(Set topicReplies) {
		this.topicReplies = topicReplies;
	}

	public Set getEssaies() {
		return this.essaies;
	}

	public void setEssaies(Set essaies) {
		this.essaies = essaies;
	}

	public Set getEssayRepliesForEssayReplyToAccountId() {
		return this.essayRepliesForEssayReplyToAccountId;
	}

	public void setEssayRepliesForEssayReplyToAccountId(
			Set essayRepliesForEssayReplyToAccountId) {
		this.essayRepliesForEssayReplyToAccountId = essayRepliesForEssayReplyToAccountId;
	}

	public Set getQuestionReplies() {
		return this.questionReplies;
	}

	public void setQuestionReplies(Set questionReplies) {
		this.questionReplies = questionReplies;
	}

	public Set getEssayRepliesForEssayReplyAuthorId() {
		return this.essayRepliesForEssayReplyAuthorId;
	}

	public void setEssayRepliesForEssayReplyAuthorId(
			Set essayRepliesForEssayReplyAuthorId) {
		this.essayRepliesForEssayReplyAuthorId = essayRepliesForEssayReplyAuthorId;
	}

	public Set getQuestions() {
		return this.questions;
	}

	public void setQuestions(Set questions) {
		this.questions = questions;
	}

}