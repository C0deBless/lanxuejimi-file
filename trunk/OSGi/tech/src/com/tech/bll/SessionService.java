package com.tech.bll;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

import com.tech.bll.models.SessionModel;

public class SessionService {
	public void iniSessionModel(String name,String remember) throws UnsupportedEncodingException{
		HttpServletRequest request=ServletActionContext.getRequest();
		AccountService accountService=new AccountService();
		HttpSession session=request.getSession();
		Date currentDate=new Date();
		String userId=accountService.getAccountByName(name).getAccountId();
		session.setAttribute(SessionModel.UserId.toString(), userId);
		session.setAttribute(SessionModel.UserName.toString(), name);
		session.setAttribute(SessionModel.LoginDate.toString(), currentDate.toString());
		session.setAttribute(SessionModel.UserLoginName.toString(),accountService.getAccountByName(name).getAccountCompetence());
		session.setAttribute(SessionModel.UserCompetence.toString(), accountService.getAccountByName(name).getAccountCompetence());
		if(remember.equals("on")){
			HttpServletResponse response=ServletActionContext.getResponse();
			Cookie cookieName=new Cookie("name",URLEncoder.encode(name,"utf-8"));
			cookieName.setMaxAge(3600*24*30);
			cookieName.setPath("/");
			Cookie cookieKey =new Cookie("key",accountService.getAccountByName(name).getAccountPassword());
			cookieKey.setMaxAge(3600*24*30);
			cookieKey.setPath("/");
			response.addCookie(cookieName);
			response.addCookie(cookieKey);
		}
		
	}
	public void uniniSessionModel(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		session.removeAttribute(SessionModel.UserId.toString());
		session.removeAttribute(SessionModel.UserName.toString());
		session.removeAttribute(SessionModel.LoginDate.toString());
		session.removeAttribute(SessionModel.UserLoginName.toString());
		session.removeAttribute(SessionModel.UserCompetence.toString());
		HttpServletResponse response=ServletActionContext.getResponse();
		Cookie cookieName=new Cookie("name","");
		cookieName.setPath("/");
		cookieName.setMaxAge(0);
		Cookie cookieKey=new Cookie("key","");
		cookieKey.setPath("/");
		cookieKey.setMaxAge(0);
		response.addCookie(cookieName);
		response.addCookie(cookieKey);
		/*Cookie[] cookies=request.getCookies();
		for(int i=0;i<cookies.length;i++){
			if(cookies[i].getName().equals("name")||cookies[i].getName().equals("key")){
				cookies[i].setValue(null);
				cookies[0].setMaxAge(0);       
				response.addCookie(cookies[i]);  
			}
		}*/
		session.invalidate();
	}
	public static String getSession(SessionModel model){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String strSession;
		try{
			strSession=session.getAttribute(model.toString()).toString();
		}
		catch(Exception e){
			strSession="";
		}
		return strSession;
	}
	public static void setSession(SessionModel model,String value){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		session.setAttribute(model.toString(), value);
	}
	public static void removeSession(SessionModel model){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		session.removeAttribute(model.toString());
	}
}
