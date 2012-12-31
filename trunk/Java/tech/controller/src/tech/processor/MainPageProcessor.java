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
public class MainPageProcessor {

	@RequestProcessing(value = "/", method = HTTPRequestMethod.GET)
	public void html(final HTTPRequestContext context,
			final HttpServletRequest request, final HttpServletResponse response) {
		AbstractFreeMarkerRenderer renderer = new FrontRenderer();
		context.setRenderer(renderer);
		renderer.setTemplateName("index.ftl");
	}
}
