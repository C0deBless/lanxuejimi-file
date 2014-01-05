package com.easymode.demo2;

public class MainClass {
	public static void main(String[] args) {
		for (int i = 1; i < 10; i++) {
			for (int j = 1; j < 10; j++){
				if(i<j)
					continue;
				System.out.print(i + " x " + j + " = " + i * j + ", ");
			}
			System.out.println();
		}

	}
}
