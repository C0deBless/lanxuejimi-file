package com.tech.actions;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

@Results({
		@Result(name="error",type="dispatcher",location="/error.jsp"),
		@Result(name="success",type="dispatcher",location="/index.jsp")
})
public class Home extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Action(value="home",results={
			@Result(name="success",type="dispatcher",location="/index.jsp")
	})
	public String execute(){
		
		return "success";
	}
}
