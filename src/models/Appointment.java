package models;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import db.DBConnection;


public class Appointment {
	private int id;
	private String name;
	private String startTime;
	private String endTime;
	private int meetingRoomNr;
	private String currentUserStatus;
	private ArrayList<Person>participants;
	
	public Appointment(int id, String name, String startTime, String endTime, int meetingRoomNr){
		this(id);
		this.startTime = startTime;
		this.name = name;
		this.endTime = endTime;
		this.meetingRoomNr = meetingRoomNr;
	}
	public Appointment(int id){
		this.id = id;
	}
	
	public String getName(){
		return name;
	}
	public String getStartTimeAndDate(){
		return startTime;
	}
	public String getStartTime(){
		return startTime.substring(11);
	}
	public void setStartTime(String startTime){
		this.startTime = startTime;
	}
	public String getEndTimeAndDate(){
		return endTime;
	}
	public String getEndTime(){
		return endTime.substring(11);
	}
	public void setEndTime(String endTime){
		this.endTime = endTime;
	}
	public int getMeetingRoomNr() {
		return meetingRoomNr;
	}
	public void setMeetingRoomNr(int meetingRoomNr) {
		this.meetingRoomNr = meetingRoomNr;
	}
	public int getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<Person> getParticipants(){
		return participants;
	}
	
	public void setStatus(Person user){
		
	}
	public String getStatus(Person user){
		try {
			DBConnection connection = new DBConnection("src/db/props.properties");
			connection.init();
			ResultSet rs = connection.smallSELECT(
					"SELECT Status FROM (employeeapointmentalarm AS EAA) " +
					"WHERE AppointmentNumber = " + this.id + " AND Username = '" + user.getUsername()+"'");
			return rs.getString("Status");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getStatus(){
		return this.currentUserStatus;
	}
	void addEmployee(Person employee){
		this.participants.add(employee);
	}
	void removeEmployee(Person employee){
		this.participants.remove(employee);
	}
	
	void saveAppointment(){
		
	}
	
	void setMeetingRoom(){
		
	}

}
