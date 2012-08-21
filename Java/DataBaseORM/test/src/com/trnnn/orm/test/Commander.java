package com.trnnn.orm.test;

import com.trnnn.orm.annotation.ORMColumn;
import com.trnnn.orm.annotation.ORMModel;

@ORMModel(tableName = "commander")
public class Commander {

	@ORMColumn(name = "user_id", isPK = true)
	private int user_id;
	private int name;
	private int level;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
