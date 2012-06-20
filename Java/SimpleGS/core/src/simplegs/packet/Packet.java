package simplegs.packet;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import simplegs.session.ClientSession;

public class Packet {
	private final int command;
	private byte[] data;
	private ClientSession session;
	private int packetId;
	private final Map<String, Object> requestParameter = new HashMap<>();

	public Packet(int command, byte[] data, ClientSession session) {
		this.command = command;
		this.data = data;
		this.session = session;
	}

	public int command() {
		return command;
	}

	public String data() {
		try {
			return new String(data, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public byte[] getByte() {
		return this.data;
	}

	public void setByte(byte[] b) {
		this.data = b;
	}

	public ClientSession getSession() {
		return session;
	}

	public void session(ClientSession session) {
		this.session = session;
	}

	public Map<String, Object> getRequestParameter() {
		return requestParameter;
	}

	public void putRequestParameter(Map<String, Object> map) {
		this.requestParameter.putAll(map);
	}

	public void clearRequestParameter() {
		this.requestParameter.clear();
	}

	public int packetId() {
		return packetId;
	}

	public void packetId(int packetId) {
		this.packetId = packetId;
	}
}
