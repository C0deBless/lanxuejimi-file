package tech.processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tech.annotations.RequestProcessing;
import tech.annotations.RequestProcessor;
import tech.listener.HTTPRequestContext;
import tech.listener.HTTPRequestMethod;
import tech.renderer.AbstractFreeMarkerRenderer;
import tech.renderer.FrontRenderer;

@RequestProcessor
public class AcccountProcessor {

	@RequestProcessing(value = "/login", method = HTTPRequestMethod.POST)
	public void processLogin(final HTTPRequestContext context,
			final HttpServletRequest request, final HttpServletResponse response) {
		// String name = request.getParameter("userName");
		// String pwd = request.getParameter("userPwd");
		// String remember = request.getParameter("remember");

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

	@RequestProcessing(value = "/login", method = HTTPRequestMethod.GET)
	public void loginPage(final HTTPRequestContext context,
			final HttpServletRequest request, final HttpServletResponse response) {
		AbstractFreeMarkerRenderer renderer = new FrontRenderer();
		context.setRenderer(renderer);
		renderer.setTemplateName("login.ftl");
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
		String name = request.getParameter("userName");
		String pwd = request.getParameter("userPwd");
		String remember = request.getParameter("remember");
	}
}
