package msp.action;

import java.util.ArrayList;
import java.util.HashMap;

import msp.loanmanager.MainActivity;
import msp.object.Group;
import msp.object.Loan;
import msp.object.Person;
import msp.object.Result;

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
				return;
			}
		}
	}

	public static void deleteGroup(int id){
		for(int i=0; i<MainActivity.groups.size(); i++){
			if(MainActivity.groups.get(i).getId() == id){
				MainActivity.groups.remove(i);
				return;
			}
		}
	}
	
	public static void deleteLoan(int id){
		for(int i=0; i<MainActivity.loans.size(); i++){
			if(MainActivity.loans.get(i).getId() == id){
				MainActivity.loans.remove(i);
				return;
			}
		}
	}
	
	public static Result getSettlingTransaction(ArrayList<Loan> loans, int personId){
		Result result = new Result();
		
		float total = 0;
		for(int i=0; i<loans.size(); i++){
			if(loans.get(i).getFromPersonId() == MainActivity.me_ID){
				total += loans.get(i).getAmount();
			}else{
				total -= loans.get(i).getAmount();
			}			
		}	
		
		if(total >= 0){
			result.setFromId(personId);
			result.setToId(MainActivity.me_ID);
			result.setAmount(total/2);
		}else{
			result.setFromId(MainActivity.me_ID);
			result.setToId(personId);
			result.setAmount(Math.abs(total)/2);
		}
		
		return result;
	}
	
	public static ArrayList<Result> getSettlingTransactions(ArrayList<Loan> loans, int groupId){
		ArrayList<Result> results = new ArrayList<Result>();
		HashMap<Integer, Float> map = new HashMap<Integer, Float>();
		Group group = findGroupById(groupId);
		int members = group.getPersonsCount();
		
		int counter = 0;
		for(int i=0; i<group.getPersons().length; i++){
			if (group.getPersons()[i]!=0){
				for(int j=0; j<loans.size(); j++){
					if(group.getPersons()[i] == loans.get(j).getFromPersonId()){
						counter++;
					}
				}
				if(counter==0){
					System.out.println("Added empty hash key " + group.getPersons()[i]);
					map.put(group.getPersons()[i], (float) 0);
				}else{
					counter = 0;
				}
			}
		}
				
		float total = 0;
		for(int i=0; i<loans.size(); i++){
			total += loans.get(i).getAmount();
			if(map.get(loans.get(i).getFromPersonId()) == null){
				map.put(loans.get(i).getFromPersonId(), loans.get(i).getAmount());
			}else{
				map.put(loans.get(i).getFromPersonId(), map.get(loans.get(i).getFromPersonId()) + loans.get(i).getAmount());
			}
		}
		float average = total / members;
		System.out.println("Average is " + average);
		
		for(Integer key : map.keySet()){
			System.out.println(key + " a " + map.get(key));
		}
		
		
		for(Integer key : map.keySet()){
			System.out.println("Comparsion " + map.get(key) + " > " + average);
			if(map.get(key) > average){				
				for(Integer key2 : map.keySet()){
					System.out.println("Comparsion2 " + map.get(key2) + " < " + average);
					if(map.get(key2) < average){						
						float posible = map.get(key) - average;
						float need = average - map.get(key2);
						Result r = new Result();
						if(posible >= need){							
							r.setFromId(key2);
							r.setToId(key);
							r.setAmount(need);
							map.put(key, average + posible - need);
							map.put(key2, average);
							results.add(r);
						}else{
							r.setFromId(key2);
							r.setToId(key);
							r.setAmount(posible);
							map.put(key2, (average - need) + posible);
							map.put(key, average);
							results.add(r);
							break;
						}						
					}
				}
			}
		}
		
		for(int i=0; i<results.size(); i++){
			System.out.println("From " + results.get(i).getFromId() + " To " + results.get(i).getToId() + " Amount " + results.get(i).getAmount());
		}
		
		return results;
	}
	
}
