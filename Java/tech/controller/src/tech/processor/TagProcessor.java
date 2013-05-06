package tech.processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tech.annotations.RequestProcessing;
import tech.annotations.RequestProcessor;
import tech.cache.Global;
import tech.listener.HTTPRequestContext;
import tech.listener.HTTPRequestMethod;
import tech.renderer.JSONRenderer;
import tech.util.JsonHelper;

@RequestProcessor
public class TagProcessor {

	private String jsonCache = null;

	@RequestProcessing(value = "/menu.json", method = HTTPRequestMethod.GET)
	public void load(final HTTPRequestContext context,
			final HttpServletRequest request, final HttpServletResponse response) {
		if (jsonCache == null) {
			jsonCache = JsonHelper.serialize(Global.TAGS);
		}

		JSONRenderer renderer = new JSONRenderer();
		renderer.setObject(jsonCache);
		context.setRenderer(renderer);
	}
}
