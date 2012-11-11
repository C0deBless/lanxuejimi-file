package tech.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.PreparedStatement;

public class DBConnection {
	static Logger logger = LoggerFactory.getLogger(DBConnection.class);
	final String host = "jdbc:mysql://127.0.0.1/tech?characterEncoding=utf-8";
	final String user = "webuser";
	final String pwd = "pwd";
	Connection connection;

	private DBConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(host, user, pwd);
	}

	public static DBConnection create() throws ClassNotFoundException,
			SQLException {
		DBConnection connection = new DBConnection();
		return connection;
	}

	public boolean doExecuteQuery(String query) {
		try {
			Statement stmt = (Statement) connection.createStatement();
			logger.trace("connection.execute -> {}", query);
			stmt.execute(query);
		} catch (SQLException ex) {
			logger.error("connection.doExecuteQuery, SQLException: "
					+ ex.getMessage());
			logger.error("connection.doExecuteQuery, SQLState: "
					+ ex.getSQLState());
			logger.error("connection.doExecuteQuery, query: " + query);
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public int doExecuteInsertQuery(String query) {
		int autoIncrement = 0;
		try {
			Statement stmt = (Statement) connection.createStatement();
			logger.trace("connection.execute -> {}", query);
			int i = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stmt.getGeneratedKeys();
			if ((null != rs) && rs.next()) {
				autoIncrement = rs.getInt(1);
			} else {
				return i;
			}
		} catch (SQLException ex) {
			logger.error("[M:connection.doExecuteInsertQuery] query : " + query);
			logger.error("[M:connection.doExecuteInsertQuery] SQLException: "
					+ ex.getMessage());
			logger.error("[M:connection.doExecuteInsertQuery] SQLState: "
					+ ex.getSQLState());
			ex.printStackTrace();
			return -1;
		}

		return autoIncrement;
	}

	public boolean doExecuteUpdateQuery(String query) {
		try {
			Statement stmt = (Statement) connection.createStatement();
			logger.trace("connection.execute -> {}", query);
			stmt.executeUpdate(query);
			return true;
		} catch (SQLException ex) {
			logger.error("[connection.doExecuteUpdateQuery] SQLException: "
					+ ex.getMessage());
			logger.error("[connection.doExecuteUpdateQuery] SQLState: "
					+ ex.getSQLState());
			logger.error("[connection.doExecuteUpdateQuery] query: " + query);
			ex.printStackTrace();
			return false;
		}
	}

	public ResultSet doSelectQuery(String query) {
		ResultSet rs = null;
		try {
			Statement stmt = (Statement) connection.createStatement();
			logger.trace("connection.execute -> {}", query);
			rs = stmt.executeQuery(query);
		} catch (SQLException ex) {
			logger.error("SQLException: {}", ex.getMessage());
			logger.error("query: {}", query);
			ex.printStackTrace();
		}
		return rs;
	}

	public PreparedStatement prepareStatement(String query) {
		try {
			return (PreparedStatement) connection.prepareStatement(query);
		} catch (SQLException e) {
			logger.error("connection.prepareStatement, msg:{}, query:{}",
					e.getMessage(), query);
			return null;
		}
	}

	public boolean executeStatement(PreparedStatement stmt) {
		try {
			logger.trace("connection.execute -> {}", stmt.getPreparedSql());
			return stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ResultSet executeStatementQuery(PreparedStatement stmt) {
		try {
			logger.trace("connection.execute -> {}", stmt.getPreparedSql());
			return stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void close() {
		if (this.connection != null) {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
