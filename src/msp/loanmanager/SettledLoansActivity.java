package msp.loanmanager;

import java.util.ArrayList;

import msp.action.Functions;
import msp.object.Loan;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SettledLoansActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settled_loans);
		ArrayList<Loan> orderedSettledLoans = getOrderedSettledLoans();
		TableLayout tl = (TableLayout) findViewById(R.id.settled_loans_table);	
		
		for(int i=0; i < orderedSettledLoans.size(); i++){
			Loan actloan =orderedSettledLoans.get(i);
			
			TableRow tr = new TableRow(this);
			TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

			tr.setId(actloan.getId());

			tr.setLayoutParams(tableRowParams); 
			
			TextView name = new TextView(this);            	
			LayoutParams lineparams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			name.setLayoutParams(lineparams);
			
			String loanDesc = "";
			if(actloan.getFromPersonId()!= -1 && null != Functions.findPersonById(actloan.getFromPersonId())){
				loanDesc = "From: " + Functions.findPersonById(actloan.getFromPersonId()).getName() + " (P) ";
			}
			
			if(actloan.getToPersonId()!= -1 && null != Functions.findPersonById(actloan.getToPersonId())){
				loanDesc += " -> To: " + Functions.findPersonById(actloan.getToPersonId()).getName() + " (P) ";
			}
			
			if(actloan.getToGroupId()!= -1 && null != Functions.findGroupById(actloan.getToGroupId())){
				loanDesc += " -> To: " + Functions.findGroupById(actloan.getToGroupId()).getGroupName() + " (G) ";
			}
			
			name.setText(loanDesc);	        		            	
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
					Intent intent = new Intent(SettledLoansActivity.this, LoanDescriptionActivity.class);			   		    
					intent.putExtra("loan_id", ((TableRow)v).getId());
					startActivity(intent);
				}
			});

			tl.addView(tr);	 

		}	
	}

	private ArrayList<Loan> getOrderedSettledLoans() {
		// TODO Auto-generated method stub
		return MainActivity.loans;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settled_loans, menu);
		return true;
	}

}
