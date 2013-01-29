package tech.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import tech.exception.ServiceException;
import tech.util.Strings;

public class AccountService {
	public boolean login(String email, String pwd) {
		email = Strings.filterSingleQuotes(email);
		pwd = Strings.filterSingleQuotes(pwd);
		DataBaseService db = DataBaseService.getService();
		String sql = "select * from account where email='%s' and pwd=PASSWORD('%s')";
		sql = String.format(sql, email, pwd);
		ResultSet rs = db.doSelectQuery(sql);
		try {
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new ServiceException(e);
		}
	}

	public void register(String nickname, String email, String gender,
			String locale, String role, String pwd) {

	}
}
