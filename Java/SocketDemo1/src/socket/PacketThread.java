package socket;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import socket.Server.ServerThread;

public class PacketThread implements Runnable {

	private BlockingQueue<Packet> queue = new LinkedBlockingQueue<Packet>();
	private Thread thread;
	private final int index;

	public PacketThread(int index) {
		this.index = index;
	}

	public void Start() {
		thread = new Thread(this);
		thread.start();
		thread.setName("PacketThread-" + index);
	}

	public void addPacket(Packet packet){
		this.queue.add(packet);
	}
	
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " 启动 ");
		while (true) {
			try {
				Packet packet = queue.take();
				String str = packet.getData();
				ServerThread thread = packet.getClient();
				
				if (str.equals(Server.DISCONNECT_TOKEN)) {
					thread.closef();
					break;
				}
				String us = thread.getUsersBymsg(str);
				String msg = thread.formatMsg(str);
				System.out.println(String.format("receive msg, str:%s, us:%s, msg:%s, threadName:%s", str, us, msg, Thread.currentThread().getName()));
				if (us.equals("all")) {
					thread.send(thread.name() + ":" + msg + "[群]");
				} else {
					thread.sendPrivateUsers(us, msg);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
