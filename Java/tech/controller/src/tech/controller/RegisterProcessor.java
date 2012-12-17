package tech.controller;

import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.b3log.latke.Keys;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.freemarker.AbstractFreeMarkerRenderer;
import org.b3log.solo.processor.ArticleProcessor;
import org.b3log.solo.processor.renderer.ConsoleRenderer;
import org.b3log.solo.processor.util.Filler;
import org.b3log.solo.service.PreferenceQueryService;
import org.json.JSONObject;

@RequestProcessor
public final class RegisterProcessor {
	static final Logger LOGGER = Logger.getLogger(ArticleProcessor.class
			.getName());
	private Filler filler = Filler.getInstance();
	private PreferenceQueryService preferenceQueryService = PreferenceQueryService
			.getInstance();

	@RequestProcessing(value = "/register", method = HTTPRequestMethod.GET)
	public void requestRegisterPage(final HTTPRequestContext context,
			final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		final AbstractFreeMarkerRenderer renderer = new ConsoleRenderer();
		context.setRenderer(renderer);
		renderer.setTemplateName("register.ftl");

		final Map<String, Object> dataModel = renderer.getDataModel();
		Keys.fillServer(dataModel);
		Keys.fillRuntime(dataModel);
		filler.fillMinified(dataModel);
		final JSONObject preference = preferenceQueryService.getPreference();
		filler.fillBlogHeader(request, dataModel, preference);
	}
}
