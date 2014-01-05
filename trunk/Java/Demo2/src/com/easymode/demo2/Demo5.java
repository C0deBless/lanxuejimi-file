package com.easymode.demo2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Demo5 {

	public static void main(String[] args) throws IOException {
		while (true) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					System.in));
			String line = reader.readLine();
			if (line == null || line.equals("")) {
				continue;
			}
			String[] strs = line.split(" ");
			String str1 = strs[0];
			String str2 = strs[1];

			int value1 = Integer.parseInt(str1);
			int value2 = Integer.parseInt(str2);

			int min = Math.min(value1, value2);
			int max = Math.max(value1, value2);

			int sum = 0;
			for (int i = min; i <= max; i++) {
				sum += i;
			}

			System.out.println("和 等于 " + sum);
		}
	}
}

class Class2{
	
}