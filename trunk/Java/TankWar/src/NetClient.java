import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;



public class NetClient {
	
	
	
	TankClient tc;
	
	private String serverIP;
	
	DatagramSocket ds = null;
	
	private  int udpPort;
	
	
	
	public NetClient(TankClient tc) {
		this.tc = tc;
	}
	

	public void connect(String ip, int port){

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
		
		new Thread(new UDPThread(this)).start();
	}

	public void send(Missage msg) {
		msg.send(ds, serverIP, TankClient.TANK_SERVER_UDP_PORT);
	}
	
	public int getUdpPort() {
		return udpPort;
	}

	public void setUdpPort(int udpPort) {
		this.udpPort = udpPort;
	}


	public String getServerIP() {
		return serverIP;
	}


	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	
	
}
