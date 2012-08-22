package com.trnnn.orm;

import com.trnnn.orm.exception.ORMNotImplementedException;

public class SQLQueryGenerator {

	ORMModelPool modelPool;

	public SQLQueryGenerator(ORMModelPool pool) {
		this.modelPool = pool;
	}

	public String generate(Action action) {
		ModelInfo model = modelPool.getModelInfo(action);
		ActionType actionType = action.getActionType();
		String sql = null;
		switch (actionType) {
		case Delete:
			sql = this.processDelete(model);
			break;
		case Insert:
			sql = this.processInsert(model);
			break;
		case Select:
			sql = this.processSelect(model);
			break;
		case Update:
			sql = this.processUpdate(model);
			break;
		default:
			break;
		}
		return sql;
	}

	String processSelect(ModelInfo model) {
		throw new ORMNotImplementedException();
	}

	String processDelete(ModelInfo model) {
		return null;
	}

	String processUpdate(ModelInfo model) {
		return null;
	}

	String processInsert(ModelInfo model) {
		return null;
	}
}
