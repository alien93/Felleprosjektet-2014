package appointment;
import java.util.ArrayList;

import models.Person;

public class Appointment {
	private String name;
	private String date;
	private String time;
	private Room meetingRoom;
	private ArrayList<Person>participants;
	
	public String getName(){
		return name;
	}
	public String getDate(){
		return date;
	}
	public String getTime(){
		return time;
	}
	
	public Room getMeetingRoom(){
		return meetingRoom;
	}
	
	public ArrayList<Person> getParticipants(){
		return participants;
	}
	
	void setStatus(){
		
	}
	void addEmployee(){
		
	}
	void removeEmployee(){
		
	}
	
	void saveAppointment(){
		
	}
	
	void setMeetingRoom(){
		
	}

}
