import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import common.Command;

import easysocket.packet.Packet;

public class GameStartBox extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton gameStart;
	private int gameWorldId;
	private int clientId;

	public GameStartBox(int gameWorldId, int clientId) {
		this();
		this.gameWorldId = gameWorldId;
		this.clientId = clientId;

	}

	public GameStartBox() {
		this.setLocation(800, 300);
		this.setSize(100, 100);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		gameStart = new JButton("游戏开始");
		this.add(gameStart);
		gameStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GameStartBox.this.setVisible(false);
				ClientMain.showGameBox(gameWorldId, clientId);
				Packet Packet = new Packet(Command.C_READY);
				ClientMain.session.sendPacket(Packet);

			}
		});

		this.setVisible(true);
	}
}
