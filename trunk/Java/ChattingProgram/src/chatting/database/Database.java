package chatting.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Database {
	private static final String connStr = "jdbc:mysql://localhost:3306/chatting";
	private static final String userName = "root";
	private static final String password = "123456";
	static Logger logger = LoggerFactory.getLogger(Database.class);

	private Connection conn;

	public Database() throws ClassNotFoundException, SQLException {
		conn = DriverManager.getConnection(connStr, userName, password);
		logger.info("数据库连接成功....");
	}

	public int login(String userName, String password) throws SQLException {
		String query = "select * from user where USER_NAME=? AND PASSWORD=?";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, userName);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return 1;
			} else {
				return 2;
			}
		}
	}
}
