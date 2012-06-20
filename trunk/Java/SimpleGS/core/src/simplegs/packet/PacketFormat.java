package simplegs.packet;

public class PacketFormat {
	private int cmdSize;
	private int maxPacketSize;

	public PacketFormat(int cmdSize, int maxPacketSize) {
		this.cmdSize = cmdSize;
		this.maxPacketSize = maxPacketSize;
	}

	public int getCmdSize() {
		return cmdSize;
	}

	public void setCmdSize(int cmdSize) {
		this.cmdSize = cmdSize;
	}

	public int getMaxPacketSize() {
		return maxPacketSize;
	}

	public void setMaxPacketSize(int maxPacketSize) {
		this.maxPacketSize = maxPacketSize;
	}

}
