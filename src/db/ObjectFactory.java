package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ObjectFactory {

	public static void getAllEmployees() {
		DBConnection con = new DBConnection("src/db/props.properties");
		PreparedStatement pst = null;
		ResultSet rs = null;
		con.init();
		
		try {
			pst = con.prepareStatement("SELECT username from employee");
			rs = pst.executeQuery();
			
			while (rs.next()) {
				//TODO returner array med alle employees
			}
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
	
//	public static void getEmployeeAppointments(Person employee) {
//		DBConnection con = new DBConnection("src/db/props.properties");
//		PreparedStatement pst;
//		ResultSet rs;
//		con.init();
//		
//		try {
//			pst = con.prepareStatement("SELECT AP.*" +
//					"FROM (appointment AS AP) NATURAL JOIN (employeeappointmentalarm AS EAA)" +
//					"WHERE EAA.Username = '" + /*employee.getUsername()*/ + "'");
//			rs = pst.executeQuery();
//			
//			while (rs.next()) {
//				// TODO Do stuff
//			}
//		} 
//	}
//	


}
