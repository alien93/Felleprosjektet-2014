package gui;

import java.awt.Color;
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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import db.DBConnection;
import db.ObjectFactory;

import models.Appointment;
import models.AvtaleBokModel;
import models.Person;


public class AvtaleBok extends JPanel {
	
	private JButton prevWeek, nextWeek, newAppointment, addRemove;
	private AvtaleBokModel model;
	private final String[] days = {"Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "L�rdag", "S�ndag"};
	private GridBagConstraints constraints;
	private JLabel[] dateLabels = new JLabel[7];
	private AvtaleList[] appList = new AvtaleList[7];
	private JLabel ukeLabel;
	private ArrayList<Person> employees;
	
	public AvtaleBok(Person person, final MainFrame frame) {
		employees = new ArrayList<Person>();
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
			}
		});
		
		addRemove = new JButton("Legg til/ fjern ansatt");
		addRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Participants(frame, employees.get(0), AvtaleBok.this);
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
			appList[i].setModel(ObjectFactory.getEmpsApps(employees, appDate, connection));

			constraints.insets = new Insets(0, 0, 0, 0); // Padding
			constraints.gridy = 2;
			JLabel weekDay = new JLabel(days[i]);
			add(weekDay, constraints);
			constraints.gridy = 3;

			constraints.insets = new Insets(0, 0, 10, 0); // Padding
			add(dateLabels[i], constraints);
			constraints.gridy = 4;
			JPanel panel = new JPanel();
			panel.setBackground(Color.WHITE);
			panel.setBorder(new LineBorder(Color.BLACK));
			panel.setPreferredSize(new Dimension(180, 600));
			panel.setMinimumSize(new Dimension(150, 300));
			add(panel, constraints);
			panel.add(appList[i], constraints);
		}
		connection.close();
		
		constraints.gridx = 5;
		constraints.gridy = 5;
		add(newAppointment, constraints);
		
		constraints.gridx = 6;
		constraints.gridy = 5;
		
		add(addRemove, constraints);
		
		//updateAvtaleBok();
		
		for(final AvtaleList list : appList){
			list.addMouseListener(new MouseListener(){

				@Override
				public void mouseClicked(MouseEvent e) {
					new AppointmentPanel(frame, (Appointment) list.getSelectedValue());	
				}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
			
			});
		}
		
	}
	
	public void addEmployees(ArrayList<Person> elist) {
		for (Person p : elist) {
			if (! employees.contains(p)) {
				employees.add(p);
			}
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
			appList[i].setModel(ObjectFactory.getEmpsApps(employees, appDate, connection));
		}
		connection.close();
		ukeLabel.setText("Uke " + model.getWeek());
	}
}
