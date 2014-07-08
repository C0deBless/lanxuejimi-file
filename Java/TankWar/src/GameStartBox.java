import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import common.Command;
import common.Packet;
import common.StringUtil;


public class GameStartBox extends JFrame{
	
	private JButton gameStart;
	private int clientId;
	private String name;
	public static void main(String[] args) {
		new GameStartBox();
	}
	public GameStartBox(int clientId, String name) {
		this();
		this.clientId = clientId;
		this.name = name;
		
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
				Packet writePacket = new Packet(Command.C_START);
				writePacket.getByteBuffer().putInt(clientId);
				StringUtil.putString(writePacket.getByteBuffer(), name);
				ClientMain.client.pushWritePacket(writePacket);
				
			}
		});
		
		
		this.setVisible(true);
	}
}
