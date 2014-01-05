package com.easymode.demo2;

public class Demo4 {

	public static void main(String[] args) {
		
		for (int i = 1; i <= 5; i++) {
			for (int j = 1; j <= i; j++) {
				System.out.print((j + 1) + ", ");
			}
			System.out.println();
		}

		int sum = 0;
		for (int i = 1; i <= 100; i++) {
			if (i % 2 == 0) {
				sum -= i;
			} else {
				sum += i;
			}
		}
		System.out.println(sum);
	 
	}
}
