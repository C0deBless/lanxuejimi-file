import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;

class UDPThread implements Runnable {

	private final NetClient netClient;

	UDPThread(NetClient netClient) {
		this.netClient = netClient;
	}

	byte[] buf = new byte[1024];

	public void run() {
		while (this.netClient.ds != null) {
			DatagramPacket dp = new DatagramPacket(buf, buf.length);
			try {
				this.netClient.ds.receive(dp);
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
		switch (msgType) {
		case Missage.TANK_NEW_MSG:
			msg = new TankNewMsg(this.netClient.tc);
			msg.parse(dis);
			break;
		case Missage.TANK_MOVE_MSG:
			msg = new TankMoveMsg(this.netClient.tc);
			msg.parse(dis);
			break;
		case Missage.MISSILE_NEW_MSG:
			msg = new MissileNewMsg(this.netClient.tc);
			msg.parse(dis);
			break;
		case Missage.TANK_DEAD_MSG:
			msg = new TankDeadMsg(this.netClient.tc);
			msg.parse(dis);
			break;
		case Missage.MISSILE_DEAD_MSG:
			msg = new MissileDeadMsg(this.netClient.tc);
			msg.parse(dis);
			break;
		}

	}
}