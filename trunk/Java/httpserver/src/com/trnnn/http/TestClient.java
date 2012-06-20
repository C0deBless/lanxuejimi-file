package com.trnnn.http;

import java.io.IOException;

public class TestClient {
	public static void main(String[] args) throws IOException,
			InterruptedException {
		// Socket socket = new Socket();
		// socket.connect(new InetSocketAddress("127.0.0.1", 8080));
		// OutputStream output = socket.getOutputStream();
		// StringBuffer sb = new StringBuffer();
		// sb.append("POST /index.jsp HTTP/1.1\r\n");// 注意\r\n为回车换行
		// sb.append("Accept-Language: zh-cn\r\n");
		// sb.append("Connection: Keep-Alive\r\n");
		// sb.append("Host: 192.168.0.106\r\n");
		// sb.append("Content-Length: 37\r\n");
		// sb.append("\r\n");
		// sb.append("userName=new_andy&password=new_andy\r\n");
		// sb.append("\r\n");
		// String str = sb.toString();
		// output.write(str.getBytes());
		// while (true) {
		// Thread.sleep(100);
		// }
		Thread t1 = new Thread(new TestThread("thread1"), "thread1");
		Thread t2 = new Thread(new TestThread("thread2"), "thread2");
		t1.start();
		t2.start();
		Thread.currentThread().join();
	}
}

class TestThread implements Runnable {
	String name;

	public TestThread(String name) {
		this.name = name;
	}

	public static final ThreadLocal<String> session = new ThreadLocal<>();

	@Override
	public void run() {
		session.set(name);
		while (true) {
			System.out.println(Thread.currentThread().getName() + ","
					+ session.get());
			// try {
			// Thread.sleep(10);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
		}
	}

}