package msp.loanmanager;

import java.util.ArrayList;

import msp.action.DataHandler;
import msp.action.Functions;
import msp.object.Loan;
import msp.object.Person;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class GivenActivity extends Activity {  
	private DataHandler handler = new DataHandler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_given);
		
		// Fake loan
		Loan loan = new Loan();
		loan.setId(1000);
		loan.setFromPersonId(MainActivity.me_ID);
		loan.setToPersonId(1001);
		loan.setAmount(200);
		MainActivity.loans.add(loan);
		  
		  
		 
		
		
		
		
		TableLayout tl = (TableLayout) findViewById(R.id.given_table);				
		int givenCounter = 0;
		
		for(int i=0; i<MainActivity.loans.size(); i++){
			if(MainActivity.loans.get(i).getFromPersonId() == MainActivity.me_ID && MainActivity.loans.get(i).getToGroupId() == 0){
				givenCounter++;
				
				TableRow tr = new TableRow(this);
				TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		     			         	
				tr.setLayoutParams(tableRowParams);            
	            tr.setBackgroundColor(Color.GREEN);
				
	        	TextView name = new TextView(this);            	
	        	LayoutParams lineparams = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	        	name.setLayoutParams(lineparams);
	        	
	        	Person person = Functions.findPersonById(MainActivity.loans.get(i).getToPersonId());
	        	name.setText(person.getName());	        		            	
	        	name.setTextSize(16);		            	            
	            name.setTextColor(Color.BLACK);
	            name.setGravity(Gravity.LEFT);
	            name.setPadding(5, 0, 5, 0); 
	            tr.addView(name);
				
	            TextView amount = new TextView(this);	     
	            LayoutParams amountparams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	            amount.setLayoutParams(amountparams);
	            
	            amount.setText(Float.toString(MainActivity.loans.get(i).getAmount()));
	            amount.setTextColor(Color.BLACK);
	            name.setTypeface(Typeface.DEFAULT_BOLD);	
	            amount.setGravity(Gravity.RIGHT);
	            amount.setPadding(5, 0, 5, 0); 
	            tr.addView(amount);	
	            
	            tl.addView(tr);	 
			}		
		}

		
		
		
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
