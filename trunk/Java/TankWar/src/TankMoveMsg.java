import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;


public class TankMoveMsg implements Missage{
	int msgType = 2;
	
	
	Tank t;
	TankClient tc;
	
	

	public TankMoveMsg(Tank t) {
		this.t = t;
	}


	public TankMoveMsg(TankClient tc) {
		this.tc = tc;
	}
	
	public void send(DatagramSocket ds, String IP, int udpPort) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		
		try {
			dos.writeInt(msgType);
			dos.writeInt(t.getId());
			dos.writeInt(t.x);
			dos.writeInt(t.y);
			dos.writeInt(t.getGunTube().ordinal());
			dos.writeInt(t.dir.ordinal());
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] buf = baos.toByteArray();
		
		try {
			DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(IP, udpPort));
			ds.send(dp);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void parse(DataInputStream dis) {
		try {
			int id = dis.readInt();
			if(tc.myTank.getId() == id){
				return;
			}
			int x = dis.readInt();
			int y = dis.readInt();
			Direction gunTube =Direction.values()[dis.readInt()];
			Direction dir = Direction.values()[dis.readInt()];
			for(int i=0; i<tc.tanks.size(); i++){
				Tank t = tc.tanks.get(i);
				if(t.getId()==id){
					t.dir = dir;
					t.x = x;
					t.y = y;
					t.setGunTube(gunTube);
					return;
				}
			}
//System.out.println("id:"+id+"--x:"+x+"--y:"+y+"--Direction:"+dir+"--good:"+good);			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
