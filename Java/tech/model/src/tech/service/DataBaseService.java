package tech.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import tech.exception.SqlExecuteException;

public final class DataBaseService {
	private static Connection conn;

	private static DataBaseService instance = new DataBaseService();
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1/tech?characterEncoding=utf8",
					"root", "123456");
		} catch (ClassNotFoundException | SQLException e) {
			throw new SqlExecuteException(e);
		}
	}

	private DataBaseService() {

	}

	public static DataBaseService getService() {
		return instance;
	}

	public int doExecuteInsertQuery(String query) {
		try {
			Statement stmt = conn.createStatement();
			int cnt = stmt.executeUpdate(query);
			stmt.close();
			return cnt;
		} catch (SQLException e) {
			throw new SqlExecuteException(e);
		}
	}

	public int doExecuteUpdateQuery(String query) {
		try {
			Statement stmt = conn.createStatement();
			return stmt.executeUpdate(query);
		} catch (SQLException e) {
			throw new SqlExecuteException(e);
		}
	}

	public ResultSet doSelectQuery(String query) {
		try {
			Statement stmt = conn.createStatement();
			return stmt.executeQuery(query);
		} catch (SQLException e) {
			throw new SqlExecuteException(e);
		}
	}

	public PreparedStatement prepareStatement(String query) {
		try {
			return conn.prepareStatement(query);
		} catch (SQLException e) {
			throw new SqlExecuteException(e);
		}
	}

	public boolean executeStatement(PreparedStatement stmt) {
		try {
			return stmt.execute();
		} catch (SQLException e) {
			throw new SqlExecuteException(e);
		}
	}

	public ResultSet executeStatementQuery(PreparedStatement stmt) {
		try {
			return stmt.executeQuery();
		} catch (SQLException e) {
			throw new SqlExecuteException(e);
		}
	}

	public int executeStatementUpdate(PreparedStatement stmt) {
		try {
			return stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SqlExecuteException(e);
		}
	}

	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			throw new SqlExecuteException(e);
		}
	}

}
