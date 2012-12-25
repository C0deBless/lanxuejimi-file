package tech.renderer;

import javax.servlet.http.HttpServletResponse;

import tech.listener.HTTPRequestContext;

public final class DoNothingRenderer extends AbstractHTTPResponseRenderer {

	@Override
	public void render(final HTTPRequestContext context) {
		final HttpServletResponse response = context.getResponse();
		response.setStatus(HttpServletResponse.SC_OK);
	}
}
