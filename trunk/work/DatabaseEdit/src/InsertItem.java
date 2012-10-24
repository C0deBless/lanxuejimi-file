import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

public class InsertItem {

	static String dbHost = "jdbc:mysql://tokyo.easymode.com/armada_qa?characterEncoding=utf-8";
	static String dbUser = "webuser";
	static String dbPassword = "web123!";
	static Connection dbConnection;
	static int itemId1 = 821116;
	static int itemId2 = 821127;

	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		dbConnection = DriverManager.getConnection(dbHost, dbUser, dbPassword);

		String sql = "select * from commander";
		InsertItem main = new InsertItem();
		ResultSet rs = main.doSelectQuery(sql);
		while (rs.next()) {
			int userId = rs.getInt("user_id");
			{
				sql = "insert into item(user_id,item_id,count,buy_price) values(%d,%d,%d,%d) on duplicate key update count=count+1";
				sql = String.format(sql, userId, itemId1, 1, 0);
				System.out.println("sql -> " + sql);
				main.doExecuteQuery(sql);
			}
			{
				sql = "insert into item(user_id,item_id,count,buy_price) values(%d,%d,%d,%d) on duplicate key update count=count+1";
				sql = String.format(sql, userId, itemId2, 1, 0);
				System.out.println("sql -> " + sql);
				main.doExecuteQuery(sql);
			}
		}

	}

	public boolean doExecuteQuery(String query) {
		try {
			Statement stmt = (Statement) dbConnection.createStatement();
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
			Statement stmt = (Statement) dbConnection.createStatement();
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
			Statement stmt = (Statement) dbConnection.createStatement();
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
			Statement stmt = (Statement) dbConnection.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return rs;
	}

	public PreparedStatement prepareStatement(String query) {
		try {
			return (PreparedStatement) dbConnection.prepareStatement(query);
		} catch (SQLException e) {
			return null;
		}
	}

	public void close() {
		if (dbConnection != null) {
			try {
				dbConnection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
