package tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Client {
	public static final String IP = "127.0.0.1";
	public static final int PORT = 7000;

	public static void main(String[] args) throws IOException,
			InterruptedException {
		AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
		final ByteBuffer readBuffer = ByteBuffer.allocate(128);
		channel.connect(new InetSocketAddress(IP, PORT), channel,
				new CompletionHandler<Void, AsynchronousSocketChannel>() {
					@Override
					public void completed(Void result,
							AsynchronousSocketChannel channel) {
						System.out.println("connected successfully");
						for (int i = 0; i < 5; i++) {
							String str = new Date().toString();
							byte[] data = str.getBytes();

							ByteBuffer buffer = ByteBuffer.allocate(1000);

							buffer.putInt(data.length);
							buffer.put(data, 0, data.length);
							buffer.flip();

							Future<Integer> future = channel.write(buffer);
							try {
								int writeResult = future.get();
							} catch (InterruptedException | ExecutionException e) {
								e.printStackTrace();
							}
						}
						Future<Integer> future2 = channel.read(readBuffer);
						try {
							int readResult = future2.get();
						} catch (InterruptedException | ExecutionException e) {
							e.printStackTrace();
						}
						readBuffer.clear();
						int length = readBuffer.getInt();
						byte[] data2 = new byte[length];
						readBuffer.get(data2, 0, length);
						readBuffer.clear();
						System.out.println("server:" + new String(data2));

					}

					@Override
					public void failed(Throwable exc,
							AsynchronousSocketChannel channel) {

					}
				});
		Thread.currentThread().join();
	}
}
