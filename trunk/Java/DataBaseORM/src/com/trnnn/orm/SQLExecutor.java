package com.trnnn.orm;

import java.sql.Connection;

public class SQLExecutor {

	Connection connection;

	public SQLExecutor(Connection connection) {
		if (connection == null) {
			throw new NullPointerException();
		}
		this.connection = connection;
	}

	public void execute(String sql) {
		
	}

}
