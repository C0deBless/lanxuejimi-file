package com.trnnn.orm;

import java.sql.Connection;

public class SQLExecutor {

	Connection connection;

	public SQLExecutor(Connection connection) {
		this.connection = connection;
	}

	public void executor(String sql) {

	}

}
