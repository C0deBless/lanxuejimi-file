package tech.factory;

public class Factory {

	private static final TagFactory TAG_FACTORY = new TagFactory();

	public static void init() {
		TAG_FACTORY.loadTag();
	}

	public static TagFactory getTagFactory() {
		return TAG_FACTORY;
	}
}
