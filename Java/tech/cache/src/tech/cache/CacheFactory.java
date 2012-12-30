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
package tech.cache;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class CacheFactory {

	private static final Logger LOGGER = Logger.getLogger(CacheFactory.class
			.getName());
	private static final Map<String, Cache<String, ?>> CACHES = Collections
			.synchronizedMap(new HashMap<String, Cache<String, ?>>());

	public static synchronized void removeAll() {
		for (final Map.Entry<String, Cache<String, ?>> entry : CACHES
				.entrySet()) {
			final Cache<String, ?> cache = entry.getValue();
			cache.removeAll();
			LOGGER.log(Level.FINEST, "Clears cache[name={0}]", entry.getKey());
		}
	}

	@SuppressWarnings("unchecked")
	public static synchronized Cache<String, ? extends Serializable> getCache(
			final String cacheName) {
		LOGGER.log(Level.INFO, "Constructing Cache[name={0}]....", cacheName);

		Cache<String, ?> ret = CACHES.get(cacheName);

		try {
			final Class<Cache<String, ?>> localLruCache = (Class<Cache<String, ?>>) Class
					.forName("org.b3log.latke.cache.local.memory.LruMemoryCache");
			ret = localLruCache.newInstance();
		} catch (final Exception e) {
			throw new RuntimeException("Can not get cache: " + e.getMessage(),
					e);
		}
		LOGGER.log(Level.INFO, "Constructed Cache[name={0}]", cacheName);

		return (Cache<String, Serializable>) ret;
	}

	/**
	 * Private default constructor.
	 */
	private CacheFactory() {
	}
}
