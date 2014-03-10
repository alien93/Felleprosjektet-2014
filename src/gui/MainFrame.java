package gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import models.Person;

public class MainFrame extends JFrame {

	private JDialog loginDialog;
	private Person user;
	
	public MainFrame() {
		super("Avtalebok");
		setSize(1024, 768);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
                loginGUI();
            }
		});
		loginGUI();
		
	}
	
	private void loginGUI() {
		LogInPanel loginPanel = new LogInPanel();
		loginDialog = new JDialog(this, "Innlogging", true);
		loginDialog.setSize(300, 150);
		loginDialog.setLocationRelativeTo(null); // Place in center of screen
		loginDialog.add(loginPanel);
		loginDialog.setVisible(true);
		
		if(loginPanel.getUser() != null) { // After dialog is dismissed we can set the user
			user = loginPanel.getUser();
			// TODO Open main window
			setExtendedState(JFrame.MAXIMIZED_BOTH);
		} else {
			System.exit(-1); // Otherwise, shut down
		}
	}
	
	public static void main(String[] args){
		new MainFrame().setVisible(true);
	}
	
}
