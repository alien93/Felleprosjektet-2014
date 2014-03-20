package models;

import java.util.ArrayList;

public class ParticipantEntity {

	private String username;
	private ArrayList<ParticipantEntity> groupMembers;
	
	public ParticipantEntity(String username) {
		this.username = username;
		this.groupMembers = null;
	}
	
	public ParticipantEntity(String userString, ArrayList<ParticipantEntity> isGroup) {
		this.username = userString;
		this.groupMembers = isGroup;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public ArrayList<ParticipantEntity> getGroupMembers() {
		return groupMembers;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ParticipantEntity))
			return false;
		if (this.groupMembers != null)
			return username.equals(((ParticipantEntity) obj).getUsername()) && groupMembers.equals(((ParticipantEntity) obj).getGroupMembers());
		else
			return ((ParticipantEntity) obj).getGroupMembers() == null && username.equals(((ParticipantEntity) obj).getUsername());
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
