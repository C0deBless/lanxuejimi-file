package chatting.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chatting.Client;
import chatting.Command;
import easysocket.packet.Packet;
import easysocket.utils.StringUtil;

public class LoginFrame extends JFrame {

	static Logger logger = LoggerFactory.getLogger(LoginFrame.class);

	private static final long serialVersionUID = 1L;

	JLabel userNameLabel;
	JLabel passwordLabel;
	JTextField userNameBox;
	JPasswordField passwordBox;
	JButton loginButton;
	JButton registerButton;

	public LoginFrame() {
		super();
		this.setTitle("登陆");
		this.setLocation(100, 100);
		this.setSize(300, 100);
		this.setLayout(new GridLayout(3, 2));
		initUI();
	}

	private void initUI() {
		userNameLabel = new JLabel();
		userNameLabel.setText("用户名：");
		passwordLabel = new JLabel();
		passwordLabel.setText("密码：");
		userNameBox = new JTextField(16);
		passwordBox = new JPasswordField(16);
		loginButton = new JButton();
		loginButton.setText("登陆");
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == loginButton) {
					String userName = userNameBox.getText();
					String password = new String(passwordBox.getPassword());
					logger.debug("userName:{}, password:{}", userName, password);

					Packet packet = new Packet(Command.C_LOGIN, Client.session);
					StringUtil.putString(packet.getByteBuffer(), userName); // username
					StringUtil.putString(packet.getByteBuffer(), password); // password
					Client.session.sendPacket(packet);
				}
			}
		});
		registerButton = new JButton();
		registerButton.setText("注册");

		this.add(userNameLabel);
		this.add(userNameBox);
		this.add(passwordLabel);
		this.add(passwordBox);
		this.add(loginButton);
		this.add(registerButton);
	}

	public void start() {
		this.setVisible(true);
	}
}
