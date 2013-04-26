package msp.object;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable{

	private int groups_size = 10;
	
	private int id;
	private int persons_id [] = new int[groups_size];
	private String groupName;
	private String info;
	
	public Group(){		
		
	}

	public void addPerson(int id){
		for(int i=0; i<groups_size; i++){
			if(this.persons_id[i] == 0){
				this.persons_id[i] = id;
				return;
			}
		}
	}
	
	public int[] getPersons(){
		return this.persons_id;
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
