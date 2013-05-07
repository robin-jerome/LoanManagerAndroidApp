package msp.loanmanager;

import msp.action.DataHandler;
import msp.action.Functions;
import msp.action.Util;
import msp.object.Group;
import msp.object.Loan;
import msp.object.Person;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoanDescriptionActivity extends Activity {

	private int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loan_description);
		Bundle extras = getIntent().getExtras();        

		if (extras != null) {

			id = extras.getInt("loan_id");
			
			Loan loan = Functions.findLoanById(id);

			if(null == loan) {
				Util.showToastMessage(getApplicationContext(), "Loan not present");
				Intent intent = new Intent(LoanDescriptionActivity.this, MainMenuActivity.class);
				startActivity(intent);
			
			} else {
				
				TextView toPersonName = (TextView)findViewById(R.id.to_person_name_value);
				Person toPerson = Functions.findPersonById(loan.getToPersonId());
				if(null != toPerson) {
					toPersonName.setText(toPerson.getName());
				} else {
					toPersonName.setText("");
				}
				
				TextView fromPersonName = (TextView)findViewById(R.id.from_person_name_value);
				Person fromPerson = Functions.findPersonById(loan.getFromPersonId());
				if(null != fromPerson){
					fromPersonName.setText(fromPerson.getName());
				} else {
					fromPersonName.setText("");
				}
				

				TextView toGroupName = (TextView)findViewById(R.id.to_group_name_value); 
				Group toGroup = Functions.findGroupById(loan.getToGroupId());
				if(null != toGroup){
					toGroupName.setText(toGroup.getGroupName());
				} else {
					toGroupName.setText("");
				}
				

				TextView amount = (TextView)findViewById(R.id.amount_value);
				amount.setText(String.valueOf(loan.getAmount()));

				TextView loanDate = (TextView)findViewById(R.id.loan_date_value);
				loanDate.setText(loan.getLoanDate());

				TextView dueDate = (TextView)findViewById(R.id.due_date_value);
				dueDate.setText(loan.getLoanDue());

				TextView comments = (TextView)findViewById(R.id.loan_info_value);
				comments.setText(loan.getInfo());

				Button delete = (Button)findViewById(R.id.loan_button_delete);
				Button edit = (Button)findViewById(R.id.loan_button_edit);

				delete.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {	
						DataHandler dh = new DataHandler();
						String filename = MainActivity.PATH + "l" + Integer.toString(id);
						dh.removeFile(filename);
						Functions.deleteLoan(id);
						Util.showToastMessage(getApplicationContext(), "Deleting Loan");
						Intent intent = new Intent(LoanDescriptionActivity.this, MainMenuActivity.class);
						startActivity(intent);
					}
				});

				edit.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {	
						Intent intent = new Intent(LoanDescriptionActivity.this, GiveActivity.class);
						intent.putExtra("loan_id", id);		   		  
						startActivity(intent);
					}
				});
			}
		}


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.loan_description, menu);
		return true;
	}

}
