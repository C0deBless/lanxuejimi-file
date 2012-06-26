package com.tech.pojo;

import java.util.Set;
import java.util.TreeSet;

/**
 * QuestionReply entity. @author MyEclipse Persistence Tools
 */

public class QuestionReply implements java.io.Serializable {

	// Fields

	private Integer questionReplyId;
	private Question question;
	private Account account;
	private String questionReplyText;
	private String questionReplyTime;
	private Set questions = new TreeSet();

	// Constructors

	/** default constructor */
	public QuestionReply() {
	}

	/** minimal constructor */
	public QuestionReply(Integer questionReplyId, Question question,
			Account account, String questionReplyText, String questionReplyTime) {
		this.questionReplyId = questionReplyId;
		this.question = question;
		this.account = account;
		this.questionReplyText = questionReplyText;
		this.questionReplyTime = questionReplyTime;
	}

	/** full constructor */
	public QuestionReply(Integer questionReplyId, Question question,
			Account account, String questionReplyText,
			String questionReplyTime, Set questions) {
		this.questionReplyId = questionReplyId;
		this.question = question;
		this.account = account;
		this.questionReplyText = questionReplyText;
		this.questionReplyTime = questionReplyTime;
		this.questions = questions;
	}

	// Property accessors

	public Integer getQuestionReplyId() {
		return this.questionReplyId;
	}

	public void setQuestionReplyId(Integer questionReplyId) {
		this.questionReplyId = questionReplyId;
	}

	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getQuestionReplyText() {
		return this.questionReplyText;
	}

	public void setQuestionReplyText(String questionReplyText) {
		this.questionReplyText = questionReplyText;
	}

	public String getQuestionReplyTime() {
		return this.questionReplyTime;
	}

	public void setQuestionReplyTime(String questionReplyTime) {
		this.questionReplyTime = questionReplyTime;
	}

	public Set getQuestions() {
		return this.questions;
	}

	public void setQuestions(Set questions) {
		this.questions = questions;
	}

}