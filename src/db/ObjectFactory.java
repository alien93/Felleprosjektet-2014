package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Appointment;

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
			pst = con.prepareStatement("SELECT AP.*" +
					"FROM (appointment AS AP) NATURAL JOIN (employeeappointmentalarm AS EAA)" +
					"WHERE EAA.Username = '" + employee.getUsername() + "'");
			rs = pst.executeQuery();
			
			while (rs.next()) {
				Appointment ap = new Appointment(rs.getInt("AppointmentNumber"),
												rs.getString("AppointmentName"),
												rs.getString("StartTime"),
												rs.getString("EndTime"),
												rs.getInt("RoomNumber"));
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
	
	

}
