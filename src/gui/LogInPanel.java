package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import db.DBConnection;

public class LogInPanel extends JPanel{
	
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton logInButton;
	
	public LogInPanel() {
		setLayout(new GridBagLayout()); // Set layout
		setVisible(true);
		
		usernameField = new JTextField(15);
		passwordField = new JPasswordField(15);
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
				try {
					DBConnection connection = new DBConnection("src/db/props.properties");
					connection.init();
					ResultSet rs = connection.smallSELECT("SELECT * from employee");
					while (rs.next()) {
						if (rs.getString(1).equals(getUsername()) && rs.getString(2).equals(getPassword())){
							rs.close();
							SwingUtilities.getWindowAncestor(LogInPanel.this).dispose(); // Close login window
							// TODO Set user
							break;
						}
						else if (rs.isLast()) {
							JOptionPane.showMessageDialog(null, "Feil brukernavn og/eller passord!", "Feil", JOptionPane.PLAIN_MESSAGE);
						}
					}
					rs.close();
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			
		});
	}
	
	public String getUsername(){
		return usernameField.getText();
	}
	
	public String getPassword(){
		//TODO kryptering
		return passwordField.getText();
	}
	

}
