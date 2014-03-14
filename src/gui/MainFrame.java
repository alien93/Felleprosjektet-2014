package gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import models.Person;

public class MainFrame extends JFrame {

	private JDialog loginDialog;
	private Person user;

	public MainFrame() {
		super("Avtalebok");
		try {
            // Set cross-platform Java L&F (also called "Metal")
        UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
		} 
		catch (UnsupportedLookAndFeelException e) {}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		
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
		LogInPanel loginDialog = new LogInPanel(user, this);
		
		if(loginDialog.getUser() != null) { // After dialog is dismissed we can set the user
			user = loginDialog.getUser(); // Set user
			mainGUI(); // Open main window
		}
		else {
			System.exit(-1); // Otherwise, shut down
		}
	}
	
	private void mainGUI() {
		setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize window
		AvtaleBok calendar = new AvtaleBok(user, this);
		add(calendar);
		
		setVisible(true);
	}

	public static void main(String[] args){
		new MainFrame();
	}

}
