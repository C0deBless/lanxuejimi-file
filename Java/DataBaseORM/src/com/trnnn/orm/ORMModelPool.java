package com.trnnn.orm;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.trnnn.orm.annotation.ORMModel;
import com.trnnn.orm.tools.ClassHelper;

public class ORMModelPool {

	static final Map<String, ModelInfo> pool = Collections
			.synchronizedMap(new HashMap<String, ModelInfo>());
	static {
		initPool();
	}

	private static void initPool() {
		Set<Class<?>> set = ClassHelper.getClasses("armada");
		for (Class<?> class1 : set) {
			if (class1.isAnnotationPresent(ORMModel.class)) {
				ModelInfo model = ModelInfo.parse(class1);
				pool.put(model.getTableName(), model);
			}
		}
	}

	public ModelInfo getModelInfo(Action action) {
		return pool.get(action.getTableName());
	}
}
