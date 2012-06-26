package com.tech.pojo;

import java.util.Set;
import java.util.TreeSet;

/**
 * Essay entity. @author MyEclipse Persistence Tools
 */

public class Essay implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private Integer essayId;
	private Account account;
	private String essayTitle;
	private String essayText;
	private String essayType;
	private Integer essayTop;
	private Integer essayStep;
	private Integer essayBrowse;
	private String essayIsEssence;
	private String essayIsRecommend;
	private String essayPublishTime;
	private String essayEnableReply;
	private String essaySummary;
	private String essayLastModifiedDate;
	private int replyCount=0;
	private Set essayReplies = new TreeSet();

	// Constructors

	/** default constructor */
	public Essay() {
	}

	/** minimal constructor */
	public Essay(Integer essayId, Account account, String essayTitle,
			String essayText, String essayType, Integer essayTop,
			Integer essayStep, Integer essayBrowse, String essayIsEssence,
			String essayIsRecommend, String essayPublishTime,
			String essayEnableReply, String essaySummary) {
		this.essayId = essayId;
		this.account = account;
		this.essayTitle = essayTitle;
		this.essayText = essayText;
		this.essayType = essayType;
		this.essayTop = essayTop;
		this.essayStep = essayStep;
		this.essayBrowse = essayBrowse;
		this.essayIsEssence = essayIsEssence;
		this.essayIsRecommend = essayIsRecommend;
		this.essayPublishTime = essayPublishTime;
		this.essayEnableReply = essayEnableReply;
		this.essaySummary = essaySummary;
	}

	/** full constructor */
	public Essay(Integer essayId, Account account, String essayTitle,
			String essayText, String essayType, Integer essayTop,
			Integer essayStep, Integer essayBrowse, String essayIsEssence,
			String essayIsRecommend, String essayPublishTime,
			String essayEnableReply, String essaySummary,
			String essayLastModifiedDate, Set essayReplies) {
		this.essayId = essayId;
		this.account = account;
		this.essayTitle = essayTitle;
		this.essayText = essayText;
		this.essayType = essayType;
		this.essayTop = essayTop;
		this.essayStep = essayStep;
		this.essayBrowse = essayBrowse;
		this.essayIsEssence = essayIsEssence;
		this.essayIsRecommend = essayIsRecommend;
		this.essayPublishTime = essayPublishTime;
		this.essayEnableReply = essayEnableReply;
		this.essaySummary = essaySummary;
		this.essayLastModifiedDate = essayLastModifiedDate;
		this.essayReplies = essayReplies;
		if(essayReplies!=null){
			this.replyCount=essayReplies.size();
		}
	}

	// Property accessors

	public Integer getEssayId() {
		return this.essayId;
	}

	public void setEssayId(Integer essayId) {
		this.essayId = essayId;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getEssayTitle() {
		return this.essayTitle;
	}

	public void setEssayTitle(String essayTitle) {
		this.essayTitle = essayTitle;
	}

	public String getEssayText() {
		return this.essayText;
	}

	public void setEssayText(String essayText) {
		this.essayText = essayText;
	}

	public String getEssayType() {
		return this.essayType;
	}

	public void setEssayType(String essayType) {
		this.essayType = essayType;
	}

	public Integer getEssayTop() {
		return this.essayTop;
	}

	public void setEssayTop(Integer essayTop) {
		this.essayTop = essayTop;
	}

	public Integer getEssayStep() {
		return this.essayStep;
	}

	public void setEssayStep(Integer essayStep) {
		this.essayStep = essayStep;
	}

	public Integer getEssayBrowse() {
		return this.essayBrowse;
	}

	public void setEssayBrowse(Integer essayBrowse) {
		this.essayBrowse = essayBrowse;
	}

	public String getEssayIsEssence() {
		return this.essayIsEssence;
	}

	public void setEssayIsEssence(String essayIsEssence) {
		this.essayIsEssence = essayIsEssence;
	}

	public String getEssayIsRecommend() {
		return this.essayIsRecommend;
	}

	public void setEssayIsRecommend(String essayIsRecommend) {
		this.essayIsRecommend = essayIsRecommend;
	}

	public String getEssayPublishTime() {
		return this.essayPublishTime;
	}

	public void setEssayPublishTime(String essayPublishTime) {
		this.essayPublishTime = essayPublishTime;
	}

	public String getEssayEnableReply() {
		return this.essayEnableReply;
	}

	public void setEssayEnableReply(String essayEnableReply) {
		this.essayEnableReply = essayEnableReply;
	}

	public String getEssaySummary() {
		return this.essaySummary;
	}

	public void setEssaySummary(String essaySummary) {
		this.essaySummary = essaySummary;
	}

	public String getEssayLastModifiedDate() {
		return this.essayLastModifiedDate;
	}

	public void setEssayLastModifiedDate(String essayLastModifiedDate) {
		this.essayLastModifiedDate = essayLastModifiedDate;
	}

	public Set getEssayReplies() {
		return this.essayReplies;
	}

	public void setEssayReplies(Set essayReplies) {
		this.essayReplies = essayReplies;
		if(essayReplies!=null){
			this.replyCount=essayReplies.size();
		}
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}

	public int getReplyCount() {
		return replyCount;
	}

}