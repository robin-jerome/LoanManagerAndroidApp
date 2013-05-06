package msp.loanmanager;

import java.util.ArrayList;

import msp.action.DataHandler;
import msp.action.Functions;
import msp.object.Loan;
import msp.object.Person;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class PersonLoansActivity extends Activity {  
	private DataHandler handler = new DataHandler();

	private int id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_loans);
		
		Bundle extras = getIntent().getExtras();        

        if (extras != null) {
            id = extras.getInt("person_id");               
        }
		
		// Fake loan
        //-=-=-=-=-=--=-=-=-=--=-=-=-=-=--=-=-=-=--=-=-=-=-=--=-=-=-=--=-=-=--=-=-=-=-
		Loan loan = new Loan();
		loan.setId(1000);
		loan.setFromPersonId(MainActivity.me_ID);
		loan.setToPersonId(1001);
		loan.setSettled(false);
		loan.setItemName("For sandwich");
		loan.setAmount(200);
		MainActivity.loans.add(loan);
		
		Loan loann = new Loan();
		loann.setId(1001);
		loann.setFromPersonId(1001);
		loann.setToPersonId(1000);
		loann.setSettled(false);
		loann.setItemName("For 5 beers");
		loann.setAmount(300);
		MainActivity.loans.add(loann);
		//-=-=-=-=-=--=-=-=-=--=-=-=-=-=--=-=-=-=--=-=-=-=-=--=-=-=-=--=-=-=--=-=-=-=-
		
		
		TableLayout tl = (TableLayout) findViewById(R.id.given_table);				
		int givenCounter = 0;
		
		for(int i=0; i<MainActivity.loans.size(); i++){
			Loan actloan = MainActivity.loans.get(i);
			
			if(((actloan.getFromPersonId() == MainActivity.me_ID && actloan.getToPersonId() == id) || (actloan.getFromPersonId() == id && actloan.getToPersonId() == MainActivity.me_ID)) && !actloan.isSettled()){
				givenCounter++;
				
				TableRow tr = new TableRow(this);
				TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		     		
				tr.setId(actloan.getId());
				
				tr.setLayoutParams(tableRowParams); 
				if(actloan.getFromPersonId() == MainActivity.me_ID){
					 tr.setBackgroundColor(Color.GREEN);
				}else{
					 tr.setBackgroundColor(Color.RED);
				}	           
				
	        	TextView name = new TextView(this);            	
	        	LayoutParams lineparams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	        	name.setLayoutParams(lineparams);
	        	
//	        	Person person = Functions.findPersonById(actloan.getToPersonId());
	        	name.setText(actloan.getItemName());	        		            	
	        	name.setTextSize(20);		            	            
	            name.setTextColor(Color.BLACK);
	            name.setGravity(Gravity.LEFT);
	            name.setPadding(5, 0, 5, 0); 
	            tr.addView(name);
				
	            TextView amount = new TextView(this);	     
	            LayoutParams amountparams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	            amount.setLayoutParams(amountparams);
	            
	            amount.setText(Float.toString(actloan.getAmount()));
	            amount.setTextColor(Color.BLACK);
	            amount.setTypeface(Typeface.DEFAULT_BOLD);	
	            amount.setGravity(Gravity.RIGHT);
	            amount.setPadding(5, 0, 5, 0); 
	            tr.addView(amount);	
	            
	            tr.setOnClickListener(new View.OnClickListener() {
		   		     @Override
		   		     public void onClick(View v) {		    	 
			   		     Intent intent = new Intent(PersonLoansActivity.this, LoanDescriptionActivity.class);			   		    
			   		     intent.putExtra("loan_id", ((TableRow)v).getId());
			   		     startActivity(intent);
		   		     }
	   		 	});
	            
	            tl.addView(tr);	 
			}		
		}		
		
		
		
		Button settle = (Button)findViewById(R.id.settle_person_button);
		
		settle.setOnClickListener(new View.OnClickListener() {
		     @Override
		     public void onClick(View v) {		    	 
		     //just toast or confirm.
		     }
		 });
		
		
		if(givenCounter==0){
			TableRow tr = new TableRow(this);
			TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
     		tr.setLayoutParams(tableRowParams);            
            tr.setBackgroundColor(Color.LTGRAY);
            
            TextView info = new TextView(this);		           
        	info.setText("You don't have any records of given loans.");
        	LayoutParams infoparams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            info.setLayoutParams(infoparams);
            info.setPadding(5, 5, 5, 5);
        	info.setTextColor(Color.BLACK);
            tr.addView(info);	
            
            tl.addView(tr);		
		}		
	}	
}
