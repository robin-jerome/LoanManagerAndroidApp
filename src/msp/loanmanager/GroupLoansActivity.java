package msp.loanmanager;

import msp.action.Functions;
import msp.object.Loan;
import msp.object.Person;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_loans);
		context = this;

		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			id = extras.getInt("group_id");
		}

		// Fake loan
		// -=-=-=-=-=--=-=-=-=--=-=-=-=-=--=-=-=-=--=-=-=-=-=--=-=-=-=--=-=-=--=-=-=-=-
		Loan loan = new Loan();
		loan.setId(1000);
		loan.setFromPersonId(MainActivity.me_ID);
		loan.setToGroupId(1000);
		loan.setSettled(false);
		loan.setItemName("For sandwich");
		loan.setAmount(200);
		MainActivity.loans.add(loan);

		Loan loann = new Loan();
		loann.setId(1001);
		loann.setFromPersonId(1001);
		loann.setToGroupId(1000);
		loann.setSettled(false);
		loann.setItemName("For 5 beers");
		loann.setAmount(300);
		MainActivity.loans.add(loann);

		Person personn = new Person();
		personn.setId(1001);
		personn.setName("Roger");
		MainActivity.persons.add(personn);
		// -=-=-=-=-=--=-=-=-=--=-=-=-=-=--=-=-=-=--=-=-=-=-=--=-=-=-=--=-=-=--=-=-=-=-

		TableLayout tl = (TableLayout) findViewById(R.id.given_table);
		int givenCounter = 0;

		for (int i = 0; i < MainActivity.loans.size(); i++) {
			Loan actloan = MainActivity.loans.get(i);

			if (actloan.getToGroupId() == id) {
				givenCounter++;

				TableRow tr = new TableRow(this);
				TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
						new LayoutParams(LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT));

				tr.setLayoutParams(tableRowParams);
				tr.setId(actloan.getId());
				if (actloan.getFromPersonId() == MainActivity.me_ID) {
					tr.setBackgroundColor(Color.GREEN);
				} else {
					tr.setBackgroundColor(Color.RED);
				}

				TextView name = new TextView(this);
				LayoutParams nameparams = new TableRow.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				name.setLayoutParams(nameparams);

				Person person = Functions.findPersonById(actloan
						.getFromPersonId());
				name.setText(person.getName());
				name.setTextSize(20);
				name.setTextColor(Color.BLACK);
				name.setGravity(Gravity.LEFT);
				name.setPadding(5, 0, 5, 0);
				tr.addView(name);

				TextView item = new TextView(this);
				LayoutParams itemparams = new TableRow.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				item.setLayoutParams(itemparams);

				item.setText(actloan.getItemName());
				item.setTextSize(20);
				item.setTextColor(Color.BLACK);
				item.setGravity(Gravity.LEFT);
				item.setPadding(5, 0, 5, 0);
				tr.addView(item);

				TextView amount = new TextView(this);
				LayoutParams amountparams = new TableRow.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
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
				View dialoglayout = factory.inflate(R.layout.dialog_layout,
						null);

				TableLayout tl = (TableLayout) findViewById(R.id.dialog_table);

				TableRow tr = new TableRow(context);
				TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	     		tr.setLayoutParams(tableRowParams);   
	     		
				TextView name1 = new TextView(context);
				LayoutParams infoparams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				name1.setLayoutParams(infoparams);
				name1.setText("Pokus");
				tr.addView(name1);

				tl.addView(tr);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

				alertDialogBuilder.setTitle("Do you want to settle all loans");
				alertDialogBuilder
						.setView(dialoglayout)
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, close
										// current activity
										GroupLoansActivity.this.finish();
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, just close
										// the dialog box and do nothing
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
