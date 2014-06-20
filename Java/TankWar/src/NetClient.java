import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;



public class NetClient {
	
	
	
	TankClient tc;
	
	String serverIP;
	
	DatagramSocket ds = null;
	
	private  int udpPort;
	
	public NetClient(TankClient tc) {
		this.tc = tc;
	}
	
	public int getUdpPort() {
		return udpPort;
	}

	public void setUdpPort(int udpPort) {
		this.udpPort = udpPort;
	}

	public void connect(String ip, int port){
		
		this.serverIP = ip;
		
		try {
			ds = new DatagramSocket(udpPort);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		Socket s = null;
		try {
			
			s = new Socket(ip, port);
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeInt(udpPort);
			DataInputStream dis = new DataInputStream(s.getInputStream());
			int id = dis.readInt();
			if(id%2 == 0){
				tc.myTank.setGood(true);
			}else{
				tc.myTank.setGood(false);
			}
			tc.myTank.setId(id);
System.out.println("connected to server...and server give me a ID:"+id);	

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(s != null){
				try {
					s.close();
					s = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		TankNewMsg msg = new TankNewMsg(tc.myTank);
		send(msg);
		
		new Thread(new UDPThread()).start();
	}

	public void send(Missage msg) {
		msg.send(ds, serverIP, TankClient.TANK_SERVER_UDP_PORT);
	}
	
	private class UDPThread implements Runnable{
		
		byte[] buf = new byte[1024];
		
		public void run() {
			while(ds != null){
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				try {
					ds.receive(dp);
					parse(dp);
System.out.println("a packet received from server");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}

		private void parse(DatagramPacket dp) {
			ByteArrayInputStream dais = new ByteArrayInputStream(buf, 0, buf.length);
			DataInputStream dis = new DataInputStream(dais);
			Missage msg = null;
			int msgType = 0;
			try {
				msgType = dis.readInt();
			} catch (IOException e) {
				e.printStackTrace();
			}
			switch(msgType){
			case Missage.TANK_NEW_MSG:
				msg = new TankNewMsg(tc);
				msg.parse(dis);
				break;
			case Missage.TANK_MOVE_MSG:
				msg = new TankMoveMsg(tc);
				msg.parse(dis);
				break;
			case Missage.MISSILE_NEW_MSG:
				msg = new MissileNewMsg(tc);
				msg.parse(dis);
				break;
			case Missage.TANK_DEAD_MSG:
				msg = new TankDeadMsg(tc);
				msg.parse(dis);
				break;
			case Missage.MISSILE_DEAD_MSG:
				msg = new MissileDeadMsg(tc);
				msg.parse(dis);
				break;
			}
			
		}
	}
	
}
