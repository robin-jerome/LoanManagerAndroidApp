package msp.loanmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class UpcomingLoansActivity extends Activity {
	
	private static final int UPCOMING_LOANS_DURATION = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upcoming_loans);
		ArrayList<Loan> orderedUpcomingLoans = getOrderedUpcomingLoans();
		TableLayout tl = (TableLayout) findViewById(R.id.upcoming_loans_table);	
		
		String customText = orderedUpcomingLoans.size()+" loans due in "+UPCOMING_LOANS_DURATION+" days!";
		if(orderedUpcomingLoans.size()==1){
			customText = orderedUpcomingLoans.size()+" loan due in "+UPCOMING_LOANS_DURATION+" days!";
		}
		
		TextView titleView = (TextView)findViewById(R.id.bar_add_title_upcoming_loans);
		titleView.setText(customText);	        		            	
		titleView.setTextSize(20);
		
		for(int i=0; i < orderedUpcomingLoans.size(); i++){
			Loan actloan =orderedUpcomingLoans.get(i);
			
			TableRow tr = new TableRow(this);
			TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

			tr.setId(actloan.getId());

			tr.setLayoutParams(tableRowParams); 
			
			TextView name = new TextView(this);            	
			LayoutParams lineparams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			name.setLayoutParams(lineparams);
			
			String loanDesc = "";
			if(actloan.getFromPersonId()!= -1 && null != Functions.findPersonById(actloan.getFromPersonId())){
				loanDesc = Functions.findPersonById(actloan.getFromPersonId()).getName() + " (P) ";
			}
			
			if(actloan.getToPersonId()!= -1 && null != Functions.findPersonById(actloan.getToPersonId())){
				loanDesc += " =>" + Functions.findPersonById(actloan.getToPersonId()).getName() + " (P) ";
			} else if(actloan.getToGroupId()!= -1 && null != Functions.findGroupById(actloan.getToGroupId())){
				loanDesc += " =>" + Functions.findGroupById(actloan.getToGroupId()).getGroupName() + " (G) ";
			}
			
			name.setText(loanDesc);	        		            	
			name.setTextSize(15);		            	            
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
					Intent intent = new Intent(UpcomingLoansActivity.this, LoanDescriptionActivity.class);			   		    
					intent.putExtra("loan_id", ((TableRow)v).getId());
					startActivity(intent);
				}
			});

			tl.addView(tr);	 

		}	
	}

	private ArrayList<Loan> getOrderedUpcomingLoans() {
		ArrayList<Loan> upComingLoans = new ArrayList<Loan>();
		for(Loan loan : MainActivity.loans){
			if(!loan.isSettled()){
				if(null != loan.getLoanDue() && !"".equals(loan.getLoanDue().trim())){
					Date dueDate;
					try {
						dueDate = new SimpleDateFormat("dd/MM/yyyy").parse(loan.getLoanDue());
					} catch (ParseException e) {
						continue;
					}
					
					
					Calendar currentCal = Calendar.getInstance();
					
					Calendar dueDateTime = Calendar.getInstance();
					dueDateTime.setTime(dueDate);
					
					if(currentCal.get(Calendar.MONTH) == dueDateTime.get(Calendar.MONTH) && currentCal.get(Calendar.YEAR) == dueDateTime.get(Calendar.YEAR)){
						if(Math.abs(currentCal.get(Calendar.DATE) - dueDateTime.get(Calendar.DATE)) <= UPCOMING_LOANS_DURATION){
							upComingLoans.add(loan);
						}
					}
				}
			}
		}
		return upComingLoans;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.upcoming_loans, menu);
		return true;
	}

}
