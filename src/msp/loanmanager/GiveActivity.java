package msp.loanmanager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import msp.action.CustomOnItemSelectedListener;
import msp.action.DataHandler;
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
	
	private static final String TAG = "GiveActivity"; 
	
	private String loansFileName = MainActivity.PATH + "lg"; // lg - loans given
	
	private Spinner toPersonNameSpinner;
	
	private Spinner fromPersonNameSpinner;
	
	private Spinner toGroupNameSpinner;
	
	private boolean loanDateSetInProgress = false;
	
	private boolean dueDateSetInProgress = false;
	
	DateFormat formatDateTime = DateFormat.getDateTimeInstance();
	
	private Calendar dueDateTime = Calendar.getInstance();
	
	private Calendar loanDateTime = Calendar.getInstance();
	
	Calendar dateTime = Calendar.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_give);
		// Adding person names on Spinner
		addToPersonsOnSpinner();
		addFromPersonsOnSpinner();
		addGroupsOnSpinner();
		  
		ImageButton add = (ImageButton)findViewById(R.id.add_loan_confirm);
		add.setOnClickListener(new View.OnClickListener() {
		     @Override
		     public void onClick(View v) {
		    	 Loan loan = new Loan();
		    	 int loanId;
		    	 if (MainActivity.loans.size() == 0){
		    		 loanId = 1000;
		    	 }else{
		    		 loanId = MainActivity.loans.get(MainActivity.loans.size()-1).getId() + 1;		    		
		    	 }
		    	 String toPersonName = String.valueOf(toPersonNameSpinner.getSelectedItem());
		    	 String toGroupName = String.valueOf(toGroupNameSpinner.getSelectedItem());
		    	 String fromPersonName = String.valueOf(fromPersonNameSpinner.getSelectedItem());
		    	 
		    	 int toPersonId = Util.getPersonIdFromName(toPersonName);
		    	 int groupId = Util.getGroupIdFromName(toGroupName);
		    	 int fromPersonId = Util.getPersonIdFromName(fromPersonName);
		    	 
		    	 EditText amount = (EditText)findViewById(R.id.add_amount);
		    	 
		    	 loan.setId(loanId);
		    	 loan.setFromPersonId(fromPersonId); // Since Action is give - Its always from the user of the App
		    	 loan.setToPersonId(toPersonId);
		    	 loan.setToGroupId(groupId);
		    	 loan.setLoanDate(loanDateTime.toString());
		    	 loan.setLoanDue(dueDateTime.toString());
		    	 loan.setAmount(Integer.valueOf(amount.getText().toString()));
		    	 loan.setSettled(false);
		    	 Log.i(TAG, "Path is: " + loansFileName + Integer.toString(loanId));
		    	 MainActivity.loans.add(loan);	    	
		    	 dataHandler.writeLoan(loansFileName + Integer.toString(loanId), loan);
		    	 Log.i(TAG, "Loan Written to file");
		    	 Util.showToastMessage(getApplicationContext(),"Loan Saved Successfully");
		    	 Intent intent = new Intent(GiveActivity.this, MainActivity.class);	
			     startActivity(intent);
		     }
		 });

	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.give, menu);
        return true;
	}
	
	public void chooseLoanDate(View v){
		loanDateSetInProgress=true;
    	new DatePickerDialog(GiveActivity.this, d, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }
	
	public void chooseDueDate(View v){
		dueDateSetInProgress=true;
    	new DatePickerDialog(GiveActivity.this, d, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }
	
	private void addToPersonsOnSpinner() {
		toPersonNameSpinner = (Spinner) findViewById(R.id.to_person_name_spinner);
		ArrayList<Person> personList = MainActivity.persons;
		List<String> list = new ArrayList<String>();
		for(Person person: personList){
			list.add(person.getName());
		}
			
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		toPersonNameSpinner.setAdapter(dataAdapter);
		toPersonNameSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}
	
	private void addFromPersonsOnSpinner() {
		fromPersonNameSpinner = (Spinner) findViewById(R.id.from_person_name_spinner);
		ArrayList<Person> personList = MainActivity.persons;
		List<String> list = new ArrayList<String>();
		for(Person person: personList){
			list.add(person.getName());
		}
			
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		fromPersonNameSpinner.setAdapter(dataAdapter);
		fromPersonNameSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}
	
	private void addGroupsOnSpinner() {
		toGroupNameSpinner = (Spinner) findViewById(R.id.to_group_name_spinner);
		ArrayList<Group> groupList = MainActivity.groups;
		List<String> list = new ArrayList<String>();
		for(Group group: groupList){
			list.add(group.getGroupName());
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		toGroupNameSpinner.setAdapter(dataAdapter);	
		toGroupNameSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}
	
	DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
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
				 ;
				Util.showToastMessage(getApplicationContext(),"Due date set");
				dueDateSetInProgress = false;
			}
		}
	};

	protected String calendarAsString(Calendar dueDateTime2) {
		return new SimpleDateFormat("MM/dd/yyyy").format(dueDateTime2.getTime());
	}
	
	

	
	
}
