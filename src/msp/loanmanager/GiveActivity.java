package msp.loanmanager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import msp.action.CustomOnItemSelectedListener;
import msp.action.DataHandler;
import msp.action.Functions;
import msp.action.Util;
import msp.object.Group;
import msp.object.Loan;
import msp.object.Person;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;


public class GiveActivity extends Activity {

	private DataHandler dataHandler = new DataHandler();
	
	private static boolean isNewLoan = true;
	
	private static Loan editLoan = null;
	
	private static int currentLoanId;
	
	private static final String TAG = "GiveActivity"; 
	
	private static final String NO_SELECTION = "No Selection";
	
	private String loansFileName = MainActivity.PATH + "l"; 
	
	private Spinner toPersonNameSpinner;
	
	private Spinner fromPersonNameSpinner;
	
	private Spinner toGroupNameSpinner;
	
	private static boolean loanDateSetInProgress = false;
	
	private static boolean dueDateSetInProgress = false;
	
	DateFormat formatDateTime = DateFormat.getDateTimeInstance();
	
	private static Calendar dueDateTime = null;
	
	private static Calendar loanDateTime = null;
	
//	Calendar dateTime = Calendar.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_give);
		
		Bundle extras = getIntent().getExtras();        

		if (extras != null) {
			currentLoanId = extras.getInt("loan_id");  
			
			if(currentLoanId > 0) {
				// This is an already existing loan
				isNewLoan = false;
				Util.showToastMessage(getApplicationContext(), "Opening Loan with Id:"+currentLoanId);

				if(null != Functions.findLoanById(currentLoanId)){
					editLoan = Functions.findLoanById(currentLoanId);
				}

				if(editLoan == null) {
					Util.showToastMessage(getApplicationContext(), "Loan with Id:"+currentLoanId+" no present");
				} else {
					EditText info = (EditText)findViewById(R.id.add_loan_info);
					info.setText(editLoan.getInfo());	

					EditText amount = (EditText)findViewById(R.id.add_amount);
					amount.setText(String.valueOf(editLoan.getAmount()));

					Button dueDateButton = (Button)findViewById(R.id.due_date_picker);
					dueDateButton.setText(editLoan.getLoanDue());
					
					try {
						Date dueDate = new SimpleDateFormat("dd/MM/yyyy").parse(editLoan.getLoanDue());
						dueDateTime = Calendar.getInstance();
						dueDateTime.setTime(dueDate);
						
					} catch (ParseException e) {
						// Do nothing
					}

					Button loanDateButton = (Button)findViewById(R.id.loan_date_picker);
					loanDateButton.setText(editLoan.getLoanDate());
					try {
						Date loanDate = new SimpleDateFormat("dd/MM/yyyy").parse(editLoan.getLoanDate());
						loanDateTime = Calendar.getInstance();
						loanDateTime.setTime(loanDate);
					} catch (ParseException e) {
						// Do nothing
					}
				}

			} else {
				// This is a new loan
				resetToNewLoan();
				Util.showToastMessage(getApplicationContext(), "Creating new Loan");
			}
		} else {
			resetToNewLoan();
			Util.showToastMessage(getApplicationContext(), "Creating new Loan");
		}
		
		// Adding person names & group names on Spinner
		addFromPersonsOnSpinner();
		addToPersonsOnSpinner();		
		addGroupsOnSpinner();
		  
		Button add = (Button)findViewById(R.id.add_loan_confirm);
		add.setOnClickListener(new View.OnClickListener() {
		     @Override
		     public void onClick(View v) {
		    	 try {
		    	 
		    	 Loan loan = new Loan();
		    	 
		    	 if(!isNewLoan) {
		    		 // loading values from previously selected loan
		    		 loan = editLoan;
		    		 currentLoanId = editLoan.getId();
		    		 // Set value of loanDate and loanDueDate
		    		
		    	 } else {
		    		 // Its a new Loan
		    		 
		    		 if (MainActivity.loans.size() == 0){
			    		 currentLoanId = 1000;
			    	 } else{
			    		 currentLoanId = MainActivity.loans.get(MainActivity.loans.size()-1).getId() + 1;		    		
			    	 }
		    		 loan.setId(currentLoanId);
		    	 }
		    	 
		    	 String fromPersonName = String.valueOf(fromPersonNameSpinner.getSelectedItem());
		    	 String toPersonName = String.valueOf(toPersonNameSpinner.getSelectedItem());
		    	 String toGroupName = String.valueOf(toGroupNameSpinner.getSelectedItem());		    
		    	 
		    	 int toPersonId = -1; 
		    	 int toGroupId = -1; 
		    	 int fromPersonId = -1;
		    		 
		    	 if(!toPersonName.equals(NO_SELECTION)){
		    		 toPersonId = Util.getPersonIdFromName(toPersonName);
		    	 }
		    	 
		    	 if(!fromPersonName.equals(NO_SELECTION)){
		    		 fromPersonId = Util.getPersonIdFromName(fromPersonName);
		    	 }
		    	 
		    	 if(!toGroupName.equals(NO_SELECTION)){
		    		 toGroupId = Util.getGroupIdFromName(toGroupName);
		    	 }
		    	 
		    	 EditText amount = (EditText)findViewById(R.id.add_amount);
		    	 
		    	 EditText loanInfo = (EditText)findViewById(R.id.add_loan_info);
		    	 
		    	 loan.setToPersonId(toPersonId);
		    	 loan.setFromPersonId(fromPersonId); 
		    	 loan.setToGroupId(toGroupId);
		    	 
		    	 if(null != loanDateTime){
		    		 loan.setLoanDate(calendarAsString(loanDateTime)); 
		    	 } else {
		    		 loan.setLoanDate("");
		    	 }
		    	 
		    	 if(null != dueDateTime){
		    		 loan.setLoanDue(calendarAsString(dueDateTime)); 
		    	 } else {
		    		 loan.setLoanDue("");
		    	 }
		    	 
		    	 loan.setAmount(Float.valueOf(amount.getText().toString()));
		    	 loan.setSettled(false);
		    	 
		    	 if(null != loanInfo){
		    		 loan.setInfo(loanInfo.getText().toString());
		    	 } else {
		    		 loan.setInfo(" ");
		    	 }
		    	 
		    	 Log.i(TAG, "Path is: " + loansFileName + Integer.toString(currentLoanId));
		    	 
		    	 if(isNewLoan){
		    		 // Add new loan
		    		 MainActivity.loans.add(loan);
		    		 dataHandler.writeLoan(loansFileName + Integer.toString(currentLoanId), loan);
			    	 Log.i(TAG, "New Loan Written to file");
			    	 Util.showToastMessage(getApplicationContext(),"Loan Added Successfully Id:"+currentLoanId);
			    	 resetToNewLoan();
			    	 Intent intent = new Intent(GiveActivity.this, MainMenuActivity.class);	
				     startActivity(intent);
		    	 } else {
		    		 // Edit existing loan
		    		 for (int i = 0; i < MainActivity.loans.size(); i++) {
		    			 if(MainActivity.loans.get(i).getId() == currentLoanId){
		    				 Log.i(TAG, "Found existing Loan");
		    				 MainActivity.loans.set(i, editLoan);
		    				 Log.i(TAG, "Loans list updated Successfully");
		    				 dataHandler.removeFile(loansFileName + Integer.toString(currentLoanId));
		    				 Log.i(TAG, "Loan file deleted Successfully");
		    				 dataHandler.writeLoan(loansFileName + Integer.toString(currentLoanId), loan);
		    				 Log.i(TAG, "New Loan Updated in file");
		    				 resetToNewLoan();
		    				 Util.showToastMessage(getApplicationContext(),"Loan Updated Successfully");
		    				 Intent intent = new Intent(GiveActivity.this, MainMenuActivity.class);	
		    				 startActivity(intent);
		    				 break;
		    			 }
		    		 }

		    	 }
		    	 } catch (Exception e) {
		    		 Util.showToastMessage(getApplicationContext(),"Something went wrong !");
		    		 Intent intent = new Intent(GiveActivity.this, MainMenuActivity.class);	
		    		 startActivity(intent);
		    	 }
		     }
			
		 });
		
//		ImageButton cancel = (ImageButton)findViewById(R.id.add_loan_cancel);
//		cancel.setOnClickListener(new View.OnClickListener() {
//		     @Override
//		     public void onClick(View v) {
//		    	 isNewLoan = true;
//		    	 editLoan = null;
//		    	 currentLoanId = 0;
//		    	 Intent intent = new Intent(GiveActivity.this, MainMenuActivity.class);	
//				 startActivity(intent);
//		     }
//		 });

	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.give, menu);
        return true;
	}
	
	public void chooseLoanDate(View v){
		Calendar dateTime = Calendar.getInstance();
		loanDateSetInProgress=true;
    	new DatePickerDialog(GiveActivity.this, d, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show();
    	
    }
	
	public void chooseDueDate(View v){
		Calendar dateTime = Calendar.getInstance();
		dueDateSetInProgress=true;
    	new DatePickerDialog(GiveActivity.this, d, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show();
    	
    }
	
	private void addToPersonsOnSpinner() {
		toPersonNameSpinner = (Spinner) findViewById(R.id.to_person_name_spinner);
		ArrayList<Person> personList = MainActivity.persons;
		List<String> list = new ArrayList<String>();
		list.add(NO_SELECTION);
		for(Person person: personList){
			list.add(person.getName());
		}
			
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		toPersonNameSpinner.setAdapter(dataAdapter);
		
		if((null != editLoan) && (editLoan.getToPersonId() !=-1)){
			toPersonNameSpinner.setSelection(list.indexOf(Functions.findPersonById(editLoan.getToPersonId()).getName()));
		} else {
			toPersonNameSpinner.setSelection(list.indexOf(NO_SELECTION));
		}
		
		toPersonNameSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}
	
	private void addFromPersonsOnSpinner() {
		fromPersonNameSpinner = (Spinner) findViewById(R.id.from_person_name_spinner);
		ArrayList<Person> personList = MainActivity.persons;
		List<String> list = new ArrayList<String>();
		list.add(NO_SELECTION);
		for(Person person: personList){
			list.add(person.getName());
		}
			
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		fromPersonNameSpinner.setAdapter(dataAdapter);
		
		if((null != editLoan) && (editLoan.getFromPersonId() !=-1)){
			fromPersonNameSpinner.setSelection(list.indexOf(Functions.findPersonById(editLoan.getFromPersonId()).getName()));
		} else {
			fromPersonNameSpinner.setSelection(list.indexOf(NO_SELECTION));
		}
		
		fromPersonNameSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}
	
	private void addGroupsOnSpinner() {
		toGroupNameSpinner = (Spinner) findViewById(R.id.to_group_name_spinner);
		ArrayList<Group> groupList = MainActivity.groups;
		List<String> list = new ArrayList<String>();
		list.add(NO_SELECTION);
		for(Group group: groupList){
			list.add(group.getGroupName());
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		toGroupNameSpinner.setAdapter(dataAdapter);	
		
		if((null != editLoan) && (editLoan.getToGroupId()!=-1)){
			toGroupNameSpinner.setSelection(list.indexOf(Functions.findGroupById(editLoan.getToGroupId()).getGroupName()));
		} else {
			toGroupNameSpinner.setSelection(list.indexOf(NO_SELECTION));
		}
		
		toGroupNameSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}
	
	DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
			Calendar dateTime = Calendar.getInstance();
			dateTime.set(Calendar.YEAR,year);
			dateTime.set(Calendar.MONTH, monthOfYear);
			dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			if(loanDateSetInProgress){
				loanDateTime = dateTime;
				Button loanDateButton = (Button)findViewById(R.id.loan_date_picker);
				loanDateButton.setText(calendarAsString(loanDateTime));
				Util.showToastMessage(getApplicationContext(),"Loan date set");
				loanDateSetInProgress = false;
				
			} else if(dueDateSetInProgress){
				dueDateTime = dateTime;
				Button dueDateButton = (Button)findViewById(R.id.due_date_picker);
				dueDateButton.setText(calendarAsString(dueDateTime));
				Util.showToastMessage(getApplicationContext(),"Due date set");
				dueDateSetInProgress = false;
			}
		}
	};
	
	
	protected String calendarAsString(Calendar dateTime) {
		String calString = "";
		if(null!=dateTime){
			calString = new SimpleDateFormat("dd/MM/yyyy").format(dateTime.getTime());
		}
		return calString;
	}
	
	private void resetToNewLoan() {
		isNewLoan = true;
    	editLoan = null;
    	currentLoanId = 0;
    	dueDateTime = null;
		loanDateTime = null;
		
	}
	
	@Override
	protected void onPause() {					//	Function called before switching over the activity
		// TODO Auto-generated method stub
		super.onPause();
		finish();							// We are trying to kill the old activity here ...
	}
	
}
