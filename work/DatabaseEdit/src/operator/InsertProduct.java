package operator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

public class InsertProduct {

	static String dbHost = "jdbc:mysql://tokyo.easymode.com/armada_qa?characterEncoding=utf-8";
	static String dbUser = "webuser";
	static String dbPassword = "web123!";
	static final List<Integer> producingBuildingList = Arrays.asList(610008,
			610009, 610010, 610011);
	static Connection dbConnection;

	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		dbConnection = DriverManager.getConnection(dbHost, dbUser, dbPassword);

		String sql = "select * from building";
		InsertProduct main = new InsertProduct();
		ResultSet rs = main.doSelectQuery(sql);
		long tick = System.currentTimeMillis();
		while (rs.next()) {
			int userId = rs.getInt("user_id");
			int buildingSeq = rs.getInt("building_seq");
			int buildingId = rs.getInt("building_id");
			if (producingBuildingList.contains(buildingId)
					&& (!main.containsProduct(buildingSeq))) {
				int itemId = main.getItemId(buildingId);
				if (itemId > 0) {
					sql = "insert into product(user_id,building_seq,item_id,count,tick,need_point,current_point,d_point) "
							+ "values(%d,%d,%d,%d,%d,%d,%d,%d)";
					sql = String.format(sql, userId, buildingSeq, itemId, 0,
							tick, 5000, 0, 500);
					System.err.println("insert -> " + sql);
					main.doExecuteInsertQuery(sql);
				} else {
					System.err.println("illegal building id -> " + buildingId);
				}
			}
		}

	}

	public int getItemId(int buildingId) {
		switch (buildingId) {
		case 610009:
			return 130001;
		case 610010:
			return 130002;
		case 610011:
			return 130003;
		}
		return 0;
	}

	public boolean containsProduct(int buildingSeq) throws SQLException {
		String sql = "select * from product where building_seq=%d";
		sql = String.format(sql, buildingSeq);
		ResultSet rs = this.doSelectQuery(sql);
		if (rs.next()) {
			return true;
		}
		return false;
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
