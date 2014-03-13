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
import javax.swing.JDialog;
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

public class AppointmentPanel extends JDialog {
	
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
	private JScrollPane participantsPane;
	private final String[] hourStrings = { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16" , "17", "18", "19", "20", "21", "22", "23"}; 
	private final String[] minuteStrings = { "00","15","30","45"};
	private final String[] alarms = { "På","Av"};
	
	public AppointmentPanel(final JFrame jf){
		super(jf, "Avtale", true);
		
		setSize(600, 400);
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
		
		
		JDateChooser dateChooser = new JDateChooser();
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
		participantsPaneConstraint.gridheight=6;

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
				dispose();
			}
		}));
		
		GridBagConstraints saveButtonConstraints = new GridBagConstraints();
		saveButtonConstraints.gridx=3;
		saveButtonConstraints.gridy=7;
		add(saveButton,saveButtonConstraints);
		
		addButton = new JButton("Legg til/fjern");
		GridBagConstraints addButtonConstraints = new GridBagConstraints();
		addButtonConstraints.gridx=4;
		addButtonConstraints.gridy=6;
		add(addButton,addButtonConstraints);
		
		
		
		shallButton = new JButton("Skal");
		GridBagConstraints shallButtonConstraints = new GridBagConstraints();
		shallButtonConstraints.gridx = 3;
		shallButtonConstraints.gridy=6;
		add(shallButton,shallButtonConstraints);
		
		shallNotButton = new JButton("Skal ikke");
		GridBagConstraints shallNotButtonConstraints = new GridBagConstraints();
		shallNotButtonConstraints.gridx=4;
		shallNotButtonConstraints.gridy= 7;
		add(shallNotButton,shallNotButtonConstraints);
		
		GridBagConstraints emailLabelConstraint = new GridBagConstraints();
		//emailLabelConstraint.gridx=
		
		setVisible(true);
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
