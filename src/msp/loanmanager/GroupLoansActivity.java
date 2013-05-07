package msp.loanmanager;

import java.util.ArrayList;

import msp.action.DataHandler;
import msp.action.Functions;
import msp.action.Util;
import msp.object.Group;
import msp.object.Loan;
import msp.object.Person;
import msp.object.Result;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GroupLoansActivity extends Activity {

	private int id;
	private Context context;
	private ArrayList<Loan> iloans = new ArrayList<Loan>();
	private DataHandler handler = new DataHandler();
	private String fileName = MainActivity.PATH + "l";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_loans);
		context = this;

		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			id = extras.getInt("group_id");
			Group group = Functions.findGroupById(id);
			TextView tv = (TextView)findViewById(R.id.bar_add_title);
			tv.setText(group.getGroupName());
		}

		TableLayout tl = (TableLayout) findViewById(R.id.given_table);
		int givenCounter = 0;

		for (int i = 0; i < MainActivity.loans.size(); i++) {
			Loan actloan = MainActivity.loans.get(i);
						
			if (actloan.getToGroupId() == id && !actloan.isSettled()) {
				iloans.add(actloan);
				givenCounter++;

				TableRow tr = new TableRow(this);
				TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
						new LayoutParams(LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT));
				tableRowParams.setMargins(2, 2, 2, 2);
				
				tr.setLayoutParams(tableRowParams);
				tr.setId(actloan.getId());
				if (actloan.getFromPersonId() == MainActivity.me_ID) {
					tr.setBackgroundColor(Color.rgb(152, 251, 152));
				} else {
					tr.setBackgroundColor(Color.rgb(250, 128, 114));
				}

				TextView name = new TextView(this);
				LayoutParams nameparams = new TableRow.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				name.setLayoutParams(nameparams);

				Person person = Functions.findPersonById(actloan
						.getFromPersonId());
				name.setText(person.getName());
				name.setTextSize(20);
				name.setTextColor(Color.BLACK);
				name.setGravity(Gravity.LEFT);
				name.setPadding(20, 20, 20, 20);
				tr.addView(name);

//				TextView item = new TextView(this);
//				LayoutParams itemparams = new TableRow.LayoutParams(
//						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//				item.setLayoutParams(itemparams);
//
//				item.setText(actloan.getInfo());
//				item.setTextSize(20);
//				item.setTextColor(Color.BLACK);
//				item.setGravity(Gravity.LEFT);
//				item.setPadding(20, 20, 20, 20);
//				tr.addView(item);

				TextView amount = new TextView(this);
				LayoutParams amountparams = new TableRow.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				amount.setLayoutParams(amountparams);

				amount.setText(Float.toString(actloan.getAmount()));
				amount.setTextColor(Color.BLACK);
				amount.setTypeface(Typeface.DEFAULT_BOLD);
				amount.setGravity(Gravity.RIGHT);
				amount.setTextSize(20);
				amount.setPadding(20, 20, 20, 20);
				tr.addView(amount);

	            tr.setOnClickListener(new View.OnClickListener() {
		   		     @Override
		   		     public void onClick(View v) {		    	 
			   		     Intent intent = new Intent(GroupLoansActivity.this, LoanDescriptionActivity.class);			   		    
			   		     intent.putExtra("loan_id", ((TableRow)v).getId());
			   		     startActivity(intent);
		   		     }
	   		 	});
				
				
				tl.addView(tr);
			}
		}

		Button settle = (Button) findViewById(R.id.settle_person_button);

		settle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				LayoutInflater factory = LayoutInflater.from(context);
				View dialoglayout = factory.inflate(R.layout.dialog_layout, null);
						
				ArrayList<Result> results = Functions.getSettlingTransactions(iloans, id);
				TableLayout tl = (TableLayout) dialoglayout.findViewById(R.id.dialog_table);

				for(int i=0; i<results.size(); i++){
					TableRow tr = new TableRow(context);
//					TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//		     		tr.setLayoutParams(tableRowParams);   
		     		
					TextView name1 = new TextView(context);
//					LayoutParams infoparams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//					name1.setLayoutParams(infoparams);
					Person p1 = Functions.findPersonById(results.get(i).getFromId());
					name1.setText(p1.getName());
					tr.addView(name1);

					TextView name2 = new TextView(context);
					Person p2 = Functions.findPersonById(results.get(i).getToId());
					name2.setText(p2.getName());
					tr.addView(name2);
					
					TextView amount = new TextView(context);					
					amount.setText(Float.toString(results.get(i).getAmount()));
					tr.addView(amount);				
					
					tl.addView(tr);
				}				
				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

				alertDialogBuilder.setTitle("Transactions for settling");
				alertDialogBuilder
						.setView(dialoglayout)
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,	int id) {										
										for(int i=0; i<iloans.size(); i++){
											Functions.deleteLoan(iloans.get(i).getId());
											iloans.get(i).setSettled(true);
											MainActivity.loans.add(iloans.get(i));
											handler.writeLoan(fileName + Integer.toString(iloans.get(i).getId()) , iloans.get(i));
										}
										GroupLoansActivity.this.finish();
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,	int id) {										
										dialog.cancel();
									}
								});

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();

			}
		});

		if (givenCounter == 0) {
			TableRow tr = new TableRow(this);
			TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
					new LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
			tr.setLayoutParams(tableRowParams);
			tr.setBackgroundColor(Color.LTGRAY);

			TextView info = new TextView(this);
			info.setText("You don't have any records of given loans.");
			LayoutParams infoparams = new TableRow.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			info.setLayoutParams(infoparams);
			info.setPadding(5, 5, 5, 5);
			info.setTextColor(Color.BLACK);
			tr.addView(info);

			tl.addView(tr);
			
			Button s = (Button) findViewById(R.id.settle_person_button);
			s.setEnabled(false);
		}

	}

}
