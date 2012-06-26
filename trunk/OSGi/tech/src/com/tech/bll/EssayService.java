package com.tech.bll;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.tech.bll.models.EssayListModel;
import com.tech.bll.models.SessionModel;
import com.tech.dal.HibernateService;
import com.tech.pojo.Account;
import com.tech.pojo.Essay;
import com.tech.pojo.EssayReply;

@SuppressWarnings("unchecked")
public class EssayService {
	private HibernateService _hibernate;
	private Session _session;

	public EssayService() {
		if (this._hibernate == null || this._session == null) {
			this._hibernate = new HibernateService();
			this._session = this._hibernate.getSession();
		}
	}

	public void addEssay(Essay essay) {
		this._session.save(essay);
		this._hibernate.beginTransaction();
	}

	public void editEssay(Essay essay) {
		this._session.update(essay);
		this._hibernate.beginTransaction();
	}

	public void deleteEssay(int essayId) throws Exception {
		//先删除文章对应的评论，再删除文章
		List<Essay> list = this._session.createCriteria(Essay.class).add(
				Restrictions.eq("essayId", essayId)).list();
		if (list.size() > 0) {
			Essay model = list.get(0);
			Iterator<EssayReply> replylist=model.getEssayReplies().iterator();
			while(replylist.hasNext()){
				EssayReply reply=replylist.next();
				this._session.delete(reply);
			}
			//this._session.delete(model.getEssayReplies());
			this._session.delete(model);
			this._hibernate.beginTransaction();
		} else {
			throw new Exception("specified essay id is not found");
		}
	}

	public Essay getEssayById(int essayId) {
		List<Essay> list = this._session.createCriteria(Essay.class).add(
				Restrictions.eq("essayId", essayId)).list();
		if (list.size() >= 1) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public EssayListModel getEssayList(String accountId, int pageIndex,
			int pageCapacity) {
		int totalPage;
		if (accountId.equals(null) || accountId.equals("")) {
			accountId = SessionService.getSession(SessionModel.UserId);
			if (accountId.equals(null) || accountId.equals("")) {
				return null;
			}
		}
		Account account = (Account) this._session.createCriteria(Account.class)
				.add(Restrictions.eq("accountId", accountId)).list().get(0);
		Criteria essayCriteria = this._session.createCriteria(Essay.class).add(
				Restrictions.eq("account", account));
		List<Essay> list1 = essayCriteria.list();
		int total = list1.size();
		if (total % pageCapacity > 0) {
			totalPage = total / pageCapacity + 1;
		} else {
			totalPage = total / pageCapacity;
		}
		List<Essay> list = essayCriteria.setFirstResult(
				(pageIndex - 1) * pageCapacity).setMaxResults(pageCapacity)
				.list();
		EssayListModel model = new EssayListModel();
		model.setList(list);
		model.setAccountId(accountId);
		model.setPageIndex(pageIndex);
		model.setTotalPage(totalPage);
		model.setPageCapacity(pageCapacity);
		return model;
	}

	public void addReply(EssayReply reply) {
		//TODO:add essay reply
		this._session.save(reply);
		this._hibernate.beginTransaction();
	}
	
	public EssayReply getEssayReplyById(int id){
		List<EssayReply> replies=this._session.createCriteria(EssayReply.class).add(Restrictions.eq("essayReplyId", id)).list();
		if(replies.size()>0){
			return replies.get(0);
		}
		else{
			return null;
		}
	}
}
