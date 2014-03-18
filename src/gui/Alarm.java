package gui;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JDialog;

import models.Appointment;
import models.AvtaleListModel;
import db.DBConnection;

public class Alarm extends JDialog {
	
	AvtaleListModel<Appointment> alarmAppointments = null;
	Appointment app = new Appointment(1, "testName", "01:30", "10:15", 101, Appointment.HOST, 0);
	
}

