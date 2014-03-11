package kalender;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import db.DBConnection;

public class AvtaleList extends JList{
	private String date;

	public AvtaleList(String date){
		this.date = date;
		//this.setCellRenderer(new AvtaleRenderer());
		//this.setModel(new DefaultListModel());
		
		//Hente avtaler fra databasen
		try {
			DBConnection connection = new DBConnection("src/db/props.properties");
			connection.init();
			ResultSet rs = connection.smallSELECT(	
					"SELECT * from appointment " +
					"WHERE DATE(StartTime)  = " + "'" +this.date+ "'");
			System.out.println("Connected");
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}
			rs.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		AvtaleList liste = new AvtaleList("2014-03-10");
		
	}

}
