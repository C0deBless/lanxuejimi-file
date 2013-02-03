package tech.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tech.exception.ServiceException;
import tech.exception.SqlExecuteException;
import tech.model.Account;
import tech.util.Strings;

public class AccountService {

	public Account getAccount(String email) {
		email = Strings.filterSingleQuotes(email);
		DataBaseService db = DataBaseService.getService();
		String sql = "select * from account where email='%s'";
		sql = String.format(sql, email);
		ResultSet rs = db.doSelectQuery(sql);
		try {
			if (rs.next()) {
				int userId = rs.getInt("user_id");
				String nickName = rs.getString("nickname");
				String locale = rs.getString("locale");
				String role = rs.getString("role");
				Account account = new Account();
				account.setEmail(email);
				account.setUserId(userId);
				account.setNickName(nickName);
				account.setRole(role);
				account.setLocale(locale);
				return account;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new ServiceException(e);
		}
	}

	public Account login(String email, String pwd) {
		email = Strings.filterSingleQuotes(email);
		pwd = Strings.filterSingleQuotes(pwd);
		DataBaseService db = DataBaseService.getService();
		String sql = "select * from account where email='%s' and pwd=PASSWORD('%s')";
		sql = String.format(sql, email, pwd);
		ResultSet rs = db.doSelectQuery(sql);
		try {
			if (rs.next()) {
				int userId = rs.getInt("user_id");
				String nickName = rs.getString("nickname");
				String locale = rs.getString("locale");
				String role = rs.getString("role");
				Account account = new Account();
				account.setEmail(email);
				account.setUserId(userId);
				account.setNickName(nickName);
				account.setRole(role);
				account.setLocale(locale);
				return account;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new ServiceException(e);
		}
	}

	public boolean changePwd(int userId, String email, String newPwd) {
		email = Strings.filterSingleQuotes(email);
		newPwd = Strings.filterSingleQuotes(newPwd);
		String sql = "update account set pwd=PASSWORD('%s') where email='%s' and user_id=%d";
		sql = String.format(sql, newPwd, email, userId);
		DataBaseService db = DataBaseService.getService();
		int cnt = db.doExecuteUpdateQuery(sql);
		if (cnt == 1) {
			return true;
		} else
			return false;
	}

	public boolean checkUserEmailDuplicate(String email) {
		email = Strings.filterSingleQuotes(email);
		String sql = "select * from account where email='%s'";
		sql = String.format(sql, email);
		DataBaseService db = DataBaseService.getService();
		ResultSet rs = db.doSelectQuery(sql);
		try {
			if (rs.next()) {
				return false;
			} else
				return true;
		} catch (SQLException e) {
			throw new SqlExecuteException(e);
		}
	}

	public void register(String nickName, String email, String locale,
			String role, String pwd) {
		try {
			String sql = "insert into account(nickname,email,locale,role,pwd) values(?,?,?,?,PASSWORD(?))";
			DataBaseService db = DataBaseService.getService();
			PreparedStatement stmt = db.prepareStatement(sql);
			stmt.setString(1, nickName);
			stmt.setString(2, email);
			stmt.setString(3, locale);
			stmt.setString(4, role);
			stmt.setString(5, pwd);
			db.executeStatement(stmt);
		} catch (SQLException e) {
			throw new SqlExecuteException(e);
		}
	}

	public boolean validateNickName(String nick) {
		if (nick == null || nick.equals("")) {
			return false;
		}
		// FIXME check nick name here
		return true;
	}

	public boolean validatePassword(String pwd) {
		if (pwd == null) {
			return false;
		}
		// FIXME check password here
		return true;
	}

	public boolean validateEmail(String email) {
		if (email == null) {
			return false;
		}
		// FIXME check email here
		return true;
	}
}
