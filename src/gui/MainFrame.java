package gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import models.Person;

public class MainFrame extends JFrame {

	private JDialog loginDialog;
	private Person user;

	public MainFrame() {
		super("Avtalebok");
		setSize(1024, 768);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // We use a custom close action
		
		UIManager.put("OptionPane.yesButtonText", "Ja"); // Translate "yes" and "no" options
		UIManager.put("OptionPane.noButtonText", "Nei");
		WindowListener exitListener = new WindowAdapter() { // Custom window closing event
			public void windowClosing(WindowEvent e) {
				int confirm = JOptionPane.showOptionDialog(null, "Er du sikker på at du vil logge ut?", "Bekreftelse", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (confirm == 0) { // If user is sure,
					setVisible(false);
					loginGUI();
				}
			}
		};
		addWindowListener(exitListener); // Add window listener to JFrame

		loginGUI(); // Open login screen for the first time

	}

	private void loginGUI() {
		user = null;
		LogInPanel loginPanel = new LogInPanel(user);
		loginDialog = new JDialog(this, "Innlogging", true);
		loginDialog.setSize(300, 150);
		loginDialog.setLocationRelativeTo(null); // Place in center of screen
		loginDialog.add(loginPanel);
		loginDialog.setVisible(true);
		
		if(loginPanel.getUser() != null) { // After dialog is dismissed we can set the user
			user = loginPanel.getUser(); // Set user
			mainGUI(); // Open main window
		}
		else {
			System.exit(-1); // Otherwise, shut down
		}
	}
	
	private void mainGUI() {
		setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize window
		setVisible(true);
		// TODO Add calendar panel here
	}

	public static void main(String[] args){
		new MainFrame();
	}

}
