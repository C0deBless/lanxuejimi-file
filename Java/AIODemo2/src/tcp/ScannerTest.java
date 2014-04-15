package tcp;

import java.util.Scanner;

public class ScannerTest {
	public static void main(String[] args) throws InterruptedException {

		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				Scanner sc = new Scanner(System.in);
				int intValue1 = sc.nextInt();
				System.out.println("intValue1: " + intValue1);
				sc.close();
			}
		});
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				Scanner sc = new Scanner(System.in);
				int intValue1 = sc.nextInt();
				System.out.println("intValue1: " + intValue1);
				sc.close();
			}
		});
		thread1.start();
		thread2.start();
		
		
		Thread.currentThread().join();
	}
}
