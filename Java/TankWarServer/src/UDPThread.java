import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;


class UDPThread implements Runnable{
		
		
		private final TankServer tankServer;

		
		UDPThread(TankServer tankServer) {
			this.tankServer = tankServer;
		}

		byte[] buf = new byte[1024];
		
		public void run() {
			DatagramSocket ds = null;
			
			try {
				ds = new DatagramSocket(TankServer.UDP_PORT);
			} catch (SocketException e) {
				e.printStackTrace();
			}
System.out.println("UDPThread strated at PORT:"+TankServer.UDP_PORT);
			while(ds != null){
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				try {
					ds.receive(dp);
System.out.println("a packet received");
					for(int i=0; i<this.tankServer.clients.size(); i++){
						Clinet c = this.tankServer.clients.get(i);
						dp.setSocketAddress(new InetSocketAddress(c.IP, c.udpPort));
						ds.send(dp);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}