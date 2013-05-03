package msp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import msp.object.Group;
import msp.object.Loan;
import msp.object.Person;

public class DataHandler {
	
	public void writeLoan(String fileName, Loan loan) {
		File file = new File(fileName);
		if (file.exists()) {
			this.removeFile(fileName);
		} 
		try {
			FileOutputStream fout = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(loan);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
	
	public ArrayList<Loan> readLoans(String filePath){
		ArrayList<Loan> loans = new ArrayList<Loan>();
		
		File dir = new File(filePath);
		String[] children = dir.list();
		if (children == null) {
		} else {
			for (int i = 0; i < children.length; i++) {
				String fileName = children[i];				
				if(fileName.startsWith("l")){
					loans.add(readLoan(filePath + fileName));
				}
			}
		}
		return loans;
	}
	
	private Loan readLoan(String file) {
		Loan loan = null;
		try {
			FileInputStream fin = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fin);
			loan = (Loan) ois.readObject();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loan;
	}
	
	public void writePerson(String fileName, Person person) {
		File file = new File(fileName);
		if (file.exists()) {	
			 this.removeFile(fileName);
		} 
		try {
			FileOutputStream fout = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(person);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}	
	
	public ArrayList<Person> readPersons(String filePath){
		ArrayList<Person> persons = new ArrayList<Person>();
		
		File dir = new File(filePath);
		String[] children = dir.list();
		if (children == null) {
			System.out.println("No files with Persons");
		} else {
			for (int i = 0; i < children.length; i++) {
				String fileName = children[i];
				if(fileName.startsWith("p")){
					persons.add(readPerson(filePath + fileName));
				}				
			}
		}
		return persons;
	}
	
	private Person readPerson(String file) {
		Person person = null;
		try {
			FileInputStream fin = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fin);
			person = (Person) ois.readObject();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return person;
	}
	
	public void writeGroup(String fileName, Group group) {
		File file = new File(fileName);
		if (file.exists()) {
			 this.removeFile(fileName);
		} 
		try {
			FileOutputStream fout = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(group);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
	
	public ArrayList<Group> readGroups(String filePath){
		ArrayList<Group> groups = new ArrayList<Group>();
		
		File dir = new File(filePath);
		String[] children = dir.list();
		if (children == null) {
		} else {
			for (int i = 0; i < children.length; i++) {
				String fileName = children[i];
				if(fileName.startsWith("g")){
					groups.add(readGroup(filePath + fileName));
				}
			}
		}
		return groups;
	}
	
	private Group readGroup(String file) {
		Group group = null;
		try {
			FileInputStream fin = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fin);
			group = (Group) ois.readObject();
			ois.close();
		} catch (Exception e) {		
			e.printStackTrace();
		}
		return group;
	}
	
	public boolean removeFile(String fileName){
		File file = new File(fileName);
		return file.delete();
	}
}
