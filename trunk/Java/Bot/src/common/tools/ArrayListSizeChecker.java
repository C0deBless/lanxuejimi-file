package common.tools;

import common.exception.ListSizeOverException;

public class ArrayListSizeChecker {

	private static final int LIST_MAX_SIZE = 100;

	public static void check(int size) {
		if (size > LIST_MAX_SIZE) {
			throw new ListSizeOverException();
		}
	}
}
