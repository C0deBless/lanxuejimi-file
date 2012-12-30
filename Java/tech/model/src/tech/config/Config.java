package tech.config;

public class Config {

	private static boolean isPageCacheEnabled;
	private static String webRoot;

	public static void setPageCacheEnabled(boolean enable) {
		isPageCacheEnabled = enable;
	}

	public static boolean isPageCacheEnabled() {
		return isPageCacheEnabled;
	}

	public static String webRoot() {
		return webRoot;
	}

	public static void setWebRoot(String path) {
		webRoot = path;
	}
}
