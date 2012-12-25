package tech.renderer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import tech.listener.HTTPRequestContext;

public final class AtomRenderer extends AbstractHTTPResponseRenderer {

	private static final Logger LOGGER = Logger.getLogger(AtomRenderer.class
			.getName());
	private String content;

	public void setContent(final String content) {
		this.content = content;
	}

	@Override
	public void render(final HTTPRequestContext context) {
		try {
			final HttpServletResponse response = context.getResponse();
			response.setContentType("application/atom+xml");
			response.setCharacterEncoding("UTF-8");

			final PrintWriter writer = response.getWriter();
			writer.write(content);
			writer.close();
		} catch (final IOException e) {
			LOGGER.log(Level.SEVERE, "Render failed", e);
		}
	}
}
