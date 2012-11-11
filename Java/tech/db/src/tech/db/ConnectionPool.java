package tech.db;

import java.sql.SQLException;

public class ConnectionPool {

	static final int poolSize = 4;
	static DBConnection[] pool = new DBConnection[poolSize];

	public static void init() throws ClassNotFoundException, SQLException {
		for (int i = 0; i < 4; i++) {
			pool[i] = DBConnection.create();
		}
	}

	public static DBConnection getConnection() {
		long currentTime = System.currentTimeMillis();
		short index = (short) (currentTime % 4);
		return pool[index];
	}
}
