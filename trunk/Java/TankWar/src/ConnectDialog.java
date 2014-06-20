import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


class ConnectDialog extends Dialog{
	/**
	 * 
	 */
	private final TankClient tankClient;
	Button b = new Button("Determine");
	TextField tfIP = new TextField("183.91.206.249", 12);
	TextField tfPort = new TextField(""+TankClient.TANK_SERVER_TCP_PORT,5);
	TextField tfMyPort = new TextField("2224", 5);
	public ConnectDialog(TankClient tankClient) {
		super(this.tankClient, true);
		this.tankClient = tankClient;
		
		this.setLocation(400, 300);
		this.setLayout(new FlowLayout());
		this.addWindowListener(new WindowAdapter() {
			 @Override
			public void windowClosing(WindowEvent e) {
				 setVisible(false);
			}
		});
		this.add(new Label("IP"));
		this.add(tfIP);
		this.add(new Label("DUP_PORT"));
		this.add(tfPort);
		this.add(new Label("MY_UDP_PORT"));
		this.add(tfMyPort);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				String ip = tfIP.getText();
				int port = Integer.parseInt(tfPort.getText());
				int myUdpPort = Integer.parseInt(tfMyPort.getText());
				ConnectDialog.this.tankClient.nc.setUdpPort(myUdpPort);
				ConnectDialog.this.tankClient.nc.connect(ip, port);
			}
		});
		this.add(b);
		this.pack();
	}
}