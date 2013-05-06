package msp.action;

import java.util.ArrayList;
import msp.loanmanager.MainActivity;
import msp.object.Group;
import msp.object.Loan;
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
	
	public static Group findGroupById(int id){
		for(int i=0; i<MainActivity.groups.size(); i++){
			if(MainActivity.groups.get(i).getId() == id){
				return MainActivity.groups.get(i); 
			}
		}
		return null;
	}
	
	public static Loan findLoanById(int id){
		for(int i=0; i<MainActivity.loans.size(); i++){
			if(MainActivity.loans.get(i).getId() == id){
				return MainActivity.loans.get(i); 
			}
		}
		return null;
	}
	
	public static ArrayList<Group> findAllPersonsGroupsById(int id){
		ArrayList<Group> groups = new ArrayList<Group>();
		
		for(int i=0; i<MainActivity.groups.size(); i++){
			for(int j=0; j<MainActivity.groups.get(0).getPersons().length; j++){
				if(MainActivity.groups.get(i).getPersons()[j] == id){
					groups.add(MainActivity.groups.get(i));
				}
			}
		}		
		return groups;		
	}
	
	public static void deletePerson(int id){
		for(int i=0; i<MainActivity.persons.size(); i++){
			if(MainActivity.persons.get(i).getId() == id){
				MainActivity.persons.remove(i);
			}
		}
	}

	public static void deleteGroup(int id){
		for(int i=0; i<MainActivity.groups.size(); i++){
			if(MainActivity.groups.get(i).getId() == id){
				MainActivity.groups.remove(i);
			}
		}
	}
	
	public static void deleteLoan(int id){
		for(int i=0; i<MainActivity.loans.size(); i++){
			if(MainActivity.loans.get(i).getId() == id){
				MainActivity.loans.remove(i);
			}
		}
	}
	
	
}
