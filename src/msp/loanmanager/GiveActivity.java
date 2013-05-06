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
	
	private Loan eLoan;
	
	private int loanId;
	
	private static final String TAG = "GiveActivity"; 
	
	private static final String NO_SELECTION = "No Selection";
	
	private String loansFileName = MainActivity.PATH + "l"; 
	
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
		
		Bundle extras = getIntent().getExtras();        

        if (extras != null) {
        	loanId = extras.getInt("loan_id");     
        	Util.showToastMessage(getApplicationContext(), "Opening Loan with Id:"+loanId);
            eLoan = Functions.findLoanById(loanId);
            EditText info = (EditText)findViewById(R.id.add_loan_info);
            info.setText(eLoan.getInfo());	
            
            EditText amount = (EditText)findViewById(R.id.add_amount);
            amount.setText(String.valueOf(eLoan.getAmount()));
            
            Button dueDateButton = (Button)findViewById(R.id.due_date_picker);
			dueDateButton.setText(eLoan.getLoanDue());
			try {
				Date dueDate = new SimpleDateFormat("MM/dd/yyyy").parse(eLoan.getLoanDue());
				dueDateTime.set(dueDate.getYear(), dueDate.getMonth(), dueDate.getDay());
			} catch (ParseException e) {
				// Do nothing
			}
            
			Button loanDateButton = (Button)findViewById(R.id.loan_date_picker);
			loanDateButton.setText(eLoan.getLoanDate());
			try {
				Date loanDate = new SimpleDateFormat("MM/dd/yyyy").parse(eLoan.getLoanDate());
				loanDateTime.set(loanDate.getYear(), loanDate.getMonth(), loanDate.getDay());
			} catch (ParseException e) {
				// Do nothing
			}
            
         }
		
		// Adding person names on Spinner
		addToPersonsOnSpinner();
		addFromPersonsOnSpinner();
		addGroupsOnSpinner();
		  
		ImageButton add = (ImageButton)findViewById(R.id.add_loan_confirm);
		add.setOnClickListener(new View.OnClickListener() {
		     @Override
		     public void onClick(View v) {
		    	 Loan loan = new Loan();
		    	 
		    	 if (MainActivity.loans.size() == 0){
		    		 loanId = 1000;
		    	 }else{
		    		 loanId = MainActivity.loans.get(MainActivity.loans.size()-1).getId() + 1;		    		
		    	 }
		    	 String toPersonName = String.valueOf(toPersonNameSpinner.getSelectedItem());
		    	 String toGroupName = String.valueOf(toGroupNameSpinner.getSelectedItem());
		    	 String fromPersonName = String.valueOf(fromPersonNameSpinner.getSelectedItem());
		    	 
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
		    	 
		    	 loan.setId(loanId);
		    	 loan.setToPersonId(toPersonId);
		    	 loan.setFromPersonId(fromPersonId); // Since Action is give - Its always from the user of the App
		    	 loan.setToGroupId(toGroupId);
		    	 loan.setLoanDate(calendarAsString(loanDateTime));
		    	 loan.setLoanDue(calendarAsString(dueDateTime));
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
		list.add(NO_SELECTION);
		for(Person person: personList){
			list.add(person.getName());
		}
			
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		toPersonNameSpinner.setAdapter(dataAdapter);
		
		if((null != eLoan) && (eLoan.getToPersonId() !=-1)){
			toPersonNameSpinner.setSelection(list.indexOf(Functions.findPersonById(eLoan.getToPersonId()).getName()));
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
		
		if((null != eLoan) && (eLoan.getFromPersonId() !=-1)){
			fromPersonNameSpinner.setSelection(list.indexOf(Functions.findPersonById(eLoan.getFromPersonId()).getName()));
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
		
		if((null != eLoan) && (eLoan.getToGroupId()!=-1)){
			toGroupNameSpinner.setSelection(list.indexOf(Functions.findGroupById(eLoan.getToGroupId()).getGroupName()));
		} else {
			toGroupNameSpinner.setSelection(list.indexOf(NO_SELECTION));
		}
		
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
				Util.showToastMessage(getApplicationContext(),"Due date set");
				dueDateSetInProgress = false;
			}
		}
	};

	protected String calendarAsString(Calendar dueDateTime2) {
		return new SimpleDateFormat("MM/dd/yyyy").format(dueDateTime2.getTime());
	}
	
	

	
	
}
