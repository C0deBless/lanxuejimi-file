package tech.listener;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import tech.config.Config;
import tech.renderer.StaticResources;
import tech.service.ServiceFactory;
import tech.util.Templates;

public class StartUpServletListener implements ServletContextListener,
		ServletRequestListener, HttpSessionListener {
	private static final Logger LOGGER = Logger
			.getLogger(StartUpServletListener.class.getName());

	@Override
	public void requestInitialized(final ServletRequestEvent servletRequestEvent) {
		final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequestEvent
				.getServletRequest();
		// final String requestURI = httpServletRequest.getRequestURI();
		// Stopwatchs.start("Request Initialized[requestURI=" + requestURI +
		// "]");
		//
		// if (Requests.searchEngineBotRequest(httpServletRequest)) {
		// LOGGER.log(Level.FINER,
		// "Request made from a search engine[User-Agent={0}]",
		// httpServletRequest.getHeader("User-Agent"));
		// httpServletRequest.setAttribute(
		// Keys.HttpRequest.IS_SEARCH_ENGINE_BOT, true);
		// } else {
		// // Gets the session of this request
		// final HttpSession session = httpServletRequest.getSession();
		// LOGGER.log(
		// Level.FINE,
		// "Gets a session[id={0}, remoteAddr={1}, User-Agent={2}, isNew={3}]",
		// new Object[] { session.getId(),
		// httpServletRequest.getRemoteAddr(),
		// httpServletRequest.getHeader("User-Agent"),
		// session.isNew() });
		// // Online visitor count
		// // Statistics.onlineVisitorCount(httpServletRequest);
		// }

		resolveSkinDir(httpServletRequest);
		initFreemarketTemplates();
	}

	private void initFreemarketTemplates() {
		final String webRootPath = Config.webRoot();
		try {
			Templates.MAIN_CFG.setDirectoryForTemplateLoading(new File(
					webRootPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

	}

	private void resolveSkinDir(final HttpServletRequest httpServletRequest) {
		try {
			// final PreferenceRepository preferenceRepository =
			// PreferenceRepositoryImpl
			// .getInstance();
			// final JSONObject preference = preferenceRepository
			// .get(Preference.PREFERENCE);
			// if (null == preference) { // Did not initialize yet
			// return;
			// }

			// final String requestURI = httpServletRequest.getRequestURI();

			// String desiredView = Requests
			// .mobileSwitchToggle(httpServletRequest);

			// if (desiredView == null
			// && !Requests.mobileRequest(httpServletRequest)
			// || desiredView != null && desiredView.equals("normal")) {
			// desiredView = preference.getString(Skin.SKIN_DIR_NAME);
			// } else {
			// desiredView = "mobile";
			// LOGGER.log(Level.FINER,
			// "The request [URI={0}] comes frome mobile device",
			// requestURI);
			// }

			String defaultView = "";

			httpServletRequest
					.setAttribute(Keys.TEMAPLTE_DIR_NAME, defaultView);
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Resolves skin failed", e);
		}
	}

	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {

	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		String webRoot = arg0.getServletContext().getRealPath("/");
		Config.setWebRoot(webRoot);
		StaticResources.init();
		ServiceFactory.init();
	}

}
