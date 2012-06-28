package testplugin1;

import java.lang.reflect.InvocationTargetException;

public class Class1 {

	public void output() {
		// System.out.println("Class1.output invoked...");
		int i = 1;
		i = i + 1;
	}

	public static void main(String[] args) throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// Method method = Class1.class.getMethod("output");
		// Class1 c = new Class1();
		// long d1 = System.nanoTime();
		// // method.invoke(c);
		// c.output();
		// long d2 = System.nanoTime();
		// // method.invoke(c);
		// c.output();
		// long d3 = System.nanoTime();
		// // method.invoke(c);
		// c.output();
		// long d4 = System.nanoTime();
		// // method.invoke(c);
		// c.output();
		// long d5 = System.nanoTime();
		// System.out.println(d2 - d1);
		// System.out.println(d3 - d2);
		// System.out.println(d4 - d3);
		// System.out.println(d5 - d4);
	}

}
