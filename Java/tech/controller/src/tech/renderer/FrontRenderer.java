package tech.renderer;

import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tech.listener.HTTPRequestContext;
import tech.listener.Keys;

public final class FrontRenderer extends CacheFreeMarkerRenderer {

	private static final Logger LOGGER = Logger.getLogger(FrontRenderer.class
			.getName());

	// private Statistics statistics = Statistics.getInstance();

	@Override
	protected void beforeRender(final HTTPRequestContext context)
			throws Exception {
		LOGGER.log(Level.FINEST, "Before render....");
		// getDataModel().put(Common.TOP_BAR_REPLACEMENT_FLAG_KEY,
		// Common.TOP_BAR_REPLACEMENT_FLAG);
	}

	@Override
	protected void doRender(final String html,
			final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		LOGGER.log(Level.FINEST, "Do render....");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

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

		// final String pageContent = (String) request
		// .getAttribute(PageCaches.CACHED_CONTENT);
		String output = html;
		// if (null != pageContent) {
		// // Adds the top bar HTML content for output
		// final String topBarHTML = TopBars.getTopBarHTML(request, response);
		// output = html.replace(Common.TOP_BAR_REPLACEMENT_FLAG, topBarHTML);
		// }

		writer.write(output);
		writer.flush();
		writer.close();
	}

	@Override
	protected void afterRender(final HTTPRequestContext context)
			throws Exception {
		LOGGER.log(Level.FINEST, "After render....");

		// try {
		// statistics.incBlogViewCount(context.getRequest(),
		// context.getResponse());
		// } catch (final Exception e) {
		// LOGGER.log(Level.WARNING, "Incs blog view count failed", e);
		// }

		final HttpServletRequest request = context.getRequest();
		if ("mobile".equals((String) request
				.getAttribute(Keys.TEMAPLTE_DIR_NAME))) {
			// Skips page caching if requested by mobile device
			return;
		}

		super.afterRender(context);
	}
}
