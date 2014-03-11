package appointment;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import panels.Participants;
import models.Person;

import com.toedter.calendar.JCalendar;

public class AppointmentPanel extends JPanel {
	
	private JTextField nameField;
	private JTextField dateField;
	private JButton dateButton;
	public AppointmentPanel(){
		setLayout(new GridBagLayout());
		nameField= new JTextField("Navn");
		dateField = new JTextField("Dato");
		dateButton= new JButton();

		
		
		GridBagConstraints nameFieldConstraint = new GridBagConstraints();
		nameFieldConstraint.gridx = 0;
		nameFieldConstraint.gridy = 0;
		add(nameField, nameFieldConstraint);
		
		GridBagConstraints dateFieldConstraint = new GridBagConstraints();
		dateFieldConstraint.gridx = 0;
		dateFieldConstraint.gridy = 1;
		add(dateField, dateFieldConstraint);
		
		GridBagConstraints dateButtonConstraint = new GridBagConstraints();
		dateButtonConstraint.gridx = 1;
		dateButtonConstraint.gridy = 1;
		add(dateButton,dateButtonConstraint);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Ny avtale");
		frame.getContentPane().add(new AppointmentPanel());
		frame.pack();
		frame.setVisible(true);
	}
	

}
