package socket;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Login extends JFrame {
	private JButton jb;
	private JTextField jtf;
	private JLabel jl;

	public static void main(String[] args) {
		new Login();
	}

	public void close() {
		this.setVisible(false);
	}

	public Login() {
		this.setTitle("用户登录");
		this.setLocation(100, 100);
		this.setSize(300, 90);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new FlowLayout());

		jb = new JButton("连接");
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == jb) {

					String name = jtf.getText();
					if (name == null || name.trim().equals("")) {
						JOptionPane.showMessageDialog(null, "必须输入用户名");
						return;
					}
					new ChatClient(name);
					close();
				}
			}
		});
		jl = new JLabel("用户名");
		jtf = new JTextField(20);
		this.add(jl);
		this.add(jtf);
		this.add(jb);

		this.setVisible(true);
	}
}
