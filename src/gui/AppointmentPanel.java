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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import models.Person;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

public class AppointmentPanel extends JPanel {
	
	private JTextField nameField;
	private JLabel nameLabel;
	private JLabel dateLabel;
	private JCalendar calender;
	private JLabel startTimeLabel;
	private JLabel endTimeLabel;
	private JLabel roomLabel;
	private JLabel alarmLabel;
	private JButton saveButton;
	private JButton addButton;
	private JButton	shallButton;
	private JButton shallNotButton;
	private JComboBox starTimeHourPropertyComponent;
	private JComboBox starTimeMinutesPropertyComponent;
	private JComboBox endTimeHourPropertyComponent;
	private JComboBox  endTimeMinutePropertyComponent;
	private JComboBox roomPropertyComponent;
	private JComboBox alarmPropertyComponent;
	private JScrollPane participantsPane;
	

	
	
	public AppointmentPanel(){
		setLayout(new GridBagLayout());
		nameField= new JTextField("                            ");
		nameLabel= new JLabel("Name");
		dateLabel= new JLabel("Date");
		calender = new JCalendar();
		startTimeLabel= new JLabel("Starttid");
		endTimeLabel= new JLabel("Sluttid");
		roomLabel= new JLabel("Møterom");
		alarmLabel = new JLabel("Alarm");
		saveButton = new JButton("Lagre");
		addButton= new  JButton("Legg til/fjern");
		shallButton= new JButton("Skal");
		shallNotButton = new JButton("Skal ikke");
		
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
		
		GridBagConstraints startTimeLabelConstraint = new GridBagConstraints();
		startTimeLabelConstraint.gridx=0;
		startTimeLabelConstraint.gridy=2;
		add(startTimeLabel, startTimeLabelConstraint);
		
		GridBagConstraints endTimeLabelConstraint = new GridBagConstraints();
		endTimeLabelConstraint.gridx=0;
		endTimeLabelConstraint.gridy=3;
		add(endTimeLabel,endTimeLabelConstraint);
		
		GridBagConstraints roomLabelConstraint= new GridBagConstraints();
		roomLabelConstraint.gridx=0;
		roomLabelConstraint.gridy=4;
		add(roomLabel,roomLabelConstraint);
		
		GridBagConstraints alarmLabelConstraint = new GridBagConstraints();
		alarmLabelConstraint.gridx=0;
		alarmLabelConstraint.gridy=5;
		add(alarmLabel,alarmLabelConstraint);
		
		
		String[] hourStrings = { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16" , "17", "18", "19", "20", "21", "22", "23"}; 
		String[] minuteStrings = { "00","15","30","45"};

		
		starTimeHourPropertyComponent= new JComboBox(hourStrings);
		GridBagConstraints starTimePropertyComponentConstraint= new GridBagConstraints();
		starTimePropertyComponentConstraint.gridx=1;
		starTimePropertyComponentConstraint.weightx=1;
		starTimePropertyComponentConstraint.fill=GridBagConstraints.HORIZONTAL;
		starTimePropertyComponentConstraint.gridy=2;
		add(starTimeHourPropertyComponent,starTimePropertyComponentConstraint);
		
		
		starTimeMinutesPropertyComponent = new JComboBox(minuteStrings);
		GridBagConstraints starTimeMinutesPropertyComponentConstraint = new GridBagConstraints();
		starTimeMinutesPropertyComponentConstraint.fill=GridBagConstraints.HORIZONTAL;
		starTimeMinutesPropertyComponentConstraint.weightx=1;
		starTimeMinutesPropertyComponentConstraint.gridx=2;
		starTimeMinutesPropertyComponentConstraint.gridy=2;;
		add(starTimeMinutesPropertyComponent,starTimeMinutesPropertyComponentConstraint);
		
		endTimeHourPropertyComponent= new JComboBox(hourStrings);
		GridBagConstraints endTimeHourPropertyComponentConstraint = new GridBagConstraints();
		endTimeHourPropertyComponentConstraint.gridx=1;
		endTimeHourPropertyComponentConstraint.fill= GridBagConstraints.HORIZONTAL;
		endTimeHourPropertyComponentConstraint.weightx=1;
		endTimeHourPropertyComponentConstraint.gridy=3;
		add(endTimeHourPropertyComponent,endTimeHourPropertyComponentConstraint);
		
		endTimeMinutePropertyComponent = new JComboBox(minuteStrings);
		GridBagConstraints endTimeMinutePropertyComponentConstraint = new GridBagConstraints();
		endTimeMinutePropertyComponentConstraint.gridx=2;
		endTimeMinutePropertyComponentConstraint.fill=GridBagConstraints.HORIZONTAL;
		endTimeMinutePropertyComponentConstraint.gridy=3;
		endTimeMinutePropertyComponentConstraint.weightx=1;
		add(endTimeMinutePropertyComponent,endTimeMinutePropertyComponentConstraint);
		
		
		String[] rooms = { "test","test"};
		roomPropertyComponent = new JComboBox(rooms);
		GridBagConstraints roomPropertyComponentConstraint = new GridBagConstraints();
		roomPropertyComponentConstraint.gridx=1;
		roomPropertyComponentConstraint.gridy=4;
		roomPropertyComponentConstraint.fill=GridBagConstraints.HORIZONTAL;

		add(roomPropertyComponent, roomPropertyComponentConstraint);
		
		
		String[] alarms = { "På","Av"};
		alarmPropertyComponent = new JComboBox(alarms);
		GridBagConstraints alarmPropertyComponentConstraint = new GridBagConstraints();
		alarmPropertyComponentConstraint.gridx=1;
		alarmPropertyComponentConstraint.gridy=5;
		alarmPropertyComponentConstraint.fill= GridBagConstraints.HORIZONTAL;
		add(alarmPropertyComponent,alarmPropertyComponentConstraint);
		
	
	
	    String headers[] = { "Deltakere", "Status" };
	    String rows[][] = { { "ola", "1" }, { "per", "2" }};
	    JTable table = new JTable(rows,headers);
		participantsPane = new JScrollPane(table);
		participantsPane.setSize(10, 10);
		participantsPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		GridBagConstraints participantsPaneConstraint = new GridBagConstraints();
		participantsPaneConstraint.gridx=7;
		participantsPaneConstraint.gridy=2;
		add(participantsPane,participantsPaneConstraint);
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
