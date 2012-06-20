package common.processmonitor;

import java.io.IOException;

public class MoniorClientMain {

	public static void main(String[] args) {
		MonitorClient client = new MonitorClient();
		try {
			client.run();
			Thread.currentThread().join();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}
}
