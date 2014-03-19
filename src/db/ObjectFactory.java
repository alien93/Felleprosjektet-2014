package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;

import models.Appointment;
import models.AvtaleListModel;
import models.Person;

public class ObjectFactory {

	public static ArrayList<Person> getAllEmployees() {
		ArrayList<Person> retList = new ArrayList<Person>();
		DBConnection con = new DBConnection("src/db/props.properties", true);
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = con.prepareStatement("SELECT username from employee");
			rs = pst.executeQuery();
			
			while (rs.next()) {
				retList.add(new Person(rs.getString(1)));
			}
			
			return retList;
			
		} catch (SQLException e) {
			throw new RuntimeException("Feil ved uthenting av ansatte!");
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pst != null)
					pst.close();
				con.close();

			} catch (SQLException e) {
				System.err.println("Kunne ikke lukke alle ressurser!");
			}
		}
	}
	
	public static void getEmployeeAppointments(Person employee) {
		ArrayList<Appointment> retList = new ArrayList<Appointment>();
		DBConnection con = new DBConnection("src/db/props.properties", true);
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = con.prepareStatement("SELECT AP.*, EAA.Status, EAA.Edited" +
					"FROM (appointment AS AP) NATURAL JOIN (employeeappointmentalarm AS EAA)" +
					"WHERE EAA.Username = '" + employee.getUsername() + "'");
			rs = pst.executeQuery();
			
			while (rs.next()) {
				Appointment ap = new Appointment(rs.getInt("AppointmentNumber"),
												rs.getString("AppointmentName"),
												rs.getString("StartTime"),
												rs.getString("EndTime"),
												rs.getInt("RoomNumber"),
												rs.getString("Status"),
												rs.getInt("Edited"));
				retList.add(ap);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Klarte ikke hente employeeAppointments!");
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pst != null)
					pst.close();
				con.close();

			} catch (SQLException e) {
				System.err.println("Kunne ikke lukke alle ressurser!");
			}
		}
	}
	
	public static String getStatus(String username , Appointment app){
		DBConnection connection = null;
		ResultSet rs = null;
		String status;
		try {
			connection = new DBConnection("src/db/props.properties", true);
			PreparedStatement pst = connection.prepareStatement(
					"SELECT Status FROM employeeappointmentalarm " +
					"WHERE AppointmentNumber = " + app.getId() + " AND Username = '" + username+"'");
			rs = pst.executeQuery();
			if (rs.next()) {
				status = rs.getString("Status");
			}
			else {
				throw new RuntimeException("Gir ikke tilbake noen status!");
			}
			return status;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Klarte ikke hente status!");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			connection.close();
		}
	}
	
	public static AvtaleListModel<Appointment> getAlarmAppointments(String username){
		AvtaleListModel<Appointment> model = new AvtaleListModel<Appointment>();
		
		DBConnection connection = null;
		ResultSet rs = null;
//		String status;
//		String text = "2011-12-30 17:10:00";
//		Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(text);
//		Date now = new Date();
//
//		if (date.after(now))
//		{
//		    // do stuff
//		}
		try {
			connection = new DBConnection("src/db/props.properties", true);
			PreparedStatement pst = connection.prepareStatement(
					"SELECT AP.AppointmentNumber, AP.AppointmentName, AP.StartTime,"
					+ "AP.EndTime, AP.RoomNumber, EAA.Status "
					+ "FROM (appointment AS AP) NATURAL JOIN (employeeappointmentalarm AS EAA) NATURAL JOIN (alarm AS A)");
//				"SELECT AP.AppointmentNumber, AP.AppointmentName, AP.StartTime, " +
//					"AP.EndTime, AP.RoomNumber" +//, EAA.Status, EAA.Edited, EAA.Username" + //, A.AlarmTime " +
//					"FROM (appointment AS AP)");//NATURAL JOIN (employeeappointmentalarm AS EAA)" + // NATURAL JOIN (alarm AS A)"); //+
				//	"WHERE (EAA.Username = " + "'" +username+"'" +")" + "");
				//		"AND (A.Seen = 0)" );
				//		"AND A.");
				rs = pst.executeQuery();
				while (rs.next()) {
					System.out.println(rs.getString(2));
//					if (java.util.Calendar.getInstance().getTime().getTime() > rs.getDate(9).getTime()){ 
//						Appointment app = new Appointment(rs.getInt(1), rs.getString(2), rs.getString(3),
//							rs.getString(4), rs.getInt(5), rs.getString(6), rs.getInt(7));
//						if (!model.contains(app)){
//							model.addElement(app);
//						}
//					}	
				}
					
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null)
						rs.close();

				} catch (SQLException e) {
						e.printStackTrace();
						throw new RuntimeException();
				}
			}
			return model;
		}
	
	public static AvtaleListModel<Appointment> getEmpsApps(ArrayList<Person> emps, String date, DBConnection connection){
		AvtaleListModel<Appointment> model = new AvtaleListModel<Appointment>();
		//Hente avtaler fra databasen
		String employeesString = "";
		for (Person employee : emps){
			employeesString += "EAA.Username = '"+employee+"' ";
			if(!employee.equals(emps.get(emps.size()-1)))employeesString += " OR ";
		}
			ResultSet rs = null;
			try {
				PreparedStatement pst = connection.prepareStatement(	
						"SELECT AP.AppointmentNumber, AP.AppointmentName, AP.StartTime, " +
								"AP.EndTime, AP.RoomNumber, EAA.Status, EAA.Edited, EAA.Username " +
								"FROM (appointment AS AP) NATURAL JOIN (employeeappointmentalarm AS EAA)" +
								"WHERE (DATE(AP.StartTime)  = " + "'" +date+"'" +
										"AND ("+employeesString+"))");
				rs = pst.executeQuery();
				while (rs.next()) {
					Appointment app = new Appointment(rs.getInt(1), rs.getString(2), rs.getString(3),
							rs.getString(4), rs.getInt(5), rs.getString(6), rs.getInt(7));
					if(rs.getString(6).equals(Appointment.HOST))app.setHost(new Person(rs.getString(8)));
					if(!rs.getString(8).equals(emps.get(0).getUsername()))app.setStatus(Appointment.GJEST);
					if (!model.contains(app)){
						model.addElement(app);
					}else if(rs.getString(8).equals(emps.get(0).getUsername())){
						model.set(model.getIndex(app), app);;
					}
						
				}
					
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null)
						rs.close();

				} catch (SQLException e) {
						e.printStackTrace();
						throw new RuntimeException();
				}
			}
			return model;
		}

}
