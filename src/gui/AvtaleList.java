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

import renderers.AvtaleRenderer;

import db.DBConnection;

public class AvtaleList extends JList{
	private String date;

	public AvtaleList(String date){
		this.date = date;
		this.setCellRenderer(new AvtaleRenderer());
		this.setModel(new DefaultListModel<Appointment>());
		
		//Hente avtaler fra databasen
		try {
			DBConnection connection = new DBConnection("src/db/props.properties");
			connection.init();
			ResultSet rs = connection.smallSELECT(	
					"SELECT * from appointment " +
					"WHERE DATE(StartTime)  = " + "'" +this.date+ "'");
			System.out.println("Connected");
			while (rs.next()) {
				((DefaultListModel<Appointment>) this.getModel()).addElement(new Appointment(
						rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)
						));
				
			}
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame("testlist");
		AvtaleList liste = new AvtaleList("2014-03-10");
		frame.setContentPane(liste);
		frame.setVisible(true);
		frame.pack();
		
	}

}
