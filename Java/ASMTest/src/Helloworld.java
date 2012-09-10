import java.io.FileOutputStream;
import java.lang.reflect.Method;

import org.objectweb.asm.ClassWriter;

public class Helloworld extends ClassLoader implements Constants {

	public static void main(final String args[]) throws Exception {

		/*
		 * 此程序将生成一个class,对应的java源代码是:
		 * 
		 * public class Example { public static void main (String[] args) {
		 * System.out.println("Hello world!"); } }
		 */

		// 创建一个ClassWriter
		ClassWriter cw = new ClassWriter(false);
		cw.visit(ACC_PUBLIC, "Example", "java/lang/Object", null, null);

		// 创建一个 MethodWriter
		CodeVisitor mw = cw.visitMethod(ACC_PUBLIC, "", "()V", null);
		// 推入 'this' 变量
		mw.visitVarInsn(ALOAD, 0);
		// 创建父类的构造函数
		mw.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "", "()V");
		mw.visitInsn(RETURN);
		// 这段代码使用最多一个栈元素和一个本地变量
		mw.visitMaxs(1, 1);

		// 为main方法创建一个MethodWriter
		mw = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main",
				"([Ljava/lang/String;)V", null);
		// 使用System类的out成员类
		mw.visitFieldInsn(GETSTATIC, "java/lang/System", "out",
				"Ljava/io/PrintStream;");
		// pushes the "Hello World!" String constant
		mw.visitLdcInsn("Hello world!");
		// 调用System.out的'println' 函数
		mw.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
				"(Ljava/lang/String;)V");
		mw.visitInsn(RETURN);
		// 这段代码使用最多两个栈元素和两个本地变量
		mw.visitMaxs(2, 2);

		// 生成字节码形式的类
		byte[] code = cw.toByteArray();

		FileOutputStream fos = new FileOutputStream("Example.class");
		// 写文件
		fos.write(code);
		// 关闭输出流
		fos.close();

		// 实例化刚刚生成的类
		Helloworld loader = new Helloworld();
		Class exampleClass = loader
				.defineClass("Example", code, 0, code.length);

		// 使用动态生成的类打印 'Helloworld'
		Method main = exampleClass.getMethods()[0];
		main.invoke(null, new Object[] { null });
	}
}