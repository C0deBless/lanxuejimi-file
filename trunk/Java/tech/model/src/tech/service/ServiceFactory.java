package tech.service;

public class ServiceFactory {

	private static final TagService TAG_SERVICE = new TagService();
	private static final AccountService ACCOUNT_SERVICE = new AccountService();

	public static void init() {
		TAG_SERVICE.loadTag();
	}

	public static TagService getTagService() {
		return TAG_SERVICE;
	}

	public static AccountService getAccountService() {
		return ACCOUNT_SERVICE;
	}
}
