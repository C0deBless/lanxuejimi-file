package tech.renderer;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import tech.cache.PageCaches;
import tech.listener.HTTPRequestContext;
import tech.util.Strings;

public class CacheFreeMarkerRenderer extends AbstractFreeMarkerRenderer {

	static final Logger LOGGER = Logger.getLogger(CacheFreeMarkerRenderer.class
			.getName());

	@Override
	protected void beforeRender(final HTTPRequestContext context)
			throws Exception {
	}

	@Override
	protected void afterRender(final HTTPRequestContext context)
			throws Exception {
		// TODO cache page here
		// final HttpServletRequest request = context.getRequest();
		// final String pageContent = (String) request
		// .getAttribute(PageCaches.CACHED_CONTENT);
		//
		// if (null == pageContent) {
		// return;
		// }
		//
		// if (Config.isPageCacheEnabled()) {
		// final String cachedPageKey = (String) request
		// .getAttribute(Keys.PAGE_CACHE_KEY);
		// if (Strings.isEmptyOrNull(cachedPageKey)) {
		// return;
		// }
		//
		// LOGGER.log(Level.FINEST, "Caching page[cachedPageKey={0}]",
		// cachedPageKey);
		//
		// check(request, pageContent);
		//
		// final JSONObject cachedValue = new JSONObject();
		// cachedValue.put(PageCaches.CACHED_CONTENT, pageContent);
		// cachedValue.put(PageCaches.CACHED_TYPE,
		// request.getAttribute(PageCaches.CACHED_TYPE));
		// cachedValue.put(PageCaches.CACHED_OID,
		// request.getAttribute(PageCaches.CACHED_OID));
		// cachedValue.put(PageCaches.CACHED_TITLE,
		// request.getAttribute(PageCaches.CACHED_TITLE));
		// cachedValue.put(PageCaches.CACHED_LINK,
		// request.getAttribute(PageCaches.CACHED_LINK));
		// if (null != request.getAttribute(PageCaches.CACHED_PWD)) {
		// cachedValue.put(PageCaches.CACHED_PWD,
		// request.getAttribute(PageCaches.CACHED_PWD));
		// }
		//
		// PageCaches.put(cachedPageKey, cachedValue, request);
		// LOGGER.log(Level.FINEST, "Cached page[cachedPageKey={0}]",
		// cachedPageKey);
		// }
	}

	/**
	 * Checks if all conditions for caching page are ready by the specified
	 * request and content.
	 * 
	 * @param request
	 *            the specified request
	 * @param content
	 *            the specified content
	 */
	public static void check(final HttpServletRequest request,
			final String content) {
		if (Strings.isEmptyOrNull(content)
				|| Strings.isEmptyOrNull((String) request
						.getAttribute(PageCaches.CACHED_TYPE))
				|| Strings.isEmptyOrNull((String) request
						.getAttribute(PageCaches.CACHED_OID))
				|| Strings.isEmptyOrNull((String) request
						.getAttribute(PageCaches.CACHED_TITLE))
				|| Strings.isEmptyOrNull((String) request
						.getAttribute(PageCaches.CACHED_LINK))) {
			throw new IllegalArgumentException(
					"Illegal arguments for caching page, "
							+ "resolve this bug first!");
		}
	}
}
