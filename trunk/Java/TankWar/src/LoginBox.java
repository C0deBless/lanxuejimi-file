import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LoginBox extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel name = new JLabel("坦克名称：");
	private JTextField gameName = new JTextField(16);
	private JButton login = new JButton("登陆");
	private JButton exit = new JButton("退出");

	public static void main(String[] args) {
		new LoginBox();
	}

	public LoginBox() {
		this.setTitle("登陆坦克游戏");
		this.setLocation(800, 300);
		this.setSize(300, 100);
		this.setResizable(true);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		this.setLayout(new FlowLayout());

		this.add(name);
		this.add(gameName);
		this.add(login);
		this.add(exit);

		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LoginBox.this.setVisible(false);
				String name = gameName.getText();

				ClientMain.connect(name);

			}
		});
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		this.setVisible(true);
	}
}
