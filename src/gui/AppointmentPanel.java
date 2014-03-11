package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Date;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import panels.Participants;
import models.Person;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

public class AppointmentPanel extends JPanel {
	
	private JTextField nameField;
	private JLabel nameLabel;
	private JLabel dateLabel;
	private JCalendar calender;
	public AppointmentPanel(){
		setLayout(new GridBagLayout());
		nameField= new JTextField("                            ");
		nameLabel= new JLabel("Name");
		dateLabel= new JLabel("Date");
		calender = new JCalendar();
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.getDateEditor().addPropertyChangeListener(new dateChooserListener());
		//nameField.setSize(100,10);
		calender.add(dateChooser);

		
		GridBagConstraints nameLabelConstraint = new GridBagConstraints();
		nameLabelConstraint.gridx = 0;
		nameLabelConstraint.gridy = 0;
		add(nameLabel, nameLabelConstraint);
		
		GridBagConstraints nameFieldConstraint = new GridBagConstraints();
		nameFieldConstraint.gridx = 1;
		nameFieldConstraint.gridy = 0;
		add(nameField, nameFieldConstraint);
		
		GridBagConstraints dateLabelConstraint = new GridBagConstraints();
		dateLabelConstraint.gridx = 0;
		dateLabelConstraint.gridy = 1;
		add(dateLabel, dateLabelConstraint);
		
		
		GridBagConstraints dateChooserConstraint = new GridBagConstraints();
		dateChooserConstraint.gridx=1;
		dateChooserConstraint.gridy=1;
		add(dateChooser,dateChooserConstraint);
	}

	class dateChooserListener implements PropertyChangeListener  {
		public void propertyChangeListener(PropertyChangeEvent ae){
			  /* if ("date".equals(ae.getPropertyName())) {
	                System.out.println(ae.getPropertyName()
	                    + ": " + (Date) ae.getNewValue());
	            }
	            */
		}
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Ny avtale");
		frame.getContentPane().add(new AppointmentPanel());
		frame.pack();
		frame.setVisible(true);
	}
	

}
