package com.trnnn.orm;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.trnnn.orm.annotation.ORMColumn;
import com.trnnn.orm.annotation.ORMModel;

public class ModelInfo {

	Class<?> clazz;
	String tableName;
	List<String> columns;
	List<String> pks;

	public Class<?> getClazz() {
		return clazz;
	}

	public String getTableName() {
		return tableName;
	}

	public List<String> getColumns() {
		return columns;
	}

	public List<String> getPks() {
		return pks;
	}

	public static ModelInfo parse(Class<?> clazz) {
		ModelInfo info = new ModelInfo();
		String tableName = info.getClass().getAnnotation(ORMModel.class)
				.tableName();
		info.tableName = tableName;
		Field[] fields = clazz.getDeclaredFields();
		info.columns = new ArrayList<>();
		info.pks = new ArrayList<>();
		for (Field field : fields) {
			ORMColumn column = field.getAnnotation(ORMColumn.class);
			if (column != null) {
				String name = column.name();
				boolean isPK = column.isPK();
				info.columns.add(name);
				if (isPK)
					info.pks.add(name);
			}
		}
		return info;
	}
}
