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
	
	protected JTextField nameField;
	private JLabel nameLabel;
	private JLabel dateLabel;
	protected JCalendar calender;
	private JLabel startTimeLabel;
	private JLabel endTimeLabel;
	private JLabel roomLabel;
	private JLabel alarmLabel;
	protected JButton saveButton;
	protected JButton addButton;
	protected JButton	shallButton;
	protected JButton shallNotButton;
	protected JComboBox starTimeHourPropertyComponent;
	protected JComboBox starTimeMinutesPropertyComponent;
	protected JComboBox endTimeHourPropertyComponent;
	protected JComboBox  endTimeMinutePropertyComponent;
	protected JComboBox roomPropertyComponent;
	protected JComboBox alarmPropertyComponent;
	private JLabel emailLabel;
	protected JTextField emailField;
	protected JButton addExternal;
	protected JScrollPane participantsPane;
	private final String[] hourStrings = { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16" , "17", "18", "19", "20", "21", "22", "23"}; 
	private final String[] minuteStrings = { "00","15","30","45"};
	private final String[] alarms = { "På","Av"};
	protected JDateChooser dateChooser;
	
	public AppointmentPanel(final JFrame jf){
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
		emailLabel = new JLabel("Epost til ekstern deltager");
		addExternal= new JButton("Legg til");
		emailField = new JTextField("");
		
		
		dateChooser = new JDateChooser();
		dateChooser.getDateEditor().addPropertyChangeListener(new dateChooserListener());
		//nameField.setSize(100,10);
		calender.add(dateChooser);

		
		GridBagConstraints nameLabelConstraint = new GridBagConstraints();
		nameLabelConstraint.gridx = 0;
		nameLabelConstraint.gridy = 0;
		nameLabelConstraint.fill=GridBagConstraints.HORIZONTAL;
		add(nameLabel, nameLabelConstraint);
		
		GridBagConstraints nameFieldConstraint = new GridBagConstraints();
		nameFieldConstraint.gridx = 1;
		nameFieldConstraint.gridy = 0;
		nameFieldConstraint.fill = GridBagConstraints.HORIZONTAL;
		nameFieldConstraint.gridwidth=2;
		add(nameField, nameFieldConstraint);
		
		GridBagConstraints dateLabelConstraint = new GridBagConstraints();
		dateLabelConstraint.gridx = 0;
		dateLabelConstraint.gridy = 1;
		dateLabelConstraint.fill= GridBagConstraints.HORIZONTAL;
		add(dateLabel, dateLabelConstraint);
		
		
		GridBagConstraints dateChooserConstraint = new GridBagConstraints();
		dateChooserConstraint.gridx=1;
		dateChooserConstraint.gridy=1;
		dateChooserConstraint.fill =GridBagConstraints.HORIZONTAL;
		dateChooserConstraint.gridwidth=2;
		add(dateChooser,dateChooserConstraint);
	
		
		GridBagConstraints startTimeLabelConstraint = new GridBagConstraints();
		startTimeLabelConstraint.gridx=0;
		startTimeLabelConstraint.gridy=2;
		startTimeLabelConstraint.fill=GridBagConstraints.HORIZONTAL;
		add(startTimeLabel, startTimeLabelConstraint);
		
		GridBagConstraints endTimeLabelConstraint = new GridBagConstraints();
		endTimeLabelConstraint.gridx=0;
		endTimeLabelConstraint.gridy=3;
		endTimeLabelConstraint.fill=GridBagConstraints.HORIZONTAL;
		add(endTimeLabel,endTimeLabelConstraint);
		
		GridBagConstraints roomLabelConstraint= new GridBagConstraints();
		roomLabelConstraint.gridx=0;
		roomLabelConstraint.gridy=4;
		roomLabelConstraint.fill=GridBagConstraints.HORIZONTAL;
		roomLabelConstraint.anchor=GridBagConstraints.NORTH;
		add(roomLabel,roomLabelConstraint);
		
		GridBagConstraints alarmLabelConstraint = new GridBagConstraints();
		alarmLabelConstraint.gridx=0;
		alarmLabelConstraint.gridy=5;
		alarmLabelConstraint.fill=GridBagConstraints.HORIZONTAL;
		alarmLabelConstraint.anchor = GridBagConstraints.NORTH;
		add(alarmLabel,alarmLabelConstraint);
		
		
		starTimeHourPropertyComponent= new JComboBox(hourStrings);
		GridBagConstraints starTimePropertyComponentConstraint= new GridBagConstraints();
		starTimePropertyComponentConstraint.gridx=1;
		starTimePropertyComponentConstraint.weightx=0.5;
		starTimePropertyComponentConstraint.fill=GridBagConstraints.HORIZONTAL;
		starTimePropertyComponentConstraint.gridy=2;
		add(starTimeHourPropertyComponent,starTimePropertyComponentConstraint);
		
		
		starTimeMinutesPropertyComponent = new JComboBox(minuteStrings);
		GridBagConstraints starTimeMinutesPropertyComponentConstraint = new GridBagConstraints();
		starTimeMinutesPropertyComponentConstraint.fill=GridBagConstraints.HORIZONTAL;
		starTimeMinutesPropertyComponentConstraint.gridwidth=1;
		starTimeMinutesPropertyComponentConstraint.weightx=0.5;
		starTimeMinutesPropertyComponentConstraint.gridx=2;
		starTimeMinutesPropertyComponentConstraint.gridy=2;;
		add(starTimeMinutesPropertyComponent,starTimeMinutesPropertyComponentConstraint);
		
		endTimeHourPropertyComponent= new JComboBox(hourStrings);
		GridBagConstraints endTimeHourPropertyComponentConstraint = new GridBagConstraints();
		endTimeHourPropertyComponentConstraint.gridx=1;
		endTimeHourPropertyComponentConstraint.fill= GridBagConstraints.HORIZONTAL;
		endTimeHourPropertyComponentConstraint.weightx=1;
		endTimeHourPropertyComponentConstraint.gridy=3;
		endTimeHourPropertyComponentConstraint.gridwidth=1;
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
		roomPropertyComponentConstraint.gridwidth=2;
		

		add(roomPropertyComponent, roomPropertyComponentConstraint);
		
		
		alarmPropertyComponent = new JComboBox(alarms);
		GridBagConstraints alarmPropertyComponentConstraint = new GridBagConstraints();
		alarmPropertyComponentConstraint.gridx=1;
		alarmPropertyComponentConstraint.gridy=5;
		alarmPropertyComponentConstraint.fill= GridBagConstraints.HORIZONTAL;
		alarmPropertyComponentConstraint.gridwidth=2;
		alarmPropertyComponentConstraint.anchor = GridBagConstraints.NORTH;
		add(alarmPropertyComponent,alarmPropertyComponentConstraint);
		
	
	
	    String headers[] = { "Deltakere", "Status" };
	    String rows[][] = { { "ola", "1" }, { "per", "2" }};
	    JTable table = new JTable(rows,headers);
		participantsPane = new JScrollPane(table);
		participantsPane.setPreferredSize(new Dimension(250, 0));
		GridBagConstraints participantsPaneConstraint = new GridBagConstraints();
		participantsPaneConstraint.gridx=3;
		participantsPaneConstraint.gridy=0;
		participantsPaneConstraint.fill=GridBagConstraints.VERTICAL;
		participantsPaneConstraint.gridwidth=2;
		participantsPaneConstraint.gridheight=4;

		/*
		participantsPaneConstraint.gridwidth=GridBagConstraints.REMAINDER;
		participantsPaneConstraint.gridheight=GridBagConstraints.REMAINDER;
		participantsPaneConstraint.fill=GridBagConstraints.HORIZONTAL;
		participantsPaneConstraint.anchor=GridBagConstraints.NORTHWEST;
		*/
		add(participantsPane,participantsPaneConstraint);
		
		saveButton = new JButton("Lagre");
		saveButton.addActionListener((new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jf.setVisible(false);
			}
		}));
		
		GridBagConstraints saveButtonConstraints = new GridBagConstraints();
		saveButtonConstraints.gridx=3;
		saveButtonConstraints.gridy=9;
		add(saveButton,saveButtonConstraints);
		
		addButton = new JButton("Legg til/fjern");
		GridBagConstraints addButtonConstraints = new GridBagConstraints();
		addButtonConstraints.gridx=3;
		addButtonConstraints.gridy=4;
		addButtonConstraints.fill=GridBagConstraints.HORIZONTAL;
		addButtonConstraints.gridwidth=3;
		//addButtonConstraints.anchor=GridBagConstraints.WEST;
		//addButtonConstraints.fill= GridBagConstraints.VERTICAL;
		//addButtonConstraints.gridwidth=1;
		add(addButton,addButtonConstraints);
		
		
		
		shallButton = new JButton("Skal");
		GridBagConstraints shallButtonConstraints = new GridBagConstraints();
		shallButtonConstraints.gridx = 3;
		shallButtonConstraints.gridy=5;
		shallButtonConstraints.weightx=0.5;
		shallButtonConstraints.fill=GridBagConstraints.HORIZONTAL;
		
		add(shallButton,shallButtonConstraints);
		
		shallNotButton = new JButton("Skal ikke");
		GridBagConstraints shallNotButtonConstraints = new GridBagConstraints();
		shallNotButtonConstraints.gridx=4;
		shallNotButtonConstraints.gridy= 5;
		shallNotButtonConstraints.weightx=0.5;
		shallNotButtonConstraints.fill=GridBagConstraints.HORIZONTAL;
		add(shallNotButton,shallNotButtonConstraints);
		
		GridBagConstraints emailLabelConstraint = new GridBagConstraints();
		emailLabelConstraint.gridx=0;
		emailLabelConstraint.gridy=8;
		emailLabelConstraint.fill=GridBagConstraints.HORIZONTAL;
		emailLabelConstraint.gridwidth=2;
		add(emailLabel,emailLabelConstraint);
		
		GridBagConstraints emailFieldConstraint = new GridBagConstraints();
		emailFieldConstraint.gridx=2;
		emailFieldConstraint.gridy=8;
		emailFieldConstraint.fill=GridBagConstraints.HORIZONTAL;
		emailFieldConstraint.gridwidth=2;
		add(emailField,emailFieldConstraint);
		
		GridBagConstraints addExternalConstraint = new GridBagConstraints();
		addExternalConstraint.gridx=4;
		addExternalConstraint.gridy=8;
		addExternalConstraint.fill=GridBagConstraints.HORIZONTAL;
		addExternalConstraint.gridwidth=3;
		//addExternalConstraint.fill=GridBagConstraints.HORIZONTAL;
		//addExternalConstraint.anchor=GridBagConstraints.SOUTH;
		add(addExternal,addExternalConstraint);
		
		
		
		
	}
	
	

	class dateChooserListener implements PropertyChangeListener  {
		public void propertyChangeListener(PropertyChangeEvent ae){
			  /* if ("date".equals(ae.getPropertyName())) {
	                System.out.print 
	              ln(ae.getPropertyName()
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
		frame.getContentPane().add(new AppointmentPanel(frame));
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}
	

}
