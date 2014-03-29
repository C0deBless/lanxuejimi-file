package socket;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClient extends JFrame {
	private JList<String> list;
	private DefaultListModel<String> model;
	private JTextField jtf;
	private JTextArea jta;
	private JButton jb;
	private JPanel jp;
	private JScrollPane jsp1, jsp2;
	private String name;
	private Socket s = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private boolean stop = false;

	public static void main(String[] args) {

	}

	public ChatClient(String name) {
		this.name = name;
		this.setTitle("网络聊天|当前用户是[" + this.name + "]");
		this.setLocation(100, 100);
		this.setSize(800, 600);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				out.println(Server.DISCONNECT_TOKEN);
			}
		});

		this.setResizable(false);

		model = new DefaultListModel<String>();
		list = new JList<String>(model);
		list.setFixedCellWidth(100);
		jsp1 = new JScrollPane(list);
		this.add(jsp1, BorderLayout.WEST);

		jtf = new JTextField(30);
		jtf.addKeyListener(new KeyClick());
		jb = new JButton("发送");
		jb.addActionListener(new ButtonClick());
		jp = new JPanel();
		jp.add(jtf);
		jp.add(jb);
		this.add(jp, BorderLayout.SOUTH);
		jta = new JTextArea();
		jsp2 = new JScrollPane(jta);
		this.add(jsp2);

		this.connect();
		this.setVisible(true);

	}

	public void connect() {
		try {
			s = new Socket(Server.HOST, Server.POST);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(s.getOutputStream(), true);
			out.println(this.name);
			new Thread(new ReceiveThread()).start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void send() {
		String word = jtf.getText();
		if (word == null || word.trim().equals("")) {
			return;
		}
		List<String> vals = list.getSelectedValuesList();
		boolean isAll = false;
		String us = Server.CHAT_FLAG_START;
		if (vals.size() <= 0)
			isAll = true;
		for (String s : vals) {
			if (s.equals("所有人")) {
				isAll = true;
				break;
			} else {
				us += s + ",";
			}
		}
		if (isAll) {
			out.println(us + "all:end" + word);
		} else {
			out.println(us + ":end" + word);
		}
		jtf.setText("");
	}

	public class ButtonClick implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == jb) {
				send();
			}
		}
	}

	public class KeyClick extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				send();
			}
		}
	}

	public void stopf() {
		stop = true;
	}

	public void handList(String str) {
		String users = str.substring(Server.CONNECT_USER_FALG.length());
		String[] us = users.split(",");
		model.removeAllElements();
		for (String u : us) {
			model.addElement(u);
		}
	}

	public void receive() throws IOException {
		String str = in.readLine();
		if (str.equals(Server.DISCONNECT_TOKEN)) {
			this.stopf();
		}
		if (str.startsWith(Server.CONNECT_USER_FALG)) {
			handList(str);
			return;
		}
		jta.setText(jta.getText() + str + "\n");

	}

	public class ReceiveThread implements Runnable {
		@Override
		public void run() {
			try {
				while (true) {
					if (stop)
						break;
					receive();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (s != null)

					System.exit(0);
			}
		}
	}
}
