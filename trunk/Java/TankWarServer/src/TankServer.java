import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TankServer {
	public static final int TCP_PORT = 8888;
	public static final int UDP_PORT = 6666;
	private static int ID = 100;

	public List<Clinet> clients = new ArrayList<>();

	public static void main(String[] args) {
		new TankServer().start();
	}

	public void start() {

		new Thread(new UDPThread(this)).start();

		ServerSocket ss = null;
		try {
			ss = new ServerSocket(TCP_PORT);

		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			Socket s = null;
			try {
				s = ss.accept();

				DataInputStream dis = new DataInputStream(s.getInputStream());
				String id = s.getInetAddress().getHostAddress();
				int udpPort = dis.readInt();
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				dos.writeInt(ID++);
				Clinet c = new Clinet(id, udpPort);
				clients.add(c);
				System.out.println("A client connected...Address:"
						+ s.getInetAddress() + "--Port:" + s.getPort()
						+ "----UDPPort:" + udpPort);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (s != null) {
					try {
						s.close();
						s = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

}
