package gui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

	private JDialog loginDialog;
	
	public MainFrame() {
		super("Avtalebok");
		setSize(1280, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		loginGUI();
		
	}
	
	public void loginGUI() {
		LogInPanel loginPanel = new LogInPanel();
		loginDialog = new JDialog(this, "Innlogging", true);
		loginDialog.setSize(300, 150);
		loginDialog.setLocationRelativeTo(null); // Place in center of screen
		loginDialog.add(loginPanel);
		loginDialog.setVisible(true);
	}
	
	public static void main(String[] args){
		new MainFrame().setVisible(true);
	}
	
}
