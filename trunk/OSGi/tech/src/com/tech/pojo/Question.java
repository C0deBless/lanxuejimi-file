package com.tech.pojo;

import java.util.Set;
import java.util.TreeSet;

/**
 * Question entity. @author MyEclipse Persistence Tools
 */

public class Question implements java.io.Serializable {

	// Fields

	private Integer questionId;
	private QuestionReply questionReply;
	private Account account;
	private String questionTitle;
	private String questionDiscription;
	private String questionQuizTime;
	private String questionIsAnswer;
	private String questionAnswerAccountId;
	private String questionAnswerTime;
	private Set questionReplies = new TreeSet();

	// Constructors

	/** default constructor */
	public Question() {
	}

	/** minimal constructor */
	public Question(Integer questionId, Account account, String questionTitle,
			String questionDiscription, String questionQuizTime,
			String questionIsAnswer, String questionAnswerAccountId,
			String questionAnswerTime) {
		this.questionId = questionId;
		this.account = account;
		this.questionTitle = questionTitle;
		this.questionDiscription = questionDiscription;
		this.questionQuizTime = questionQuizTime;
		this.questionIsAnswer = questionIsAnswer;
		this.questionAnswerAccountId = questionAnswerAccountId;
		this.questionAnswerTime = questionAnswerTime;
	}

	/** full constructor */
	public Question(Integer questionId, QuestionReply questionReply,
			Account account, String questionTitle, String questionDiscription,
			String questionQuizTime, String questionIsAnswer,
			String questionAnswerAccountId, String questionAnswerTime,
			Set questionReplies) {
		this.questionId = questionId;
		this.questionReply = questionReply;
		this.account = account;
		this.questionTitle = questionTitle;
		this.questionDiscription = questionDiscription;
		this.questionQuizTime = questionQuizTime;
		this.questionIsAnswer = questionIsAnswer;
		this.questionAnswerAccountId = questionAnswerAccountId;
		this.questionAnswerTime = questionAnswerTime;
		this.questionReplies = questionReplies;
	}

	// Property accessors

	public Integer getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public QuestionReply getQuestionReply() {
		return this.questionReply;
	}

	public void setQuestionReply(QuestionReply questionReply) {
		this.questionReply = questionReply;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getQuestionTitle() {
		return this.questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public String getQuestionDiscription() {
		return this.questionDiscription;
	}

	public void setQuestionDiscription(String questionDiscription) {
		this.questionDiscription = questionDiscription;
	}

	public String getQuestionQuizTime() {
		return this.questionQuizTime;
	}

	public void setQuestionQuizTime(String questionQuizTime) {
		this.questionQuizTime = questionQuizTime;
	}

	public String getQuestionIsAnswer() {
		return this.questionIsAnswer;
	}

	public void setQuestionIsAnswer(String questionIsAnswer) {
		this.questionIsAnswer = questionIsAnswer;
	}

	public String getQuestionAnswerAccountId() {
		return this.questionAnswerAccountId;
	}

	public void setQuestionAnswerAccountId(String questionAnswerAccountId) {
		this.questionAnswerAccountId = questionAnswerAccountId;
	}

	public String getQuestionAnswerTime() {
		return this.questionAnswerTime;
	}

	public void setQuestionAnswerTime(String questionAnswerTime) {
		this.questionAnswerTime = questionAnswerTime;
	}

	public Set getQuestionReplies() {
		return this.questionReplies;
	}

	public void setQuestionReplies(Set questionReplies) {
		this.questionReplies = questionReplies;
	}

}