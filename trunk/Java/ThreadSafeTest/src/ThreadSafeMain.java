import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadSafeMain {

	static int i = 0;
	static BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
	
	static Runnable addTask = new Runnable() {
		public void run() {
			i++;
		}
	};

	public static void main(String[] args) throws InterruptedException {
		// CPU核心， 进程， 线程

		// i = 2
		// A i+1
		// B i+1

		Thread producer1 = new Thread(new Runnable() {
			public void run() {
				for (int j = 0; j < 10000; j++) {
					queue.add(addTask);
				}
			}
		});

		Thread producer2 = new Thread(new Runnable() {
			public void run() {
				for (int j = 0; j < 10000; j++) {
					queue.add(addTask);
				}
			}
		});

		Thread consumer = new Thread(new Runnable() {
			public void run() {
				while(true){
					try {
						Runnable task = queue.take();
						task.run();
						System.err.println(i);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});

		producer1.start();
		producer2.start();
		consumer.start();

		producer1.join();
		producer2.join();

	}
}
