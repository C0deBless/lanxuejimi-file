package common.processmonitor.gui;

import java.awt.Color;
import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ConsoleFrame extends JFrame {

	private static final long serialVersionUID = 5647327051352636360L;

	JTextArea textArea;

	public ConsoleFrame() {
		textArea = new JTextArea();
		textArea.setBackground(Color.WHITE);
		this.add(textArea);
		// captureConsoleOutput();
	}

	public void captureConsoleOutput() {
		System.setOut(new PrintStream(new ConsoleOutputStream(this.textArea)));
	}
}

class ConsoleOutputStream extends OutputStream {

	JTextArea textArea;
	private Console console = null;
	private ByteBuffer consoleBuffer = ByteBuffer.allocate(65536);

	public ConsoleOutputStream(JTextArea textArea) {
		this.textArea = textArea;
	}

	@Override
	public void write(int b) throws IOException {
		consoleBuffer.put((byte) b);
		if ((char) b == '\n') {
			String s = null;
			int size = consoleBuffer.position();
			byte[] bs = new byte[size];
			consoleBuffer.clear();
			consoleBuffer.get(bs);
			s = new String(bs);
			consoleBuffer.clear();
			// if (textArea.isVisible())
			this.textArea.append(s);
			if (console != null) {
				this.console.format("%s", s);
			}
		}
	}

}
