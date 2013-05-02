package msp.action;

import msp.loanmanager.MainActivity;
import msp.object.Person;

public class Functions {
	
	public static Person findPersonById(int id){
		for(int i=0; i<MainActivity.persons.size(); i++){
			if(MainActivity.persons.get(i).getId() == id){
				return MainActivity.persons.get(i); 
			}
		}
		return null;
	}
	
	
}
