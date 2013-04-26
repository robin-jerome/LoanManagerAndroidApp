package msp.object;

import java.io.Serializable;

public class Loan implements Serializable{

	private int id;
	private int person_id;
	private int group_id;
	private String itemName;
	private int cathegory;
	private int amount;
	private String loanDate;
	private String loanDue;
	private String info;
	
	public Loan(){
		
	}

	public int getPerson_id() {
		return person_id;
	}

	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}

	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
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
