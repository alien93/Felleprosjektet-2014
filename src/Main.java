
import javax.swing.JFrame;

import db.*;
import GUI.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LogInPanel panel = new LogInPanel();
		JFrame frame = new JFrame("Log in");
		frame.setSize(250, 180);
		frame. setVisible(true);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
