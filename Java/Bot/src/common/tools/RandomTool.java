package common.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomTool {
	private static final Random ran = new Random();

	public static int[] randNum(int num, int min, int max) {
		// ran.setSeed(System.currentTimeMillis());
		// Log.output("RandomTool.randNum","num="+num+", min="+min+", max="+max);
		ArrayList<Integer> list = new ArrayList<Integer>();
		// Random rand = new Random();
		while (true) {
			int rm = (ran.nextInt(max - min + 1) + min);
			if (!list.contains(rm)) {
				list.add(rm);
				if (list.size() >= num || list.size() >= (max - min + 1))
					break;
			}
		}
		int result[] = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			result[i] = list.get(i);
		}
		return result;
	}

	public static List<Integer> randNumAsList(int num, int min, int max) {
		int result[] = randNum(num, min, max);
		List<Integer> list = new ArrayList<>(result.length);
		for (int i : result) {
			list.add(i);
		}
		return list;
	}

	public static boolean isLucky(int rate) {
		// ran.setSeed(System.currentTimeMillis());
		if (rate <= 100) {
			int r = ran.nextInt(100) + 1;
			if (r <= rate) {
				return true;
			} else
				return false;
		} else if (rate <= 10000) {
			int r = ran.nextInt(10000) + 1;
			if (r <= rate) {
				return true;
			} else
				return false;
		}
		return false;
	}

	public static int nextInt(int size) {
		return ran.nextInt(size);
	}
	
	public static double nextDouble() {
		return ran.nextDouble();
	}
}
