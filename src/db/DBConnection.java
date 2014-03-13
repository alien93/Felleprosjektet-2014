package db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBConnection {

	
	private Properties properties;
	private String url;
	private Connection conn;
	
	public DBConnection(String propName, boolean autoCommit) {
		properties = new Properties();
		try {
			properties.load(new FileInputStream(new File(propName)));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not load properties-file!");
		}
		url = properties.getProperty("url");
		try {
			conn = DriverManager.getConnection(url, properties.getProperty("user"), properties.getProperty("password"));
			conn.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			close();
			e.printStackTrace();
			throw new RuntimeException("Klarte ikke åpne kobling til databasen!");
		}
	}
	
	/*
	 * Bruk PreparedStatements n���r du skal gj���re st���rre updates/selections
	 * 
	 * Bruk:
	 * 
	 * 	DBConnection db = new DBConnection();
	 * 
	 * SELECT:
	 * 
	 *  pst = db.prepareStatement("SELECT * from Ansatt");
	 *  ResultSet rs = pst.executeQuery();
	 *  
	 * INSERT
	 *  
	 *  pst = db.prepareStatement("INSERT INTO Ansatt(BrukerNavn, Passord) VALUES(?,?)");
	 *  pst.setString(1, "ALien");
	 *  pst.setString(2, "mittPassord");
	 *  pst.executeUpdate();
	 *  
	 * UPDATE
	 * 	
	 * 	Samme som ved insert, bare med: "UPDATE Ansatt SET Passord = ? WHERE BrukerNavn = ?"
	 * 
	 * DELETE
	 *
	 * 	Samme som ved insert, bare med: "DELETE FROM employeeappointmentalarm WHERE Username = ?"
	 * 	
	 * 	VIKTIG ��� HUSKE WHERE !!
	 */
	
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return conn.prepareStatement(sql);
	}
	
	/*
	 * Bruk smallSELECT n���r du skal SELECT f��� rader
	 * Ikke bruk semikolon
	 */
	
	public ResultSet smallSELECT(String sql) {
		Statement st;
		try {
			st = conn.createStatement();
			return st.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			close();
			throw new RuntimeException();
		}
	}
	
	/*
	 * bruk smallUPDATEorINSERT n���r du skal oppdatere f��� rader
	 * Ikke bruk semikolon
	 */
	
	public void smallUPDATEorINSERT(String sql) {
		Statement st;
		try {
			st = conn.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void commit() {
		try {
			conn.commit();
		} catch (SQLException e) {
			if (conn != null)
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			e.printStackTrace();
		}
	}

	
	public void close() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Klarte ikke lukke kobling til databasen!");
		}
	}
	

}
