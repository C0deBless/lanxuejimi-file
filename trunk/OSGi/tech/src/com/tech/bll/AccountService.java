package com.tech.bll;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.securitytools.Encriptor;
import com.tech.bll.models.*;
import com.tech.dal.HibernateService;
import com.tech.pojo.*;

/**
 * @author Visual Studio
 *
 */

@SuppressWarnings("unchecked")
public class AccountService {
	private HibernateService _hibernate;
	private Session _session;
	public AccountService(){
		if(this._hibernate==null||this._session==null){
			this._hibernate=new HibernateService();
			this._session=this._hibernate.getSession();
		}
	}
	public Account getAccountModelByName(String name){
		List<Account> list = this._session.createCriteria(Account.class).add(Restrictions.eq("accountName", name)).list();
		if(list.size()>0){
			Account model=list.get(0);
			return model;
		}
		else{
			return null;
		}
	}
	public SigninStateModel logon(String name,String pwd,String remember) throws Exception{
		List<Account> list = this._session.createCriteria(Account.class).add(Restrictions.eq("accountName", name)).list();
		Account model=list.get(0);
		String realPwd=Encriptor.decrypt(model.getAccountPassword());
		try{
			pwd=Encriptor.decrypt(pwd);
		}
		catch(Exception e){
			
		}
		if(!realPwd.equals(pwd)){
			return SigninStateModel.failed;
		}
		else if(model.getAccountIsActive()=="false"){
			return SigninStateModel.unActivitied;
		}
		if(model.getAccountDisable()=="true"){
			return SigninStateModel.disabled;
		}
		SessionService sessionService=new SessionService();
		sessionService.iniSessionModel(name,remember);
		return SigninStateModel.succeed;
	}
	public SigninInfoModel getSigninModelByName(String userName){
		List<Account> list=this._session.createCriteria(Account.class).add(Restrictions.eq("accountName", userName)).list();
		if(list.size()>=1){
			Account model=list.get(0);
			return this.getSigninModelById(model);
		}
		else{
			return null;
		}
	}
	public SigninInfoModel getSigninModelByLoginName(String userLoginName){
		List<Account> list=this._session.createCriteria(Account.class).add(Restrictions.eq("accountLoginName", userLoginName)).list();
		if(list.size()>=1){
			Account model=list.get(0);
			return this.getSigninModelById(model);
		}
		else{
			return null;
		}
	}
	private SigninInfoModel getSigninModelById(Account model){
			SigninInfoModel _model=new SigninInfoModel();
			_model.setUserId(model.getAccountId());
			_model.setUserCompetence(model.getAccountCompetence());
			_model.setUserLogonName(model.getAccountLoginName());
			_model.setUserName(model.getAccountName());
			_model.setUserNickName(model.getAccountNickName());
			return _model;
	}
	/**
	 * @param userName
	 * @return
	 */
	public boolean checkUserName(String userName){
		List<Account> list=this._session.createCriteria(Account.class).add(Restrictions.eq("accountName", userName)).list();
		if(list.size()>=1){
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * @param userLoginName
	 * @return
	 */
	public boolean checkUserLoginName(String userLoginName){
		List<Account> list=this._session.createCriteria(Account.class).add(Restrictions.eq("accountLoginName", userLoginName)).list();
		if(list.size()>=1){
			return false;
		}
		else{
			return true;
		}
	}
	public boolean addTempAccount(Account model) throws Exception{
		model.setAccountPassword(Encriptor.encrypt(model.getAccountPassword()));
		this._session.save(model);
		this._hibernate.beginTransaction();
		MessageService.sendActiviteEmail(model.getAccountName(), model.getAccountNickName(), model.getAccountLoginName(),model.getAccountId());
		return false;
    }
	public boolean activite(String accountId){
    	try{
    		List<Account> list=this._session.createCriteria(Account.class).add(Restrictions.eq("accountId", accountId)).list();
        	if(list.size()>=1){
        		Account model=list.get(0);
        		model.setAccountIsActive("true");
        		this._session.update(model);
        		this._hibernate.beginTransaction();
        		return true;
        	}
    		return false;
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    }
	public String checkIsActivited(String accountId){
    	try{
    		List<Account> list=this._session.createCriteria(Account.class).add(Restrictions.eq("accountId", accountId)).list();
        	if(list.size()>=1){
        		Account model=list.get(0);
        		return model.getAccountIsActive();
        	}
        	else{
        		throw new Exception("Can't find specified row");
        	}
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return "false";
    	}
    }
    public boolean changePwd(String name,String oldPwd,String newPwd){
		return false;
    }
    public  boolean changeInformation(Account model){
		return false;
    }
    public boolean disableAccount(String accountId){
		return false;
    }
	public Account getAccountByAccontId(String accountId){
    	List<Account> list=this._session.createCriteria(Account.class).add(Restrictions.eq("accountId",accountId)).list();
    	if(list.size()>=1){
    		Account model=list.get(0);
    		return model;
    	}
		return null;
    }
	public Account getAccountByName(String accountName){
		List<Account> list=this._session.createCriteria(Account.class).add(Restrictions.eq("accountName",accountName)).list();
    	if(list.size()>=1){
    		Account model=list.get(0);
    		return model;
    	}
		return null;
	}
    public void finalize(){
    	this._hibernate.dispose();
    	this._session.close();
    }
}
