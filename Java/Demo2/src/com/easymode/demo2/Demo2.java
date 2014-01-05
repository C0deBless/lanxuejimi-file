package com.easymode.demo2;

public class Demo2 {
	public static void main(String[] args) {

		for (int n = 100; n <= Integer.MAX_VALUE; n++) {
			int sum = sum(n);
			if (n == sum) {
				System.out.println(sum);
			}
		}

	}

	public static int pow(int a, int b) {
		int sum = 1;
		for (int n = 1; n <= b; n++) {
			sum *= a;
		}
		return sum;

	}

	public static int sum(int a) {
		int c = numCount(a), sum = 0;
		for (int n = c, z = a; n >= 1; n--) {
			int b = z / pow(10, n - 1);
			z %= pow(10, n - 1);

			sum += pow(b, c);
		}
		return sum;
	}

	public static int numCount(int a) {
		int c = 1;
		for (int n = 1; n < 99; n++) {
			int b = a / pow(10, n - 1);
			if (b == 0) {
				c = n - 1;
				break;
			}
		}
		return c;
	}
}
