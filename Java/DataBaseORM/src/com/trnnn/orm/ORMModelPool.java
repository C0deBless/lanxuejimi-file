package com.trnnn.orm;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.trnnn.orm.annotation.ORMModel;
import com.trnnn.orm.tools.ClassHelper;

public class ORMModelPool {

	static final Map<Class<?>, ModelInfo> pool = Collections
			.synchronizedMap(new HashMap<Class<?>, ModelInfo>());
	static {
		Set<Class<?>> set = ClassHelper.getClasses("armada");
		for (Class<?> class1 : set) {
			// System.out.println(class1.getName());
			if (class1.isAnnotationPresent(ORMModel.class)) {
				// this.POJO_POOL.add(class1);
			}
		}
	}
}
