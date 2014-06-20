import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;


public class TankNewMsg implements Missage{
	
	int msgType = 1;
	Tank t;
	TankClient tc;
	public TankNewMsg(Tank t) {
		this.t = t;
		
	}
	
	public TankNewMsg(TankClient tc) {
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
			dos.writeInt(t.dir.ordinal());
			dos.writeBoolean(t.isGood());
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
			Direction dir = Direction.values()[dis.readInt()];
			boolean good = dis.readBoolean();
			boolean exist = false;
			for(int i=0; i<tc.tanks.size(); i++){
				Tank t = tc.tanks.get(i);
				if(t.getId() == id){
					exist = true;
					return;
				}
			}
			if(!exist){
				
				TankNewMsg tnMsg = new TankNewMsg(tc.myTank);
				tc.nc.send(tnMsg);
				
				Tank t = new Tank(x, y, good, tc);
				t.setId(id);
				tc.tanks.add(t);
			}
			
//System.out.println("id:"+id+"--x:"+x+"--y:"+y+"--Direction:"+dir+"--good:"+good);			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
