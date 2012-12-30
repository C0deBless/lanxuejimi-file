package tech.cache;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import tech.util.Strings;
import tech.util.Templates;

@SuppressWarnings("unchecked")
public final class PageCaches {

	private static final Logger LOGGER = Logger.getLogger(PageCaches.class
			.getName());
	private static final Cache<String, Serializable> CACHE;
	private static final Set<String> KEYS = new HashSet<String>();
	private static final int MAX_CACHEABLE_PAGE_CNT = 10240;
	public static final String PAGE_CACHE_NAME = "page";
	public static final String CACHED_TIME = "cachedTime";
	public static final String CACHED_BYTES_LENGTH = "cachedBtypesLength";
	public static final String CACHED_HIT_COUNT = "cachedHitCount";
	public static final String CACHED_TITLE = "cachedTitle";
	public static final String CACHED_OID = "cachedOid";
	public static final String CACHED_CONTENT = "cachedContent";
	public static final String CACHED_PWD = "cachedPwd";
	public static final String CACHED_TYPE = "cachedType";
	public static final String CACHED_LINK = "cachedLink";

	static {
		CACHE = (Cache<String, Serializable>) CacheFactory
				.getCache(PAGE_CACHE_NAME);
		CACHE.setMaxCount(MAX_CACHEABLE_PAGE_CNT);
		LOGGER.log(Level.INFO, "Initialized page cache[maxCount={0}]",
				MAX_CACHEABLE_PAGE_CNT);
	}

	public static String getPageCacheKey(final String uri,
			final String queryString) {
		String ret = uri;

		try {
			if (!Strings.isEmptyOrNull(queryString)) {
				ret += "?" + queryString;
			}

			return ret;
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}

		return ret;
	}

	public static Set<String> getKeys() {
		syncKeys();
		return KEYS;
	}

	public static Cache<String, ?> getCache() {
		return CACHE;
	}

	public static void removeAll() {
		CacheFactory.removeAll();
		Templates.CACHE.clear();

		KEYS.clear();
		LOGGER.info("Removed all cache....");
	}

	private static void syncKeys() {
		final Iterator<String> iterator = KEYS.iterator();
		final Set<String> toRemove = new HashSet<String>();

		while (iterator.hasNext()) {
			final String key = iterator.next();

			if (!CACHE.contains(key)) {
				toRemove.add(key);
				// iterator.remove() will also throw
				// ConcurrentModificationException on GAE
			}
		}

		if (!toRemove.isEmpty()) {
			KEYS.removeAll(toRemove);
			LOGGER.log(Level.FINER, "Removed page cache keys[{0}] for sync",
					toRemove);
		}
	}

	private PageCaches() {
	}
}
