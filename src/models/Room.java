package models;

import java.util.ArrayList;


public class Room {
	private String name;
	private int size;
	private ArrayList<Appointment> users;
	boolean checkStatus(String time) {
		return false;
	}
	public String getRoom(){
		return name;
	}
	
	public int getSize(){
		return size;
	}
	
	public ArrayList<Appointment> getUsers(){
		return users;
	}
	
	
	
}
