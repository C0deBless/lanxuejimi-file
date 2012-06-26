package com.tech.bll.models;

import java.util.List;

import com.tech.pojo.Essay;

public class EssayListModel {
	public List<Essay> list;
	public int pageIndex;
	public int totalPage;
	public String accountId;
	public int pageCapacity;
	public List<Essay> getList() {
		return list;
	}
	public void setList(List<Essay> list) {
		for(int i=0;i<list.size();i++){
			Essay model=list.get(i);
			model.setAccount(null);
			model.setEssayText(null);
			model.setEssayReplies(null);
		}
		this.list = list;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public int getPageCapacity() {
		return pageCapacity;
	}
	public void setPageCapacity(int pageCapacity) {
		this.pageCapacity = pageCapacity;
	}
}
