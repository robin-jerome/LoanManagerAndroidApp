package msp.entity;

import java.io.Serializable;

public class Loan implements Serializable{

	private int id;
	private Person person = new Person();
	private Group group = new Group();
	private String itemName;
	private int cathegory;
	private int amount;
	private String loanDate;
	private String loanDue;
	private String info;
	
	public Loan(){
		
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getCathegory() {
		return cathegory;
	}

	public void setCathegory(int cathegory) {
		this.cathegory = cathegory;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}

	public String getLoanDue() {
		return loanDue;
	}

	public void setLoanDue(String loanDue) {
		this.loanDue = loanDue;
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
