package stresstest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class StressTestMain {

	public static void main(String[] args) throws IOException,
			InterruptedException, SQLException, ClassNotFoundException {
		// String serverHost = "175.209.144.44";
		// int serverPort= 9989;
		// int clientCount = 10000;
		// ClientPool pool = new ClientPool(serverHost, serverPort,
		// clientCount);
		// pool.start();
		//
		// Thread.currentThread().join();

		String strConn = "jdbc:mysql://192.168.0.193:7401/st_game01?user=root&password=xkdlrj@tigergames^";
		Class.forName("com.mysql.jdbc.Driver");
		final Connection conn = DriverManager.getConnection(strConn);
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String query = "UPDATE GT_USER SET SCORE=40000,MC_WINNING_W=20, MC_WINNING_T=20,C_WINNING=1,LAST_GAME_TIME=635291025055439446,MAX_TYPING_SPEED = 245, AC_TYPING_SPEED=8041, AC_GAME_COUNT=80, XP=1791, LEVEL=6, AC_WIN_COUNT=84  WHERE USER_ID = 1001";
					Statement stmt = conn.prepareStatement(query);
					for (int i = 0; i < 100; i++) {
						long d1 = System.nanoTime();
						stmt.execute(query);
						long d2 = System.nanoTime();
						long delta = d2 - d1;
						System.out.println("time: " + delta / 1000.0 / 1000
								+ " ms");
					}
					stmt.close();
					conn.close();
				} catch (Exception e) {

				}

			}

		});

		thread.start();
		thread.join();
	}
}
