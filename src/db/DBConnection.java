package db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	
	public DBConnection(String propName) throws IOException, FileNotFoundException {
		properties = new Properties();
		properties.load(new FileInputStream(new File(propName)));
		url = properties.getProperty("url");
	}
	
	public void init() throws SQLException {
		conn = DriverManager.getConnection(url, properties.getProperty("user"), properties.getProperty("password"));
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
	
	public ResultSet smallSELECT(String sql) throws SQLException {
		Statement st = conn.createStatement();
		return st.executeQuery(sql);
	}
	
	/*
	 * bruk smallUPDATEorINSERT når du skal oppdatere få rader
	 * Ikke bruk semikolon
	 */
	
	public void smallUPDATEorINSERT(String sql) throws SQLException {
		Statement st = conn.createStatement();
		st.executeUpdate(sql);
	}
	
	public void close() throws SQLException {
		conn.close();
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DBConnection connection = new DBConnection("src/db/props.properties");
			connection.init();
			ResultSet rs = connection.smallSELECT("SELECT * from ansatt");
			while (rs.next()) {
				System.out.println(rs.getString(1) + ": " + rs.getString(2));
			}
			rs.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
