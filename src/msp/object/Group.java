package msp.object;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable{

	private int id;
	private ArrayList<Person> persons = new ArrayList<Person>();
	private String groupName;
	private String info;
	
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

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
