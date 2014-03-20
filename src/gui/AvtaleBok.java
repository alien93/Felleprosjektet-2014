package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import res.IconURL;

import db.DBConnection;
import db.ObjectFactory;
import models.Appointment;
import models.AvtaleBokModel;
import models.AvtaleListModel;
import models.ParticipantEntity;


public class AvtaleBok extends JPanel {
	
	private JButton prevWeek, nextWeek, newAppointment, addRemove;
	private AvtaleBokModel model;
	private final String[] days = {"Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Lørdag", "Søndag"};
	private GridBagConstraints constraints;
	private JLabel[] dateLabels = new JLabel[7];
	private AvtaleList[] appList = new AvtaleList[7];
	private JScrollPane[] scrollere = new JScrollPane[7];
	private JLabel ukeLabel;
	private JLabel[] fargeLabels = new JLabel[5];
	private ArrayList<ParticipantEntity> employees;
	
	public AvtaleBok(ParticipantEntity person, final MainFrame frame) {
		employees = new ArrayList<ParticipantEntity>();
		employees.add(person);
		setLayout(new GridBagLayout());
		model = new AvtaleBokModel();
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0, 0, 50, 0); // Padding
		

		prevWeek = new JButton("<<");
		prevWeek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setWeek(model.getWeek() - 1);
				updateAvtaleBok();
			}
		});

		nextWeek = new JButton(">>");
		nextWeek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setWeek(model.getWeek() + 1);
				updateAvtaleBok();
			}
		});
		

		newAppointment = new JButton("Ny avtale");
		newAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AppointmentPanel(frame, employees.get(0));
				updateAvtaleBok();
			}
		});
		
		addRemove = new JButton("Multivisning");
		addRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Participants(frame, AvtaleBok.this, employees);
				updateAvtaleBok();
			}
		});
		
		

		constraints.gridx = 2;
		constraints.gridy = 0;
		add(prevWeek, constraints);
		
		constraints.gridx = 3;
		constraints.gridy = 0;
		ukeLabel = new JLabel("Uke " + model.getWeek());
		add(ukeLabel, constraints);
		
		constraints.gridx = 4;
		constraints.gridy = 0;
		add(nextWeek, constraints);
		
		
		Date dates = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy w u");
		DBConnection connection = new DBConnection("src/db/props.properties", true);
		for (int i = 0; i < days.length; i++) {
			constraints.gridx = i;
			try {
				dates = df.parse(model.getYear() + " " + model.getWeek() + " " + (i % 8 + 1));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			dateLabels[i] = new JLabel(new SimpleDateFormat("dd.MM.yy").format(dates));
			String appDate = new SimpleDateFormat("yyyy-MM-dd").format(dates);
			appList[i] = new AvtaleList(appDate);
			AvtaleListModel model = ObjectFactory.getEmpsApps(employees, appDate, connection);
			model.sort();
			appList[i].setModel(model);
			//((AvtaleListModel<Appointment>) appList[i].getModel()).sort();

			constraints.insets = new Insets(0, 0, 0, 0); // Padding
			constraints.gridy = 2;
			JLabel weekDay = new JLabel(days[i]);
			add(weekDay, constraints);
			constraints.gridy = 3;

			constraints.insets = new Insets(0, 0, 10, 0); // Padding
			add(dateLabels[i], constraints);
			constraints.gridy = 4;
			scrollere[i] = new JScrollPane(appList[i]);
			scrollere[i].setPreferredSize(new Dimension(200, 600));
			scrollere[i].setMinimumSize(new Dimension(150, 300));
			
			add(scrollere[i], constraints);
		}
		updateAlarm(connection);
		connection.close();

		constraints.gridy = 5;
		
		constraints.gridx = 0;
		fargeLabels[0] = new JLabel(" - Deltar");
		fargeLabels[0].setIcon(new ImageIcon(IconURL.CONF_URL));
		fargeLabels[0].setSize(new Dimension(180, 25));
		add(fargeLabels[0], constraints);
		
		constraints.gridx = 1;
		fargeLabels[1] = new JLabel(" - Deltar ikke");
		fargeLabels[1].setIcon(new ImageIcon(IconURL.DECL_URL));
		add(fargeLabels[1], constraints);
		
		constraints.gridx = 2;
		fargeLabels[2] = new JLabel(" - Vert");
		fargeLabels[2].setIcon(new ImageIcon(IconURL.HOST_URL));
		add(fargeLabels[2], constraints);
		
		constraints.gridx = 3;
		fargeLabels[3] = new JLabel(" - Avventer svar");
		fargeLabels[3].setIcon(new ImageIcon(IconURL.NO_RE_URL));
		add(fargeLabels[3], constraints);
		
		constraints.gridx = 4;
		fargeLabels[4] = new JLabel(" - Gjest");
		fargeLabels[4].setIcon(new ImageIcon(IconURL.GJEST_URL));
		add(fargeLabels[4], constraints);
		
		
		
		constraints.gridx = 5;
		add(newAppointment, constraints);
		
		constraints.gridx = 6;
		add(addRemove, constraints);
		
		//updateAvtaleBok();
		
		for(final AvtaleList list : appList){
			list.addMouseListener(new MouseListener(){

				@Override
				public void mouseClicked(MouseEvent e) {
					if (!list.getSelectionModel().isSelectionEmpty()) {
						new AppointmentPanel(frame, (Appointment) list.getSelectedValue(), employees.get(0));
						updateAvtaleBok();
					}
				}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
			
			});
		}
		
	}
	
	public void addEmployees(ArrayList<ParticipantEntity> elist) {
		for (ParticipantEntity p : elist) {
			if (! employees.contains(p)) {
				employees.add(p);
			}
		}
	}
	
	public void removeEmployees(ArrayList<ParticipantEntity> elist) {
		employees.removeAll(elist);
	}
	
	public void updateAlarm(DBConnection con) {
		ArrayList<String[]> alarmAppointments = ObjectFactory.getAlarmAppointments(employees.get(0).getUsername(), con);
		for (String[] alarmInfo : alarmAppointments){
			JOptionPane.showMessageDialog(null, alarmInfo[1] + " starter " + alarmInfo[0].substring(0, 5), "Alarm for " + alarmInfo[1], JOptionPane.INFORMATION_MESSAGE);
		}

	}
	
	public void updateAvtaleBok() {
		Date dates = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy w u");
		DBConnection connection = new DBConnection("src/db/props.properties", true);
		for (int i = 0; i < days.length; i++) {
			constraints.gridx = i;
			try {
				dates = df.parse(model.getYear() + " " + model.getWeek() + " " + (i % 8 + 1));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			dateLabels[i].setText((new SimpleDateFormat("dd.MM.yy").format(dates)));
			String appDate = new SimpleDateFormat("yyyy-MM-dd").format(dates);
			appList[i].setDate(appDate);
			AvtaleListModel model = ObjectFactory.getEmpsApps(employees, appDate, connection);
			model.sort();
			appList[i].setModel(model);
			//((AvtaleListModel<Appointment>) appList[i].getModel()).sort();
		}
		ukeLabel.setText("Uke " + model.getWeek());
		updateAlarm(connection);
		connection.close();
	}
}
