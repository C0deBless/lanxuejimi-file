package aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;

public class Client {
	public static void main(String[] args) throws IOException, InterruptedException {
		final AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
		client.connect(new InetSocketAddress("127.0.0.1", 8000), client,
				new CompletionHandler<Void, AsynchronousSocketChannel>() {
					@Override
					public void completed(Void result,
							AsynchronousSocketChannel attachment) {
						System.out.println("连接成功");
						String msg= "这里是客户端来的数据， " +new Date().toString();
						byte[] data = msg.getBytes();
						System.out.println("发送数据长度：" + data.length);
						ByteBuffer writeBuffer = ByteBuffer.wrap(data);
						client.write(writeBuffer);
					}

					@Override
					public void failed(Throwable exc,
							AsynchronousSocketChannel attachment) {
						System.err.println("连接失败");
					}
				});
		Thread.currentThread().join();
	}
}
