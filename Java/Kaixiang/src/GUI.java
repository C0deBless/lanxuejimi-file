import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class GUI {

	public static void main(String[] args) throws InterruptedException {
		JFrame frame = new JFrame();
		frame.setSize(600, 600);

		JFileChooser fc = new JFileChooser();
		frame.add(fc);
		

		frame.setVisible(true);

	}
}
