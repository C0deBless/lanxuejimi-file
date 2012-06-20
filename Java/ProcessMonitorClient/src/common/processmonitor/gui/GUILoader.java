package common.processmonitor.gui;

import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class GUILoader {

	public GUILoader(String name) {
		this.name = name;
	}

	TestFrame frame = new TestFrame();
	ConsoleFrame console = new ConsoleFrame();
	String name;

	public void displayGUI() {
		frame.setSize(1200, 700);
		frame.setTitle(name);
		frame.setVisible(true);
		frame.setLayout(new GridLayout(5, 5));
		frame.addWindowListener(new MainFrameWindowListener());
		console.setSize(1200, 700);
		console.setTitle(name + "-console");

		// JButton consoleButton = new JButton();
		// consoleButton.setText("Show console");
		// consoleButton.setSize(50, 20);
		// consoleButton.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent arg0) {
		// if (console.isShowing()) {
		// console.setVisible(false);
		// } else {
		// console.setVisible(true);
		// }
		//
		// }
		// });
		// frame.add(consoleButton);
	}

	public void showConsole() {
		console.setVisible(true);
	}

	public void closeConsole() {
		console.setVisible(false);
	}

	public static void main(String[] args) {
		System.out.println();
	}
}

class MainFrameWindowListener implements WindowListener {

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {

	}

	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	@Override
	public void windowDeactivated(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.out.println("window closing");
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {

	}
}
