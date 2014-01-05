package com.easymode.demo2;

public class Demo7 {
	public static void main(String[] args) {
		long a=1,sum=0;
		for(int n=1;n<=100;n++){
			a*=n;
			sum+=a;
			
		}
		System.out.println(sum);
	}
}
