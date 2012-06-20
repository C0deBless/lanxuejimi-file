package common.processmonitor.handler;

import common.processmonitor.IntegerTool;


public class Packet {

	public static final int SNAPSHOT = 0;
	public static final int JVMDATA = 1;

	public Packet(int cmd, byte[] data) {
		this.cmd = cmd;
		this.data = data;
		this.length = data.length + 4;
	}

	public Packet(int cmd, String data) {
		this.cmd = cmd;
		this.data = data.getBytes();
		this.length = this.data.length + 4;
	}

	private int cmd;
	private int length;
	private byte[] data;

	public int getCmd() {
		return cmd;
	}

	public void setCmd(int cmd) {
		this.cmd = cmd;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public byte[] array() {
		byte[] b1 = IntegerTool.intToByteArray(cmd);
		byte[] b2 = IntegerTool.intToByteArray(this.length);
		byte[] b = new byte[this.length];
		b[0] = b1[0];
		b[1] = b1[1];
		b[2] = b2[0];
		b[3] = b2[1];
		for (int i = 3; i < b.length; i++) {
			b[i] = this.data[i - 3];
		}
		return b;
	}

}