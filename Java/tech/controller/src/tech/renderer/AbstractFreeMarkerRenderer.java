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
package tech.renderer;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tech.cache.PageCaches;
import tech.listener.HTTPRequestContext;
import tech.listener.Keys;
import tech.util.Templates;
import freemarker.template.Template;

/**
 * Abstract <a href="http://freemarker.org">FreeMarker</a> HTTP response
 * renderer.
 * 
 * @author <a href="mailto:DL88250@gmail.com">Liang Ding</a>
 * @version 1.0.0.7, Dec 3, 2011
 */
public abstract class AbstractFreeMarkerRenderer extends
		AbstractHTTPResponseRenderer {

	private static final Logger LOGGER = Logger
			.getLogger(AbstractFreeMarkerRenderer.class.getName());
	private String templateName;
	private Map<String, Object> dataModel = new HashMap<String, Object>();

	protected Template getTemplate(final String templateDirName,
			final String templateName) throws IOException {
		return Templates.getTemplate(templateDirName, templateName);
	}

	protected abstract void beforeRender(final HTTPRequestContext context)
			throws Exception;

	protected abstract void afterRender(final HTTPRequestContext context)
			throws Exception;

	@Override
	public void render(final HTTPRequestContext context) {
		final HttpServletResponse response = context.getResponse();

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		PrintWriter writer;
		try {
			writer = response.getWriter();
		} catch (final Exception e) {
			try {
				writer = new PrintWriter(response.getOutputStream());
			} catch (final IOException ex) {
				LOGGER.log(Level.SEVERE, "Can not get response writer", ex);
				return;
			}
		}

		if (response.isCommitted()) { // response has been sent redirect
			writer.flush();
			writer.close();

			return;
		}

		try {
			final HttpServletRequest request = context.getRequest();
			final Template template = getTemplate(
					(String) request.getAttribute(Keys.TEMAPLTE_DIR_NAME),
					templateName);

			if (Templates.hasExpression(template, "${request}")) {
				dataModel.put(Keys.REQUEST, request);
			}

			beforeRender(context);

			final String html = genHTML(context.getRequest(), dataModel,
					template);
			doRender(html, context.getRequest(), response);

			afterRender(context);
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "FreeMarker renders error", e);

			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} catch (final IOException ex) {
				LOGGER.log(Level.SEVERE, "Can not send error 500!", ex);
			}
		}
	}

	protected String genHTML(final HttpServletRequest request,
			final Map<String, Object> dataModel, final Template template)
			throws Exception {
		final StringWriter stringWriter = new StringWriter();
		template.setOutputEncoding("UTF-8");
		template.process(dataModel, stringWriter);

		final StringBuilder pageContentBuilder = new StringBuilder(
				stringWriter.toString());

		final String ret = pageContentBuilder.toString();

		request.setAttribute(PageCaches.CACHED_CONTENT, ret);

		return ret;
	}

	protected void doRender(final String html,
			final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		PrintWriter writer;
		try {
			writer = response.getWriter();
		} catch (final Exception e) {
			writer = new PrintWriter(response.getOutputStream());
		}

		if (response.isCommitted()) { // response has been sent redirect
			writer.flush();
			writer.close();

			return;
		}

		writer.write(html);
		writer.flush();
		writer.close();
	}

	public Map<String, Object> getDataModel() {
		return dataModel;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(final String templateName) {
		this.templateName = templateName;
	}
}
