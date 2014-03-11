package gui;

import java.awt.Component;
import java.awt.Container;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;

import models.Appointment;
import models.Person;

import renderers.AvtaleRenderer;

import db.DBConnection;
import db.ObjectFactory;

public class AvtaleList extends JList{
	private String date;
	
	public AvtaleList(String date, String employee){
		this(date, new String[]{employee});
	}
	public AvtaleList(String date, String[] employees){
		this.date = date;
		this.setCellRenderer(new AvtaleRenderer());
		this.setModel(new DefaultListModel<Appointment>());
		
		//Hente avtaler fra databasen
		String employeesString = "";
		for (String employee : employees){
			employeesString += "EAA.Username = '"+employee+"' ";
			if(!employee.equals(employees[employees.length-1]))employeesString += " OR ";
		}
		
		try {
			DBConnection connection = new DBConnection("src/db/props.properties");
			connection.init();
			ResultSet rs = connection.smallSELECT(	
					"SELECT AP.* FROM (appointment AS AP) NATURAL JOIN (employeeappointmentalarm AS EAA)" +
					"WHERE (DATE(AP.StartTime)  = " + "'" +this.date+"'" +
							"AND ("+employeesString+"))");
			while (rs.next()) {
				Appointment app = new Appointment(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));
				app.setStatus(ObjectFactory.getStatus(employees[0], app));
				((DefaultListModel<Appointment>) this.getModel()).addElement(app);
				
			}
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame("testlist");
		AvtaleList liste = new AvtaleList("2014-03-10", "Anders");
		frame.setContentPane(liste);
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
