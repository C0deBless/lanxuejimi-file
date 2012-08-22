package com.trnnn.orm;

import java.util.Map;

public class ActionFilter {

	public static final String LABLE_TABLE_NAME = "table_name";
	public static final String LABLE_ACTION_TYPE = "action_type";

	public Action parse(Map<String, Object> values) {
		String tableName = values.get(LABLE_TABLE_NAME).toString();
		String actionType = values.get(LABLE_ACTION_TYPE).toString();
		ActionType type = ActionType.valueOf(actionType);
		Action action = new Action(tableName, type, values);
		return action;
	}
}
