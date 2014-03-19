package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import models.Appointment;
import models.Person;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import db.DBConnection;

public class AppointmentPanel extends JDialog {

	private JTextField nameField, locationField, emailField;
	private JLabel nameLabel, locationLabel, dateLabel, emailLabel;
	private JCalendar calender;
	private JLabel startTimeLabel, endTimeLabel, roomLabel, alarmLabel;
	private JButton saveButton, deleteButton, addButton, shallButton, shallNotButton, addExternal;
	private JComboBox starTimeHourPropertyComponent, starTimeMinutesPropertyComponent, endTimeHourPropertyComponent,endTimeMinutePropertyComponent,roomPropertyComponent, alarmPropertyComponent;
	private JScrollPane participantsPane;
	private final String[] hourStrings = { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16" , "17", "18", "19", "20", "21", "22", "23"}; 
	private final String[] minuteStrings = { "00","15","30","45"};
	private final String[] alarms = { "På","Av"};
	private JDateChooser dateChooser;
	private Appointment app;
	private HashMap<String, String> oldRows;
	private ArrayList<String> currentRows;
	private Person currentUser, host;
	private DefaultTableModel  tableModel;
	private String[] tableHeaders = { "Deltakere", "Status" };
	private GridBagConstraints nameLabelConstraint, nameFieldConstraint, locationLabelConstraint, locationFieldConstraint, dateLabelConstraint, dateChooserConstraint, startTimeLabelConstraint,
	endTimeLabelConstraint, roomLabelConstraint, alarmLabelConstraint, starTimePropertyComponentConstraint,
	starTimeMinutesPropertyComponentConstraint, endTimeHourPropertyComponentConstraint, endTimeMinutePropertyComponentConstraint,
	roomPropertyComponentConstraint, alarmPropertyComponentConstraint, participantsPaneConstraint, saveButtonConstraints, deleteButtonConstraints,
	addButtonConstraints, shallButtonConstraints, shallNotButtonConstraints, emailLabelConstraint, emailFieldConstraint, addExternalConstraint;
	private boolean isEdited = false;
	private JTable table;


	public AppointmentPanel(final MainFrame jf, final Person user){
		super(jf, "Avtale", true);

		currentUser = user;
		host = user;
		oldRows = new HashMap<String, String>();
		
		makeGui(jf);
		this.deleteButton.setEnabled(false);
		dateChooser.setDate(new Date());
		setVisible(true);
	}

	public AppointmentPanel(final MainFrame jf, Appointment app, Person user){
		super(jf, "Avtale", true);

		currentUser = user;
		this.app = app;

		getInitialParticipants();
		makeGui(jf);
		getAppointmentInfo();

		if (!currentUser.equals(host)) {

			this.nameField.setEditable(false);
			this.locationField.setEditable(false);
			this.dateChooser.setEnabled(false);
			//this.dateChooser.getDateEditor().se
			this.starTimeHourPropertyComponent.setEnabled(false);
			this.starTimeMinutesPropertyComponent.setEnabled(false);
			this.endTimeHourPropertyComponent.setEnabled(false);
			this.endTimeMinutePropertyComponent.setEnabled(false);
			this.roomPropertyComponent.setEnabled(false);
			this.addExternal.setEnabled(false);
			this.addButton.setEnabled(false);
			this.calender.setEnabled(false);
			this.emailField.setEnabled(false);
			this.table.setEnabled(false);
			this.deleteButton.setEnabled(false);
		}
		setVisible(true);
	}

	public void updateParticipantRows(HashMap<String, String> participants) {
		String[][] s = new String[participants.size() + 1][2];
		String[] initialTable = { host.getUsername(), "Host" };
		s[0] = initialTable;
		currentRows = new ArrayList<String>();

		int i = 0;
		for (String e : participants.keySet()) {
			s[i+1][0] = e;
			s[i+1][1] = participants.get(e);
			currentRows.add(e);
			i++;
		}
		if (app != null && !(currentRows.contains(currentUser.getUsername()) || host.equals(currentUser))) {
			this.alarmPropertyComponent.setEnabled(false);
			this.shallButton.setEnabled(false);
			this.shallNotButton.setEnabled(false);
			this.saveButton.setEnabled(false);
		}

		tableModel = new DefaultTableModel(s, tableHeaders) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setModel(tableModel);
	}

	public void makeGui(MainFrame jf) {
		setSize(610, 400);
		setResizable(false);
		setLayout(new GridBagLayout());
		nameField= new JTextField();
		locationField= new JTextField();
		nameLabel= new JLabel("Navn:");
		locationLabel= new JLabel("Sted:");
		dateLabel= new JLabel("Dato:");
		calender = new JCalendar();
		startTimeLabel= new JLabel("Starttid:");
		endTimeLabel= new JLabel("Sluttid:");
		roomLabel= new JLabel("Møterom:");
		alarmLabel = new JLabel("Alarm:");
		saveButton = new JButton("Lagre");
		deleteButton = new JButton("Slett");
		addButton= new JButton("Legg til/fjern");
		shallButton= new JButton("Skal");
		shallNotButton = new JButton("Skal ikke");
		emailLabel = new JLabel("Epost til ekstern deltager:");
		addExternal= new JButton("Legg til");
		emailField = new JTextField();
		table = new JTable();

		dateChooser = new JDateChooser();
		calender.add(dateChooser);

		nameLabelConstraint = new GridBagConstraints();
		nameLabelConstraint.gridx = 0;
		nameLabelConstraint.gridy = 0;
		nameLabelConstraint.fill=GridBagConstraints.HORIZONTAL;
		nameLabelConstraint.insets = new Insets(5, 5, 5, 5);
		add(nameLabel, nameLabelConstraint);

		nameFieldConstraint = new GridBagConstraints();
		nameFieldConstraint.gridx = 1;
		nameFieldConstraint.gridy = 0;
		nameFieldConstraint.fill = GridBagConstraints.HORIZONTAL;
		nameFieldConstraint.gridwidth=2;
		nameFieldConstraint.insets = new Insets(5, 5, 5, 5);
		add(nameField, nameFieldConstraint);
		
		locationLabelConstraint = new GridBagConstraints();
		locationLabelConstraint.gridx = 0;
		locationLabelConstraint.gridy = 1;
		locationLabelConstraint.fill=GridBagConstraints.HORIZONTAL;
		locationLabelConstraint.insets = new Insets(5, 5, 5, 5);
		add(locationLabel, locationLabelConstraint);
		
		locationFieldConstraint = new GridBagConstraints();
		locationFieldConstraint.gridx = 1;
		locationFieldConstraint.gridy = 1;
		locationFieldConstraint.fill = GridBagConstraints.HORIZONTAL;
		locationFieldConstraint.gridwidth=2;
		locationFieldConstraint.insets = new Insets(5, 5, 5, 5);
		add(locationField, locationFieldConstraint);

		dateLabelConstraint = new GridBagConstraints();
		dateLabelConstraint.gridx = 0;
		dateLabelConstraint.gridy = 2;
		dateLabelConstraint.fill= GridBagConstraints.HORIZONTAL;
		dateLabelConstraint.insets = new Insets(5, 5, 5, 5);
		add(dateLabel, dateLabelConstraint);


		dateChooserConstraint = new GridBagConstraints();
		dateChooserConstraint.gridx=1;
		dateChooserConstraint.gridy=2;
		dateChooserConstraint.fill =GridBagConstraints.HORIZONTAL;
		dateChooserConstraint.gridwidth=2;
		dateChooserConstraint.insets = new Insets(5, 5, 5, 5);
		add(dateChooser,dateChooserConstraint);


		startTimeLabelConstraint = new GridBagConstraints();
		startTimeLabelConstraint.gridx=0;
		startTimeLabelConstraint.gridy=3;
		startTimeLabelConstraint.fill=GridBagConstraints.HORIZONTAL;
		startTimeLabelConstraint.insets = new Insets(5, 5, 5, 5);
		add(startTimeLabel, startTimeLabelConstraint);

		endTimeLabelConstraint = new GridBagConstraints();
		endTimeLabelConstraint.gridx=0;
		endTimeLabelConstraint.gridy=4;
		endTimeLabelConstraint.fill=GridBagConstraints.HORIZONTAL;
		endTimeLabelConstraint.insets = new Insets(5, 5, 5, 5);
		add(endTimeLabel,endTimeLabelConstraint);

		roomLabelConstraint= new GridBagConstraints();
		roomLabelConstraint.gridx=0;
		roomLabelConstraint.gridy=5;
		roomLabelConstraint.fill=GridBagConstraints.HORIZONTAL;
		roomLabelConstraint.anchor=GridBagConstraints.NORTH;
		roomLabelConstraint.insets = new Insets(5, 5, 5, 5);
		add(roomLabel,roomLabelConstraint);

		alarmLabelConstraint = new GridBagConstraints();
		alarmLabelConstraint.gridx=0;
		alarmLabelConstraint.gridy=6;
		alarmLabelConstraint.fill=GridBagConstraints.HORIZONTAL;
		alarmLabelConstraint.anchor = GridBagConstraints.NORTH;
		alarmLabelConstraint.insets = new Insets(5, 5, 5, 5);
		add(alarmLabel,alarmLabelConstraint);


		starTimeHourPropertyComponent= new JComboBox(hourStrings);
		starTimePropertyComponentConstraint= new GridBagConstraints();
		starTimePropertyComponentConstraint.gridx=1;
		starTimePropertyComponentConstraint.weightx=0.5;
		starTimePropertyComponentConstraint.fill=GridBagConstraints.HORIZONTAL;
		starTimePropertyComponentConstraint.gridy=3;
		starTimePropertyComponentConstraint.insets = new Insets(5, 5, 5, 5);
		add(starTimeHourPropertyComponent,starTimePropertyComponentConstraint);
		
		starTimeHourPropertyComponent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parseDateAndCheckRoom();
			}
		});


		starTimeMinutesPropertyComponent = new JComboBox(minuteStrings);
		starTimeMinutesPropertyComponentConstraint = new GridBagConstraints();
		starTimeMinutesPropertyComponentConstraint.fill=GridBagConstraints.HORIZONTAL;
		starTimeMinutesPropertyComponentConstraint.gridwidth=1;
		starTimeMinutesPropertyComponentConstraint.weightx=0.5;
		starTimeMinutesPropertyComponentConstraint.gridx=2;
		starTimeMinutesPropertyComponentConstraint.gridy=3;
		starTimeMinutesPropertyComponentConstraint.insets = new Insets(5, 5, 5, 5);
		add(starTimeMinutesPropertyComponent,starTimeMinutesPropertyComponentConstraint);

		starTimeMinutesPropertyComponent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parseDateAndCheckRoom();
			}
		});
		
		endTimeHourPropertyComponent= new JComboBox(hourStrings);
		endTimeHourPropertyComponentConstraint = new GridBagConstraints();
		endTimeHourPropertyComponentConstraint.gridx=1;
		endTimeHourPropertyComponentConstraint.fill= GridBagConstraints.HORIZONTAL;
		endTimeHourPropertyComponentConstraint.weightx=1;
		endTimeHourPropertyComponentConstraint.gridy=4;
		endTimeHourPropertyComponentConstraint.gridwidth=1;
		endTimeHourPropertyComponentConstraint.insets = new Insets(5, 5, 5, 5);
		add(endTimeHourPropertyComponent,endTimeHourPropertyComponentConstraint);

		endTimeHourPropertyComponent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parseDateAndCheckRoom();
			}
		});
		
		endTimeMinutePropertyComponent = new JComboBox(minuteStrings);
		endTimeMinutePropertyComponentConstraint = new GridBagConstraints();
		endTimeMinutePropertyComponentConstraint.gridx=2;
		endTimeMinutePropertyComponentConstraint.fill=GridBagConstraints.HORIZONTAL;
		endTimeMinutePropertyComponentConstraint.gridy=4;
		endTimeMinutePropertyComponentConstraint.weightx=1;
		endTimeMinutePropertyComponentConstraint.insets = new Insets(5, 5, 5, 5);
		add(endTimeMinutePropertyComponent,endTimeMinutePropertyComponentConstraint);

		endTimeMinutePropertyComponent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parseDateAndCheckRoom();
			}
		});
		
		roomPropertyComponent = new JComboBox(getInitialRooms());
		roomPropertyComponentConstraint = new GridBagConstraints();
		roomPropertyComponentConstraint.gridx=1;
		roomPropertyComponentConstraint.gridy=5;
		roomPropertyComponentConstraint.fill=GridBagConstraints.HORIZONTAL;
		roomPropertyComponentConstraint.gridwidth=2;
		roomPropertyComponent.setFocusable(false);
		roomPropertyComponentConstraint.insets = new Insets(5, 5, 5, 5);
		add(roomPropertyComponent, roomPropertyComponentConstraint);
		
		roomPropertyComponent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parseDateAndCheckRoom();
			}
		});

		alarmPropertyComponent = new JComboBox(alarms);
		alarmPropertyComponentConstraint = new GridBagConstraints();
		alarmPropertyComponentConstraint.gridx=1;
		alarmPropertyComponentConstraint.gridy=6;
		alarmPropertyComponentConstraint.fill= GridBagConstraints.HORIZONTAL;
		alarmPropertyComponentConstraint.gridwidth=2;
		alarmPropertyComponentConstraint.anchor = GridBagConstraints.NORTH;
		alarmPropertyComponentConstraint.insets = new Insets(5, 5, 5, 5);
		add(alarmPropertyComponent,alarmPropertyComponentConstraint);


		tableModel = new DefaultTableModel(new String[0][2], tableHeaders) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setModel(tableModel);
		table.setFocusable(false);
		table.setSelectionModel(new DefaultListSelectionModel() {
			@Override
			public boolean isSelectedIndex(int index) {
				return index != 0 && super.isSelectedIndex(index);
			}
		});
		table.setColumnModel(new DefaultTableColumnModel() {
			@Override
			public void moveColumn(int columnIndex, int newIndex) {
				;
			}
		});
		participantsPane = new JScrollPane(table);
		participantsPane.setPreferredSize(new Dimension(300, 0));
		participantsPaneConstraint = new GridBagConstraints();
		participantsPaneConstraint.gridx=3;
		participantsPaneConstraint.gridy=0;
		participantsPaneConstraint.fill=GridBagConstraints.VERTICAL;
		participantsPaneConstraint.gridwidth=2;
		participantsPaneConstraint.gridheight=5;


		/*
		participantsPaneConstraint.gridwidth=GridBagConstraints.REMAINDER;
		participantsPaneConstraint.gridheight=GridBagConstraints.REMAINDER;
		participantsPaneConstraint.fill=GridBagConstraints.HORIZONTAL;
		participantsPaneConstraint.anchor=GridBagConstraints.NORTHWEST;
		 */
		participantsPaneConstraint.insets = new Insets(5, 0, 5, 5);
		add(participantsPane,participantsPaneConstraint);

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String roomAvail = parseDateAndCheckRoom();
				if (roomAvail != null) {
					JOptionPane.showMessageDialog(null, roomAvail, "Opptatt", 0);
				}
				else if (isEndTimeAfterStartTime()) {
					DBConnection con = new DBConnection("src/db/props.properties", true);
					if (app != null) {
						deleteParticipantsNotOnAttending(con);
						updateAppointment(con);
					}
					else
						createAppointment(con);
					saveParticipantsOnAttending(con);
					updateParticipantStatus(con);
					if (isEdited)
						setEdited(con);
					con.close();
					dispose();
				}
				else {
					JOptionPane.showMessageDialog(null, "Sluttid kan ikke være før starttid!", "Feil", 2);
				}
			}

			private void setEdited(DBConnection con2) {
				con2.smallUPDATEorINSERT("UPDATE employeeappointmentalarm SET Edited=1 WHERE AppointmentNumber=" + app.getId() + " AND Username <> '" + currentUser.getUsername() + "'");
			}

			private void updateAppointment(DBConnection con2) {
				String roomTemp = (String) roomPropertyComponent.getSelectedItem();
				String[] roomStripped = roomTemp.split("\\s+");
				if (roomStripped[0].equals("")) {
					roomStripped[0] = "NULL";
				}
				con2.smallUPDATEorINSERT("UPDATE appointment SET AppointmentName = '" + nameField.getText() + "', " +
						"StartTime='" + new SimpleDateFormat("yyyy-MM-dd").format(dateChooser.getDate()).toString() + " " +
						(String) starTimeHourPropertyComponent.getSelectedItem() + ":" + (String) starTimeMinutesPropertyComponent.getSelectedItem() + ":00', " +
						"EndTime='"+ new SimpleDateFormat("yyyy-MM-dd").format(dateChooser.getDate()).toString() + " " +
						(String) endTimeHourPropertyComponent.getSelectedItem() + ":" + (String) endTimeMinutePropertyComponent.getSelectedItem() + ":00', " +
						"RoomNumber=" + roomStripped[0] + ", Location='" + locationField.getText() + "' WHERE AppointmentNumber = " + app.getId());
			}

			private void createAppointment(DBConnection con2) {
				String roomTemp = (String) roomPropertyComponent.getSelectedItem();
				String[] roomStripped = roomTemp.split("\\s+");
				if (roomStripped[0].equals("")) {
					roomStripped[0] = "NULL";
				}
				con2.smallUPDATEorINSERT("INSERT INTO appointment(AppointmentName, StartTime, EndTime, RoomNumber, Location) VALUES('" + 
						nameField.getText() + "', '" + new SimpleDateFormat("yyyy-MM-dd").format(dateChooser.getDate()).toString() + " " +
						(String) starTimeHourPropertyComponent.getSelectedItem() + ":" + (String) starTimeMinutesPropertyComponent.getSelectedItem() + ":00', '" +
						new SimpleDateFormat("yyyy-MM-dd").format(dateChooser.getDate()).toString() + " " + (String) endTimeHourPropertyComponent.getSelectedItem() + ":" +
						(String) endTimeMinutePropertyComponent.getSelectedItem() + ":00', " + roomStripped[0] + ", '"+ locationField.getText() +"')");
				ResultSet rs = con2.smallSELECT("SELECT LAST_INSERT_ID() FROM appointment");
				try {
					rs.next();
					makeAppointment(rs.getString(1));
				} catch (SQLException e) {
					try { rs.close(); } catch (SQLException e1) { e1.printStackTrace(); }
					throw new RuntimeException("LOL");
				}
				con2.smallUPDATEorINSERT("INSERT INTO employeeappointmentalarm(Username, AppointmentNumber,Status)" +
						"VALUES ('"+ currentUser.getUsername() +"', " + app.getId() + ", 'host')");
			}

			private void deleteParticipantsNotOnAttending(DBConnection con2) {
				PreparedStatement prs;
				try {
					prs = con2.prepareStatement("DELETE FROM employeeappointmentalarm WHERE Username = ? AND AppointmentNumber = ?");
					for (String username : oldRows.keySet()) {
						if (!currentRows.contains(username)) { 	// If a person in oldRows isn't in currentRows
							prs.setString(1, username);	// it has been unattended
							prs.setInt(2, app.getId());
							prs.executeUpdate();
						}
					}
				} catch (SQLException e) {
					con2.close();
					e.printStackTrace();
					throw new RuntimeException();
				} 

			}

			private void saveParticipantsOnAttending(DBConnection con2) {
				ArrayList<String> saveList = new ArrayList<String>(currentRows);
				saveList.removeAll(oldRows.keySet());	// Do not insert the ones already in the database
				PreparedStatement prs;
				try {
					if (app != null) {
						prs = con2.prepareStatement("INSERT INTO employeeappointmentalarm(Username, AppointmentNumber,Status)" +
								"VALUES (?,?,?)");
						for (int i = 1; i < tableModel.getRowCount(); i++) {
							if (saveList.contains(tableModel.getValueAt(i, 0))) {
								prs.setString(1, (String) tableModel.getValueAt(i, 0));
								prs.setInt(2, app.getId());
								switch ((String) tableModel.getValueAt(i, 1)) {
									case "Deltar":
										prs.setString(3, Appointment.CONFIRMED);
										break;
									case "Deltar ikke":
										prs.setString(3, Appointment.DECLINED);
										break;
									case "Avventer svar":
										prs.setString(3, Appointment.NOT_RESPONDED);
										break;
								}
								prs.executeUpdate();
							}
						}
					}
					else {
						throw new RuntimeException("FUUUUUUUUCK!");
					}

				} catch (SQLException e) {
					con2.close();
					e.printStackTrace();
					throw new RuntimeException();
				}
			}

			private void updateParticipantStatus(DBConnection con2) {
				ArrayList<String> updateList = new ArrayList<String>(currentRows);
				updateList.retainAll(oldRows.keySet());
				for (int i = 1; i < tableModel.getRowCount(); i++) {
					String username = (String) tableModel.getValueAt(i, 0);
					String status = null;
					switch ((String) tableModel.getValueAt(i, 1)) {
						case "Deltar":
							status = Appointment.CONFIRMED;
							break;
						case "Deltar ikke":
							status = Appointment.DECLINED;
							break;
						case "Avventer svar":
							status = Appointment.NOT_RESPONDED;
							break;
					}
					if (updateList.contains(username) && !oldRows.get(username).equals(status) && !tableModel.getValueAt(i, 1).equals(oldRows.get(username))) {
						con2.smallUPDATEorINSERT("UPDATE employeeappointmentalarm SET Status = '" + status +
								"' WHERE (Username = '" + username + "' AND AppointmentNumber = " + app.getId() + ")");
					}
				}

			}});

		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (app != null) {
					int confirm = JOptionPane.showOptionDialog(null, "Er du sikker?", "Bekreftelse", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
					if (confirm == 0) { // If user is sure
						DBConnection con = new DBConnection("src/db/props.properties", true);
						con.smallUPDATEorINSERT("DELETE FROM appointment WHERE AppointmentNumber = " + app.getId());
						con.close();
						dispose();
					}
				}
			}
		});

		saveButtonConstraints = new GridBagConstraints();
		saveButtonConstraints.gridx=2;
		saveButtonConstraints.gridy=9;
		saveButtonConstraints.insets = new Insets(5, 5, 5, 5);
		add(saveButton,saveButtonConstraints);
		
		deleteButtonConstraints = new GridBagConstraints();
		deleteButtonConstraints.gridx = 3;
		deleteButtonConstraints.gridy = 9;
		deleteButtonConstraints.insets = new Insets(5, 5, 5, 5);
		add(deleteButton, deleteButtonConstraints);

		addButton = new JButton("Legg til/fjern");
		addButtonConstraints = new GridBagConstraints();
		addButtonConstraints.gridx=3;
		addButtonConstraints.gridy=5;
		addButtonConstraints.fill=GridBagConstraints.HORIZONTAL;
		addButtonConstraints.insets = new Insets(5, 5, 5, 5);
		addButtonConstraints.gridwidth=3;
		//addButtonConstraints.anchor=GridBagConstraints.WEST;
		//addButtonConstraints.fill= GridBagConstraints.VERTICAL;
		//addButtonConstraints.gridwidth=1;
		add(addButton,addButtonConstraints);

		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				HashMap<String, String> temp = new HashMap<String, String>();
				for (int i = 1; i < tableModel.getRowCount(); i++) {
					temp.put((String) tableModel.getValueAt(i, 0), (String) tableModel.getValueAt(i, 1));
				}
				new Participants(AppointmentPanel.this, currentUser , temp);
			}
		});

		addExternal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Email email = new SimpleEmail();
					email.setHostName("smtp.googlemail.com");
					email.setSmtpPort(465);
					email.setAuthenticator(new DefaultAuthenticator("ikkesvar.fellesprosjektet", "Fellesprosjekt26"));
					email.setSSLOnConnect(true);
					email.setFrom("ikkesvar.fellesprosjektet@gmail.com");
					email.setSubject("Du har blitt lagt til i en avtale");
					email.setMsg("Heisann!\n\n"
							+ "Du har blitt lagt til som deltager i en avtale "
							+ "opprettet av bruker " + currentUser.getUsername());
					email.addTo(emailField.getText());
					email.send();
					isEdited = true;
				}
				catch (EmailException ee) {
					ee.printStackTrace();
				}
			}
		});

		shallButton = new JButton("Skal");
		shallButtonConstraints = new GridBagConstraints();
		shallButtonConstraints.gridx = 3;
		shallButtonConstraints.gridy=6;
		shallButtonConstraints.weightx=0.5;
		shallButtonConstraints.fill=GridBagConstraints.HORIZONTAL;
		shallButtonConstraints.insets = new Insets(5, 5, 5, 5);

		shallButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentUser.equals(host)) {
					ListSelectionModel sel = table.getSelectionModel();
					for (int i = 1; i < tableModel.getRowCount(); i++) {
						if (sel.isSelectedIndex(i)) {
							tableModel.setValueAt("Deltar", i, 1);
						}
					}
				}
				else {
					for (int i = 1; i < tableModel.getRowCount(); i++) {
						if (tableModel.getValueAt(i, 0).equals(currentUser.getUsername())) {
							tableModel.setValueAt("Deltar", i, 1);
						}
					}
				}
				isEdited = true;
			}
		});

		add(shallButton,shallButtonConstraints);

		shallNotButton = new JButton("Skal ikke");
		shallNotButtonConstraints = new GridBagConstraints();
		shallNotButtonConstraints.gridx=4;
		shallNotButtonConstraints.gridy=6;
		shallNotButtonConstraints.weightx=0.5;
		shallNotButtonConstraints.fill=GridBagConstraints.HORIZONTAL;
		shallNotButtonConstraints.insets = new Insets(5, 5, 5, 5);
		shallNotButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentUser.equals(host)) {
					ListSelectionModel sel = table.getSelectionModel();
					for (int i = 1; i < tableModel.getRowCount(); i++) {
						if (sel.isSelectedIndex(i)) {
							tableModel.setValueAt("Deltar ikke", i, 1);
						}
					}
				}
				else {
					for (int i = 1; i < tableModel.getRowCount(); i++) {
						if (tableModel.getValueAt(i, 0).equals(currentUser.getUsername())) {
							tableModel.setValueAt("Deltar ikke", i, 1);
						}
					}
				}
				isEdited = true;
			}
		});
		add(shallNotButton,shallNotButtonConstraints);

		emailLabelConstraint = new GridBagConstraints();
		emailLabelConstraint.gridx=0;
		emailLabelConstraint.gridy=8;
		emailLabelConstraint.fill=GridBagConstraints.HORIZONTAL;
		emailLabelConstraint.gridwidth=2;
		emailLabelConstraint.insets = new Insets(5, 5, 5, 5);
		add(emailLabel,emailLabelConstraint);

		emailFieldConstraint = new GridBagConstraints();
		emailFieldConstraint.gridx=2;
		emailFieldConstraint.gridy=8;
		emailFieldConstraint.fill=GridBagConstraints.HORIZONTAL;
		emailFieldConstraint.gridwidth=2;
		emailFieldConstraint.insets = new Insets(5, 5, 5, 5);
		add(emailField,emailFieldConstraint);

		addExternalConstraint = new GridBagConstraints();
		addExternalConstraint.gridx=4;
		addExternalConstraint.gridy=8;
		addExternalConstraint.fill=GridBagConstraints.HORIZONTAL;
		addExternalConstraint.gridwidth=3;
		//addExternalConstraint.fill=GridBagConstraints.HORIZONTAL;
		//addExternalConstraint.anchor=GridBagConstraints.SOUTH;
		addExternalConstraint.insets = new Insets(5, 5, 5, 5);
		add(addExternal,addExternalConstraint);

		updateParticipantRows(oldRows);
		setLocationRelativeTo(jf);
	}

	public void getInitialParticipants() {
		oldRows = new HashMap<String, String>();

		// Push employees who's allready attending into oldRows
		DBConnection con = new DBConnection("src/db/props.properties", true);
		ResultSet rsAtLoad = null;
		try {
			con.smallUPDATEorINSERT("UPDATE employeeappointmentalarm SET Edited=0 WHERE AppointmentNumber=" + app.getId() + " AND Username <> '" + currentUser.getUsername() + "'");
			rsAtLoad = con.smallSELECT("SELECT Username, Status FROM employeeappointmentalarm WHERE AppointmentNumber = " + app.getId());
			while (rsAtLoad.next()) {
				if (rsAtLoad.getString("Status").equals("host")) {
					Person thisHost = new Person(rsAtLoad.getString("Username"));
					this.host = thisHost;
					app.setHost(thisHost);
				}
				else {
					switch (rsAtLoad.getString("Status")) {
						case Appointment.CONFIRMED:
							oldRows.put(rsAtLoad.getString("Username"), "Deltar");
							break;
						case Appointment.DECLINED:
							oldRows.put(rsAtLoad.getString("Username"), "Deltar ikke");
							break;
						case Appointment.NOT_RESPONDED:
							oldRows.put(rsAtLoad.getString("Username"), "Avventer svar");
							break;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rsAtLoad != null) {
					rsAtLoad.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			con.close();
		}
	}
	
	public void getAppointmentInfo() {
		DBConnection con = new DBConnection("src/db/props.properties", true);
		ResultSet rsAtLoad = null;
		try {
			rsAtLoad = con.smallSELECT("SELECT AppointmentName, StartTime, EndTime, RoomNumber, Location FROM appointment WHERE AppointmentNumber = " + app.getId());
			if (rsAtLoad.next()) {
				nameField.setText(rsAtLoad.getString("AppointmentName"));
				locationField.setText(rsAtLoad.getString("Location"));
				int year = Integer.parseInt(rsAtLoad.getString("StartTime").substring(0, 4))-1900;
				int month = Integer.parseInt(rsAtLoad.getString("StartTime").substring(5, 7))-1;
				int day = Integer.parseInt(rsAtLoad.getString("StartTime").substring(8, 10));
				dateChooser.setDate(new Date(year, month, day));
				starTimeHourPropertyComponent.setSelectedItem(rsAtLoad.getString("StartTime").substring(11, 13));
				starTimeMinutesPropertyComponent.setSelectedItem(rsAtLoad.getString("StartTime").substring(14, 16));
				endTimeHourPropertyComponent.setSelectedItem(rsAtLoad.getString("EndTime").substring(11, 13));
				endTimeMinutePropertyComponent.setSelectedItem(rsAtLoad.getString("EndTime").substring(14, 16));
				String room = rsAtLoad.getString("RoomNumber");
				for (int i = 0; i < roomPropertyComponent.getItemCount(); i++) {
					String roomTemp = (String) roomPropertyComponent.getItemAt(i);
					String[] splitted = roomTemp.split("\\s+");
					if (splitted[0].equals(room)) {
						roomPropertyComponent.setSelectedIndex(i);;
					}
				}
//				alarmPropertyComponent.setSelectedItem(rsAtLoad.getString("")); TODO
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rsAtLoad.close();				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			con.close();			
		}
	}
	
	public String[] getInitialRooms() {
		String[] rooms = null;
		DBConnection con = new DBConnection("src/db/props.properties", true);
		try {
			ResultSet rs = con.smallSELECT("SELECT count(RoomNumber) FROM meetingroom");
			rs.next();
			rooms = new String[rs.getInt(1) + 1];
			rooms[0] = "";
			rs = con.smallSELECT("SELECT RoomNumber, Size FROM meetingroom");
			int i = 1;
			while (rs.next()) {
				rooms[i] = rs.getString("RoomNumber") + " (Plass til " + rs.getString("Size") + " personer)";
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rooms;
	}
	
	public String isRoomAvailable(int appRoomNr, Date appStartTime, Date appEndTime) {
		if (appRoomNr == -1) {
			return null;
		}
		DBConnection con = new DBConnection("src/db/props.properties", true);
		ResultSet rs = null;
		try {
			if (app != null) {				
				rs = con.smallSELECT("SELECT AppointmentNumber, AppointmentName, StartTime, EndTime, RoomNumber FROM appointment WHERE RoomNumber = " + appRoomNr + " AND AppointmentNumber <> " + app.getId());
			} else {
				rs = con.smallSELECT("SELECT AppointmentNumber, AppointmentName, StartTime, EndTime, RoomNumber FROM appointment WHERE RoomNumber = " + appRoomNr);
			}
			
			if (! rs.next()) { // If no appointments were found
				return null;
			} else {
				rs.beforeFirst();
				while (rs.next()) {
					if ((appEndTime.after(rs.getTimestamp("StartTime")) && appEndTime.before(rs.getTimestamp("EndTime"))) ||
							(appStartTime.after(rs.getTimestamp("StartTime")) && appStartTime.before(rs.getTimestamp("EndTime"))) ||
							((appStartTime.before(rs.getTimestamp("StartTime")) || appStartTime.equals(rs.getTimestamp("StartTime"))) &&
									(appEndTime.after(rs.getTimestamp("EndTime")) || appEndTime.equals(rs.getTimestamp("EndTime"))))) {
						return "Dette rommet er ikke ledig på valgt tidspunkt.\n\r"
								+ "Det er opptatt av " + rs.getString("AppointmentName")
								+ " som varer fra " + rs.getTime("StartTime")
								+ " til " + rs.getTime("EndTime");
					}
				}
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();				
			} catch (SQLException se) {
				se.printStackTrace();
			}
			con.close();
		}
		return null;
	}
	
	public String parseDateAndCheckRoom() {
		String roomTemp = (String) roomPropertyComponent.getSelectedItem();
		Person hostTemp = null;
		try {
			hostTemp = app.getHost();
		} catch (NullPointerException npe) {
			hostTemp = null;
		}
		if (! roomTemp.equals("") && (hostTemp == null || (hostTemp != null && hostTemp.getUsername().equals(currentUser.getUsername())))) {
			String[] roomSplitted = roomTemp.split("\\s+");
			
			String startHour = starTimeHourPropertyComponent.getSelectedItem().toString();
			String startMin = starTimeMinutesPropertyComponent.getSelectedItem().toString();
			String endHour = endTimeHourPropertyComponent.getSelectedItem().toString();
			String endMin = endTimeMinutePropertyComponent.getSelectedItem().toString();
			
			String date = new SimpleDateFormat("yyyy-MM-dd").format(dateChooser.getDate());
			Date startTime = null;
			Date endTime = null;
			try {
				startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date + " " + startHour + ":" + startMin);
				endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date + " " + endHour + ":" + endMin);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			
			String roomAvailTemp = isRoomAvailable(Integer.parseInt(roomSplitted[0]), startTime, endTime);
			if (roomAvailTemp != null) {
				roomPropertyComponent.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
				return roomAvailTemp;
			}
			else {
				roomPropertyComponent.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Panel.background")));
				return null;
			}
		}
		else {
			return null;
		}
	}
	
	public boolean isEndTimeAfterStartTime() {
		String startHour = starTimeHourPropertyComponent.getSelectedItem().toString();
		String startMin = starTimeMinutesPropertyComponent.getSelectedItem().toString();
		String endHour = endTimeHourPropertyComponent.getSelectedItem().toString();
		String endMin = endTimeMinutePropertyComponent.getSelectedItem().toString();
		
		Date startTime = null;
		Date endTime = null;
		try {
			startTime = new SimpleDateFormat("HH:mm").parse(startHour + ":" + startMin);
			endTime = new SimpleDateFormat("HH:mm").parse(endHour + ":" + endMin);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return(startTime.before(endTime));
	}
	
	public void makeAppointment(String id) {
		app = new Appointment(Integer.parseInt(id));
		isEdited = true;
	}

}
