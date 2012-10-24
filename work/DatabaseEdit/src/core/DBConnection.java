package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

public class DBConnection {

	private String host;
	private String name;
	private String pwd;
	private Connection connection;

	public DBConnection(String host, String name, String pwd)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager
				.getConnection(this.host, this.name, this.pwd);
	}

	public boolean doExecuteQuery(String query) {
		try {
			Statement stmt = (Statement) connection.createStatement();
			stmt.execute(query);
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public int doExecuteInsertQuery(String query) {
		int autoIncrement = 0;
		try {
			Statement stmt = (Statement) connection.createStatement();
			int i = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

			ResultSet rs = stmt.getGeneratedKeys();
			if ((null != rs) && rs.next()) {
				autoIncrement = rs.getInt(1);
			} else {
				return i;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			return -1;
		}

		return autoIncrement;
	}

	public boolean doExecuteUpdateQuery(String query) {
		try {
			Statement stmt = (Statement) connection.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public ResultSet doSelectQuery(String query) {
		ResultSet rs = null;
		try {
			Statement stmt = (Statement) connection.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return rs;
	}

	public PreparedStatement prepareStatement(String query) {
		try {
			return (PreparedStatement) connection.prepareStatement(query);
		} catch (SQLException e) {
			return null;
		}
	}

	public void close() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
