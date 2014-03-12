package models;

public class Person {

	private String username;
	
	public Person(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public boolean equals(Object obj) {
		return username.equals(((Person) obj).getUsername());
	}

}
