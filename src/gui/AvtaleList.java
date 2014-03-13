package gui;


import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;

import models.Appointment;
import renderers.AvtaleRenderer;
import db.DBConnection;
import db.ObjectFactory;

public class AvtaleList extends JList{
	private String date;
	
	public AvtaleList(){
		this.setCellRenderer(new AvtaleRenderer());
		this.setModel(new DefaultListModel<Appointment>());
	}
	
	public AvtaleList(String date, String employee){
		this(date, new String[]{employee});
	}
	public AvtaleList(String date, String[] employees){
		this.date = date;
		this.setCellRenderer(new AvtaleRenderer());
		this.setModel(new DefaultListModel<Appointment>());
		
		fetchApps(employees);
		
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public void fetchApps(String[] employees){
		//Hente avtaler fra databasen
		String employeesString = "";
		for (String employee : employees){
			employeesString += "EAA.Username = '"+employee+"' ";
			if(!employee.equals(employees[employees.length-1]))employeesString += " OR ";
		}
		DBConnection connection = null;
		ResultSet rs = null;
		try {
			connection = new DBConnection("src/db/props.properties", true);
			rs = connection.smallSELECT(	
					"SELECT AP.* FROM (appointment AS AP) NATURAL JOIN (employeeappointmentalarm AS EAA)" +
							"WHERE (DATE(AP.StartTime)  = " + "'" +this.date+"'" +
									"AND ("+employeesString+"))");
			while (rs.next()) {
				Appointment app = new Appointment(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));
				app.setStatus(ObjectFactory.getStatus(employees[0], app));
				((DefaultListModel<Appointment>) this.getModel()).addElement(app);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException();
			}
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
