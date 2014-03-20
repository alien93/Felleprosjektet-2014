package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.commons.codec.digest.DigestUtils;

import db.DBConnection;
import models.Person;

public class LogInPanel extends JDialog{
	
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton logInButton;
	private Person user;
	
	public LogInPanel(Person user, JFrame f) {
		super(f, "Innlogging", true);
		
		
		setSize(300, 150);
		this.user = user;
		setLayout(new GridBagLayout()); // Set layout
		
		usernameField = new JTextField(15);
		usernameField.setName("UsernameField");
		passwordField = new JPasswordField(15);
		passwordField.setName("passwordField");
		logInButton = new JButton("Logg inn");
		
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(5, 5, 5, 5); // Padding
		gc.anchor = GridBagConstraints.EAST; // Alignment
		gc.gridx = 0; // Column
		gc.gridy = 0; // Row
		add(new JLabel("Brukernavn:"), gc);

		gc.gridx = 0;
		gc.gridy = 1;
		add(new JLabel("Passord:"), gc);

		gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 1;
		gc.gridy = 0;
		add(usernameField, gc);

		gc.gridx = 1;
		gc.gridy = 1;
		add(passwordField, gc);

		gc.gridx = 1;
		gc.gridy = 2;
		add(logInButton, gc);
		
		logInButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				loginAttempt();
			}
		});
		usernameField.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { }
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					loginAttempt();
				}
			}
		});
		passwordField.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { }
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					loginAttempt();
				}
			}
		});
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void loginAttempt() {
		ResultSet rs = null;
		DBConnection con = new DBConnection("src/db/props.properties", true); // Connect to database
		try {
			String username = usernameField.getText();
			String sha1password = DigestUtils.sha1Hex(passwordField.getText());
			rs = con.smallSELECT("SELECT Username, Password FROM employee WHERE Username = '" + username + "'"); // Get credentials from database
			// Using Apache commons codec to easily convert password to sha1 and compare with database
			if (rs.next()) {
				if (rs.getString(1).equals(username) && rs.getString(2).equals(sha1password)) { // Check user and pass
					SwingUtilities.getWindowAncestor(LogInPanel.this).dispose(); // Close login window
					user = new Person(username); // Set user
				}
				else if (rs.isLast()) {
					JOptionPane.showMessageDialog(null, "Feil brukernavn og/eller passord!", "Feil", JOptionPane.PLAIN_MESSAGE);
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Feil brukernavn og/eller passord!", "Feil", JOptionPane.PLAIN_MESSAGE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
			con.close();
		}
	}
	
	public Person getUser() {
		return user;
	}

}
