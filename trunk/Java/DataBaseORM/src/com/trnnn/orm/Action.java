package com.trnnn.orm;

import java.util.Map;

public class Action {

	Map<String, Object> data;
	String tableName;
	ActionType actionType;

	public Action(String tableName, ActionType actionType,
			Map<String, Object> data) {
		this.data = data;
		this.tableName = tableName;
		this.actionType = actionType;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public String getTableName() {
		return tableName;
	}

	public ActionType getActionType() {
		return actionType;
	}

}
