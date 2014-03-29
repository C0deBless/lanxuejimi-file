package socket;


public class PacketProcessor {

	private PacketThread[] threadPool;

	public PacketProcessor() {
		int processorCount = Runtime.getRuntime().availableProcessors();
		System.out.println("服务器 PacketProcessor 启动，线程数：" + processorCount);
		threadPool = new PacketThread[processorCount];
		for (int i = 0; i < processorCount; i++) {
			threadPool[i] = new PacketThread(i);
			threadPool[i].Start();
		}
	}

	public void pushPacket(Packet packet) {
		int code = packet.getClient().hashCode();

		int randomIndex = code % 4;
		PacketThread thread = threadPool[randomIndex];
		thread.addPacket(packet);
	}
}
