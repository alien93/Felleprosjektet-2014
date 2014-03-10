package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ObjectFactory {

	public static void getAllEmployees() {
		DBConnection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = new DBConnection("src/db/props.properties");
			con.init();
			pst = con.prepareStatement("SELECT username from employee");
			rs = pst.executeQuery();
			
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}
		} catch (SQLException e) {
			throw new RuntimeException("Feil ved uthenting av ansatte!");
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pst != null)
					pst.close();
				if (con != null)
					con.close();
				
			} catch (SQLException e) {
				System.err.println("Could not close all resources!");
			}
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		getAllEmployees();

	}

}
