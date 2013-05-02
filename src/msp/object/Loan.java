package msp.object;

import java.io.Serializable;

public class Loan implements Serializable{

	private int id;
	private int fromPersonId;
	private int toPersonId;
	private int toGroupId;
	private String itemName;
	private int cathegory;
	private float amount;
	private String loanDate;
	private String loanDue;
	private String info;
	private boolean settled;
	
	public Loan(){
		
	}

	public int getToPersonId() {
		return toPersonId;
	}

	public void setToPersonId(int person_id) {
		this.toPersonId = person_id;
	}

	public int getToGroupId() {
		return toGroupId;
	}

	public void setToGroupId(int group_id) {
		this.toGroupId = group_id;
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

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
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

	public boolean isSettled() {
		return settled;
	}

	public void setSettled(boolean settled) {
		this.settled = settled;
	}

	public int getFromPersonId() {
		return fromPersonId;
	}

	public void setFromPersonId(int fromPersonId) {
		this.fromPersonId = fromPersonId;
	}
	
}	
