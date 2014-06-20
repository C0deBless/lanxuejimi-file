import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;


public class MissileNewMsg implements Missage {
	
	int msgType = 3;
	
	Missile m;
	TankClient tc;
	
	
	

	public MissileNewMsg(Missile m) {
		this.m = m;
	}
	
	
	
	public MissileNewMsg(TankClient tc) {
		this.tc = tc;
	}

	public void send(DatagramSocket ds, String IP, int udpPort) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		
		try {
			dos.writeInt(msgType);
			dos.writeInt(m.getTankId());
			dos.writeInt(m.getId());
			dos.writeInt(m.getX());
			dos.writeInt(m.getY());
			dos.writeInt(m.getDir().ordinal());
			dos.writeBoolean(m.isGood());
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
			int tankId = dis.readInt();
			if(tc.myTank.getId() == tankId){
				return;
			}
			int id = dis.readInt();
			int x = dis.readInt();
			int y = dis.readInt();
			Direction dir = Direction.values()[dis.readInt()];
			boolean good = dis.readBoolean();
			Missile m = new Missile(tankId, x, y, good, dir, tc);
			m.setId(id);
			tc.missiles.add(m);
//System.out.println("id:"+id+"--x:"+x+"--y:"+y+"--Direction:"+dir+"--good:"+good);			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

}
