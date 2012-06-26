package com.tech.actions;

import java.io.InputStream;
import java.net.URLDecoder;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.io.InputStreamProvider;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sdicons.json.mapper.MapperException;
import com.tech.bll.AccountService;
import com.tech.bll.JsonService;
import com.tech.bll.SessionService;
import com.tech.bll.models.SigninInfoModel;
import com.tech.bll.models.SigninStateModel;

@Namespace(value="/account")
@Results(
		@Result(name="error",type="dispatcher",location="/error.jsp")
)
public class Account extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public InputStream output;
	
	public String execute(){
		return "success";
	}
	
	/**
	 * @return
	 * @throws Exception 
	 */
	@Action(value="login",results={
			@Result(type="stream",params={"contentType","text/plain;charset=gb2312","inputName","output","bufferSize","1024"})
	})
	public String Login() throws Exception{
		HttpServletRequest request=ServletActionContext.getRequest();
		String userName=URLDecoder.decode(request.getParameter("userName"), "utf-8");
		String userPwd=request.getParameter("userPwd");
		String remember=request.getParameter("remember");
		AccountService as=new AccountService();
		SigninStateModel model=as.logon(userName, userPwd,remember);
		String status=model.toString();
		String json;
		if(model.equals(SigninStateModel.succeed)){
			SigninInfoModel signinModel=as.getSigninModelByName(userName);
			json=JsonService.serializeEx(signinModel,status,"");
		}
		else{
			json=JsonService.serializeEx(null, status, "");
		}
		//String json="{\"status\":\""+status+"\",\"model\":"+signInfo+"}";
		this.output=InputStreamProvider.getPlainTextInputStream(json);
		return "success";
	}
	/**
	 * @return
	 * @throws MapperException 
	 */
	@Action(value="logout",results={
			@Result(type="stream",params={"contentType","text/plain","inputName","output","bufferSize","1024"})
	})
	public String Logout() throws MapperException{
		String status;
		try{
			SessionService ss=new SessionService();
			ss.uniniSessionModel();
			status="succeed";
		}
		catch(Exception e){
			status="failed";
			e.printStackTrace();
		}
		//String json="{\"status\":\""+status+"\",\"model\":\"\"}";
		this.output=InputStreamProvider.getPlainTextInputStream(JsonService.serializeEx(null, status, ""));
		return "success";
	}
	/**
	 * @return
	 * @throws MapperException 
	 */
	@Action(value="checkusername",results={
			@Result(type="stream",params={"contentType","text/plain","inputName","output","bufferSize","1024"})
	})
	public String CheckUserName() throws MapperException{
		String status;
		String userName=ActionContext.getContext().getParameters().get("userName").toString();
		AccountService accountService=new AccountService();
		if(accountService.checkUserName(userName)){
			status="succeed";
		}
		else{
			status="failed";
		}
		//String json="{\"status\":\""+status+"\",\"model\":\"\"}";
		this.output=InputStreamProvider.getPlainTextInputStream(JsonService.serializeEx(null, status, ""));
		return "success";
	}
	/**
	 * @return
	 * @throws MapperException 
	 */
	@Action(value="checkuserloginname",results={
			@Result(type="stream",params={"contentType","text/plain","inputName","output","bufferSize","1024"})
	})
	public String CheckUserLoginName() throws MapperException{
		String status;
		String userLoginName=ActionContext.getContext().getParameters().get("userLoginName").toString();
		AccountService accountService=new AccountService();
		if(accountService.checkUserLoginName(userLoginName)){
			status="succeed";
		}
		else{
			status="failed";
		}
		//String json="{\"status\":\""+status+"\",\"model\":\"\"}";
		this.output=InputStreamProvider.getPlainTextInputStream(JsonService.serializeEx(null, status, ""));
		return "success";
	}
	/**
	 * @return
	 */
	@Action(value="register",results={
			@Result(name="success",type="dispatcher",location="/register.jsp"),
	})
	public String Register(){
		return "success";
	}
	/**
	 * @return
	 */
	@Action(value="addTempUser",results={
			@Result(name="success",type="dispatcher",location="/RegisterMessage.jsp"),
	})
	public String AddTempUser(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String userName= request.getParameter("username");
		String userPwd= request.getParameter("password");
		String userLoginName= request.getParameter("loginname");
		String userNickName= request.getParameter("nickname");
		String userTechType= request.getParameter("techtype");
		//TODO:在这验证数据
		//
		if(userName==null||userPwd==null
				||userLoginName==null||userNickName==null
				||userTechType==null){
			return "error";
		}
		AccountService as=new AccountService();
		com.tech.pojo.Account model=new com.tech.pojo.Account();
		model.setAccountAvatarPath("null");
		model.setAccountCompetence("normal");
		model.setAccountDisable("false");
		model.setAccountFriendList("null");
		model.setAccountGender("null");
		model.setAccountId(UUID.randomUUID().toString());
		model.setAccountIsActive("false");
		model.setAccountLastLoginTime("null");
		model.setAccountName(userName);
		model.setAccountNickName(userNickName);
		model.setAccountLoginName(userLoginName);
		model.setAccountPassword(userPwd);
		model.setAccountTechType(userTechType);
		model.setAccountVisits(0);
		try{
			as.addTempAccount(model);
		}
		catch(Exception e){
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
	@Action(value="activite",results={
			@Result(name="success",type="dispatcher",location="/ActiviteMessage.jsp"),
	})
	public String Activite(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String key=request.getParameter("key");
		AccountService as=new AccountService();
		if(as.checkIsActivited(key).equals("true")){
			request.getSession().setAttribute("activitestate", "failed");
		}
		else{
			request.getSession().setAttribute("activitestate", "succeed");
			as.activite(key);
		}
		return "success";
	}
}
