package com.tech.actions;

import java.io.InputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import com.io.InputStreamProvider;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sdicons.json.mapper.MapperException;
import com.tech.bll.AccountService;
import com.tech.bll.DateTimeUtil;
import com.tech.bll.EssayService;
import com.tech.bll.JsonService;
import com.tech.bll.models.EssayListModel;
import com.tech.bll.models.SessionModel;
import com.tech.pojo.Account;
import com.tech.pojo.Essay;
import com.tech.pojo.EssayReply;

@Namespace(value = "/blog")
public class Blog extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int essayId;
	private String replyText;
	private String toAccount;
	private String userName;
	private InputStream output;

	public int getEssayId() {
		return essayId;
	}

	public void setEssayId(int essayId) {
		this.essayId = essayId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Action(value = "addessay", results = { @Result(name = "success", type = "stream", params = {
			"inputName", "output", "contentType", "text/plain", "bufferSize",
			"1024" }) })
	public String AddEssay() {
		AccountService accountService = new AccountService();
		// TODO:delete this session
		ActionContext.getContext().getSession().put(
				SessionModel.UserName.toString(), "trnnn@qq.com");
		HttpServletRequest request = ServletActionContext.getRequest();

		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String category = request.getParameter("category");
		String summary = request.getParameter("summary");
		com.tech.pojo.Account account;
		if (request.getSession().getAttribute(SessionModel.UserName.toString()) != null) {
			account = accountService.getAccountModelByName(request.getSession()
					.getAttribute(SessionModel.UserName.toString()).toString());
		} else {
			return "error";
		}
		Date date = new Date();
		Essay essay = new Essay();
		essay.setAccount(account);
		essay.setEssaySummary(summary);
		essay.setEssayBrowse(0);
		essay.setEssayIsEssence("false");
		essay.setEssayIsRecommend("false");
		essay.setEssayEnableReply("true");
		essay.setEssayPublishTime(DateTimeUtil.getGMTTime(date));
		essay.setEssayLastModifiedDate(DateTimeUtil.getGMTTime(date));
		essay.setEssayStep(0);
		essay.setEssayText(content);
		essay.setEssayTitle(title);
		essay.setEssayTop(0);
		essay.setEssayType(category);
		new EssayService().addEssay(essay);
		this.output = InputStreamProvider.getPlainTextInputStream("success");
		return "success";
	}

	@Action(value = "editessay", results = { @Result(name = "success", type = "stream", params = {
			"inputName", "output", "contentType", "text/plain", "bufferSize",
			"1024" }) })
	public String editEssay() {
		AccountService accountService = new AccountService();
		ActionContext.getContext().getSession().put(
				SessionModel.UserName.toString(), "trnnn@qq.com");
		HttpServletRequest request = ServletActionContext.getRequest();
		String title = request.getParameter("title");
		int essayId = Integer.parseInt(request.getParameter("essayid"));
		String content = request.getParameter("content");
		String category = request.getParameter("category");
		String summary = request.getParameter("summary");
		com.tech.pojo.Account account;
		if (request.getSession().getAttribute(SessionModel.UserName.toString()) != null) {
			account = accountService.getAccountModelByName(request.getSession()
					.getAttribute(SessionModel.UserName.toString()).toString());
		} else {
			return "error";
		}
		Date date = new Date();
		Essay essay = new Essay();
		essay.setEssayId(essayId);
		essay.setAccount(account);
		essay.setEssaySummary(summary);
		essay.setEssayIsEssence("false");
		essay.setEssayIsRecommend("false");
		essay.setEssayEnableReply("true");
		essay.setEssayLastModifiedDate(DateTimeUtil.getGMTTime(date));
		essay.setEssayText(content);
		essay.setEssayTitle(title);
		essay.setEssayType(category);
		new EssayService().editEssay(essay);
		this.output = InputStreamProvider.getPlainTextInputStream("success");
		return "success";
	}

	@Action(value = "essaylist", results = { @Result(name = "success", type = "stream", params = {
			"inputName", "output", "contentType", "text/plain;charset=gb2312",
			"bufferSize", "1024" }) })
	public String getAssayList() throws MapperException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String accountId = request.getParameter("accountId");
		int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		int pageCapacity = Integer.parseInt(request
				.getParameter("pageCapacity"));
		EssayListModel model = new EssayService().getEssayList(accountId,
				pageIndex, pageCapacity);
		String json = JsonService.serializeEx(model, "success", "");
		// HttpServletResponse response = ServletActionContext.getResponse();
		// response.setContentType("text/html;charset=gb2312");
		this.output = InputStreamProvider.getPlainTextInputStream(json);
		return "success";
	}

	@Action(value = "essayeditor", results = { @Result(name = "success", type = "dispatcher", location = "/partialpage/essayeditor.jsp") })
	public String EssayEditor() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String mode = request.getParameter("mode");
		if (mode.equals("editessay")) {
			try {
				int essayId = Integer.parseInt(request.getParameter("essayid"));
				request.getSession().setAttribute("essay",
						new EssayService().getEssayById(essayId));
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		}
		return "success";
	}

	@Action(value = "deleteessay", results = { @Result(name = "success", type = "stream", params = {
			"inputName", "output", "contentType", "text/plain", "bufferSize",
			"1024" }) })
	public String deleteEssay() throws MapperException {
		try {
			new EssayService().deleteEssay(this.getEssayId());
			this.output = InputStreamProvider
					.getPlainTextInputStream(JsonService.serializeEx(null,
							"success", ""));
		} catch (Exception e) {
			e.printStackTrace();
			this.output = InputStreamProvider
					.getPlainTextInputStream(JsonService.serializeEx(null,
							"failed", e.getMessage()));
		}
		return "success";
	}

	@Action(value = "viewessay", results = { @Result(name = "success", type = "dispatcher", location = "/blog/viewessay.jsp") })
	public String viewEssay() {
		// ValueStack stack=ActionContext.getContext().getValueStack();
		Essay essay = new EssayService().getEssayById(essayId);
		// stack.setValue("essay", essay);
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("essay", essay);
		return "success";
	}

	@Action(value = "addreply", results = { @Result(name = "success", type = "stream", params = {
			"inputName", "output", "contentType", "text/plain", "bufferSize",
			"1024" }) })
	public String addReply() throws MapperException {
		if (ActionContext.getContext().getSession().get(
				SessionModel.UserId.toString()) != null) {
			String accountId = (String) ActionContext.getContext().getSession()
					.get(SessionModel.UserId.toString());
			
			EssayReply reply = new EssayReply();
			AccountService accountService = new AccountService();
			Account author = accountService.getAccountByAccontId(accountId);
			reply.setAccountByEssayReplyAuthorId(author);
			reply.setEssay(new EssayService().getEssayById(this.essayId));
			reply.setEssayReplyText(this.replyText);
			reply.setEssayReplyTime(DateTimeUtil.getGMTTime(new Date()));
			
			if (this.toAccount != null && !this.toAccount.equals("")) {
				Account to_account = accountService.getAccountByAccontId(this.toAccount);
				reply.setAccountByEssayReplyToAccountId(to_account);
				//System.out.println("设置了回复的用户");
			}

			
			new EssayService().addReply(reply);

			this.output = InputStreamProvider
					.getPlainTextInputStream(JsonService.serializeEx(null,
							"success", ""));
		} else {
			this.output = InputStreamProvider
					.getPlainTextInputStream(JsonService.serializeEx(null,
							"failed", "logoff"));
		}
		return "success";
	}

	@Action(value = "*", params = { "userName", "{1}" }, results = { @Result(name = "success", type = "dispatcher", location = "/blog/index.jsp") })
	public String index() {
		return "success";
	}

	public void setOutput(InputStream output) {
		this.output = output;
	}

	public InputStream getOutput() {
		return output;
	}

	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}

	public String getReplyText() {
		return replyText;
	}


	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}

	public String getToAccount() {
		return toAccount;
	}
}
