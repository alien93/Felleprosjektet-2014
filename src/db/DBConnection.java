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
	
	public DBConnection(String propName) {
		properties = new Properties();
		try {
			properties.load(new FileInputStream(new File(propName)));
		} catch (IOException e) {
			throw new RuntimeException("Could not load properties-file!");
		}
		url = properties.getProperty("url");
	}
	
	public void init() {
		try {
			conn = DriverManager.getConnection(url, properties.getProperty("user"), properties.getProperty("password"));
		} catch (SQLException e) {
			throw new RuntimeException("Klarte ikke åpne kobling til databasen!");
		}
	}
	
	/*
	 * Bruk PreparedStatements når du skal gjøre større updates/selections
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
	 */
	
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return conn.prepareStatement(sql);
	}
	
	/*
	 * Bruk smallSELECT når du skal SELECT få rader
	 * Ikke bruk semikolon
	 */
	
	public ResultSet smallSELECT(String sql) {
		Statement st;
		try {
			st = conn.createStatement();
			return st.executeQuery(sql);
		} catch (SQLException e) {
			throw new RuntimeException("Klarte ikke utføre SELECT-query!");
		}
	}
	
	/*
	 * bruk smallUPDATEorINSERT når du skal oppdatere få rader
	 * Ikke bruk semikolon
	 */
	
	public void smallUPDATEorINSERT(String sql) {
		Statement st;
		try {
			st = conn.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			throw new RuntimeException("Klarte ikke utføre UPDATE/INSERT-query!");
		}
		
	}

	
	public void close() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("Klarte ikke lukke kobling til databasen!");
		}
	}
	

}
