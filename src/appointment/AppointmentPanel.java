package appointment;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import panels.Participants;
import models.Person;

import com.toedter.calendar.JCalendar;

public class AppointmentPanel extends JPanel {
	
	private JTextField nameField;
	private JCalendar dateField;
	private JComboBox dateBox;
	public AppointmentPanel(){
		setLayout(new GridBagLayout());
		nameField= new JTextField("Navn");
		dateField= new JCalendar();
		dateBox= new JComboBox();
		add(dateBox);
		dateField.setPreferredSize(new Dimension(500,500));
		
		
		GridBagConstraints nameFieldConstraint = new GridBagConstraints();
		nameFieldConstraint.gridx = 0;
		nameFieldConstraint.gridy = 0;
		add(nameField, nameFieldConstraint);
		
		GridBagConstraints dateFieldConstraint = new GridBagConstraints();
		dateFieldConstraint.gridx = 0;
		dateFieldConstraint.gridy = 1;
		add(dateField, dateFieldConstraint);
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Ny avtale");
		frame.getContentPane().add(new AppointmentPanel());
		frame.pack();
		frame.setVisible(true);
	}
	

}
