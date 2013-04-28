package msp.object;

import java.io.Serializable;

public class Person implements Serializable{

	private int id;
	private String name;
	private String info;
	
	public Person(){
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}	
}
