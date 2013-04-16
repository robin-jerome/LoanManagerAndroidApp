package msp.Entity;

import java.util.ArrayList;

public class Group {

	private ArrayList<Person> persons = new ArrayList<Person>();
	private String groupName;
	
	public Group(){
		
	}

	public ArrayList<Person> getPersons() {
		return persons;
	}

	public void setPersons(ArrayList<Person> persons) {
		this.persons = persons;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	
}
