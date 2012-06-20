package simplegs.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public final class ServerThreadGroup extends ThreadGroup {

	public ExecutorService threadPool;
	private final ThreadFactory threadFactory = Executors
			.defaultThreadFactory();

	private ServerThreadGroup(String name) {
		super(name);
	}

	public static ServerThreadGroup init(String name) {
		ServerThreadGroup threadGroup = new ServerThreadGroup(name);
		threadGroup.threadPool = Executors
				.newCachedThreadPool(threadGroup.threadFactory);
		return threadGroup;
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.err.print("Exception in thread \"" + t.getName() + "\" ");
		e.printStackTrace(System.err);
		// StringWriter sw = new StringWriter();
		// e.printStackTrace(new PrintWriter(sw, true));
		// String strStack = sw.toString();
	}

	public void invokeThread(Runnable target) {
		Thread thread = new Thread(target);
		invokeThread(thread);
	}

	public void invokeThread(Thread thread) {
		threadPool.submit(thread);
	}
}
