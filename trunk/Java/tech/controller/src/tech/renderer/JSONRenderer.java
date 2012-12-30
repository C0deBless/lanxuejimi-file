package tech.renderer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import tech.listener.HTTPRequestContext;
import tech.util.JsonHelper;

public final class JSONRenderer extends AbstractHTTPResponseRenderer {

	private static final Logger logger = Logger.getLogger(JSONRenderer.class
			.getName());
	private String json;

	public void setObject(String json) {
		this.json = json;
	}

	public void setObject(Object obj) {
		this.json = JsonHelper.serialize(obj);
	}

	@Override
	public void render(final HTTPRequestContext context) {
		final HttpServletResponse response = context.getResponse();
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");

		try {
			if (this.json != null) {
				final PrintWriter writer = response.getWriter();
				writer.print(this.json);
				writer.close();
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, "FreeMarker renders error", e);

			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			} catch (final IOException ex) {
				logger.log(Level.SEVERE, "Can not send error 500!", ex);
			}
		}
	}
}
