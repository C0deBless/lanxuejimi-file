import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class MissileDeadMsg implements Missage {

	int msgType = Missage.MISSILE_DEAD_MSG;

	int tankId;
	int id;

	TankClient tc;

	public MissileDeadMsg(int tankId, int id) {
		this.tankId = tankId;
		this.id = id;
	}

	public MissileDeadMsg(TankClient tc) {
		this.tc = tc;
	}

	public void send(DatagramSocket ds, String IP, int udpPort) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);

		try {
			dos.writeInt(msgType);
			dos.writeInt(tankId);
			dos.writeInt(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] buf = baos.toByteArray();

		try {
			DatagramPacket dp = new DatagramPacket(buf, buf.length,
					new InetSocketAddress(IP, udpPort));
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
			int id = dis.readInt();

			for (int i = 0; i < tc.missiles.size(); i++) {
				Missile m = tc.missiles.get(i);
				if (m.getTankId() == tankId && m.getId() == id) {
					m.setLive(false);
					tc.explode.add(new Explode(m.getX(), m.getY(), tc));
					break;
				}
			}
			// System.out.println("id:"+id+"--x:"+x+"--y:"+y+"--Direction:"+dir+"--good:"+good);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
