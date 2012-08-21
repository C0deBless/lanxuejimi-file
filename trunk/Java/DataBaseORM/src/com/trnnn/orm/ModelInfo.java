package com.trnnn.orm;

import com.trnnn.orm.annotation.ORMModel;

public class ModelInfo {

	Class<?> clazz;
	String tableName;
	String[] columns;
	String[] pks;

	public static ModelInfo parse(Class<?> class1) {
		ModelInfo info = new ModelInfo();
		String tableName = info.getClass().getAnnotation(ORMModel.class)
				.tableName();
		info.tableName = tableName;
		return info;
	}

}
