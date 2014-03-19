package models;

import java.util.ArrayList;

public class Person {

	private String username;
	private ArrayList<Person> groupMembers;
	
	public Person(String username) {
		this.username = username;
		this.groupMembers = null;
	}
	
	public Person(String userString, ArrayList<Person> isGroup) {
		this.username = userString;
		this.groupMembers = isGroup;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public ArrayList<Person> getGroupMembers() {
		return groupMembers;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Person))
			return false;
		if (this.groupMembers != null)
			return username.equals(((Person) obj).getUsername()) && groupMembers.equals(((Person) obj).getGroupMembers());
		else
			return ((Person) obj).getGroupMembers() == null && username.equals(((Person) obj).getUsername());
	}
	
	public String toString(){
		if (groupMembers != null)
			return this.username;
		else
			return this.username;
	}
	
	public String getGMembersString() {
		String initial = "Medlemmer: ";
		for (int i = 0; i < groupMembers.size() - 1; i++) {
			initial += groupMembers.get(i).getUsername() + ", ";
		}
		initial += groupMembers.get(groupMembers.size()-1);
		return initial;
	}
}
