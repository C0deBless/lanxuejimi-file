package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Server {
	public static final String HOST = "127.0.0.1";
	public static final int POST = 1989;
	public static final String DISCONNECT_TOKEN = "disconnect";
	public static final String CONNECT_USER_FALG = "connect:";
	public static final String CHAT_FLAG_START = "to:";
	public static final String CHAT_FALG_END = ":end";
	private Map<String, ServerThread> st;
	public static PacketProcessor pakcetProcessor;

	public static void main(String[] args) {
		pakcetProcessor = new PacketProcessor();
		new Server().startup();
	}

	public void startup() {
		ServerSocket ss = null;

		try {
			ss = new ServerSocket(POST);
			st = new HashMap<String, ServerThread>();
			while (true) {
				Socket s = ss.accept();
				new Thread(new ServerThread(s)).start();

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ss != null)
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}

	public class ServerThread implements Runnable {
		private Socket s = null;
		private BufferedReader in = null;
		private PrintWriter out = null;
		private String name;
		private boolean close = false;

		public String name(){
			return name;
		}
		
		public ServerThread(Socket s) throws IOException {
			this.s = s;
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(s.getOutputStream(), true);
			name = in.readLine();
			name += "[" + s.getInetAddress().getHostAddress() + "]";
			st.put(name, this);
			send(name + "上线了");
			sendUser();
		}

		public void sendUser() {
			String us = CONNECT_USER_FALG;
			Set<String> keys = st.keySet();
			for (String u : keys) {
				us += u + ",";
			}
			send(us);

		}

		public void closef() {
			close = true;
			out.println(DISCONNECT_TOKEN);
			System.out.println(name + "断开连接了");
			st.remove(name);
			sendUser();
		}

		public void send(String msg) {
			Set<String> key = st.keySet();
			for (String keys : key) {
				st.get(keys).out.println(msg);
			}
		}

		public String getUsersBymsg(String msg) {
			String str = msg.substring(CHAT_FLAG_START.length(), msg.indexOf(CHAT_FALG_END));
			msg.substring(3, 11);
			return str;
		}

		public String formatMsg(String msg) {
			String str = msg.substring(msg.indexOf(CHAT_FALG_END) + CHAT_FALG_END.length());
			return str;
		}

		public void sendPrivateUsers(String user, String msg) {
			String[] uus = user.split(",");
			for (String u : uus) {
				ServerThread thread = st.get(u);
				if(thread == null){
					System.err.println("找不到ServerThread， user:" + u);
					continue;
				}
				thread.out.println(name + ":" + msg + "[私]");
			}
		}

		@Override
		public void run() {
			try {
				while (true) {
					if (close)
						break;
					String str = in.readLine();
					Packet packet = new Packet(str, this);
					Server.pakcetProcessor.pushPacket(packet);

				}
			} catch (SocketException e) {
				System.out.println(name + "非正常的退出了");
				this.closef();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (s != null)
					try {
						s.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}

	}

}
