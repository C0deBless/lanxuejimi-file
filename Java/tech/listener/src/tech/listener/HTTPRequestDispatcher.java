/*
 * Copyright (c) 2009, 2010, 2011, 2012, B3log Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tech.listener;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tech.renderer.AbstractHTTPResponseRenderer;
import tech.renderer.HTTP404Renderer;
import tech.renderer.StaticResources;
import tech.util.Stopwatchs;
import tech.util.Strings;

public final class HTTPRequestDispatcher extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger
			.getLogger(HTTPRequestDispatcher.class.getName());
	private static final String COMMON_DEFAULT_SERVLET_NAME = "default";
	private static final String GAE_DEFAULT_SERVLET_NAME = "_ah_default";
	private static final String RESIN_DEFAULT_SERVLET_NAME = "resin-file";
	private static final String WEBLOGIC_DEFAULT_SERVLET_NAME = "FileServlet";
	private static final String WEBSPHERE_DEFAULT_SERVLET_NAME = "SimpleFileServlet";
	private String defaultServletName;

	private static String contextPath;

	public static String contentPath() {
		return contextPath;
	}

	@Override
	public void init() throws ServletException {
		Stopwatchs.start("Discovering Request Processors");
		try {
			LOGGER.info("Discovering request processors....");
			RequestProcessors.discover();
			LOGGER.info("Discovered request processors");
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Initializes request processors failed", e);
		} finally {
			Stopwatchs.end();
		}

		final ServletContext servletContext = getServletContext();
		contextPath = servletContext.getContextPath();
		if (servletContext.getNamedDispatcher(COMMON_DEFAULT_SERVLET_NAME) != null) {
			defaultServletName = COMMON_DEFAULT_SERVLET_NAME;
		} else if (servletContext.getNamedDispatcher(GAE_DEFAULT_SERVLET_NAME) != null) {
			defaultServletName = GAE_DEFAULT_SERVLET_NAME;
		} else if (servletContext
				.getNamedDispatcher(RESIN_DEFAULT_SERVLET_NAME) != null) {
			defaultServletName = RESIN_DEFAULT_SERVLET_NAME;
		} else if (servletContext
				.getNamedDispatcher(WEBLOGIC_DEFAULT_SERVLET_NAME) != null) {
			defaultServletName = WEBLOGIC_DEFAULT_SERVLET_NAME;
		} else if (servletContext
				.getNamedDispatcher(WEBSPHERE_DEFAULT_SERVLET_NAME) != null) {
			defaultServletName = WEBSPHERE_DEFAULT_SERVLET_NAME;
		} else {
			throw new IllegalStateException(
					"Unable to locate the default servlet for serving static content. "
							+ "Please set the 'defaultServletName' property explicitly.");
		}

		LOGGER.log(Level.CONFIG,
				"The default servlet for serving static resource is [{0}]",
				defaultServletName);
	}

	@Override
	protected void service(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		final String resourcePath = request.getPathTranslated();
		final String requestURI = request.getRequestURI();

		LOGGER.log(Level.FINEST,
				"Request[contextPath={0}, pathTranslated={1}, requestURI={2}]",
				new Object[] { request.getContextPath(), resourcePath,
						requestURI });

		final long startTimeMillis = System.currentTimeMillis();
		request.setAttribute(Keys.HttpRequest.START_TIME_MILLIS,
				startTimeMillis);

		// TODO cache page here
		// if (Latkes.isPageCacheEnabled()) {
		// final String queryString = request.getQueryString();
		// String pageCacheKey = (String) request
		// .getAttribute(Keys.PAGE_CACHE_KEY);
		// if (Strings.isEmptyOrNull(pageCacheKey)) {
		// pageCacheKey = PageCaches.getPageCacheKey(requestURI,
		// queryString);
		// request.setAttribute(Keys.PAGE_CACHE_KEY, pageCacheKey);
		// }
		// }

		// Encoding configuration to filter/EncodingFilter

		final HTTPRequestContext context = new HTTPRequestContext();
		context.setRequest(request);
		context.setResponse(response);

		dispatch(context);
	}

	public static void dispatch(final HTTPRequestContext context)
			throws ServletException, IOException {
		final HttpServletRequest request = context.getRequest();

		String requestURI = (String) request
				.getAttribute(Keys.HttpRequest.REQUEST_URI);
		if (Strings.isEmptyOrNull(requestURI)) {
			requestURI = request.getRequestURI();
		}

		String method = (String) request
				.getAttribute(Keys.HttpRequest.REQUEST_METHOD);
		if (Strings.isEmptyOrNull(method)) {
			method = request.getMethod();
		}

		LOGGER.log(Level.FINER, "Request[requestURI={0}, method={1}]",
				new Object[] { requestURI, method });

		try {
			RequestProcessors.invoke(requestURI, contextPath, method, context);
		} catch (final Exception e) {
			// final String exceptionTypeName = e.getClass().getName();
			// LOGGER.log(
			// Level.FINER,
			// "Occured error while processing request[requestURI={0}, method={1}, exceptionTypeName={2}, errorMsg={3}]",
			// new Object[] { requestURI, method, exceptionTypeName,
			// e.getMessage() });
			// if ("com.google.apphosting.api.ApiProxy$OverQuotaException"
			// .equals(exceptionTypeName)) {
			// PageCaches.removeAll();
			//
			// context.getResponse().sendError(
			// HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			// return;
			// }
			//
			throw new ServletException(e);
		} catch (final Error e) {
			final Runtime runtime = Runtime.getRuntime();
			LOGGER.log(Level.FINER,
					"Memory status[total={0}, max={1}, free={2}]",
					new Object[] { runtime.totalMemory(), runtime.maxMemory(),
							runtime.freeMemory() });

			LOGGER.log(Level.SEVERE, e.getMessage(), e);

			throw e;
		}

		// XXX: processor method ret?

		final HttpServletResponse response = context.getResponse();
		if (response.isCommitted()) { // Sends rdirect or send error
			final PrintWriter writer = response.getWriter();
			writer.flush();
			writer.close();

			return;
		}

		AbstractHTTPResponseRenderer renderer = context.getRenderer();

		if (null == renderer) {
			renderer = new HTTP404Renderer();
		}

		renderer.render(context);
	}

}
