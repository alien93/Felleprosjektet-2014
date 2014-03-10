package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import db.DBConnection;

public class LogInPanel extends JPanel{
	
	private JTextField usernameTextField;
	private JPasswordField passwordField;
	
	private JButton logInButton;
	
	public LogInPanel(){
		
		usernameTextField = new JTextField(20);
		passwordField = new JPasswordField(20);
		
		logInButton = new JButton("Logg inn");
		
		this.add(new JLabel("Brukernavn: "));
		this.add(usernameTextField);
		
		this.add(new JLabel("Passord: "));
		this.add(passwordField);
		
		this.add(logInButton);
		
		logInButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent ae) {
				try {
					DBConnection connection = new DBConnection("src/db/props.properties");
					connection.init();
					ResultSet rs = connection.smallSELECT("SELECT * from ansatt");
					while (rs.next()) {
						if (rs.getString(1).equals(getUsername()) && rs.getString(2).equals(getPassword())){
							System.out.println("Velkommen, " + rs.getString(1) + ". Du er nå logget inn.");
							rs.close();
							break;
						}
						else if (rs.isLast()) System.out.println("Feil brukernavn eller passord.");
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
	
	public static void main(String[] args){
		LogInPanel panel = new LogInPanel();
		JFrame frame = new JFrame("Log in");
		frame.setSize(250, 180);
		frame. setVisible(true);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public String getUsername(){
		return this.usernameTextField.getText();
	}
	
	public String getPassword(){
		//TODO kryptering
		return this.passwordField.getText();
	}
	

}
