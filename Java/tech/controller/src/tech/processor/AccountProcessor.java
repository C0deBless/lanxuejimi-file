package tech.processor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tech.annotations.RequestProcessing;
import tech.annotations.RequestProcessor;
import tech.listener.HTTPRequestContext;
import tech.listener.HTTPRequestMethod;
import tech.model.Account;
import tech.renderer.AbstractFreeMarkerRenderer;
import tech.renderer.FrontRenderer;
import tech.renderer.JSONRenderer;
import tech.service.AccountService;
import tech.service.ServiceFactory;

@RequestProcessor
public class AccountProcessor {

	static Logger logger = LoggerFactory.getLogger(AccountProcessor.class);

	@RequestProcessing(value = "/login", method = HTTPRequestMethod.POST)
	public void processLogin(final HTTPRequestContext context,
			final HttpServletRequest request, final HttpServletResponse response) {

		String email = request.getParameter("email");
		String pwd = request.getParameter("userPwd");
		@SuppressWarnings("unused")
		String remember = request.getParameter("remember");
		AccountService service = ServiceFactory.getAccountService();
		Account account = service.login(email, pwd);
		Map<String, Object> values = new HashMap<>();
		if (account == null) {
			values.put("res", -1);
		} else {
			values.put("res", 1);
			values.put("accunt", account);
		}
		JSONRenderer renderer = new JSONRenderer();
		renderer.setObject(values);
		context.setRenderer(renderer);
		// if (data.status == "succeed") {
		// showProgress("登录成功", function() {
		// window.setTimeout(function() {
		// hideProgress();
		// }, 1000);
		// });
		// $("#register").html("");
		// $("#signin").html(data.model.userNickName);
		// var a = document.createElement("a");
		// a.href = "javascript:void(0)";
		// a.onclick = logout;
		// a.innerHTML = "注销";
		// $("#register").append(a);
		// // install user data here
		// addData("userId", data.userId);
		// addData("userName", data.userName);
		// addData("userNickName", data.userNickName);
		// addData("userCompetence", data.userCompetence);
		// addData("userLogonName", data.userLogonName);
		// addData("userAvatarPath", data.userAvatarPath);
		// }
	}

	@RequestProcessing(value = "/register", method = HTTPRequestMethod.GET)
	public void registerPage(final HTTPRequestContext context,
			final HttpServletRequest request, final HttpServletResponse response) {
		AbstractFreeMarkerRenderer renderer = new FrontRenderer();
		context.setRenderer(renderer);
		renderer.setTemplateName("register.ftl");
	}

	@RequestProcessing(value = "/register.do", method = HTTPRequestMethod.POST)
	public void register(final HTTPRequestContext context,
			final HttpServletRequest request, final HttpServletResponse response) {
		logger.trace("AccountProcessor.register");
		String nick = request.getParameter("nick");
		String email = request.getParameter("email");
		String pwd = request.getParameter("pwd");
		String role = Account.ROLE_NORMAL;
		String locale = "en-us";
		AccountService service = ServiceFactory.getAccountService();
		JSONRenderer renderer = new JSONRenderer();
		context.setRenderer(renderer);
		if (!service.validateEmail(email)) {
			String json = "{\"res\":3}";
			renderer.setObject(json);
			return;
		}
		if (!service.validatePassword(pwd)) {
			String json = "{\"res\":4}";
			renderer.setObject(json);
			return;
		}
		if (service.checkUserEmailDuplicate(email)) {
			String json = "{\"res\":2}";
			renderer.setObject(json);
			return;
		} else {
			// do add account here
			service.register(nick, email, locale, role, pwd);
			String json = "{\"res\":1}";
			renderer.setObject(json);
			return;
		}
	}
}
