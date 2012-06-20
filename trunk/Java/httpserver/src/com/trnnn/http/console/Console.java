package com.trnnn.http.console;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Console {

	private static SimpleDateFormat df = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");

	private Console() {
	}

	private static boolean isOpen = true;

	public static void open() {
		isOpen = true;
	}


	public static void output(String tag, String message) {
		String str = String.format("[%s] %s", tag, message);
		output(str);
	}

	public static void output(Object message) {
		if (isOpen) {
			String str = String.format("[%s] %s", df.format(new Date()),
					message);
			System.out.println(str);
		}
	}

	public static void outputErr(String tag, String message) {
		String str = String.format("[%s] %s", tag, message);
		outputErr(str);
	}

	public static void outputErr(Object message) {
		if (isOpen) {
			String str = String.format("[%s] %s", df.format(new Date()),
					message);
			System.err.println(str);
		}
	}

	public static void format(String str, Object... args) {
		String message = String.format(str, args);
		output(message);
	}

	public static void outputStackTrace(String message) {
		StackTraceElement stacks[] = (new Throwable()).getStackTrace();
		outputErr("outputStackTrace ->> " + message);
		for (StackTraceElement stack : stacks) {
			String methodName = stack.getMethodName();
			if (!methodName.equals("outputStackTrace"))
				outputErr(" -> " + stack);
		}
	}

	public static void outputStackTrace() {
		outputStackTrace(null);
	}
}
